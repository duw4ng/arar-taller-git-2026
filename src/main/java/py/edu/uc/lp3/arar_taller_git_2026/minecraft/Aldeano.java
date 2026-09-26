package py.edu.uc.lp3.arar_taller_git_2026.minecraft;

public class Aldeano extends EntidadPasiva {
    private String profesion;

    public Aldeano(String nombre, int vida, String posicion, String alimentacion, String profesion) {
        super(nombre, vida, posicion, alimentacion);
        this.profesion = profesion;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    public void comerciar() {
        if (getVida() <= 0) {
            System.out.println("No es posible comerciar con un aldeano fuera de combate.");
            return;
        }
        System.out.println(getNombre() + " (" + profesion + ") abre su interfaz de intercambio comercial.");
    }

    @Override
    public void actuar() {
        if (getVida() <= 0) return;
        System.out.println(getNombre() + " camina hacia su bloque de trabajo relacionado con su profesión (" + profesion + ").");
    }
}