package py.edu.uc.lp3.arar_taller_git_2026.domain.exception;

public class MarceloRechazoException extends CompaneroRechazoException {

    public MarceloRechazoException(int estadoCompanero, String codigoCompanero, String mensaje, String cuerpoCompanero) {
        super("A3 (Marcelo) rechazó la explosión [" + codigoCompanero + "]: " + mensaje,
                "A3 Marcelo", estadoCompanero, codigoCompanero, cuerpoCompanero);
    }
}
