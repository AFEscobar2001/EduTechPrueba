package EduTech.EduTech.controller;

import EduTech.EduTech.dto.PerfilDTO;
import EduTech.EduTech.model.Perfil;
import EduTech.EduTech.service.PerfilService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/perfiles")
public class PerfilController {

    @Autowired
    private PerfilService perfilService;

    @PostMapping
    public String guardar(@RequestBody Perfil perfil) {
        return perfilService.guardar(perfil);
    }

    @GetMapping
    public List<PerfilDTO> listar() {
        return perfilService.listar()
                .stream()
                .map(PerfilDTO::new)
                .collect(Collectors.toList());
    }

    @PutMapping("/{correo}/perfil/{id}")
    public String asignarPerfil(@PathVariable String correo, @PathVariable Integer id) {
        return perfilService.asignarPerfil(correo, id);
    }

}
