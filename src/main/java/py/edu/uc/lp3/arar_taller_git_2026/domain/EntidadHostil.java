package py.edu.uc.lp3.arar_taller_git_2026.domain;

public abstract class EntidadHostil extends Entidad {
    private int daño;
    private String objetivo;

    public EntidadHostil(String nombre, int vida, Posicion posicion, int daño, String objetivo) {
        super(nombre, vida, posicion);
        this.daño = daño;
        this.objetivo = objetivo;
    }

    public int getDaño() {
        return daño;
    }

    public void setDaño(int daño) {
        this.daño = daño;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public void atacar() {
        if (getVida() <= 0) {
            System.out.println(getNombre() + " no puede atacar porque está muerto.");
            return;
        }
        if (objetivo == null || objetivo.trim().isEmpty()) {
            System.out.println(getNombre() + " no tiene un objetivo fijado.");
            return;
        }
        System.out.println(getNombre() + " ataca a " + objetivo + " infligiendo " + daño + " puntos de daño.");
    }
}
