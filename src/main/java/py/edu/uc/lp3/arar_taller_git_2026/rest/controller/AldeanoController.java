package py.edu.uc.lp3.arar_taller_git_2026.rest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import py.edu.uc.lp3.arar_taller_git_2026.domain.Aldeano;
import py.edu.uc.lp3.arar_taller_git_2026.domain.AldeanoArmero;
import py.edu.uc.lp3.arar_taller_git_2026.domain.AldeanoGranjero;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class AldeanoController {

    // Exposición Polimórfica (invoca trabajar() sobre la clase base)
    @GetMapping("/minecraft/trabajo")
    public Map<String, String> reporteTrabajo() {
        List<Aldeano> aldeanos = List.of(
            new AldeanoArmero(), // Usa constructor simple
            new AldeanoGranjero("Juan", 20, "15,64,12", "Pan", "Trigo") // Usa constructor sobrecargado
        );

        return aldeanos.stream().collect(Collectors.toMap(
            Aldeano::getNombre,
            Aldeano::trabajar
        ));
    }

    // Construcción desde URL con validación de reglas
    @GetMapping("/minecraft/crear-armero")
    public ResponseEntity<?> crearArmero(
            @RequestParam(defaultValue = "Pedro") String nombre,
            @RequestParam(defaultValue = "20") int vida,
            @RequestParam(defaultValue = "10,64,10") String posicion,
            @RequestParam(defaultValue = "Carne") String alimentacion,
            @RequestParam(defaultValue = "3") int nivelHerreria) {

        try {
            AldeanoArmero armero = new AldeanoArmero(nombre, vida, posicion, alimentacion, nivelHerreria);
            
            Map<String, Object> response = new HashMap<>();
            response.put("instancia", armero);
            response.put("comercioSimple", armero.comerciar());
            response.put("comercioSobrecargado", armero.comerciar("Casco de Hierro", 2)); // Demuestra sobrecarga
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            // Si viola una regla del dominio, se informa el rechazo
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Regla de dominio violada",
                "mensaje", e.getMessage()
            ));
        }
    }

    @GetMapping("/minecraft/crear-granjero")
    public ResponseEntity<?> crearGranjero(
            @RequestParam(defaultValue = "Juan") String nombre,
            @RequestParam(defaultValue = "20") int vida,
            @RequestParam(defaultValue = "15,64,12") String posicion,
            @RequestParam(defaultValue = "Pan") String alimentacion,
            @RequestParam(defaultValue = "Trigo") String tipoCultivo) {

        try {
            AldeanoGranjero granjero = new AldeanoGranjero(nombre, vida, posicion, alimentacion, tipoCultivo);
            
            Map<String, Object> response = new HashMap<>();
            response.put("instancia", granjero);
            response.put("comercioSimple", granjero.comerciar());
            response.put("comercioSobrecargado", granjero.comerciar("Zanahoria", 12));
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Regla de dominio violada",
                "mensaje", e.getMessage()
            ));
        }
    }
}
