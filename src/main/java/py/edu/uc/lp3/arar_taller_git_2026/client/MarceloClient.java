package py.edu.uc.lp3.arar_taller_git_2026.client;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import py.edu.uc.lp3.arar_taller_git_2026.domain.exception.CompaneroNoConfiguradoException;
import py.edu.uc.lp3.arar_taller_git_2026.domain.exception.MarceloRechazoException;

@Component
public class MarceloClient {

    private final RestClient restClient;
    private final ObjectMapper objectMapper;
    private final String baseUrl;

    public MarceloClient(@Value("${apis.marcelo.base-url:}") String baseUrl) {
        this.restClient = RestClient.builder().build();
        this.objectMapper = new ObjectMapper();
        this.baseUrl = baseUrl == null ? "" : baseUrl.trim().replaceAll("/+$", "");
    }

    public RespuestaCompanero explotar(long creeperId) {
        return intercambiar(HttpMethod.POST, "/api/minecraft/entidades/" + creeperId + "/explosion");
    }

    public RespuestaCompanero openApi() {
        return intercambiar(HttpMethod.GET, "/v3/api-docs");
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    private RespuestaCompanero intercambiar(HttpMethod metodo, String ruta) {
        if (baseUrl.isBlank()) {
            throw new CompaneroNoConfiguradoException(
                    "Falta la URL de A3 (Marcelo): configurar apis.marcelo.base-url o MARCELO_API_URL.");
        }
        ResponseEntity<String> entidad = restClient.method(metodo)
                .uri(baseUrl + ruta)
                .retrieve()
                .onStatus(HttpStatusCode::isError, (solicitud, respuesta) -> {
                    byte[] bytes = respuesta.getBody() == null ? new byte[0] : respuesta.getBody().readAllBytes();
                    String crudo = new String(bytes, StandardCharsets.UTF_8);
                    throw new MarceloRechazoException(
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
