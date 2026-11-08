package py.edu.uc.lp3.arar_taller_git_2026.rest.dto;

public record ExplosionRequest(
        Integer potencia,
        Integer radio,
        String posicionCreeper,
        Integer tiempoCarga) {
}
