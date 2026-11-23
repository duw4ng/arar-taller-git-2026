package py.edu.uc.lp3.arar_taller_git_2026.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import py.edu.uc.lp3.arar_taller_git_2026.client.CuerpoCompanero;
import py.edu.uc.lp3.arar_taller_git_2026.client.EliasClient;
import py.edu.uc.lp3.arar_taller_git_2026.client.MarceloClient;
import py.edu.uc.lp3.arar_taller_git_2026.client.RespuestaCompanero;
import py.edu.uc.lp3.arar_taller_git_2026.domain.JugadaCruzada;
import py.edu.uc.lp3.arar_taller_git_2026.domain.exception.EntidadNoEncontradaException;
import py.edu.uc.lp3.arar_taller_git_2026.domain.exception.ReglaDeJuegoException;
import py.edu.uc.lp3.arar_taller_git_2026.repository.JugadaCruzadaRepository;
import py.edu.uc.lp3.arar_taller_git_2026.rest.dto.CompaneroOpenApiResponse;
import py.edu.uc.lp3.arar_taller_git_2026.rest.dto.EscenarioCruzadoRequest;

@Service
public class EscenarioCruzadoService {

    private final EliasClient eliasClient;
    private final MarceloClient marceloClient;
    private final JugadaCruzadaRepository jugadaRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public EscenarioCruzadoService(EliasClient eliasClient, MarceloClient marceloClient,
            JugadaCruzadaRepository jugadaRepository) {
        this.eliasClient = eliasClient;
        this.marceloClient = marceloClient;
        this.jugadaRepository = jugadaRepository;
    }

    @Transactional
    public JugadaCruzada ejecutar(EscenarioCruzadoRequest solicitud) {
        validar(solicitud);
        RespuestaCompanero respuestaElias = eliasClient.explotar(
                solicitud.creeperEliasId(), solicitud.zombieEliasId(), solicitud.distanciaElias());
        RespuestaCompanero respuestaMarcelo = marceloClient.explotar(solicitud.creeperMarceloId());
        JugadaCruzada jugada = new JugadaCruzada(
                solicitud.creeperEliasId(),
                solicitud.zombieEliasId(),
                solicitud.distanciaElias(),
                solicitud.creeperMarceloId(),
                respuestaElias.estado(),
                CuerpoCompanero.codigo(objectMapper, respuestaElias.crudo()),
                respuestaMarcelo.estado(),
                CuerpoCompanero.codigo(objectMapper, respuestaMarcelo.crudo()),
                respuestaElias.crudo(),
                respuestaMarcelo.crudo());
        return jugadaRepository.save(jugada);
    }

    @Transactional(readOnly = true)
    public JugadaCruzada obtener(long id) {
        return jugadaRepository.findById(id)
                .orElseThrow(() -> new EntidadNoEncontradaException("No existe la jugada cruzada con id " + id));
    }

    @Transactional(readOnly = true)
    public List<JugadaCruzada> listar() {
        return jugadaRepository.findAll();
    }

    public CompaneroOpenApiResponse diagnosticarElias() {
        return diagnosticar("A2 Elias", eliasClient.getBaseUrl(), eliasClient::openApi);
    }

    public CompaneroOpenApiResponse diagnosticarMarcelo() {
        return diagnosticar("A3 Marcelo", marceloClient.getBaseUrl(), marceloClient::openApi);
    }

    private CompaneroOpenApiResponse diagnosticar(String origen, String baseUrl,
            java.util.function.Supplier<RespuestaCompanero> proveedor) {
        try {
            RespuestaCompanero respuesta = proveedor.get();
            return new CompaneroOpenApiResponse(
                    origen,
                    baseUrl,
                    respuesta.estado(),
                    CuerpoCompanero.titulo(objectMapper, respuesta.cuerpo()),
                    CuerpoCompanero.version(objectMapper, respuesta.cuerpo()));
        } catch (RuntimeException excepcion) {
            return new CompaneroOpenApiResponse(
                    origen,
                    baseUrl,
                    0,
                    "NO_DISPONIBLE",
                    excepcion.getClass().getSimpleName() + ": " + excepcion.getMessage());
        }
    }

    private void validar(EscenarioCruzadoRequest solicitud) {
        if (solicitud == null) {
            throw new ReglaDeJuegoException("El escenario cruzado no puede ser nulo.");
        }
        if (solicitud.creeperEliasId() == null || solicitud.creeperEliasId() <= 0) {
            throw new ReglaDeJuegoException("creeperEliasId debe ser un id positivo creado en A2.");
        }
        if (solicitud.zombieEliasId() == null || solicitud.zombieEliasId() <= 0) {
            throw new ReglaDeJuegoException("zombieEliasId debe ser un id positivo creado en A2.");
        }
        if (solicitud.distanciaElias() < 0) {
            throw new ReglaDeJuegoException("distanciaElias debe ser mayor o igual a 0.");
        }
        if (solicitud.creeperMarceloId() == null || solicitud.creeperMarceloId() <= 0) {
            throw new ReglaDeJuegoException("creeperMarceloId debe ser un id positivo creado en A3.");
        }
    }
}
