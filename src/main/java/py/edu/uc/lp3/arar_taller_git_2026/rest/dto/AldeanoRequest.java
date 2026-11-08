package py.edu.uc.lp3.arar_taller_git_2026.rest.dto;

public record AldeanoRequest(
        String tipo,
        String nombre,
        Integer vida,
        String posicion,
        String alimentacion,
        Integer nivelHerreria,
        String tipoCultivo) {
}
