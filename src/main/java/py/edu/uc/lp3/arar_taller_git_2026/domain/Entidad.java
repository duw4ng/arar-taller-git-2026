package py.edu.uc.lp3.arar_taller_git_2026.domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import py.edu.uc.lp3.arar_taller_git_2026.domain.exception.ReglaDeJuegoException;

// Clase base abstracta que define las propiedades y comportamientos comunes
@MappedSuperclass
public abstract class Entidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private int vida;
    private String posicion;

    protected Entidad() {
        this("Entidad", 20, "(0,0,0)");
    }

    public Entidad(String nombre, int vidaInicial, String posicion) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ReglaDeJuegoException("El nombre no puede estar vacío.");
        }
        if (vidaInicial < 0) {
            throw new ReglaDeJuegoException("La vida inicial no puede ser negativa.");
        }
        if (posicion == null || posicion.trim().isEmpty()) {
            throw new ReglaDeJuegoException("La posición no puede ser nula o vacía.");
        }
        this.nombre = nombre.trim();
        this.vida = vidaInicial;
        this.posicion = posicion.trim();
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

    public String getPosicion() {
        return posicion;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ReglaDeJuegoException("El nombre no puede estar vacío.");
        }
        this.nombre = nombre.trim();
    }

    public void setVida(int vida) {
        if (vida < 0) {
            throw new ReglaDeJuegoException("La vida no puede ser negativa.");
        }
        this.vida = vida;
    }

    public void setPosicion(String nuevaPosicion) {
        if (nuevaPosicion == null || nuevaPosicion.trim().isEmpty()) {
            throw new ReglaDeJuegoException("La posición no puede ser nula o vacía.");
        }
        this.posicion = nuevaPosicion.trim();
    }

    public boolean estaVivo() {
        return this.vida > 0;
    }

    public void moverse(String nuevaPosicion) {
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

    public double distanciaA(Entidad otra) {
        if (otra == null) {
            throw new ReglaDeJuegoException("No se puede calcular la distancia contra una entidad nula.");
        }
        return distanciaA(otra.getPosicion());
    }

    public double distanciaA(String otraPosicion) {
        double[] propia = parsearPosicion(this.posicion);
        double[] ajena = parsearPosicion(otraPosicion);
        double dx = propia[0] - ajena[0];
        double dy = propia[1] - ajena[1];
        double dz = propia[2] - ajena[2];
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    private double[] parsearPosicion(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new ReglaDeJuegoException("La posición no puede ser nula o vacía.");
        }
        String limpio = valor.replace("(", "").replace(")", "").trim();
        String[] partes = limpio.split(",");
        if (partes.length != 3) {
            throw new ReglaDeJuegoException("La posición debe tener el formato x,y,z: " + valor);
        }
        try {
            return new double[] {
                Double.parseDouble(partes[0].trim()),
                Double.parseDouble(partes[1].trim()),
                Double.parseDouble(partes[2].trim())
            };
        } catch (NumberFormatException e) {
            throw new ReglaDeJuegoException("La posición contiene coordenadas no numéricas: " + valor, e);
        }
    }

    @PrePersist
    @PreUpdate
    protected void validarAntesDeGuardar() {
        validar();
    }

    protected void validar() {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ReglaDeJuegoException("El nombre no puede estar vacío.");
        }
        if (vida < 0) {
            throw new ReglaDeJuegoException("La vida no puede ser negativa.");
        }
        if (posicion == null || posicion.trim().isEmpty()) {
            throw new ReglaDeJuegoException("La posición no puede ser nula o vacía.");
        }
    }

    public abstract void actuar();
}
