package py.edu.uc.lp3.arar_taller_git_2026.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "ErrorResponse",
        description = "Cuerpo común de error traducido por ApiExceptionHandler. El `tipo` distingue la excepción de dominio.",
        requiredProperties = { "tipo", "mensaje", "estado", "ruta", "marcaTemporal" })
public record ErrorResponse(
        @Schema(description = "REGLA_DE_JUEGO, ENTIDAD_NO_ENCONTRADA o ERROR_DE_DOMINIO.", example = "REGLA_DE_JUEGO",
                allowableValues = { "REGLA_DE_JUEGO", "ENTIDAD_NO_ENCONTRADA", "ERROR_DE_DOMINIO" }) String tipo,
        @Schema(description = "Mensaje de la excepción.", example = "La potencia de la explosión debe ser mayor a 0.") String mensaje,
        @Schema(description = "Código HTTP.", example = "400") int estado,
        @Schema(description = "Ruta solicitada.", example = "/minecraft/simulaciones/explosion") String ruta,
        @Schema(description = "Marca de tiempo ISO local.", example = "2026-11-22T20:42:00.123") String marcaTemporal) {
}
