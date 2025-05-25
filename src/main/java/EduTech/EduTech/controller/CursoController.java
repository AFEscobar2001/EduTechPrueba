package EduTech.EduTech.controller;

import EduTech.EduTech.dto.CursoDTO;
import EduTech.EduTech.model.Curso;
import EduTech.EduTech.service.CursoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @PostMapping
    public String guardar(@RequestBody Curso curso) {
        return cursoService.guardar(curso);
    }

    @GetMapping
    public List<CursoDTO> listarDTO() {
        return cursoService.listarDTO();
    }

    @PutMapping("/{id}")
    public String actualizar(@PathVariable Integer id, @RequestBody Curso curso) {
        curso.setId(id);
        return cursoService.actualizar(curso);
    }

    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable Integer id) {
        return cursoService.eliminar(id);
    }
}


