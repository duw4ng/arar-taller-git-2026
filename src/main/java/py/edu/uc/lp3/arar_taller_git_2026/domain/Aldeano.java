package py.edu.uc.lp3.arar_taller_git_2026.domain;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

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
        this("Aldeano Desconocido", 20, new Posicion(0, 64, 0), "Pan", "Sin Profesion");
    }

    public Aldeano(String nombre, int vida, Posicion posicion, String alimentacion, String profesion) {
        super(nombre, vida, posicion, alimentacion);
        this.profesion = profesion;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
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
        if (item == null || item.isBlank() || cantidad <= 0) {
            return "No es posible comerciar: el ítem y la cantidad deben ser válidos.";
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

    @Override
    public String estadoVida() {
        return getNombre() + " (" + profesion + ") tiene " + getVida() + " de vida.";
    }
}
