package py.edu.uc.lp3.arar_taller_git_2026.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class EntidadPasiva extends Entidad {

    @Schema(description = "Alimento del aldeano.", example = "Carne")
    private String alimentacion;

    protected EntidadPasiva() {
        this("Entidad Pasiva", 20, new Posicion(0, 64, 0), "Pan");
    }

    public EntidadPasiva(String nombre, int vida, Posicion posicion, String alimentacion) {
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
        if (!estaVivo()) {
            return;
        }
    }
}
