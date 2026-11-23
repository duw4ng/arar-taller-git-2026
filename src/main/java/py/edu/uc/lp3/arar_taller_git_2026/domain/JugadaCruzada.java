package py.edu.uc.lp3.arar_taller_git_2026.domain;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "jugadas_cruzadas")
@Schema(name = "JugadaCruzada", description = "Jugada guardada en el H2 de A1 sólo después de que A2 y A3 aceptaron.")
public class JugadaCruzada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Id de la jugada en A1.", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Id del Creeper en la API de Elias (A2).", example = "1")
    private Long creeperEliasId;

    @Schema(description = "Id del Zombie en la API de Elias (A2).", example = "2")
    private Long zombieEliasId;

    @Schema(description = "Distancia aceptada por Elias (A2).", example = "1")
    private int distanciaElias;

    @Schema(description = "Id del Creeper en la API de Marcelo (A3).", example = "1")
    private Long creeperMarceloId;

    @Schema(description = "Código HTTP devuelto por A2.", example = "200")
    private int estadoElias;

    @Schema(description = "Código de negocio devuelto por A2.", example = "OK")
    private String codigoElias;

    @Schema(description = "Código HTTP devuelto por A3.", example = "200")
    private int estadoMarcelo;

    @Schema(description = "Código de negocio devuelto por A3.", example = "OK")
    private String codigoMarcelo;

    @Column(length = 2000)
    @Schema(description = "Respuesta cruda de A2.", accessMode = Schema.AccessMode.READ_ONLY)
    private String respuestaElias;

    @Column(length = 2000)
    @Schema(description = "Respuesta cruda de A3.", accessMode = Schema.AccessMode.READ_ONLY)
    private String respuestaMarcelo;

    @Schema(description = "Momento en que A1 persistió la jugada.", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime registradaEn;

    protected JugadaCruzada() {
        this.registradaEn = LocalDateTime.now();
    }

    public JugadaCruzada(Long creeperEliasId, Long zombieEliasId, int distanciaElias,
            Long creeperMarceloId, int estadoElias, String codigoElias,
            int estadoMarcelo, String codigoMarcelo, String respuestaElias, String respuestaMarcelo) {
        this.creeperEliasId = creeperEliasId;
        this.zombieEliasId = zombieEliasId;
        this.distanciaElias = distanciaElias;
        this.creeperMarceloId = creeperMarceloId;
        this.estadoElias = estadoElias;
        this.codigoElias = codigoElias;
        this.estadoMarcelo = estadoMarcelo;
        this.codigoMarcelo = codigoMarcelo;
        this.respuestaElias = respuestaElias;
        this.respuestaMarcelo = respuestaMarcelo;
        this.registradaEn = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Long getCreeperEliasId() {
        return creeperEliasId;
    }

    public Long getZombieEliasId() {
        return zombieEliasId;
    }

    public int getDistanciaElias() {
        return distanciaElias;
    }

    public Long getCreeperMarceloId() {
        return creeperMarceloId;
    }

    public int getEstadoElias() {
        return estadoElias;
    }

    public String getCodigoElias() {
        return codigoElias;
    }

    public int getEstadoMarcelo() {
        return estadoMarcelo;
    }

    public String getCodigoMarcelo() {
        return codigoMarcelo;
    }

    public String getRespuestaElias() {
        return respuestaElias;
    }

    public String getRespuestaMarcelo() {
        return respuestaMarcelo;
    }

    public LocalDateTime getRegistradaEn() {
        return registradaEn;
    }
}
