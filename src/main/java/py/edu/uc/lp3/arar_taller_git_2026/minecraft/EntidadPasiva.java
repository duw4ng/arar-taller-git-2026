package py.edu.uc.lp3.arar_taller_git_2026.minecraft;

// Subclase abstracta para criaturas pacíficas
public abstract class EntidadPasiva extends Entidad {
    private String alimentacion;

    public EntidadPasiva(String nombre, int vida, String posicion, String alimentacion) {
        super(nombre, vida, posicion);
        this.alimentacion = alimentacion;
    }

    public String getAlimentacion() {
        return alimentacion;
    }

    public void setAlimentacion(String alimentacion) {
        this.alimentacion = alimentacion;
    }

    public void huir() {
        if (getVida() <= 0) return;
        System.out.println(getNombre() + " se ha asustado y empieza a huir.");
    }
}