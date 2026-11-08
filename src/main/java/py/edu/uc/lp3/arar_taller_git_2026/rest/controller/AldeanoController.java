package py.edu.uc.lp3.arar_taller_git_2026.rest.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import py.edu.uc.lp3.arar_taller_git_2026.domain.Aldeano;
import py.edu.uc.lp3.arar_taller_git_2026.rest.dto.AldeanoPatchRequest;
import py.edu.uc.lp3.arar_taller_git_2026.rest.dto.AldeanoRequest;
import py.edu.uc.lp3.arar_taller_git_2026.service.AldeanoService;

@RestController
@RequestMapping("/minecraft/aldeanos")
public class AldeanoController {

    private final AldeanoService service;

    public AldeanoController(AldeanoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Aldeano> crear(@RequestBody AldeanoRequest solicitud) {
        Aldeano creado = service.crear(solicitud);
        return ResponseEntity
                .created(URI.create("/minecraft/aldeanos/" + creado.getId()))
                .body(creado);
    }

    @GetMapping
    public List<Aldeano> listar(@RequestParam(required = false) String profesion) {
        return service.listarPorProfesion(profesion);
    }

    @GetMapping("/{id}")
    public Aldeano buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @PutMapping("/{id}")
    public Aldeano reemplazar(@PathVariable Long id, @RequestBody AldeanoRequest solicitud) {
        return service.reemplazar(id, solicitud);
    }

    @PatchMapping("/{id}")
    public Aldeano actualizar(@PathVariable Long id, @RequestBody AldeanoPatchRequest solicitud) {
        return service.actualizarParcial(id, solicitud);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
