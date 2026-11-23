package py.edu.uc.lp3.arar_taller_git_2026.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ErrorCompaneroResponse", description = "Error traducido por A1; el campo origen distingue A2 de A3.")
public record ErrorCompaneroResponse(

        @Schema(description = "Tipo interno de A1.", example = "COMPANERO_RECHAZO") String tipo,
        @Schema(description = "Compañero que rechazó.", example = "A2 Elias") String origen,
        @Schema(description = "Mensaje legible de A1.") String mensaje,
        @Schema(description = "HTTP devuelto por A1.", example = "502") int estado,
        @Schema(description = "HTTP original del compañero.", example = "422") int estadoCompanero,
        @Schema(description = "Código original del compañero.", example = "FUERA_DE_ALCANCE") String codigoCompanero,
        @Schema(description = "Ruta llamada en A1.", example = "/minecraft/escenarios/explosiones-cruzadas") String ruta,
        @Schema(description = "Momento del error.") String marcaTemporal) {
}
