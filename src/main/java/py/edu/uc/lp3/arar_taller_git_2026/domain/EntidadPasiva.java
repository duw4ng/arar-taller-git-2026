package py.edu.uc.lp3.arar_taller_git_2026.domain;

import jakarta.persistence.MappedSuperclass;
import py.edu.uc.lp3.arar_taller_git_2026.domain.exception.ReglaDeJuegoException;

// Subclase abstracta para criaturas pacíficas
@MappedSuperclass
public abstract class EntidadPasiva extends Entidad {

    private String alimentacion;

    protected EntidadPasiva() {
        this("Entidad Pasiva", 20, "(0,0,0)", "Pan");
    }

    public EntidadPasiva(String nombre, int vida, String posicion, String alimentacion) {
        super(nombre, vida, posicion);
        if (alimentacion == null || alimentacion.trim().isEmpty()) {
            throw new ReglaDeJuegoException("La alimentación no puede estar vacía.");
        }
        this.alimentacion = alimentacion.trim();
    }

    public String getAlimentacion() {
        return alimentacion;
    }

    public void setAlimentacion(String alimentacion) {
        if (alimentacion == null || alimentacion.trim().isEmpty()) {
            throw new ReglaDeJuegoException("La alimentación no puede estar vacía.");
        }
        this.alimentacion = alimentacion.trim();
    }

    public void huir() {
        if (!estaVivo()) {
            return;
        }
    }

    @Override
    protected void validar() {
        super.validar();
        if (alimentacion == null || alimentacion.trim().isEmpty()) {
            throw new ReglaDeJuegoException("La alimentación no puede estar vacía.");
        }
    }
}
