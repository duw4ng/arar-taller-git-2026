package py.edu.uc.lp3.arar_taller_git_2026.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class IndexController {

    @GetMapping("/")
    public Map<String, String> index() {
        return Map.of(
            "estado", "Servicio activo",
            "autor", "Alexandre Avalos",
            "dominio", "Minecraft"
        );
    }
}