package py.edu.uc.lp3.arar_taller_git_2026.rest.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "JugadaCruzadaResponse", description = "Jugada ya persistida en el H2 de A1 tras doble aceptación.")
public record JugadaCruzadaResponse(

        @Schema(description = "Id de la jugada en A1.", example = "1") Long id,
        @Schema(description = "Creeper usado en A2.", example = "1") Long creeperEliasId,
        @Schema(description = "Zombie usado en A2.", example = "2") Long zombieEliasId,
        @Schema(description = "Distancia usada en A2.", example = "1") int distanciaElias,
        @Schema(description = "Creeper usado en A3.", example = "1") Long creeperMarceloId,
        @Schema(description = "HTTP de A2.", example = "200") int estadoElias,
        @Schema(description = "Código de A2.", example = "OK") String codigoElias,
        @Schema(description = "HTTP de A3.", example = "200") int estadoMarcelo,
        @Schema(description = "Código de A3.", example = "OK") String codigoMarcelo,
        @Schema(description = "Momento del guardado en A1.") LocalDateTime registradaEn) {
}
