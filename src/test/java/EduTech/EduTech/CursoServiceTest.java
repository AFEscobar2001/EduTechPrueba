package EduTech.EduTech;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import EduTech.EduTech.model.Curso;
import EduTech.EduTech.model.Perfil;
import EduTech.EduTech.model.Usuario;
import EduTech.EduTech.repository.CursoRepository;
import EduTech.EduTech.repository.UsuarioRepository;
import EduTech.EduTech.service.CursoService;

@ExtendWith(MockitoExtension.class)
public class CursoServiceTest {

    @Mock
    private CursoRepository cursoRepository;

    @Mock 
    UsuarioRepository usuarioRepository;

    @InjectMocks
    private CursoService cursoService;

    @Test
    void almacenarCursoNuevo() {
        Curso curso = new Curso();
        curso.setNombre("Programación");
        curso.setDescripcion("Python, Java");


        String resultado = cursoService.guardar(curso);

        assertEquals("El curso Programación ya existe.", resultado);
    }

    @Test
    void listar() {

        Curso c1 = new Curso();
        c1.setNombre("Programación");
        c1.setDescripcion("Python");

        Curso c2 = new Curso();
        c2.setNombre("Programación 2");
        c2.setDescripcion("Java");

        List<Curso> lista = new ArrayList<>();
        lista.add(c1);
        lista.add(c2);

        when(cursoRepository.findAll()).thenReturn(lista);
        
        List<Curso> resultado = cursoService.listar();

        assertEquals(2, resultado.size());
        assertEquals("Programación", resultado.get(0).getNombre());

    }

    @Test
    void eliminar() {
        Integer idCurso = 1;
        String correoAdmin = "admin@correo.com";

        Curso curso = new Curso();
        curso.setId(idCurso);
        curso.setNombre("Programación");
        curso.setInstructores(new ArrayList<>());
        curso.setUsuarios(new ArrayList<>());

        Usuario usuario = new Usuario();
        usuario.setCorreo(correoAdmin);

        Perfil perfilAdmin = new Perfil();
        perfilAdmin.setNombre("Administrador");

        usuario.setPerfiles(List.of(perfilAdmin));

        when(cursoRepository.findById(idCurso)).thenReturn(Optional.of(curso));
        when(usuarioRepository.findByCorreo(correoAdmin)).thenReturn(usuario);

        String resultado = cursoService.eliminar(idCurso, correoAdmin);

        assertEquals("Curso eliminado correctamente.", resultado);
        verify(cursoRepository).saveAndFlush(curso);
        verify(cursoRepository).delete(curso);
    }

    @Test
    void asignar() {
        String correoUsuario = "usuario@correo.com";
        Integer idCurso = 1;

        Usuario usuario = new Usuario();
        usuario.setCorreo(correoUsuario);
        usuario.setCursos(new ArrayList<>());

        Curso curso = new Curso();
        curso.setId(idCurso);
        curso.setNombre("Java Avanzado");

        when(usuarioRepository.findById(correoUsuario)).thenReturn(Optional.of(usuario));
        when(cursoRepository.findById(idCurso)).thenReturn(Optional.of(curso));

        String resultado = cursoService.asignarCursoUsuario(correoUsuario, idCurso);

        assertEquals("Curso 'Java Avanzado' asignado correctamente al usuario '" + correoUsuario, resultado);
        assertTrue(usuario.getCursos().contains(curso));
        verify(usuarioRepository).save(usuario);

        }

}
