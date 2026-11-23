package py.edu.uc.lp3.arar_taller_git_2026.rest.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import py.edu.uc.lp3.arar_taller_git_2026.rest.dto.ErrorResponse;
import py.edu.uc.lp3.arar_taller_git_2026.rest.dto.ExplosionRequest;
import py.edu.uc.lp3.arar_taller_git_2026.rest.dto.ExplosionResponse;
import py.edu.uc.lp3.arar_taller_git_2026.service.SimulacionService;

@RestController
@RequestMapping("/minecraft/simulaciones")
@Tag(name = "simulaciones", description = "Simulación de explosión. Escribe en H2: persiste la vida resultante de todos los aldeanos.")
public class SimulacionController {

    private final SimulacionService service;

    public SimulacionController(SimulacionService service) {
        this.service = service;
    }

    @PostMapping("/explosion")
    @Operation(
            summary = "Simular explosión de un Creeper",
            description = """
                    **MODIFICA H2**: no es una validación sin efectos. `SimulacionService` recorre todos los aldeanos
                    persistidos, calcula el daño con las dos reglas del juego, aplica `recibirDaño`, marca el pánico del
                    granjero superviviente y ejecuta `repository.saveAll(aldeanos)`. El `GET /minecraft/aldeanos`
                    posterior muestra la vida ya guardada.

                    **Regla 1 — onda expansiva:** daño = `max(0, round(potencia - distancia))`; fuera del radio es `0`.
                    **Regla 2 — mitigación por oficio:** el armero resta `nivelHerreria * 2`; el granjero recibe el daño
                    completo y, si recibió daño y sigue vivo, queda `enPanico = true`.
                    """)
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            description = "Parámetros de la explosión. El cuerpo vacío es rechazado.",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExplosionRequest.class),
                    examples = {
                            @ExampleObject(name = "Aceptada", summary = "Aceptado: 200; persiste vida en H2", value = """
                                    {
                                      "potencia": 30,
                                      "radio": 30,
                                      "posicionCreeper": "0,64,0",
                                      "tiempoCarga": 3
                                    }
                                    """),
                            @ExampleObject(name = "Rechazada", summary = "Rechazado: 400 REGLA_DE_JUEGO", value = """
                                    {
                                      "potencia": 0,
                                      "radio": 30,
                                      "tiempoCarga": 3
                                    }
                                    """)
                    }))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Simulación ejecutada y vida persistida en H2.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExplosionResponse.class),
                            examples = @ExampleObject(name = "Dos aldeanos afectados", value = """
                                    {
                                      "potencia": 30,
                                      "radio": 30,
                                      "posicionCreeper": "0,64,0",
                                      "vidaCreeper": 0,
                                      "aldeanos": [
                                        {
                                          "id": 1,
                                          "nombre": "Pedro",
                                          "tipo": "AldeanoArmero",
                                          "vidaAntes": 20,
                                          "vidaDespues": 0,
                                          "danoRecibido": 21,
                                          "distancia": 5.0
                                        },
                                        {
                                          "id": 2,
                                          "nombre": "Juan",
                                          "tipo": "AldeanoGranjero",
                                          "vidaAntes": 20,
                                          "vidaDespues": 15,
                                          "danoRecibido": 5,
                                          "distancia": 25.0
                                        }
                                      ]
                                    }
                                    """))),
            @ApiResponse(responseCode = "400", description = "REGLA_DE_JUEGO: cuerpo vacío, `potencia <= 0`, `radio < 0` o `tiempoCarga <= 0`. H2 queda sin cambios.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(name = "Potencia inválida", value = """
                                    {
                                      "tipo": "REGLA_DE_JUEGO",
                                      "mensaje": "La potencia de la explosión debe ser mayor a 0.",
                                      "estado": 400,
                                      "ruta": "/minecraft/simulaciones/explosion",
                                      "marcaTemporal": "2026-11-22T20:42:00.123"
                                    }
                                    """)))
    })
    public ExplosionResponse explotar(@RequestBody ExplosionRequest solicitud) {
        return service.simularExplosion(solicitud);
    }
}
