package EduTech.EduTech;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
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
import EduTech.EduTech.model.Instructor;
import EduTech.EduTech.model.Perfil;
import EduTech.EduTech.model.Usuario;
import EduTech.EduTech.repository.CursoRepository;
import EduTech.EduTech.repository.InstructorRepository;
import EduTech.EduTech.repository.UsuarioRepository;
import EduTech.EduTech.service.CursoService;

//Realizado por: Felipe Escobar

@ExtendWith(MockitoExtension.class)
public class CursoServiceTest {

    @Mock
    private CursoRepository cursoRepository;

    @Mock 
    UsuarioRepository usuarioRepository;

    @Mock
    InstructorRepository instructorRepository;

    @InjectMocks
    private CursoService cursoService;

    @Test
    void listarCurso() {
        Curso c1 = new Curso();
        c1.setNombre("Programación");
        c1.setPrecio(25000.0);

        Curso c2 = new Curso();
        c2.setNombre("Programación 2");
        c2.setPrecio(30000.0);

        List<Curso> lista = new ArrayList<>();
        lista.add(c1);
        lista.add(c2);

        when(cursoRepository.findAll()).thenReturn(lista);
        
        List<Curso> resultado = cursoService.listar();

        assertEquals(2, resultado.size());
        assertEquals("Programación", resultado.get(0).getNombre());
        assertEquals(25000.0, resultado.get(0).getPrecio());
        assertEquals("Programación 2", resultado.get(1).getNombre());
        assertEquals(30000.0, resultado.get(1).getPrecio());
    }

    @Test
    void eliminarCurso() {
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
    void asignarCurso() {
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

    //Usuarios
    @Test
    void asignarCursoUsuario_exitoso() {
        String correoUsuario = "usuario@correo.com";
        Integer idCurso = 1;

        Usuario usuario = new Usuario();
        usuario.setCorreo(correoUsuario);
        usuario.setCursos(new ArrayList<>());

        Curso curso = new Curso();
        curso.setId(idCurso);
        curso.setNombre("Java Básico");

        when(usuarioRepository.findById(correoUsuario)).thenReturn(Optional.of(usuario));
        when(cursoRepository.findById(idCurso)).thenReturn(Optional.of(curso));

        String resultado = cursoService.asignarCursoUsuario(correoUsuario, idCurso);

        assertEquals("Curso 'Java Básico' asignado correctamente al usuario '" + correoUsuario, resultado);
        assertTrue(usuario.getCursos().contains(curso));
        verify(usuarioRepository).save(usuario);
    }

    @Test
    void asignarCursoUsuario_usuarioNoEncontrado() {
        String correoUsuario = "desconocido@correo.com";
        Integer idCurso = 1;

        when(usuarioRepository.findById(correoUsuario)).thenReturn(Optional.empty());

        String resultado = cursoService.asignarCursoUsuario(correoUsuario, idCurso);

        assertEquals("Usuario con correo '" + correoUsuario + "' no encontrado.", resultado);
    }

    @Test
    void asignarCursoUsuario_cursoNoEncontrado() {
        String correoUsuario = "usuario@correo.com";
        Integer idCurso = 99;

        Usuario usuario = new Usuario();
        usuario.setCorreo(correoUsuario);

        when(usuarioRepository.findById(correoUsuario)).thenReturn(Optional.of(usuario));
        when(cursoRepository.findById(idCurso)).thenReturn(Optional.empty());

        String resultado = cursoService.asignarCursoUsuario(correoUsuario, idCurso);

        assertEquals("Curso con ID " + idCurso + " no encontrado.", resultado);
    }

    @Test
    void asignarCursoUsuario_yaAsignado() {
        String correoUsuario = "usuario@correo.com";
        Integer idCurso = 1;

        Curso curso = new Curso();
        curso.setId(idCurso);
        curso.setNombre("Java Básico");

        Usuario usuario = new Usuario();
        usuario.setCorreo(correoUsuario);
        usuario.setCursos(new ArrayList<>(List.of(curso)));

        when(usuarioRepository.findById(correoUsuario)).thenReturn(Optional.of(usuario));
        when(cursoRepository.findById(idCurso)).thenReturn(Optional.of(curso));

        String resultado = cursoService.asignarCursoUsuario(correoUsuario, idCurso);

        assertEquals("El curso ya está asignado al usuario.", resultado);
        verify(usuarioRepository, never()).save(any());
    }

    //Instructores
    @Test
    void asignarCursoInstructor_exitoso() {
        String rutInstructor = "12345678-9";
        Integer idCurso = 1;

        Instructor instructor = new Instructor();
        instructor.setRut(rutInstructor);
        instructor.setCursos(new ArrayList<>());

        Curso curso = new Curso();
        curso.setId(idCurso);
        curso.setNombre("Spring Boot");

        when(instructorRepository.findById(rutInstructor)).thenReturn(Optional.of(instructor));
        when(cursoRepository.findById(idCurso)).thenReturn(Optional.of(curso));

        String resultado = cursoService.asignarCursoInstructor(rutInstructor, idCurso);

        assertEquals("Curso 'Spring Boot' asignado correctamente al instructor '" + rutInstructor + "'.", resultado);
        assertTrue(instructor.getCursos().contains(curso));
        verify(instructorRepository).save(instructor);
    }

    @Test
    void asignarCursoInstructor_instructorNoEncontrado() {
        String rutInstructor = "99999999-9";
        Integer idCurso = 1;

        when(instructorRepository.findById(rutInstructor)).thenReturn(Optional.empty());

        String resultado = cursoService.asignarCursoInstructor(rutInstructor, idCurso);

        assertEquals("Instructor con RUT '" + rutInstructor + "' no encontrado.", resultado);
        verify(instructorRepository, never()).save(any());
    }

    @Test
    void asignarCursoInstructor_cursoNoEncontrado() {
        String rutInstructor = "12345678-9";
        Integer idCurso = 99;

        Instructor instructor = new Instructor();
        instructor.setRut(rutInstructor);

        when(instructorRepository.findById(rutInstructor)).thenReturn(Optional.of(instructor));
        when(cursoRepository.findById(idCurso)).thenReturn(Optional.empty());

        String resultado = cursoService.asignarCursoInstructor(rutInstructor, idCurso);

        assertEquals("Curso con ID " + idCurso + " no encontrado.", resultado);
        verify(instructorRepository, never()).save(any());
    }

    @Test
    void asignarCursoInstructor_yaAsignado() {
        String rutInstructor = "12345678-9";
        Integer idCurso = 1;

        Curso curso = new Curso();
        curso.setId(idCurso);
        curso.setNombre("Spring Boot");

        Instructor instructor = new Instructor();
        instructor.setRut(rutInstructor);
        instructor.setCursos(new ArrayList<>(List.of(curso)));

        when(instructorRepository.findById(rutInstructor)).thenReturn(Optional.of(instructor));
        when(cursoRepository.findById(idCurso)).thenReturn(Optional.of(curso));

        String resultado = cursoService.asignarCursoInstructor(rutInstructor, idCurso);

        assertEquals("El curso ya está asignado al instructor.", resultado);
        verify(instructorRepository, never()).save(any());
    }

}
