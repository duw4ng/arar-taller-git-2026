package py.edu.uc.lp3.arar_taller_git_2026.domain.exception;

public class MinecraftException extends RuntimeException {

    public MinecraftException(String mensaje) {
        super(mensaje);
    }

    public MinecraftException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
