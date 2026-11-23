package py.edu.uc.lp3.arar_taller_git_2026.rest.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "ExplosionResponse",
        description = "Resultado de una simulación que ya modificó H2: `aldeanos` trae la vida persistida después de la explosión.",
        requiredProperties = { "potencia", "radio", "posicionCreeper", "vidaCreeper", "aldeanos" })
public record ExplosionResponse(
        @Schema(description = "Potencia usada en la simulación.", example = "30") int potencia,
        @Schema(description = "Radio usado en la simulación.", example = "30") int radio,
        @Schema(description = "Posición del Creeper.", example = "0,64,0") String posicionCreeper,
        @Schema(description = "Vida del Creeper después de `explotar()`; siempre 0 en una simulación aceptada.", example = "0") int vidaCreeper,
        @Schema(description = "Una entrada por cada aldeano persistido, con la vida ya guardada.") List<ResultadoAldeano> aldeanos) {
}
