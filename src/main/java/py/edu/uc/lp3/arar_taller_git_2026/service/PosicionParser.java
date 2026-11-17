package py.edu.uc.lp3.arar_taller_git_2026.service;

import py.edu.uc.lp3.arar_taller_git_2026.domain.Posicion;
import py.edu.uc.lp3.arar_taller_git_2026.domain.exception.ReglaDeJuegoException;

public final class PosicionParser {

    private PosicionParser() {
    }

    public static Posicion parsear(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new ReglaDeJuegoException("La posición es obligatoria.");
        }
        String limpio = valor.replace("(", "").replace(")", "").trim();
        String[] partes = limpio.split(",");
        if (partes.length != 3) {
            throw new ReglaDeJuegoException("La posición debe tener el formato x,y,z: " + valor);
        }
        try {
            double x = Double.parseDouble(partes[0].trim());
            double y = Double.parseDouble(partes[1].trim());
            double z = Double.parseDouble(partes[2].trim());
            if (!Double.isFinite(x) || !Double.isFinite(y) || !Double.isFinite(z)) {
                throw new ReglaDeJuegoException("La posición debe contener coordenadas numéricas finitas: " + valor);
            }
            return new Posicion(x, y, z);
        } catch (NumberFormatException e) {
            throw new ReglaDeJuegoException("La posición debe contener coordenadas numéricas: " + valor, e);
        }
    }
}
