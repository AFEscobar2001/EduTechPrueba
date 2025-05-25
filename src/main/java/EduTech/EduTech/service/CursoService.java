package EduTech.EduTech.service;

import EduTech.EduTech.dto.CursoDTO;
import EduTech.EduTech.model.Curso;
import EduTech.EduTech.model.Instructor;
import EduTech.EduTech.model.Usuario;
import EduTech.EduTech.repository.CursoRepository;
import EduTech.EduTech.repository.InstructorRepository;
import EduTech.EduTech.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired 
    private UsuarioRepository usuarioRepository;

    @Autowired
    private InstructorRepository instructorRepository;

    public String guardar(Curso curso) {
        if (curso.getNombre() == null || curso.getNombre().isBlank()) {
            return "El nombre del curso es obligatorio.";
        }

        if (curso.getDescripcion() == null || curso.getDescripcion().isBlank()) {
            return "La descripción del curso es obligatoria.";
        }

        Curso validacion = cursoRepository.findByNombre(curso.getNombre());

        if (validacion == null) {
            cursoRepository.save(curso);
            return "Curso " + curso.getNombre() + " creado correctamente.";
        } else {
            return "El curso " + curso.getNombre() + " ya existe.";
        }
    }

    public List<Curso> listar() {
        return cursoRepository.findAll();
    }

    public List<CursoDTO> listarDTO() {
        return cursoRepository.findAll()
                              .stream()
                              .map(CursoDTO::new)
                              .toList();
    }

    public Curso buscarPorId(Integer id) {
        return cursoRepository.findById(id).orElse(null);
    }

    public String actualizar(Curso curso) {
        Curso existente = cursoRepository.findById(curso.getId()).orElse(null);

        if (existente == null) {
            return "Curso no encontrado.";
        }

        existente.setNombre(curso.getNombre());
        existente.setDescripcion(curso.getDescripcion());

        cursoRepository.save(existente);
        return "Curso actualizado correctamente.";
    }

    public String eliminar(Integer id) {
        Curso curso = cursoRepository.findById(id).orElse(null);

        if (curso == null) {
            return "Curso no encontrado.";
        }

        if ((curso.getInstructores() != null && !curso.getInstructores().isEmpty())) {
            return "No se puede eliminar el curso: está asignado a instructores.";
        }

        cursoRepository.deleteById(id);
        return "Curso eliminado correctamente.";
    }

    public String asignarCursoUsuario(String correoUsuario, Integer idCurso) {
        Optional<Usuario> optUsuario = usuarioRepository.findById(correoUsuario);
        Optional<Curso> optCurso = cursoRepository.findById(idCurso);

        if (optUsuario.isEmpty()) {
            return "Usuario con correo '" + correoUsuario + "' no encontrado.";
        }

        if (optCurso.isEmpty()) {
            return "Curso con ID " + idCurso + " no encontrado.";
        }

        Usuario usuario = optUsuario.get();
        Curso curso = optCurso.get();

        if (usuario.getCursos() == null) {
            usuario.setCursos(new ArrayList<>());
        }

        if (usuario.getCursos().contains(curso)) {
            return "El curso ya está asignado al usuario.";
        }

        usuario.getCursos().add(curso);
        usuarioRepository.save(usuario);

        return "Curso '" + curso.getNombre() + "' asignado correctamente al usuario '" + correoUsuario;
    }

    public String asignarCursoInstructor(String rutInstructor, Integer idCurso) {
        Optional<Instructor> optInstructor = instructorRepository.findById(rutInstructor);
        Optional<Curso> optCurso = cursoRepository.findById(idCurso);

        if (optInstructor.isEmpty()) {
            return "Instructor con RUT '" + rutInstructor + "' no encontrado.";
        }

        if (optCurso.isEmpty()) {
            return "Curso con ID " + idCurso + " no encontrado.";
        }

        Instructor instructor = optInstructor.get();
        Curso curso = optCurso.get();

        if (instructor.getCursos() == null) {
            instructor.setCursos(new ArrayList<>());
        }

        if (instructor.getCursos().contains(curso)) {
            return "El curso ya está asignado al instructor.";
        }

        instructor.getCursos().add(curso);
        instructorRepository.save(instructor);

        return "Curso '" + curso.getNombre() + "' asignado correctamente al instructor '" + rutInstructor + "'.";
    }


}
