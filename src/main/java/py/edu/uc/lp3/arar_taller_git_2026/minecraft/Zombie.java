package py.edu.uc.lp3.arar_taller_git_2026.minecraft;

public class Zombie extends EntidadHostil {
    private boolean esHostil;

    public Zombie(String nombre, int vida, String posicion, int daño, String objetivo, boolean esHostil) {
        super(nombre, vida, posicion, daño, objetivo);
        this.esHostil = esHostil;
    }

    public boolean isEsHostil() {
        return esHostil;
    }

    public void setEsHostil(boolean esHostil) {
        this.esHostil = esHostil;
    }

    @Override
    public void actuar() {
        if (getVida() <= 0) return;
        if (esHostil) {
            System.out.println(getNombre() + " emite gruñidos y persigue a " + getObjetivo());
        } else {
            System.out.println(getNombre() + " se encuentra pasivo por luz solar o condición especial.");
        }
    }

    @Override
    public void atacar() {
        if (!esHostil) {
            System.out.println(getNombre() + " no es hostil actualmente.");
            return;
        }
        super.atacar();
    }
}