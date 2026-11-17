package py.edu.uc.lp3.arar_taller_git_2026.domain;

import com.fasterxml.jackson.annotation.JsonValue;

import jakarta.persistence.Embeddable;

@Embeddable
public class Posicion {

    private double x;
    private double y;
    private double z;

    protected Posicion() {
        this(0, 0, 0);
    }

    public Posicion(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    @JsonValue
    public String comoTexto() {
        return formatear(x) + "," + formatear(y) + "," + formatear(z);
    }

    private static String formatear(double valor) {
        if (valor == Math.rint(valor)) {
            return String.valueOf((long) valor);
        }
        return String.valueOf(valor);
    }

    @Override
    public String toString() {
        return comoTexto();
    }
}
