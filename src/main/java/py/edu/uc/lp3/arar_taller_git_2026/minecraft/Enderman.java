package py.edu.uc.lp3.arar_taller_git_2026.minecraft;

public class Enderman extends EntidadHostil {
    private boolean puedeTeletransportarse;

    public Enderman(String nombre, int vida, String posicion, int daño, String objetivo, boolean puedeTeletransportarse) {
        super(nombre, vida, posicion, daño, objetivo);
        this.puedeTeletransportarse = puedeTeletransportarse;
    }

    public boolean isPuedeTeletransportarse() {
        return puedeTeletransportarse;
    }

    public void setPuedeTeletransportarse(boolean puedeTeletransportarse) {
        this.puedeTeletransportarse = puedeTeletransportarse;
    }

    public void teletransportarse(String nuevaPosicion) {
        if (getVida() <= 0) return;
        if (!puedeTeletransportarse) {
            System.out.println(getNombre() + " no puede teletransportarse actualmente.");
            return;
        }
        setPosicion(nuevaPosicion);
        System.out.println(getNombre() + " se teletransportó instantáneamente a " + getPosicion());
    }

    @Override
    public void actuar() {
        if (getVida() <= 0) return;
        System.out.println(getNombre() + " observa fijamente o recoloca un bloque del entorno.");
    }
}