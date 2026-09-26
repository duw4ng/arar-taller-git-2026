package py.edu.uc.lp3.arar_taller_git_2026.minecraft;

// Clase base abstracta que define las propiedades y comportamientos comunes
public abstract class Entidad {
    private String nombre;
    private int vida;
    private String posicion;

    public Entidad(String nombre, int vidaInicial, String posicion) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        if (vidaInicial < 0) {
            throw new IllegalArgumentException("La vida inicial no puede ser negativa.");
        }
        this.nombre = nombre;
        this.vida = vidaInicial;
        this.posicion = (posicion != null) ? posicion : "(0, 0, 0)";
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

    public void setPosicion(String nuevaPosicion) {
        if (nuevaPosicion == null || nuevaPosicion.trim().isEmpty()) {
            throw new IllegalArgumentException("La posición no puede ser nula o vacía.");
        }
        this.posicion = nuevaPosicion;
    }

    public void moverse(String nuevaPosicion) {
        if (this.vida <= 0) {
            System.out.println(nombre + " no puede moverse porque está muerto.");
            return;
        }
        setPosicion(nuevaPosicion);
        System.out.println(nombre + " se desplazó a " + posicion);
    }

    public void recibirDaño(int cantidad) {
        if (this.vida <= 0) {
            System.out.println(nombre + " ya está muerto.");
            return;
        }
        if (cantidad <= 0) {
            System.out.println("El daño recibido debe ser mayor a 0.");
            return;
        }
        this.vida -= cantidad;
        if (this.vida <= 0) {
            this.vida = 0;
            morir();
        } else {
            System.out.println(nombre + " recibió " + cantidad + " de daño. Vida restante: " + vida);
        }
    }

    public void morir() {
        System.out.println(nombre + " ha sido eliminado.");
    }

    public abstract void actuar();
}