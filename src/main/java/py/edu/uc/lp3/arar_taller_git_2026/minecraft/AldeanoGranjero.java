package py.edu.uc.lp3.arar_taller_git_2026.minecraft;

public class AldeanoGranjero extends Aldeano {
    private String tipoCultivo;

    public AldeanoGranjero(String nombre, int vida, String posicion, String alimentacion, String tipoCultivo) {
        super(nombre, vida, posicion, alimentacion, "Granjero");
        this.tipoCultivo = tipoCultivo;
    }

    @Override
    public String trabajar() {
        return "Cosechando " + tipoCultivo + " y depositando el excedente en el compostador.";
    }
}