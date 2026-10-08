package py.edu.uc.lp3.arar_taller_git_2026.domain;

public abstract class Aldeano extends EntidadPasiva {
    private String profesion;

    // Constructor simple / por defecto
    public Aldeano() {
        super("Aldeano Desconocido", 20, "(0,0,0)", "Pan");
        this.profesion = "Sin Profesion";
    }

    // Constructor sobrecargado
    public Aldeano(String nombre, int vida, String posicion, String alimentacion, String profesion) {
        super(nombre, vida, posicion, alimentacion);
        this.profesion = profesion;
    }

    public String getProfesion() { return profesion; }
    public void setProfesion(String profesion) { this.profesion = profesion; }

    // --- SOBRECARGA DE MENSAJE DEL DOMINIO (1) ---
    public String comerciar() {
        if (getVida() <= 0) return "No es posible comerciar con un aldeano fuera de combate.";
        return getNombre() + " (" + profesion + ") abre su interfaz de intercambio básico.";
    }

    // --- SOBRECARGA DE MENSAJE DEL DOMINIO (2) ---
    public String comerciar(String item, int cantidad) {
        if (getVida() <= 0) return "No es posible comerciar con un aldeano fuera de combate.";
        if (cantidad <= 0) throw new IllegalArgumentException("La cantidad a comerciar debe ser mayor a 0.");
        return getNombre() + " (" + profesion + ") intercambia " + cantidad + "x " + item + " por esmeraldas.";
    }

    @Override
    public void actuar() {
        if (getVida() <= 0) return;
        System.out.println(getNombre() + " camina hacia su bloque de trabajo (" + profesion + ").");
    }

    // Método abstracto obligatorio
    public abstract String trabajar();
}
