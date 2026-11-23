package py.edu.uc.lp3.arar_taller_git_2026.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "ResultadoAldeano",
        description = "Estado calculado y persistido de un aldeano tras la explosión.",
        requiredProperties = { "id", "nombre", "tipo", "vidaAntes", "vidaDespues", "danoRecibido", "distancia" })
public record ResultadoAldeano(
        @Schema(description = "Id del aldeano en H2.", example = "1") Long id,
        @Schema(description = "Nombre del aldeano.", example = "Pedro") String nombre,
        @Schema(description = "Clase concreta del aldeano.", example = "AldeanoArmero") String tipo,
        @Schema(description = "Vida antes de la explosión.", example = "20") int vidaAntes,
        @Schema(description = "Vida después de la explosión; ya guardada en H2.", example = "0") int vidaDespues,
        @Schema(description = "Daño aplicado tras la mitigación por oficio.", example = "21") int danoRecibido,
        @Schema(description = "Distancia euclídea hasta el Creeper, redondeada a dos decimales.", example = "5.0") double distancia) {
}
