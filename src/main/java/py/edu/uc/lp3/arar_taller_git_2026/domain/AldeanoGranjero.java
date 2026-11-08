package py.edu.uc.lp3.arar_taller_git_2026.domain;

import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import py.edu.uc.lp3.arar_taller_git_2026.domain.exception.ReglaDeJuegoException;

@Entity
@DiscriminatorValue("GRANJERO")
@JsonTypeName("GRANJERO")
public class AldeanoGranjero extends Aldeano {

    private String tipoCultivo;
    private boolean enPanico;

    protected AldeanoGranjero() {
        this("Juan Granjero", 20, "0,64,0", "Pan", "Trigo");
    }

    public AldeanoGranjero(String nombre, String tipoCultivo) {
        this(nombre, 20, "0,64,0", "Pan", tipoCultivo);
    }

    public AldeanoGranjero(String nombre, int vida, String posicion, String alimentacion, String tipoCultivo) {
        super(nombre, vida, posicion, alimentacion, "Granjero");
        if (tipoCultivo == null || tipoCultivo.trim().isEmpty()) {
            throw new ReglaDeJuegoException("El tipo de cultivo no puede estar vacío.");
        }
        this.tipoCultivo = tipoCultivo.trim();
        this.enPanico = false;
    }

    public String getTipoCultivo() {
        return tipoCultivo;
    }

    public void setTipoCultivo(String tipoCultivo) {
        if (tipoCultivo == null || tipoCultivo.trim().isEmpty()) {
            throw new ReglaDeJuegoException("El tipo de cultivo no puede estar vacío.");
        }
        this.tipoCultivo = tipoCultivo.trim();
    }

    public boolean isEnPanico() {
        return enPanico;
    }

    public void setEnPanico(boolean enPanico) {
        this.enPanico = enPanico;
    }

    @Override
    public String trabajar() {
        return "Cosechando " + tipoCultivo + " y depositando el excedente en el compostador.";
    }

    @Override
    public int resistirExplosion(int danoBase) {
        if (danoBase <= 0) {
            return 0;
        }
        recibirDaño(danoBase);
        if (estaVivo()) {
            this.enPanico = true;
        }
        return danoBase;
    }

    @Override
    protected void validar() {
        super.validar();
        if (tipoCultivo == null || tipoCultivo.trim().isEmpty()) {
            throw new ReglaDeJuegoException("El tipo de cultivo no puede estar vacío.");
        }
    }
}
