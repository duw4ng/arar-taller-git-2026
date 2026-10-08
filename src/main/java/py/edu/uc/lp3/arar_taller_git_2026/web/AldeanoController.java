package py.edu.uc.lp3.arar_taller_git_2026.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import py.edu.uc.lp3.arar_taller_git_2026.minecraft.Aldeano;
import py.edu.uc.lp3.arar_taller_git_2026.minecraft.AldeanoArmero;
import py.edu.uc.lp3.arar_taller_git_2026.minecraft.AldeanoGranjero;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class AldeanoController {

    @GetMapping("/minecraft/trabajo")
    public Map<String, String> reporteTrabajo() {
        // Se instancian las hijas, pero se tratan genéricamente como "Aldeano"
        List<Aldeano> aldeanos = List.of(
            new AldeanoArmero("Pedro", 20, "10,64,10", "Carne", 3),
            new AldeanoGranjero("Juan", 20, "15,64,12", "Pan", "Trigo")
        );

        // Se envía el mensaje polimórfico trabajar() sin preguntar qué tipo de clase es
        return aldeanos.stream().collect(Collectors.toMap(
            Aldeano::getNombre,
            Aldeano::trabajar
        ));
    }

    // Endpoint para construir un AldeanoArmero desde la URL
    @GetMapping("/minecraft/crear-armero")
    public AldeanoArmero crearArmero(
            @RequestParam(defaultValue = "Pedro") String nombre,
            @RequestParam(defaultValue = "20") int vida,
            @RequestParam(defaultValue = "10,64,10") String posicion,
            @RequestParam(defaultValue = "Carne") String alimentacion,
            @RequestParam(defaultValue = "3") int nivelHerreria) {

        return new AldeanoArmero(nombre, vida, posicion, alimentacion, nivelHerreria);
    }

    // Endpoint para construir un AldeanoGranjero desde la URL
    @GetMapping("/minecraft/crear-granjero")
    public AldeanoGranjero crearGranjero(
            @RequestParam(defaultValue = "Juan") String nombre,
            @RequestParam(defaultValue = "20") int vida,
            @RequestParam(defaultValue = "15,64,12") String posicion,
            @RequestParam(defaultValue = "Pan") String alimentacion,
            @RequestParam(defaultValue = "Trigo") String tipoCultivo) {

        return new AldeanoGranjero(nombre, vida, posicion, alimentacion, tipoCultivo);
    }
}
