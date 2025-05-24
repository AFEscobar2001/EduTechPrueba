package EduTech.EduTech.service;

import EduTech.EduTech.model.Contenido;
import EduTech.EduTech.repository.ContenidoRepository;
import EduTech.EduTech.repository.CursoRepository;
import EduTech.EduTech.repository.ProveedorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContenidoService {

    @Autowired
    private ContenidoRepository contenidoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private ProveedorRepository proveedorRepository;

    public String guardar(Contenido contenido) {

        if (contenido.getCurso() == null ||
            !cursoRepository.existsById(contenido.getCurso().getId())) {
            return "Curso no válido o inexistente.";
        }

        if (contenido.getProveedor() != null &&
            !proveedorRepository.existsById(contenido.getProveedor().getId())) {
            return "Proveedor no válido o inexistente.";
        }

        contenidoRepository.save(contenido);
        return "Contenido guardado correctamente.";
    }
}
