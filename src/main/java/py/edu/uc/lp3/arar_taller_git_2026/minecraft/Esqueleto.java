package py.edu.uc.lp3.arar_taller_git_2026.minecraft;

public class Esqueleto extends EntidadHostil {
    private String arma;

    public Esqueleto(String nombre, int vida, String posicion, int daño, String objetivo, String arma) {
        super(nombre, vida, posicion, daño, objetivo);
        this.arma = (arma != null) ? arma : "Arco";
    }

    public String getArma() {
        return arma;
    }

    public void setArma(String arma) {
        this.arma = arma;
    }

    @Override
    public void actuar() {
        if (getVida() <= 0) return;
        System.out.println(getNombre() + " mantiene distancia equipando su " + arma);
    }

    @Override
    public void atacar() {
        if (getVida() <= 0) return;
        System.out.println(getNombre() + " dispara a " + getObjetivo() + " usando su " + arma);
    }
}