package EduTech.EduTech.controller;

import EduTech.EduTech.model.Contenido;
import EduTech.EduTech.service.ContenidoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contenidos")
public class ContenidoController {

    @Autowired
    private ContenidoService contenidoService;

    @PostMapping
    public String guardar(@RequestBody Contenido contenido) {
        return contenidoService.guardar(contenido);
    }
}
