package py.edu.uc.lp3.arar_taller_git_2026.domain;

public class AldeanoArmero extends Aldeano {
    private int nivelHerreria;

    // Constructor simple
    public AldeanoArmero() {
        super("Pedro Armero", 20, "0,64,0", "Carne", "Armero");
        this.nivelHerreria = 1;
    }

    // Constructor sobrecargado
    public AldeanoArmero(String nombre, int vida, String posicion, String alimentacion, int nivelHerreria) {
        super(nombre, vida, posicion, alimentacion, "Armero");
        if (nivelHerreria < 1 || nivelHerreria > 5) {
            throw new IllegalArgumentException("El nivel de herrería debe estar entre 1 y 5.");
        }
        this.nivelHerreria = nivelHerreria;
    }

    public int getNivelHerreria() { return nivelHerreria; }

    @Override
    public String trabajar() {
        return "Forjando armaduras de nivel " + nivelHerreria + " en el alto horno.";
    }
}
