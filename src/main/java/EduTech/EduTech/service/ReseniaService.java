package EduTech.EduTech.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import EduTech.EduTech.model.Resenia;
import EduTech.EduTech.repository.CursoRepository;
import EduTech.EduTech.repository.ReseniaRepository;

@Service
public class ReseniaService {
    @Autowired
    private ReseniaRepository reseniaRepository;

    @Autowired
    private CursoRepository cursoRepository;

    public String guardar(Resenia resenia) {
        if (resenia.getComentario() == null || resenia.getComentario().isBlank()) {
            return "El comentario es obligatorio.";
        }

        if (resenia.getPuntuacion() < 1 || resenia.getPuntuacion() > 10) {
            return "La puntuación debe estar entre 1 y 10";
        }

        if (resenia.getCurso() == null || !cursoRepository.existsById(resenia.getCurso().getId())) {
            return "Debe asociar la reseña a un curso válido.";
        }

        reseniaRepository.save(resenia);
        return "Reseña registrada correctamente.";
    }

    public List<Resenia> listar() {
        return reseniaRepository.findAll();
    }


}
