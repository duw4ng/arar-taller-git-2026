package py.edu.uc.lp3.arar_taller_git_2026.domain;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import py.edu.uc.lp3.arar_taller_git_2026.domain.exception.ReglaDeJuegoException;

@Entity
@Table(name = "aldeanos")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_aldeano", discriminatorType = DiscriminatorType.STRING)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "tipo")
@JsonSubTypes({
    @JsonSubTypes.Type(value = AldeanoArmero.class, name = "ARMERO"),
    @JsonSubTypes.Type(value = AldeanoGranjero.class, name = "GRANJERO")
})
public abstract class Aldeano extends EntidadPasiva implements Danable {

    private String profesion;

    protected Aldeano() {
        this("Aldeano Desconocido", 20, "(0,0,0)", "Pan", "Sin Profesion");
    }

    public Aldeano(String nombre, int vida, String posicion, String alimentacion, String profesion) {
        super(nombre, vida, posicion, alimentacion);
        if (profesion == null || profesion.trim().isEmpty()) {
            throw new ReglaDeJuegoException("La profesión no puede estar vacía.");
        }
        this.profesion = profesion.trim();
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        if (profesion == null || profesion.trim().isEmpty()) {
            throw new ReglaDeJuegoException("La profesión no puede estar vacía.");
        }
        this.profesion = profesion.trim();
    }

    public String comerciar() {
        if (!estaVivo()) {
            return "No es posible comerciar con un aldeano fuera de combate.";
        }
        return getNombre() + " (" + profesion + ") abre su interfaz de intercambio básico.";
    }

    public String comerciar(String item, int cantidad) {
        if (!estaVivo()) {
            return "No es posible comerciar con un aldeano fuera de combate.";
        }
        if (item == null || item.trim().isEmpty()) {
            throw new ReglaDeJuegoException("El ítem a comerciar no puede estar vacío.");
        }
        if (cantidad <= 0) {
            throw new ReglaDeJuegoException("La cantidad a comerciar debe ser mayor a 0.");
        }
        return getNombre() + " (" + profesion + ") intercambia " + cantidad + "x " + item + " por esmeraldas.";
    }

    @Override
    public void actuar() {
        if (!estaVivo()) {
            return;
        }
    }

    public abstract String trabajar();

    public abstract int resistirExplosion(int danoBase);

    @Override
    public String estadoVida() {
        return getNombre() + " (" + profesion + ") tiene " + getVida() + " de vida.";
    }

    @Override
    protected void validar() {
        super.validar();
        if (profesion == null || profesion.trim().isEmpty()) {
            throw new ReglaDeJuegoException("La profesión no puede estar vacía.");
        }
    }
}
