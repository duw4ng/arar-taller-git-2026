package py.edu.uc.lp3.arar_taller_git_2026.rest.dto;

public record ResultadoAldeano(
        Long id,
        String nombre,
        String tipo,
        int vidaAntes,
        int vidaDespues,
        int danoRecibido,
        double distancia) {
}
