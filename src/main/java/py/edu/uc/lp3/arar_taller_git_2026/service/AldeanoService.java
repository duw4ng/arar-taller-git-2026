package py.edu.uc.lp3.arar_taller_git_2026.service;

import java.util.List;
import java.util.Locale;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import py.edu.uc.lp3.arar_taller_git_2026.domain.Aldeano;
import py.edu.uc.lp3.arar_taller_git_2026.domain.AldeanoArmero;
import py.edu.uc.lp3.arar_taller_git_2026.domain.AldeanoGranjero;
import py.edu.uc.lp3.arar_taller_git_2026.domain.exception.EntidadNoEncontradaException;
import py.edu.uc.lp3.arar_taller_git_2026.domain.exception.ReglaDeJuegoException;
import py.edu.uc.lp3.arar_taller_git_2026.repository.AldeanoRepository;
import py.edu.uc.lp3.arar_taller_git_2026.rest.dto.AldeanoPatchRequest;
import py.edu.uc.lp3.arar_taller_git_2026.rest.dto.AldeanoRequest;

@Service
@Transactional(readOnly = true)
public class AldeanoService {

    private final AldeanoRepository repository;

    public AldeanoService(AldeanoRepository repository) {
        this.repository = repository;
    }

    public List<Aldeano> listar() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public List<Aldeano> listarPorProfesion(String profesion) {
        if (profesion == null || profesion.isBlank()) {
            return listar();
        }
        return repository.findByProfesionIgnoreCase(profesion.trim());
    }

    public Aldeano buscar(Long id) {
        if (id == null) {
            throw new ReglaDeJuegoException("El identificador no puede ser nulo.");
        }
        return repository.findById(id)
                .orElseThrow(() -> new EntidadNoEncontradaException("No existe un aldeano con id " + id + "."));
    }

    @Transactional
    public Aldeano crear(AldeanoRequest solicitud) {
        validarDatosBase(solicitud);
        String tipo = normalizarTipo(solicitud.tipo());
        Aldeano aldeano = switch (tipo) {
            case "ARMERO" -> new AldeanoArmero(
                    solicitud.nombre(),
                    solicitud.vida(),
                    solicitud.posicion(),
                    solicitud.alimentacion(),
                    nivelHerreriaRequerido(solicitud.nivelHerreria()));
            case "GRANJERO" -> new AldeanoGranjero(
                    solicitud.nombre(),
                    solicitud.vida(),
                    solicitud.posicion(),
                    solicitud.alimentacion(),
                    tipoCultivoRequerido(solicitud.tipoCultivo()));
            default -> throw new ReglaDeJuegoException("El tipo de aldeano debe ser ARMERO o GRANJERO.");
        };
        return repository.save(aldeano);
    }

    @Transactional
    public Aldeano reemplazar(Long id, AldeanoRequest solicitud) {
        Aldeano actual = buscar(id);
        validarDatosBase(solicitud);
        String tipoSolicitado = solicitud.tipo() == null || solicitud.tipo().isBlank()
                ? tipoDe(actual)
                : normalizarTipo(solicitud.tipo());
        if (!tipoSolicitado.equals(tipoDe(actual))) {
            throw new ReglaDeJuegoException("No se permite cambiar el tipo de un aldeano existente.");
        }
        actual.setNombre(solicitud.nombre());
        actual.setVida(solicitud.vida());
        actual.setPosicion(solicitud.posicion());
        actual.setAlimentacion(solicitud.alimentacion());
        if (actual instanceof AldeanoArmero armero) {
            armero.setNivelHerreria(nivelHerreriaRequerido(solicitud.nivelHerreria()));
        } else if (actual instanceof AldeanoGranjero granjero) {
            granjero.setTipoCultivo(tipoCultivoRequerido(solicitud.tipoCultivo()));
        }
        return repository.save(actual);
    }

    @Transactional
    public Aldeano actualizarParcial(Long id, AldeanoPatchRequest solicitud) {
        if (solicitud == null) {
            throw new ReglaDeJuegoException("El cuerpo de la solicitud no puede estar vacío.");
        }
        Aldeano actual = buscar(id);
        if (solicitud.tipo() != null && !solicitud.tipo().isBlank()) {
            String tipoSolicitado = normalizarTipo(solicitud.tipo());
            if (!tipoSolicitado.equals(tipoDe(actual))) {
                throw new ReglaDeJuegoException("No se permite cambiar el tipo de un aldeano existente.");
            }
        }
        if (solicitud.nombre() != null) {
            actual.setNombre(solicitud.nombre());
        }
        if (solicitud.vida() != null) {
            actual.setVida(solicitud.vida());
        }
        if (solicitud.posicion() != null) {
            actual.setPosicion(solicitud.posicion());
        }
        if (solicitud.alimentacion() != null) {
            actual.setAlimentacion(solicitud.alimentacion());
        }
        if (actual instanceof AldeanoArmero armero && solicitud.nivelHerreria() != null) {
            armero.setNivelHerreria(solicitud.nivelHerreria());
        }
        if (actual instanceof AldeanoGranjero granjero && solicitud.tipoCultivo() != null) {
            granjero.setTipoCultivo(solicitud.tipoCultivo());
        }
        return repository.save(actual);
    }

    @Transactional
    public void eliminar(Long id) {
        Aldeano actual = buscar(id);
        repository.delete(actual);
    }

    private void validarDatosBase(AldeanoRequest solicitud) {
        if (solicitud == null) {
            throw new ReglaDeJuegoException("El cuerpo de la solicitud no puede estar vacío.");
        }
        if (solicitud.nombre() == null || solicitud.nombre().isBlank()) {
            throw new ReglaDeJuegoException("El nombre es obligatorio.");
        }
        if (solicitud.vida() == null || solicitud.vida() < 0) {
            throw new ReglaDeJuegoException("La vida es obligatoria y no puede ser negativa.");
        }
        if (solicitud.posicion() == null || solicitud.posicion().isBlank()) {
            throw new ReglaDeJuegoException("La posición es obligatoria.");
        }
        if (solicitud.alimentacion() == null || solicitud.alimentacion().isBlank()) {
            throw new ReglaDeJuegoException("La alimentación es obligatoria.");
        }
    }

    private String normalizarTipo(String tipo) {
        if (tipo == null || tipo.isBlank()) {
            throw new ReglaDeJuegoException("El tipo de aldeano es obligatorio (ARMERO o GRANJERO).");
        }
        return tipo.trim().toUpperCase(Locale.ROOT);
    }

    private String tipoDe(Aldeano aldeano) {
        if (aldeano instanceof AldeanoArmero) {
            return "ARMERO";
        }
        if (aldeano instanceof AldeanoGranjero) {
            return "GRANJERO";
        }
        throw new ReglaDeJuegoException("El aldeano no pertenece a una familia conocida.");
    }

    private int nivelHerreriaRequerido(Integer nivelHerreria) {
        if (nivelHerreria == null) {
            throw new ReglaDeJuegoException("El nivel de herrería es obligatorio para un armero.");
        }
        return nivelHerreria;
    }

    private String tipoCultivoRequerido(String tipoCultivo) {
        if (tipoCultivo == null || tipoCultivo.isBlank()) {
            throw new ReglaDeJuegoException("El tipo de cultivo es obligatorio para un granjero.");
        }
        return tipoCultivo;
    }
}
