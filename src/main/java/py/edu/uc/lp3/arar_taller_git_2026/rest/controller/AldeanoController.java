package py.edu.uc.lp3.arar_taller_git_2026.rest.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import py.edu.uc.lp3.arar_taller_git_2026.domain.Aldeano;
import py.edu.uc.lp3.arar_taller_git_2026.rest.dto.AldeanoPatchRequest;
import py.edu.uc.lp3.arar_taller_git_2026.rest.dto.AldeanoRequest;
import py.edu.uc.lp3.arar_taller_git_2026.rest.dto.ErrorResponse;
import py.edu.uc.lp3.arar_taller_git_2026.service.AldeanoService;

@RestController
@RequestMapping("/minecraft/aldeanos")
@Tag(name = "aldeanos", description = "CRUD de aldeanos persistidos en H2. POST, PUT, PATCH y DELETE escriben; GET sólo lee.")
public class AldeanoController {

    private final AldeanoService service;

    public AldeanoController(AldeanoService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(
            summary = "Crear aldeano",
            description = "**Modifica H2**: inserta una fila en la tabla `aldeanos` y devuelve el `id` generado. "
                    + "`tipo` debe ser `ARMERO` o `GRANJERO`; `nivelHerreria` va de 1 a 5 y `tipoCultivo` no puede quedar vacío.")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            description = "Cuerpo del aldeano a insertar.",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AldeanoRequest.class),
                    examples = @ExampleObject(name = "Armero válido", summary = "Aceptado: 201", value = """
                            {
                              "tipo": "ARMERO",
                              "nombre": "Pedro",
                              "vida": 20,
                              "posicion": "0,64,0",
                              "alimentacion": "Carne",
                              "nivelHerreria": 2
                            }
                            """)))
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Aldeano insertado en H2.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Aldeano.class),
                            examples = @ExampleObject(name = "Creado", value = """
                                    {
                                      "id": 1,
                                      "tipo": "ARMERO",
                                      "nombre": "Pedro",
                                      "vida": 20,
                                      "posicion": "0,64,0",
                                      "alimentacion": "Carne",
                                      "profesion": "Armero",
                                      "nivelHerreria": 2
                                    }
                                    """))),
            @ApiResponse(responseCode = "400", description = "REGLA_DE_JUEGO: dato obligatorio ausente, posición inválida o campo fuera de rango.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(name = "Rechazado", summary = "nivelHerreria = 99", value = """
                                    {
                                      "tipo": "REGLA_DE_JUEGO",
                                      "mensaje": "El nivel de herrería debe estar entre 1 y 5.",
                                      "estado": 400,
                                      "ruta": "/minecraft/aldeanos",
                                      "marcaTemporal": "2026-11-22T20:42:00.123"
                                    }
                                    """)))
    })
    public ResponseEntity<Aldeano> crear(@RequestBody AldeanoRequest solicitud) {
        Aldeano creado = service.crear(solicitud);
        return ResponseEntity
                .created(URI.create("/minecraft/aldeanos/" + creado.getId()))
                .body(creado);
    }

    @GetMapping
    @Operation(summary = "Listar aldeanos", description = "**Sólo lectura**. Devuelve todos los aldeanos persistidos, ordenados por `id`.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Colección persistida (puede estar vacía).",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Aldeano.class),
                            examples = @ExampleObject(name = "Dos aldeanos", value = """
                                    [
                                      {
                                        "id": 1,
                                        "tipo": "ARMERO",
                                        "nombre": "Pedro",
                                        "vida": 20,
                                        "posicion": "0,64,0",
                                        "alimentacion": "Carne",
                                        "profesion": "Armero",
                                        "nivelHerreria": 2
                                      },
                                      {
                                        "id": 2,
                                        "tipo": "GRANJERO",
                                        "nombre": "Juan",
                                        "vida": 20,
                                        "posicion": "20,64,0",
                                        "alimentacion": "Pan",
                                        "profesion": "Granjero",
                                        "tipoCultivo": "Trigo",
                                        "enPanico": false
                                      }
                                    ]
                                    """)))
    })
    public List<Aldeano> listar(
            @RequestParam(required = false)
            @Parameter(description = "Filtro opcional por profesión, sin distinción de mayúsculas.", example = "Armero") String profesion) {
        return service.listarPorProfesion(profesion);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar aldeano por id", description = "**Sólo lectura**. No modifica H2.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Aldeano encontrado.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Aldeano.class))),
            @ApiResponse(responseCode = "404", description = "ENTIDAD_NO_ENCONTRADA: no existe ese id.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public Aldeano buscar(
            @PathVariable
            @Parameter(description = "Id del aldeano en H2.", example = "1") Long id) {
        return service.buscar(id);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Reemplazar aldeano",
            description = "**Modifica H2**: reemplaza todos los campos de la fila existente. No permite cambiar `tipo` ni revivir un aldeano con vida 0.")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            description = "Representación completa del aldeano.",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = AldeanoRequest.class),
                    examples = @ExampleObject(name = "Granjero válido", value = """
                            {
                              "tipo": "GRANJERO",
                              "nombre": "Juan",
                              "vida": 20,
                              "posicion": "20,64,0",
                              "alimentacion": "Pan",
                              "tipoCultivo": "Trigo"
                            }
                            """)))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Fila reemplazada en H2.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Aldeano.class))),
            @ApiResponse(responseCode = "400", description = "REGLA_DE_JUEGO: tipo distinto, dato inválido o intento de revivir un muerto.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "ENTIDAD_NO_ENCONTRADA: id inexistente.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public Aldeano reemplazar(
            @PathVariable
            @Parameter(description = "Id del aldeano en H2.", example = "1") Long id,
            @RequestBody AldeanoRequest solicitud) {
        return service.reemplazar(id, solicitud);
    }

    @PatchMapping("/{id}")
    @Operation(
            summary = "Actualizar aldeano parcialmente",
            description = "**Modifica H2**: actualiza sólo los campos presentes; los omitidos conservan su valor. Un aldeano muerto no puede volver a tener vida.")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            description = "Sólo los campos a cambiar.",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = AldeanoPatchRequest.class),
                    examples = @ExampleObject(name = "Cambio parcial", value = """
                            {
                              "vida": 18,
                              "posicion": "5,64,0"
                            }
                            """)))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Fila actualizada en H2.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Aldeano.class))),
            @ApiResponse(responseCode = "400", description = "REGLA_DE_JUEGO: dato inválido, cambio de tipo o intento de revivir un muerto.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "ENTIDAD_NO_ENCONTRADA: id inexistente.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public Aldeano actualizar(
            @PathVariable
            @Parameter(description = "Id del aldeano en H2.", example = "1") Long id,
            @RequestBody AldeanoPatchRequest solicitud) {
        return service.actualizarParcial(id, solicitud);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar aldeano", description = "**Modifica H2**: borra físicamente la fila y responde `204` sin cuerpo.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Fila borrada de H2; sin cuerpo."),
            @ApiResponse(responseCode = "404", description = "ENTIDAD_NO_ENCONTRADA: id inexistente.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<Void> eliminar(
            @PathVariable
            @Parameter(description = "Id del aldeano en H2.", example = "1") Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
