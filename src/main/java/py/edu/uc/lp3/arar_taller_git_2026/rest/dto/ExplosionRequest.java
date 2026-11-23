package py.edu.uc.lp3.arar_taller_git_2026.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "ExplosionRequest",
        description = """
                Cuerpo de la simulación de explosión. `potencia` es obligatoria y debe ser mayor que 0.
                `radio` vale 0 si se omite y no puede ser negativo. `posicionCreeper` vale `0,64,0` si se omite.
                `tiempoCarga` vale 3 si se omite y debe ser mayor que 0 si se envía.
                """,
        requiredProperties = { "potencia" })
public record ExplosionRequest(
        @Schema(description = "Potencia de la explosión; entero mayor que 0.", example = "30", minimum = "1") Integer potencia,
        @Schema(description = "Radio de la onda expansiva; entero no negativo. Valor por defecto: 0.", example = "30", minimum = "0", defaultValue = "0") Integer radio,
        @Schema(description = "Posición textual `x,y,z` del Creeper. Valor por defecto: `0,64,0`.", example = "0,64,0", defaultValue = "0,64,0") String posicionCreeper,
        @Schema(description = "Tiempo de carga del Creeper; entero mayor que 0. Valor por defecto: 3.", example = "3", minimum = "1", defaultValue = "3") Integer tiempoCarga) {
}
