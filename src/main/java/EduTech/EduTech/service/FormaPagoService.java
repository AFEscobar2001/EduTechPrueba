package EduTech.EduTech.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import EduTech.EduTech.model.FormaPago;
import EduTech.EduTech.repository.FormaPagoRepository;

@Service
public class FormaPagoService {
    @Autowired
    private FormaPagoRepository formaPagoRepository;

    public String guardar(FormaPago formaPago) {
        if (formaPago.getNombre() == null || formaPago.getNombre().isBlank()) {
            return "El nombre de la forma de pago es obligatorio.";
        }

        FormaPago existente = formaPagoRepository.findByNombre(formaPago.getNombre());

        if (existente != null) {
            return "Ya existe una forma de pago con ese nombre.";
        }

        formaPagoRepository.save(formaPago);
        return "Forma de pago '" + formaPago.getNombre() + "' registrada correctamente.";
    }

    public List<FormaPago> listar() {
        return formaPagoRepository.findAll();
    }

}
