package EduTech.EduTech.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import EduTech.EduTech.model.CuponDescuento;
import EduTech.EduTech.repository.CuponDescuentoRepository;

@Service
public class CuponDescuentoService {
    @Autowired
    private CuponDescuentoRepository cuponDescuentoRepository;

    public String registrar(CuponDescuento cuponDescuento) {
        cuponDescuentoRepository.save(cuponDescuento);
        return "Cupón registrado correctamente.";
    }

    public List<CuponDescuento> listar() {
        return cuponDescuentoRepository.findAll();
    }

    public String aplicarCupon(String codigo) {
        CuponDescuento cuponDescuento = cuponDescuentoRepository.findById(codigo).orElse(null);
        if (cuponDescuento != null && cuponDescuento.isActivo()) {
            return "Cupón aplicado con éxito: " + cuponDescuento.getDescuento() + "% de descuento.";
        } else {
            return "El cupón no existe o está inactivo.";
        }
    }

}
