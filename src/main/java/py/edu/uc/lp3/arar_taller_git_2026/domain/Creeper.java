package py.edu.uc.lp3.arar_taller_git_2026.domain;

import py.edu.uc.lp3.arar_taller_git_2026.domain.exception.ReglaDeJuegoException;

public class Creeper extends EntidadHostil implements Danable {

    private int tiempoCarga;

    public Creeper(String nombre, int vida, String posicion, int daño, String objetivo, int tiempoCarga) {
        super(nombre, vida, posicion, daño, objetivo);
        setTiempoCarga(tiempoCarga);
    }

    public int getTiempoCarga() {
        return tiempoCarga;
    }

    public void setTiempoCarga(int tiempoCarga) {
        if (tiempoCarga < 0) {
            throw new ReglaDeJuegoException("El tiempo de carga no puede ser negativo.");
        }
        this.tiempoCarga = tiempoCarga;
    }

    public int danoPorOnda(int potencia, double distancia) {
        if (!estaVivo()) {
            throw new ReglaDeJuegoException("El Creeper ya explotó y no puede volver a explotar.");
        }
        if (potencia <= 0) {
            throw new ReglaDeJuegoException("La potencia de la explosión debe ser mayor a 0.");
        }
        if (distancia < 0) {
            throw new ReglaDeJuegoException("La distancia de la explosión no puede ser negativa.");
        }
        if (tiempoCarga <= 0) {
            throw new ReglaDeJuegoException("El Creeper no está cargado: su tiempo de carga debe ser mayor a 0.");
        }
        return (int) Math.max(0, Math.round(potencia - distancia));
    }

    public void explotar() {
        if (!estaVivo()) {
            return;
        }
        recibirDaño(getVida());
    }

    @Override
    public void actuar() {
        if (!estaVivo()) {
            return;
        }
    }

    @Override
    public String estadoVida() {
        return getNombre() + " (Creeper) tiene " + getVida() + " de vida y " + tiempoCarga + "s de carga.";
    }
}
