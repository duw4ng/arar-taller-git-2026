package py.edu.uc.lp3.arar_taller_git_2026.minecraft;

// Representa al jugador principal del juego
public class Jugador extends Entidad {
    private int experiencia;
    private String inventario;

    public Jugador(String nombre, int vida, String posicion, int experiencia, String inventario) {
        super(nombre, vida, posicion);
        setExperiencia(experiencia);
        this.inventario = (inventario != null) ? inventario : "Vacio";
    }

    public int getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(int experiencia) {
        if (experiencia < 0) {
            throw new IllegalArgumentException("La experiencia no puede ser negativa.");
        }
        this.experiencia = experiencia;
    }

    public String getInventario() {
        return inventario;
    }

    public void setInventario(String inventario) {
        this.inventario = inventario;
    }

    @Override
    public void actuar() {
        if (getVida() <= 0) return;
        System.out.println(getNombre() + " explora el mundo y gestiona su inventario: " + inventario);
    }

    public void atacar() {
        if (getVida() <= 0) {
            System.out.println("El jugador no puede atacar porque está muerto.");
            return;
        }
        System.out.println(getNombre() + " realiza un ataque.");
    }
}