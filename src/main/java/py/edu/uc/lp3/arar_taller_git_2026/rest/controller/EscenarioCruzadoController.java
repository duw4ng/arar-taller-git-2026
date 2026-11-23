package py.edu.uc.lp3.arar_taller_git_2026.rest.controller;

import java.net.URI;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import py.edu.uc.lp3.arar_taller_git_2026.domain.JugadaCruzada;
import py.edu.uc.lp3.arar_taller_git_2026.rest.dto.CompaneroOpenApiResponse;
import py.edu.uc.lp3.arar_taller_git_2026.rest.dto.ErrorCompaneroResponse;
import py.edu.uc.lp3.arar_taller_git_2026.rest.dto.ErrorResponse;
import py.edu.uc.lp3.arar_taller_git_2026.rest.dto.EscenarioCruzadoRequest;
import py.edu.uc.lp3.arar_taller_git_2026.rest.dto.JugadaCruzadaResponse;
import py.edu.uc.lp3.arar_taller_git_2026.service.EscenarioCruzadoService;

@RestController
@RequestMapping("/minecraft/escenarios")
@Tag(name = "escenarios", description = "Challenge POO-09: A1 consulta a A2 (Elias) y A3 (Marcelo); sólo guarda en su H2 si ambos aceptan.")
public class EscenarioCruzadoController {

    private final EscenarioCruzadoService escenarioService;

    public EscenarioCruzadoController(EscenarioCruzadoService escenarioService) {
        this.escenarioService = escenarioService;
    }

    @PostMapping("/explosiones-cruzadas")
    @Operation(summary = "Ejecutar explosión cruzada", description = "Llama a Elias y Marcelo con ids ya creados en sus APIs. "
            + "Si los dos devuelven 200, A1 persiste la jugada en su H2; si uno rechaza, A1 no guarda y el error dice qué compañero rechazó.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Ambos aceptaron y A1 guardó la jugada.",
                    content = @Content(schema = @Schema(implementation = JugadaCruzadaResponse.class))),
            @ApiResponse(responseCode = "400", description = "Datos del escenario inválidos en A1.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "502", description = "A2 o A3 rechazó; el cuerpo identifica el origen.",
                    content = @Content(schema = @Schema(implementation = ErrorCompaneroResponse.class))),
            @ApiResponse(responseCode = "503", description = "Falta configurar la URL base de alguno de los compañeros.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<JugadaCruzadaResponse> ejecutar(
            @RequestBody EscenarioCruzadoRequest solicitud) {
        JugadaCruzada guardada = escenarioService.ejecutar(solicitud);
        return ResponseEntity
                .created(URI.create("/minecraft/escenarios/explosiones-cruzadas/" + guardada.getId()))
                .body(aRespuesta(guardada));
    }

    @GetMapping("/explosiones-cruzadas")
    @Operation(summary = "Listar jugadas cruzadas", description = "Devuelve todas las jugadas que A1 guardó tras doble aceptación.")
    public List<JugadaCruzadaResponse> listar() {
        return escenarioService.listar().stream().map(this::aRespuesta).toList();
    }

    @GetMapping("/explosiones-cruzadas/{id}")
    @Operation(summary = "Obtener una jugada cruzada", description = "Permite comprobar el efecto en el H2 de A1.")
    public JugadaCruzadaResponse obtener(
            @Parameter(description = "Id de la jugada en A1.", example = "1") @PathVariable long id) {
        return aRespuesta(escenarioService.obtener(id));
    }

    @GetMapping("/diagnostico")
    @Operation(summary = "Primer contacto con los OpenAPI ajenos", description = "Hace GET /v3/api-docs a A2 y A3 con la URL configurada y muestra título y versión.")
    public Map<String, CompaneroOpenApiResponse> diagnostico() {
        return Map.of(
                "elias", escenarioService.diagnosticarElias(),
                "marcelo", escenarioService.diagnosticarMarcelo());
    }

    private JugadaCruzadaResponse aRespuesta(JugadaCruzada jugada) {
        return new JugadaCruzadaResponse(
                jugada.getId(),
                jugada.getCreeperEliasId(),
                jugada.getZombieEliasId(),
                jugada.getDistanciaElias(),
                jugada.getCreeperMarceloId(),
                jugada.getEstadoElias(),
                jugada.getCodigoElias(),
                jugada.getEstadoMarcelo(),
                jugada.getCodigoMarcelo(),
                jugada.getRegistradaEn());
    }
}
