package py.edu.uc.lp3.arar_taller_git_2026.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import py.edu.uc.lp3.arar_taller_git_2026.minecraft.Aldeano;

@RestController
public class AldeanoController {

    @GetMapping("/minecraft/aldeano")
    public Aldeano crearAldeano(
        @RequestParam(defaultValue = "Aldeano PNJ") String nombre,
        @RequestParam(defaultValue = "20") int vida,
        @RequestParam(defaultValue = "0,0,0") String posicion,
        @RequestParam(defaultValue = "Pan") String alimentacion,
        @RequestParam(defaultValue = "Granjero") String profesion
    ) {
        // Delegación directa al dominio: el controlador no valida ni modifica reglas
        return new Aldeano(nombre, vida, posicion, alimentacion, profesion);
    }
}