package py.edu.uc.lp3.arar_taller_git_2026.domain;

public interface Danable {

    void recibirDaño(int cantidad);

    int getVida();

    boolean estaVivo();

    String estadoVida();
}
