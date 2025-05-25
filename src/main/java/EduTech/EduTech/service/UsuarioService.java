package EduTech.EduTech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import EduTech.EduTech.model.Curso;
import EduTech.EduTech.model.Perfil;
import EduTech.EduTech.model.Usuario;
import EduTech.EduTech.repository.CursoRepository;
import EduTech.EduTech.repository.PerfilRepository;
import EduTech.EduTech.repository.UsuarioRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private CursoRepository cursoRepository;

    public String guardar(Usuario usuario) {
    if (usuarioRepository.existsById(usuario.getCorreo())) {
        return "El usuario con ID " + usuario.getCorreo() + " ya existe.";
    } else {
        usuarioRepository.save(usuario);
        return "Usuario " + usuario.getCorreo() + " creado correctamente.";
    }
}
    public Usuario obtenerPorCorreo(String correo) {
        return usuarioRepository.findById(correo).orElse(null);
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

    public void eliminar(String correo) {
        usuarioRepository.deleteById(correo);
    }

    public void desactivar(String correo) {
        Usuario usuario = usuarioRepository.findById(correo).orElse(null);
        if (usuario != null) {
            usuario.setEstado(false);
            usuarioRepository.save(usuario);
        }
    }

    public void activar(String correo) {
        Usuario usuario = usuarioRepository.findById(correo).orElse(null);
        if (usuario != null) {
            usuario.setEstado(true);
            usuarioRepository.save(usuario);
        }
    }

    public String asignarPerfil(String correoUsuario, Integer idPerfil) {
        Usuario usuario = usuarioRepository.findById(correoUsuario).orElse(null);
        Perfil perfil = perfilRepository.findById(idPerfil).orElse(null);

        if (usuario == null) {
            return "Usuario no encontrado.";
        }

        if (perfil == null) {
            return "Perfil no encontrado.";
        }

        if (usuario.getPerfiles().contains(perfil)) {
            return "El usuario ya tiene asignado este perfil.";
        }

        usuario.getPerfiles().add(perfil);
        usuarioRepository.save(usuario);
        return "Perfil asignado correctamente al usuario.";
    }

    public List<Perfil> obtenerPerfiles(String correoUsuario) {
        Usuario usuario = usuarioRepository.findById(correoUsuario).orElse(null);
        if (usuario != null) {
            return usuario.getPerfiles();
        }
        return null;
    }

    public String asignarCurso(String correoUsuario, Integer idCurso) {
    Usuario usuario = usuarioRepository.findById(correoUsuario).orElse(null);
    Curso curso = cursoRepository.findById(idCurso).orElse(null);

        if (usuario == null) {
            return "Usuario no encontrado.";
        }

        if (curso == null) {
            return "Curso no encontrado.";
        }

        if (usuario.getCursos() == null) {
            usuario.setCursos(new ArrayList<>());
        }

        if (usuario.getCursos().contains(curso)) {
            return "El curso ya está asignado al usuario.";
        }

        usuario.getCursos().add(curso);
        usuarioRepository.save(usuario);

        return "Curso asignado correctamente al usuario.";
    }

    public String eliminarPerfil(String correoUsuario, Integer idPerfil) {
        Usuario usuario = usuarioRepository.findById(correoUsuario).orElse(null);
        Perfil perfil = perfilRepository.findById(idPerfil).orElse(null);

        if (usuario == null) {
            return "Usuario no encontrado.";
        }

        if (perfil == null) {
            return "Perfil no encontrado.";
        }

        if (!usuario.getPerfiles().contains(perfil)) {
            return "El usuario no tiene este perfil asignado.";
        }

        usuario.getPerfiles().remove(perfil);
        usuarioRepository.save(usuario);

        return "Perfil eliminado correctamente del usuario.";
    }
}

