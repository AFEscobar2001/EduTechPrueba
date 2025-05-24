package EduTech.EduTech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import EduTech.EduTech.model.Perfil;
import EduTech.EduTech.repository.PerfilRepository;

import java.util.List;

@Service
public class PerfilService {

    @Autowired
    private PerfilRepository perfilRepository;

    public String guardar(Perfil perfil) {
    Perfil validacion = perfilRepository.findByNombre(perfil.getNombre());

        if (validacion == null) {
            perfilRepository.save(perfil);
            return "Perfil " + perfil.getNombre() + " creado correctamente.";
        } else {
            return "El perfil " + perfil.getNombre() + " ya existe.";
        }
}

    public Perfil buscarPorId(Integer id) {
        return perfilRepository.findById(id).orElse(null);
    }

    public List<Perfil> listar() {
        return perfilRepository.findAll();
    }

    public void eliminar(Integer id) {
        perfilRepository.deleteById(id);
    }
}
