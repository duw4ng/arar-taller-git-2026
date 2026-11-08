package py.edu.uc.lp3.arar_taller_git_2026.domain.exception;

public class ReglaDeJuegoException extends MinecraftException {

    public ReglaDeJuegoException(String mensaje) {
        super(mensaje);
    }

    public ReglaDeJuegoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
