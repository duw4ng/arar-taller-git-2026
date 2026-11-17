package py.edu.uc.lp3.arar_taller_git_2026.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import py.edu.uc.lp3.arar_taller_git_2026.domain.Aldeano;
import py.edu.uc.lp3.arar_taller_git_2026.domain.AldeanoArmero;
import py.edu.uc.lp3.arar_taller_git_2026.domain.AldeanoGranjero;
import py.edu.uc.lp3.arar_taller_git_2026.domain.Creeper;
import py.edu.uc.lp3.arar_taller_git_2026.domain.Danable;
import py.edu.uc.lp3.arar_taller_git_2026.domain.Posicion;
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

    public List<Danable> listarDanables() {
        List<Danable> danables = new ArrayList<>(listar());
        danables.add(new Creeper("Creeper de muestra", 20, new Posicion(0, 64, 0), 10, "aldeanos", 3));
        return danables;
    }

    public Map<String, String> mensajesTrabajo() {
        Map<String, String> respuesta = new LinkedHashMap<>();
        for (Aldeano aldeano : listar()) {
            respuesta.put(aldeano.getNombre(), aldeano.trabajar());
        }
        return respuesta;
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
        Posicion posicion = validarDatosBase(solicitud);
        String tipo = normalizarTipo(solicitud.tipo());
        Aldeano aldeano = switch (tipo) {
            case "ARMERO" -> new AldeanoArmero(
                    solicitud.nombre().trim(),
                    solicitud.vida(),
                    posicion,
                    solicitud.alimentacion().trim(),
                    nivelHerreriaRequerido(solicitud.nivelHerreria()));
            case "GRANJERO" -> new AldeanoGranjero(
                    solicitud.nombre().trim(),
                    solicitud.vida(),
                    posicion,
                    solicitud.alimentacion().trim(),
                    tipoCultivoRequerido(solicitud.tipoCultivo()));
            default -> throw new ReglaDeJuegoException("El tipo de aldeano debe ser ARMERO o GRANJERO.");
        };
        return repository.save(aldeano);
    }

    @Transactional
    public Aldeano reemplazar(Long id, AldeanoRequest solicitud) {
        Aldeano actual = buscar(id);
        Posicion posicion = validarDatosBase(solicitud);
        String tipoSolicitado = solicitud.tipo() == null || solicitud.tipo().isBlank()
                ? tipoDe(actual)
                : normalizarTipo(solicitud.tipo());
        if (!tipoSolicitado.equals(tipoDe(actual))) {
            throw new ReglaDeJuegoException("No se permite cambiar el tipo de un aldeano existente.");
        }
        validarVidaDeAldeano(actual, solicitud.vida());
        actual.setNombre(solicitud.nombre().trim());
        actual.setVida(solicitud.vida());
        actual.setPosicion(posicion);
        actual.setAlimentacion(solicitud.alimentacion().trim());
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
            validarTexto(solicitud.nombre(), "El nombre");
            actual.setNombre(solicitud.nombre().trim());
        }
        if (solicitud.vida() != null) {
            if (solicitud.vida() < 0) {
                throw new ReglaDeJuegoException("La vida no puede ser negativa.");
            }
            validarVidaDeAldeano(actual, solicitud.vida());
            actual.setVida(solicitud.vida());
        }
        if (solicitud.posicion() != null) {
            actual.setPosicion(PosicionParser.parsear(solicitud.posicion()));
        }
        if (solicitud.alimentacion() != null) {
            validarTexto(solicitud.alimentacion(), "La alimentación");
            actual.setAlimentacion(solicitud.alimentacion().trim());
        }
        if (actual instanceof AldeanoArmero armero && solicitud.nivelHerreria() != null) {
            armero.setNivelHerreria(nivelHerreriaRequerido(solicitud.nivelHerreria()));
        }
        if (actual instanceof AldeanoGranjero granjero && solicitud.tipoCultivo() != null) {
            granjero.setTipoCultivo(tipoCultivoRequerido(solicitud.tipoCultivo()));
        }
        return repository.save(actual);
    }

    @Transactional
    public void eliminar(Long id) {
        Aldeano actual = buscar(id);
        repository.delete(actual);
    }

    private Posicion validarDatosBase(AldeanoRequest solicitud) {
        if (solicitud == null) {
            throw new ReglaDeJuegoException("El cuerpo de la solicitud no puede estar vacío.");
        }
        validarTexto(solicitud.nombre(), "El nombre");
        if (solicitud.vida() == null || solicitud.vida() < 0) {
            throw new ReglaDeJuegoException("La vida es obligatoria y no puede ser negativa.");
        }
        Posicion posicion = PosicionParser.parsear(solicitud.posicion());
        validarTexto(solicitud.alimentacion(), "La alimentación");
        return posicion;
    }

    private void validarTexto(String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            throw new ReglaDeJuegoException(campo + " es obligatorio.");
        }
    }

    private void validarVidaDeAldeano(Aldeano actual, int nuevaVida) {
        if (!actual.estaVivo() && nuevaVida > 0) {
            throw new ReglaDeJuegoException("Un aldeano muerto no puede volver a tener vida.");
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
        if (nivelHerreria < 1 || nivelHerreria > 5) {
            throw new ReglaDeJuegoException("El nivel de herrería debe estar entre 1 y 5.");
        }
        return nivelHerreria;
    }

    private String tipoCultivoRequerido(String tipoCultivo) {
        validarTexto(tipoCultivo, "El tipo de cultivo");
        return tipoCultivo.trim();
    }
}
