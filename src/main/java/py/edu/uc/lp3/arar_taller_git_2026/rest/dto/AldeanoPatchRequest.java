package py.edu.uc.lp3.arar_taller_git_2026.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "AldeanoPatchRequest",
        description = """
                Cuerpo para `PATCH`. Todos los campos son opcionales: los omitidos conservan su valor anterior.
                Si `tipo` se envía, debe coincidir con el subtipo ya persistido; un aldeano con vida 0 no puede volver a tener vida.
                """)
public record AldeanoPatchRequest(
        @Schema(description = "Si se envía, debe ser igual al tipo actual.", example = "ARMERO", allowableValues = { "ARMERO", "GRANJERO" }) String tipo,
        @Schema(description = "Nombre no vacío.", example = "Pedro") String nombre,
        @Schema(description = "Vida nueva; no negativa y no mayor que 0 si el aldeano ya murió.", example = "18", minimum = "0") Integer vida,
        @Schema(description = "Posición textual `x,y,z` con coordenadas numéricas finitas.", example = "5,64,0") String posicion,
        @Schema(description = "Alimentación no vacía.", example = "Pan") String alimentacion,
        @Schema(description = "Sólo se aplica al ARMERO; entero entre 1 y 5.", example = "3", minimum = "1", maximum = "5") Integer nivelHerreria,
        @Schema(description = "Sólo se aplica al GRANJERO; texto no vacío.", example = "Zanahoria") String tipoCultivo) {
}
