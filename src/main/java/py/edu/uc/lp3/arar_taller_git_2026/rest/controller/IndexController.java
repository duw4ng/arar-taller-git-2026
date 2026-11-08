package py.edu.uc.lp3.arar_taller_git_2026.rest.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import py.edu.uc.lp3.arar_taller_git_2026.domain.Aldeano;
import py.edu.uc.lp3.arar_taller_git_2026.repository.AldeanoRepository;

@RestController
public class IndexController {

    private final AldeanoRepository repository;

    public IndexController(AldeanoRepository repository) {
        this.repository = repository;
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
        Map<String, String> respuesta = new LinkedHashMap<>();
        for (Aldeano aldeano : repository.findAll(Sort.by(Sort.Direction.ASC, "id"))) {
            respuesta.put(aldeano.getNombre(), aldeano.trabajar());
        }
        return respuesta;
    }
}
