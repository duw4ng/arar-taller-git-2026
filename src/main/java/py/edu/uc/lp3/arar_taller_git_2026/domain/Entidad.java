package py.edu.uc.lp3.arar_taller_git_2026.domain;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Entidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private int vida;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "x", column = @Column(name = "posicion_x")),
        @AttributeOverride(name = "y", column = @Column(name = "posicion_y")),
        @AttributeOverride(name = "z", column = @Column(name = "posicion_z"))
    })
    private Posicion posicion;

    protected Entidad() {
        this("Entidad", 20, new Posicion(0, 64, 0));
    }

    public Entidad(String nombre, int vidaInicial, Posicion posicion) {
        this.nombre = nombre;
        this.vida = vidaInicial;
        this.posicion = posicion;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getVida() {
        return vida;
    }

    public Posicion getPosicion() {
        return posicion;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public void setPosicion(Posicion posicion) {
        this.posicion = posicion;
    }

    public boolean estaVivo() {
        return this.vida > 0;
    }

    public void moverse(Posicion nuevaPosicion) {
        if (!estaVivo()) {
            return;
        }
        setPosicion(nuevaPosicion);
    }

    public void recibirDaño(int cantidad) {
        if (!estaVivo()) {
            return;
        }
        if (cantidad <= 0) {
            return;
        }
        this.vida -= cantidad;
        if (this.vida <= 0) {
            this.vida = 0;
            morir();
        }
    }

    public void morir() {
    }

    public abstract void actuar();
}
