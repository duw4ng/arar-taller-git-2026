package py.edu.uc.lp3.arar_taller_git_2026.domain;

import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import py.edu.uc.lp3.arar_taller_git_2026.domain.exception.ReglaDeJuegoException;

@Entity
@DiscriminatorValue("ARMERO")
@JsonTypeName("ARMERO")
public class AldeanoArmero extends Aldeano {

    private int nivelHerreria;

    protected AldeanoArmero() {
        this("Pedro Armero", 20, "0,64,0", "Carne", 1);
    }

    public AldeanoArmero(String nombre) {
        this(nombre, 20, "0,64,0", "Carne", 1);
    }

    public AldeanoArmero(String nombre, int vida, String posicion, String alimentacion, int nivelHerreria) {
        super(nombre, vida, posicion, alimentacion, "Armero");
        if (nivelHerreria < 1 || nivelHerreria > 5) {
            throw new ReglaDeJuegoException("El nivel de herrería debe estar entre 1 y 5.");
        }
        this.nivelHerreria = nivelHerreria;
    }

    public int getNivelHerreria() {
        return nivelHerreria;
    }

    public void setNivelHerreria(int nivelHerreria) {
        if (nivelHerreria < 1 || nivelHerreria > 5) {
            throw new ReglaDeJuegoException("El nivel de herrería debe estar entre 1 y 5.");
        }
        this.nivelHerreria = nivelHerreria;
    }

    @Override
    public String trabajar() {
        return "Forjando armaduras de nivel " + nivelHerreria + " en el alto horno.";
    }

    @Override
    public int resistirExplosion(int danoBase) {
        if (danoBase <= 0) {
            return 0;
        }
        int dano = Math.max(0, danoBase - (nivelHerreria * 2));
        if (dano > 0) {
            recibirDaño(dano);
        }
        return dano;
    }

    @Override
    protected void validar() {
        super.validar();
        if (nivelHerreria < 1 || nivelHerreria > 5) {
            throw new ReglaDeJuegoException("El nivel de herrería debe estar entre 1 y 5.");
        }
    }
}
