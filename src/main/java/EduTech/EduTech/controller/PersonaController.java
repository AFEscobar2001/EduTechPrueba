package EduTech.EduTech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import EduTech.EduTech.dto.PersonaDTO;
import EduTech.EduTech.model.Persona;

import EduTech.EduTech.service.PersonaService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/personas")
public class PersonaController {

    @Autowired
    private PersonaService personaService;


    @PostMapping
    public String guardar(@RequestBody PersonaDTO dto) {
        return personaService.guardar(dto);
    }

    @GetMapping
    public List<PersonaDTO> listar() {
        return personaService.listar()
                .stream()
                .map(p -> {
                    PersonaDTO dto = new PersonaDTO();
                    dto.setRut(p.getRut());
                    dto.setNombre(p.getNombre());
                    dto.setApellido(p.getApellido());
                    dto.setCorreo(p.getUsuario().getCorreo());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @GetMapping("/{rut}")
    public PersonaDTO buscarPorRut(@PathVariable String rut) {
        Persona persona = personaService.buscarPorRut(rut);

        if (persona != null) {
            PersonaDTO dto = new PersonaDTO();
            dto.setRut(persona.getRut());
            dto.setNombre(persona.getNombre());
            dto.setApellido(persona.getApellido());
            dto.setCorreo(persona.getUsuario().getCorreo());
            return dto;
        } else {
            return null;
        }
    }

    @DeleteMapping("/{rut}")
    public void eliminar(@PathVariable String rut) {
        personaService.eliminar(rut);
    }
}


