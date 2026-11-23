package py.edu.uc.lp3.arar_taller_git_2026.client;

import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import py.edu.uc.lp3.arar_taller_git_2026.domain.exception.CompaneroNoConfiguradoException;
import py.edu.uc.lp3.arar_taller_git_2026.domain.exception.EliasRechazoException;

@Component
public class EliasClient {

    private final RestClient restClient;
    private final ObjectMapper objectMapper;
    private final String baseUrl;

    public EliasClient(@Value("${apis.elias.base-url:}") String baseUrl) {
        this.restClient = RestClient.builder().build();
        this.objectMapper = new ObjectMapper();
        this.baseUrl = baseUrl == null ? "" : baseUrl.trim().replaceAll("/+$", "");
    }

    public RespuestaCompanero explotar(long creeperId, long zombieId, int distancia) {
        Map<String, Object> cuerpo = new LinkedHashMap<>();
        cuerpo.put("zombieId", zombieId);
        cuerpo.put("distancia", distancia);
        return intercambiar(HttpMethod.POST, "/api/hostiles/" + creeperId + "/explosiones", cuerpo);
    }

    public RespuestaCompanero openApi() {
        return intercambiar(HttpMethod.GET, "/v3/api-docs", null);
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    private RespuestaCompanero intercambiar(HttpMethod metodo, String ruta, Object cuerpo) {
        if (baseUrl.isBlank()) {
            throw new CompaneroNoConfiguradoException(
                    "Falta la URL de A2 (Elias): configurar apis.elias.base-url o ELIAS_API_URL.");
        }
        var especificacion = restClient.method(metodo).uri(baseUrl + ruta);
        if (cuerpo != null) {
            especificacion.contentType(MediaType.APPLICATION_JSON).body(cuerpo);
        }
        ResponseEntity<String> entidad = especificacion.retrieve()
                .onStatus(HttpStatusCode::isError, (solicitud, respuesta) -> {
                    byte[] bytes = respuesta.getBody() == null ? new byte[0] : respuesta.getBody().readAllBytes();
                    String crudo = new String(bytes, StandardCharsets.UTF_8);
                    throw new EliasRechazoException(
                            respuesta.getStatusCode().value(),
                            CuerpoCompanero.codigo(objectMapper, crudo),
                            CuerpoCompanero.mensaje(objectMapper, crudo),
                            crudo);
                })
                .toEntity(String.class);
        String crudo = entidad.getBody() == null ? "" : entidad.getBody();
        JsonNode nodo = CuerpoCompanero.parsear(objectMapper, crudo);
        return new RespuestaCompanero(entidad.getStatusCode().value(), nodo, crudo);
    }
}
