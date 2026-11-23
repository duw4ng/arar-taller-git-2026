package py.edu.uc.lp3.arar_taller_git_2026.client;

import com.fasterxml.jackson.databind.JsonNode;

public record RespuestaCompanero(int estado, JsonNode cuerpo, String crudo) {
}
