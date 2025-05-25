package EduTech.EduTech.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import EduTech.EduTech.model.Resenia;
import EduTech.EduTech.service.ReseniaService;

@RestController
@RequestMapping("/resenias")
public class ReseniaController {
    @Autowired
    private ReseniaService reseniaService;

    @PostMapping
    public String guardar(@RequestBody Resenia resenia) {
        return reseniaService.guardar(resenia);
    }

    @GetMapping
    public List<Resenia> listar() {
        return reseniaService.listar();
    }

}
