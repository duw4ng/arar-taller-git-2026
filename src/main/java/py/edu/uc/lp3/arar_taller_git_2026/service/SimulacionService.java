package py.edu.uc.lp3.arar_taller_git_2026.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import py.edu.uc.lp3.arar_taller_git_2026.domain.Aldeano;
import py.edu.uc.lp3.arar_taller_git_2026.domain.Creeper;
import py.edu.uc.lp3.arar_taller_git_2026.domain.exception.ReglaDeJuegoException;
import py.edu.uc.lp3.arar_taller_git_2026.repository.AldeanoRepository;
import py.edu.uc.lp3.arar_taller_git_2026.rest.dto.ExplosionRequest;
import py.edu.uc.lp3.arar_taller_git_2026.rest.dto.ExplosionResponse;
import py.edu.uc.lp3.arar_taller_git_2026.rest.dto.ResultadoAldeano;

@Service
public class SimulacionService {

    private final AldeanoRepository repository;

    public SimulacionService(AldeanoRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public ExplosionResponse simularExplosion(ExplosionRequest solicitud) {
        if (solicitud == null) {
            throw new ReglaDeJuegoException("El cuerpo de la simulación no puede estar vacío.");
        }
        int potencia = solicitud.potencia() == null ? 0 : solicitud.potencia();
        if (potencia <= 0) {
            throw new ReglaDeJuegoException("La potencia de la explosión debe ser mayor a 0.");
        }
        int radio = solicitud.radio() == null ? 0 : solicitud.radio();
        if (radio < 0) {
            throw new ReglaDeJuegoException("El radio de la explosión no puede ser negativo.");
        }
        int tiempoCarga = solicitud.tiempoCarga() == null ? 3 : solicitud.tiempoCarga();
        if (tiempoCarga <= 0) {
            throw new ReglaDeJuegoException("El tiempo de carga del Creeper debe ser mayor a 0.");
        }
        String posicionCreeper = solicitud.posicionCreeper() == null || solicitud.posicionCreeper().isBlank()
                ? "0,64,0"
                : solicitud.posicionCreeper();

        Creeper creeper = new Creeper("Creeper simulado", 20, posicionCreeper, potencia, "aldeanos", tiempoCarga);
        List<Aldeano> aldeanos = repository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        List<ResultadoAldeano> resultados = new ArrayList<>();

        for (Aldeano aldeano : aldeanos) {
            double distancia = aldeano.distanciaA(creeper);
            int danoBase = distancia > radio ? 0 : creeper.danoPorOnda(potencia, distancia);
            int vidaAntes = aldeano.getVida();
            int danoRecibido = aldeano.resistirExplosion(danoBase);
            resultados.add(new ResultadoAldeano(
                    aldeano.getId(),
                    aldeano.getNombre(),
                    aldeano.getClass().getSimpleName(),
                    vidaAntes,
                    aldeano.getVida(),
                    danoRecibido,
                    redondear(distancia)));
        }

        repository.saveAll(aldeanos);
        creeper.explotar();

        return new ExplosionResponse(potencia, radio, posicionCreeper, creeper.getVida(), resultados);
    }

    private double redondear(double valor) {
        return Math.round(valor * 100.0) / 100.0;
    }
}
