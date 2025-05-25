package EduTech.EduTech.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import EduTech.EduTech.model.Evaluacion;
import EduTech.EduTech.repository.ContenidoRepository;
import EduTech.EduTech.repository.EvaluacionRepository;

@Service
public class EvaluacionService {
    @Autowired
    private EvaluacionRepository evaluacionRepository;

    @Autowired
    private ContenidoRepository contenidoRepository;

    public String guardar(Evaluacion evaluacion) {
        if (evaluacion.getNota() < 1.0 || evaluacion.getNota() > 7.0) {
            return "La nota debe estar entre 1.0 y 7.0.";
        }

        if (evaluacion.getContenido() == null || !contenidoRepository.existsById(evaluacion.getContenido().getId())) {
            return "Debe asociar la evaluación a un contenido válido.";
        }

        evaluacionRepository.save(evaluacion);
        return "Evaluación registrada correctamente.";
    }

    public List<Evaluacion> listar() {
        return evaluacionRepository.findAll();
}
}
