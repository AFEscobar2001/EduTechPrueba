package EduTech.EduTech.service;

import EduTech.EduTech.dto.InstructorDTO;
import EduTech.EduTech.model.Instructor;
import EduTech.EduTech.repository.InstructorRepository;
import EduTech.EduTech.repository.PersonaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstructorService {

    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired PersonaRepository personaRepository;

    public String guardar(Instructor instructor) {
    try {
        // Validaciones previas...
        if (!personaRepository.existsById(instructor.getRut())) {
            return "Debe existir una persona con el RUT proporcionado.";
        }

        Instructor validacion = instructorRepository.findById(instructor.getRut()).orElse(null);

        if (validacion == null) {
            instructorRepository.save(instructor);
            return "Instructor registrado correctamente.";
        } else {
            return "Instructor ya registrado.";
        }

    } catch (Exception e) {
        return "Error interno: " + e.getMessage();
    }
}

    public Instructor buscarPorRut(String rut) {
        return instructorRepository.findById(rut).orElse(null);
    }


    public List<InstructorDTO> listarDTO() {
    return instructorRepository.findAll()
                               .stream()
                               .map(InstructorDTO::new)
                               .toList();
}

    public void eliminar(String rut) {
        instructorRepository.deleteById(rut);
    }
}

