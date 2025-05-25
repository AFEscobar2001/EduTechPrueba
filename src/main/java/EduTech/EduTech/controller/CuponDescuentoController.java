package EduTech.EduTech.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import EduTech.EduTech.model.CuponDescuento;
import EduTech.EduTech.service.CuponDescuentoService;

public class CuponDescuentoController {
    @Autowired
    private CuponDescuentoService cuponDescuentoService;

    @PostMapping("/registrar")
    public String registrar(@RequestBody CuponDescuento cuponDescuento) {
        return cuponDescuentoService.registrar(cuponDescuento);
    }

    @GetMapping("/listar")
    public List<CuponDescuento> listar() {
        return cuponDescuentoService.listar();
    }

    @GetMapping("/aplicar")
    public String aplicar(@RequestParam String codigo) {
        return cuponDescuentoService.aplicarCupon(codigo);
    }

}
