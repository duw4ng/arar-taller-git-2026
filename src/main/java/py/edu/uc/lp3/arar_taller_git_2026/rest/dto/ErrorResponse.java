package py.edu.uc.lp3.arar_taller_git_2026.rest.dto;

public record ErrorResponse(
        String tipo,
        String mensaje,
        int estado,
        String ruta,
        String marcaTemporal) {
}
