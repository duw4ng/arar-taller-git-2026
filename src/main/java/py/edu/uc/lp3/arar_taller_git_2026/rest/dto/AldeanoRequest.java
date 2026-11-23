package py.edu.uc.lp3.arar_taller_git_2026.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "AldeanoRequest",
        description = """
                Cuerpo para `POST` y `PUT`. Obligatorios: `tipo`, `nombre`, `vida`, `posicion` y `alimentacion`.
                Si `tipo` es `ARMERO`, `nivelHerreria` es obligatorio y debe estar entre 1 y 5.
                Si `tipo` es `GRANJERO`, `tipoCultivo` es obligatorio y no puede quedar vacío.
                `PUT` no permite cambiar el tipo del aldeano existente.
                """,
        requiredProperties = { "tipo", "nombre", "vida", "posicion", "alimentacion" })
public record AldeanoRequest(
        @Schema(description = "Subtipo de aldeano.", example = "ARMERO", allowableValues = { "ARMERO", "GRANJERO" }) String tipo,
        @Schema(description = "Nombre no vacío.", example = "Pedro") String nombre,
        @Schema(description = "Vida inicial; no puede ser negativa.", example = "20", minimum = "0") Integer vida,
        @Schema(description = "Posición textual `x,y,z` con coordenadas numéricas finitas.", example = "0,64,0") String posicion,
        @Schema(description = "Alimentación no vacía.", example = "Carne") String alimentacion,
        @Schema(description = "Obligatorio para ARMERO; entero entre 1 y 5.", example = "2", minimum = "1", maximum = "5") Integer nivelHerreria,
        @Schema(description = "Obligatorio para GRANJERO; texto no vacío.", example = "Trigo") String tipoCultivo) {
}
