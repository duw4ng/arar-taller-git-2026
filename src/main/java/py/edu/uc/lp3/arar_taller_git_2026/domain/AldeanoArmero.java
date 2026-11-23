package py.edu.uc.lp3.arar_taller_git_2026.domain;

import com.fasterxml.jackson.annotation.JsonTypeName;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("ARMERO")
@JsonTypeName("ARMERO")
@Schema(name = "AldeanoArmero", description = "Armero: reduce el daño de la explosión en `nivelHerreria * 2`.")
public class AldeanoArmero extends Aldeano {

    @Schema(description = "Nivel de herrería; entero entre 1 y 5.", example = "2", minimum = "1", maximum = "5")
    private int nivelHerreria;

    protected AldeanoArmero() {
        this("Pedro Armero", 20, new Posicion(0, 64, 0), "Carne", 1);
    }

    public AldeanoArmero(String nombre) {
        this(nombre, 20, new Posicion(0, 64, 0), "Carne", 1);
    }

    public AldeanoArmero(String nombre, int vida, Posicion posicion, String alimentacion, int nivelHerreria) {
        super(nombre, vida, posicion, alimentacion, "Armero");
        this.nivelHerreria = nivelHerreria;
    }

    public int getNivelHerreria() {
        return nivelHerreria;
    }

    public void setNivelHerreria(int nivelHerreria) {
        this.nivelHerreria = nivelHerreria;
    }

    @Override
    public String trabajar() {
        return "Forjando armaduras de nivel " + nivelHerreria + " en el alto horno.";
    }
}
