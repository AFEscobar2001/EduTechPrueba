package EduTech.EduTech.controller;

import EduTech.EduTech.dto.InstructorDTO;
import EduTech.EduTech.model.Instructor;
import EduTech.EduTech.service.InstructorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/instructores")
public class InstructorController {

    @Autowired
    private InstructorService instructorService;

    @PostMapping
    public String guardar(@RequestBody Instructor instructor) {
    return instructorService.guardar(instructor);
}

    @GetMapping
    public List<InstructorDTO> listarDTO() {
        return instructorService.listarDTO();
    }

    @GetMapping("/{rut}")
    public InstructorDTO buscarPorRut(@PathVariable String rut) {
        Instructor instructor = instructorService.buscarPorRut(rut);
        return (instructor != null) ? new InstructorDTO(instructor) : null;
    }

    @DeleteMapping("/{rut}")
    public void eliminar(@PathVariable String rut) {
        instructorService.eliminar(rut);
    }
}
