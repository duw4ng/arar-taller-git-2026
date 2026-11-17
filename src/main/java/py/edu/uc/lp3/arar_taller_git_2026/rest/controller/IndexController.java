package py.edu.uc.lp3.arar_taller_git_2026.rest.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import py.edu.uc.lp3.arar_taller_git_2026.service.AldeanoService;

@RestController
public class IndexController {

    private final AldeanoService service;

    public IndexController(AldeanoService service) {
        this.service = service;
    }

    @GetMapping("/")
    public Map<String, String> index() {
        Map<String, String> respuesta = new LinkedHashMap<>();
        respuesta.put("estado", "Servicio activo");
        respuesta.put("autor", "Alexandre Avalos");
        respuesta.put("dominio", "Minecraft");
        respuesta.put("entrega", "POO-08 CRUD REST, H2 y simulacion");
        return respuesta;
    }

    @GetMapping("/minecraft/trabajo")
    public Map<String, String> trabajo() {
        return service.mensajesTrabajo();
    }
}
