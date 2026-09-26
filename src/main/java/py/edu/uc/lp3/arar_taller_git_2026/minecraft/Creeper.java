package py.edu.uc.lp3.arar_taller_git_2026.minecraft;

public class Creeper extends EntidadHostil {
    private int tiempoCarga;

    public Creeper(String nombre, int vida, String posicion, int daño, String objetivo, int tiempoCarga) {
        super(nombre, vida, posicion, daño, objetivo);
        setTiempoCarga(tiempoCarga);
    }

    public int getTiempoCarga() {
        return tiempoCarga;
    }

    public void setTiempoCarga(int tiempoCarga) {
        if (tiempoCarga < 0) {
            throw new IllegalArgumentException("El tiempo de carga no puede ser negativo.");
        }
        this.tiempoCarga = tiempoCarga;
    }

    public void explotar() {
        if (getVida() <= 0) return;
        System.out.println(getNombre() + " genera un siseo por " + tiempoCarga + "s y ¡EXPLOTA!");
        recibirDaño(getVida()); // La explosión elimina al propio Creeper
    }

    @Override
    public void actuar() {
        if (getVida() <= 0) return;
        System.out.println(getNombre() + " se aproxima silenciosamente a " + getObjetivo());
    }
}