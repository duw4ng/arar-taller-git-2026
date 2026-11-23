package py.edu.uc.lp3.arar_taller_git_2026.domain.exception;

public class CompaneroRechazoException extends MinecraftException {

    private final String origen;
    private final int estadoCompanero;
    private final String codigoCompanero;
    private final String cuerpoCompanero;

    protected CompaneroRechazoException(String mensaje, String origen, int estadoCompanero,
            String codigoCompanero, String cuerpoCompanero) {
        super(mensaje);
        this.origen = origen;
        this.estadoCompanero = estadoCompanero;
        this.codigoCompanero = codigoCompanero;
        this.cuerpoCompanero = cuerpoCompanero;
    }

    public String getOrigen() {
        return origen;
    }

    public int getEstadoCompanero() {
        return estadoCompanero;
    }

    public String getCodigoCompanero() {
        return codigoCompanero;
    }

    public String getCuerpoCompanero() {
        return cuerpoCompanero;
    }
}
