package py.edu.uc.lp3.arar_taller_git_2026.rest.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import py.edu.uc.lp3.arar_taller_git_2026.domain.Creeper;
import py.edu.uc.lp3.arar_taller_git_2026.domain.Danable;
import py.edu.uc.lp3.arar_taller_git_2026.repository.AldeanoRepository;

@RestController
@RequestMapping("/minecraft/danables")
public class DanableController {

    private final AldeanoRepository repository;

    public DanableController(AldeanoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Map<String, Object>> listar() {
        List<Danable> danables = new ArrayList<>(repository.findAll(Sort.by(Sort.Direction.ASC, "id")));
        danables.add(new Creeper("Creeper de muestra", 20, "0,64,0", 10, "aldeanos", 3));

        List<Map<String, Object>> respuesta = new ArrayList<>();
        for (Danable danable : danables) {
            Map<String, Object> fila = new LinkedHashMap<>();
            fila.put("clase", danable.getClass().getSimpleName());
            fila.put("vida", danable.getVida());
            fila.put("estadoVida", danable.estadoVida());
            respuesta.add(fila);
        }
        return respuesta;
    }
}
