package py.edu.uc.lp3.arar_taller_git_2026.domain;

public class Creeper extends EntidadHostil implements Danable {

    private int tiempoCarga;

    public Creeper(String nombre, int vida, Posicion posicion, int daño, String objetivo, int tiempoCarga) {
        super(nombre, vida, posicion, daño, objetivo);
        this.tiempoCarga = tiempoCarga;
    }

    public int getTiempoCarga() {
        return tiempoCarga;
    }

    public void setTiempoCarga(int tiempoCarga) {
        this.tiempoCarga = tiempoCarga;
    }

    public void explotar() {
        if (!estaVivo()) {
            return;
        }
        recibirDaño(getVida());
    }

    @Override
    public void actuar() {
        if (!estaVivo()) {
            return;
        }
    }

    @Override
    public String estadoVida() {
        return getNombre() + " (Creeper) tiene " + getVida() + " de vida y " + tiempoCarga + "s de carga.";
    }
}
