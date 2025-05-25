package EduTech.EduTech.service;

import EduTech.EduTech.dto.CursoDTO;
import EduTech.EduTech.model.Curso;
import EduTech.EduTech.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

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
}
