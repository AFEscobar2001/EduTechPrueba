package EduTech.EduTech.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import EduTech.EduTech.model.Usuario;
import EduTech.EduTech.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;


    public String guardar(Usuario usuario) {
        if (usuario.getCorreo() == null || usuario.getCorreo().isBlank()) {
            return "El correo es obligatorio.";
        }

        if (usuario.getContrasena() == null || usuario.getContrasena().isBlank()) {
            return "La contraseña es obligatoria.";
        }

        if (usuarioRepository.existsById(usuario.getCorreo())) {
            return "El usuario con correo " + usuario.getCorreo() + " ya existe.";
        }

        usuarioRepository.save(usuario);
        return "Usuario " + usuario.getCorreo() + " creado correctamente.";
    }


    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    public String actualizar(Usuario nuevoUsuario) {
        Usuario existente = usuarioRepository.findById(nuevoUsuario.getCorreo()).orElse(null);

        if (existente == null) {
            return "Usuario no encontrado.";
        }

        existente.setContrasena(nuevoUsuario.getContrasena());
        existente.setEstado(nuevoUsuario.isEstado());

        usuarioRepository.save(existente);
        return "Usuario actualizado correctamente.";
    }

    public String eliminar(String correo) {
        Usuario usuario = usuarioRepository.findById(correo).orElse(null);

        if (usuario == null) {
            return "Usuario no encontrado.";
        }

        usuarioRepository.deleteById(correo);
        return "Usuario eliminado correctamente.";
    }

    public void desactivar(String correo) {
        Usuario usuario = usuarioRepository.findById(correo).orElse(null);
        if (usuario != null) {
            usuario.setEstado(false);
            usuarioRepository.save(usuario);
        }
    }

}

