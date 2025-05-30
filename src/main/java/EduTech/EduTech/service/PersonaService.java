package EduTech.EduTech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import EduTech.EduTech.dto.PersonaDTO;
import EduTech.EduTech.model.Persona;
import EduTech.EduTech.model.Usuario;
import EduTech.EduTech.repository.PersonaRepository;
import EduTech.EduTech.repository.UsuarioRepository;

import java.util.List;

//Realizado por: Alison Aranda

@Service
public class PersonaService {

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public String guardar(PersonaDTO dto) {
        if (dto.getCorreo() == null || dto.getCorreo().isBlank()) {
            return "El correo del usuario es obligatorio.";
        }

        if (dto.getRut() == null || dto.getRut().isBlank()) {
            return "El RUT es obligatorio.";
        }

        Usuario usuario = usuarioRepository.findById(dto.getCorreo()).orElse(null);
        if (usuario == null) {
            return "No se puede registrar la persona. Primero debes crear un usuario con el correo proporcionado.";
        }

        if (personaRepository.existsById(dto.getRut())) {
            return "La persona con RUT " + dto.getRut() + " ya está registrada.";
        }

        Persona persona = new Persona();
        persona.setRut(dto.getRut());
        persona.setNombre(dto.getNombre());
        persona.setApellido(dto.getApellido());
        persona.setUsuario(usuario);

        personaRepository.save(persona);
        return "Persona registrada correctamente.";
    }

    public List<Persona> listar() {
        return personaRepository.findAll();
    }

}

