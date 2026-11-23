package py.edu.uc.lp3.arar_taller_git_2026.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "CompaneroOpenApiResponse", description = "Resultado del primer contacto con el OpenAPI de un compañero.")
public record CompaneroOpenApiResponse(

        @Schema(description = "Etiqueta del compañero.", example = "A2 Elias") String origen,
        @Schema(description = "URL base configurada.") String baseUrl,
        @Schema(description = "HTTP del GET /v3/api-docs.", example = "200") int estado,
        @Schema(description = "Título del OpenAPI ajeno.", example = "API de hostiles - Elias") String titulo,
        @Schema(description = "Versión del OpenAPI ajeno.", example = "1.0") String version) {
}
