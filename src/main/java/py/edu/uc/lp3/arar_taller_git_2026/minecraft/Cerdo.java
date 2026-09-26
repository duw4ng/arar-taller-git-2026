package py.edu.uc.lp3.arar_taller_git_2026.minecraft;

public class Cerdo extends EntidadPasiva {
    private String color;

    public Cerdo(String nombre, int vida, String posicion, String alimentacion, String color) {
        super(nombre, vida, posicion, alimentacion);
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void comer() {
        if (getVida() <= 0) return;
        System.out.println(getNombre() + " consume " + getAlimentacion());
    }

    @Override
    public void actuar() {
        if (getVida() <= 0) return;
        System.out.println(getNombre() + " de color " + color + " deambula alegremente.");
    }
}