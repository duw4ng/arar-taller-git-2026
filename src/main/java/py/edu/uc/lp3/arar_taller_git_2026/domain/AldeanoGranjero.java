package py.edu.uc.lp3.arar_taller_git_2026.domain;

import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("GRANJERO")
@JsonTypeName("GRANJERO")
public class AldeanoGranjero extends Aldeano {

    private String tipoCultivo;
    private boolean enPanico;

    protected AldeanoGranjero() {
        this("Juan Granjero", 20, new Posicion(0, 64, 0), "Pan", "Trigo");
    }

    public AldeanoGranjero(String nombre, String tipoCultivo) {
        this(nombre, 20, new Posicion(0, 64, 0), "Pan", tipoCultivo);
    }

    public AldeanoGranjero(String nombre, int vida, Posicion posicion, String alimentacion, String tipoCultivo) {
        super(nombre, vida, posicion, alimentacion, "Granjero");
        this.tipoCultivo = tipoCultivo;
        this.enPanico = false;
    }

    public String getTipoCultivo() {
        return tipoCultivo;
    }

    public void setTipoCultivo(String tipoCultivo) {
        this.tipoCultivo = tipoCultivo;
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
}
