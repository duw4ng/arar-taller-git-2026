package py.edu.uc.lp3.arar_taller_git_2026.rest.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import py.edu.uc.lp3.arar_taller_git_2026.service.AldeanoService;

@RestController
@Tag(name = "dominio", description = "Endpoints de sólo lectura sobre el dominio y la interfaz Danable.")
public class IndexController {

    private final AldeanoService service;

    public IndexController(AldeanoService service) {
        this.service = service;
    }

    @GetMapping("/")
    @Operation(summary = "Estado del servicio", description = "**Sólo lectura**. Devuelve los datos de la entrega.")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "Servicio activo."))
    public Map<String, String> index() {
        Map<String, String> respuesta = new LinkedHashMap<>();
        respuesta.put("estado", "Servicio activo");
        respuesta.put("autor", "Alexandre Avalos");
        respuesta.put("dominio", "Minecraft");
        respuesta.put("entrega", "POO-08 CRUD REST, H2 y simulacion");
        return respuesta;
    }

    @GetMapping("/minecraft/trabajo")
    @Operation(summary = "Mensajes de trabajar()", description = "**Sólo lectura**. Invocación polimórfica de `trabajar()` sobre los aldeanos persistidos.")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "Mapa nombre → mensaje de trabajo; sin escritura en H2."))
    public Map<String, String> trabajo() {
        return service.mensajesTrabajo();
    }
}
