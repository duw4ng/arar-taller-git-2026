package py.edu.uc.lp3.arar_taller_git_2026.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "EscenarioCruzadoRequest", description = "Ids ya creados en las APIs de A2 y A3; A1 no crea entidades ajenas.")
public record EscenarioCruzadoRequest(

        @Schema(description = "Id del Creeper vivo en la API de Elias (A2).", example = "1")
        Long creeperEliasId,

        @Schema(description = "Id del Zombie vivo en la API de Elias (A2).", example = "2")
        Long zombieEliasId,

        @Schema(description = "Distancia entre Creeper y Zombie para A2; Elias exige 0..rango.", example = "1")
        int distanciaElias,

        @Schema(description = "Id del Creeper vivo en la API de Marcelo (A3).", example = "1")
        Long creeperMarceloId) {
}
