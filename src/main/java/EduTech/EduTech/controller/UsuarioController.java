package EduTech.EduTech.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import EduTech.EduTech.dto.UsuarioDTO;
import EduTech.EduTech.model.Usuario;
import EduTech.EduTech.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public String guardar(@RequestBody Usuario usuario) {
        return usuarioService.guardar(usuario);
    }

    @GetMapping
    public List<UsuarioDTO> listar() {
        return usuarioService.listar()
                             .stream()
                             .map(UsuarioDTO ::new)
                             .toList();
    }

    @DeleteMapping("/{correo}")
    public void eliminar(@PathVariable String correo) {
        usuarioService.eliminar(correo);
    }

    @PutMapping("/{correo}/desactivar")
    public void desactivar(@PathVariable String correo) {
        usuarioService.desactivar(correo);
    }

    @PutMapping("/{correo}")
    public String actualizar(@PathVariable String correo, @RequestBody Usuario usuarioActualizado) {
        usuarioActualizado.setCorreo(correo); // aseguras que el correo no cambie
        return usuarioService.actualizar(usuarioActualizado);
    }

}

