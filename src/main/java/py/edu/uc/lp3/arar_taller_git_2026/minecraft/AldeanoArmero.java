package py.edu.uc.lp3.arar_taller_git_2026.minecraft;

public class AldeanoArmero extends Aldeano {
    private int nivelHerreria;

    public AldeanoArmero(String nombre, int vida, String posicion, String alimentacion, int nivelHerreria) {
        super(nombre, vida, posicion, alimentacion, "Armero");
        this.nivelHerreria = nivelHerreria;
    }

    @Override
    public String trabajar() {
        return "Forjando armaduras de nivel " + nivelHerreria + " en el alto horno.";
    }
}