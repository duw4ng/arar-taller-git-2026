package py.edu.uc.lp3.arar_taller_git_2026.domain.exception;

public class EliasRechazoException extends CompaneroRechazoException {

    public EliasRechazoException(int estadoCompanero, String codigoCompanero, String mensaje, String cuerpoCompanero) {
        super("A2 (Elias) rechazó la explosión [" + codigoCompanero + "]: " + mensaje,
                "A2 Elias", estadoCompanero, codigoCompanero, cuerpoCompanero);
    }
}
