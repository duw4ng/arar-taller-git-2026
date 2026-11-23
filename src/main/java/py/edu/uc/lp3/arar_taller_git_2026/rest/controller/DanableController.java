package py.edu.uc.lp3.arar_taller_git_2026.rest.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import py.edu.uc.lp3.arar_taller_git_2026.domain.Danable;
import py.edu.uc.lp3.arar_taller_git_2026.service.AldeanoService;

@RestController
@RequestMapping("/minecraft/danables")
@Tag(name = "dominio", description = "Endpoints de sólo lectura sobre el dominio y la interfaz Danable.")
public class DanableController {

    private final AldeanoService service;

    public DanableController(AldeanoService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(
            summary = "Ver la interfaz Danable",
            description = "**Sólo lectura**. Devuelve armeros y granjeros persistidos más un Creeper de muestra a través de `List<Danable>`.")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "Mensajes `estadoVida()` de cada `Danable`; sin escritura en H2."))
    public List<Map<String, Object>> listar() {
        List<Map<String, Object>> respuesta = new ArrayList<>();
        for (Danable danable : service.listarDanables()) {
            Map<String, Object> fila = new LinkedHashMap<>();
            fila.put("clase", danable.getClass().getSimpleName());
            fila.put("vida", danable.getVida());
            fila.put("estadoVida", danable.estadoVida());
            respuesta.add(fila);
        }
        return respuesta;
    }
}
