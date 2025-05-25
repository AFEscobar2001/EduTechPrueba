package EduTech.EduTech.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import EduTech.EduTech.model.FormaPago;
import EduTech.EduTech.service.FormaPagoService;

@RestController
@RequestMapping("/formasPago")
public class FormaPagoController {

    @Autowired
    private FormaPagoService formaPagoService;

    @PostMapping
    public String guardar(@RequestBody FormaPago formaPago) {
        return formaPagoService.guardar(formaPago);
    }

    @GetMapping
        public List<FormaPago> listar() {
            return formaPagoService.listar();
        }

}