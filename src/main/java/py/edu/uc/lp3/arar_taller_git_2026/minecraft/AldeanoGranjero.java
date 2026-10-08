package py.edu.uc.lp3.arar_taller_git_2026.domain;

public class AldeanoGranjero extends Aldeano {
    private String tipoCultivo;

    // Constructor simple
    public AldeanoGranjero() {
        super("Juan Granjero", 20, "0,64,0", "Pan", "Granjero");
        this.tipoCultivo = "Trigo";
    }

    // Constructor sobrecargado
    public AldeanoGranjero(String nombre, int vida, String posicion, String alimentacion, String tipoCultivo) {
        super(nombre, vida, posicion, alimentacion, "Granjero");
        if (tipoCultivo == null || tipoCultivo.trim().isEmpty()) {
            throw new IllegalArgumentException("El tipo de cultivo no puede estar vacío.");
        }
        this.tipoCultivo = tipoCultivo;
    }

    public String getTipoCultivo() { return tipoCultivo; }

    @Override
    public String trabajar() {
        return "Cosechando " + tipoCultivo + " y depositando el excedente en el compostador.";
    }
}
