package py.edu.uc.lp3.arar_taller_git_2026.client;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class CuerpoCompanero {

    private static final List<String> CLAVES_CODIGO = List.of("codigo", "codigoError", "tipo", "code", "errorCode", "error");
    private static final List<String> CLAVES_MENSAJE = List.of("mensaje", "message", "detail", "descripcion", "description", "error");

    private CuerpoCompanero() {
    }

    public static JsonNode parsear(ObjectMapper objectMapper, String crudo) {
        if (crudo == null || crudo.isBlank()) {
            return null;
        }
        try {
            return objectMapper.readTree(crudo);
        } catch (IOException excepcion) {
            return null;
        }
    }

    public static String codigo(ObjectMapper objectMapper, String crudo) {
        return primera(parsear(objectMapper, crudo), CLAVES_CODIGO, "OK");
    }

    public static String mensaje(ObjectMapper objectMapper, String crudo) {
        return primera(parsear(objectMapper, crudo), CLAVES_MENSAJE, "sin mensaje");
    }

    public static String titulo(ObjectMapper objectMapper, JsonNode cuerpo) {
        if (cuerpo == null || !cuerpo.has("info")) {
            return "desconocido";
        }
        return primera(cuerpo.get("info"), List.of("title", "titulo"), "desconocido");
    }

    public static String version(ObjectMapper objectMapper, JsonNode cuerpo) {
        if (cuerpo == null || !cuerpo.has("info")) {
            return "desconocida";
        }
        return primera(cuerpo.get("info"), List.of("version"), "desconocida");
    }

    private static String primera(JsonNode nodo, List<String> claves, String porDefecto) {
        if (nodo == null) {
            return porDefecto;
        }
        for (String clave : claves) {
            if (nodo.hasNonNull(clave)) {
                return nodo.get(clave).asText();
            }
        }
        return porDefecto;
    }
}
