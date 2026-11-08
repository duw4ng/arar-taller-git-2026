package py.edu.uc.lp3.arar_taller_git_2026.rest.dto;

import java.util.List;

public record ExplosionResponse(
        int potencia,
        int radio,
        String posicionCreeper,
        int vidaCreeper,
        List<ResultadoAldeano> aldeanos) {
}
