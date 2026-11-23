package py.edu.uc.lp3.arar_taller_git_2026.rest.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.tags.Tag;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Minecraft API — POO-09",
                version = "1.0.0",
                description = """
                        Servicio REST de Minecraft sobre Spring Boot 4.1.1, documentado con springdoc-openapi 3.1.1.
                        El contrato JSON está en `/v3/api-docs`, el YAML en `/v3/api-docs.yaml` y la UI en `/swagger-ui.html`.

                        ## Operaciones que modifican H2
                        | Operación | Efecto real |
                        |---|---|
                        | `POST /minecraft/aldeanos` | Inserta una fila en `aldeanos`. |
                        | `PUT /minecraft/aldeanos/{id}` | Reemplaza los campos de la fila existente. |
                        | `PATCH /minecraft/aldeanos/{id}` | Actualiza sólo los campos presentes de la fila existente. |
                        | `DELETE /minecraft/aldeanos/{id}` | Borra físicamente la fila. |
                        | `POST /minecraft/simulaciones/explosion` | **MODIFICA H2**: recalcula la vida de todos los aldeanos persistidos y los guarda con `saveAll`. **No es una validación sin efectos.** |
                        | `POST /minecraft/escenarios/explosiones-cruzadas` | **MODIFICA H2 de A1**: inserta una fila en `jugadas_cruzadas` sólo cuando A2 y A3 devuelven 200. **No es una validación sin efectos.** |
                        | `GET ...` | Sólo lectura; no escribe en H2. |

                        ## Las dos reglas del juego
                        1. **Onda expansiva:** cada aldeano recibe `max(0, round(potencia - distancia))` de daño; si está fuera del radio, recibe `0`.
                        2. **Mitigación por oficio:** el armero reduce el daño en `nivelHerreria * 2`; el granjero recibe el daño completo y, si sobrevive después de recibir daño, queda con `enPanico = true`.

                        ## Ejemplo aceptado
                        `POST /minecraft/simulaciones/explosion` con
                        `{"potencia":30,"radio":30,"posicionCreeper":"0,64,0","tiempoCarga":3}` → `200`.
                        Un armero de nivel 2 a 5 bloques recibe `25 - 4 = 21` de daño; un granjero a 25 bloques recibe `5` y entra en pánico si sigue vivo. La vida nueva queda guardada en H2.

                        ## Ejemplo rechazado
                        `POST /minecraft/simulaciones/explosion` con
                        `{"potencia":0,"radio":30,"tiempoCarga":3}` → `400 REGLA_DE_JUEGO`
                        porque `SimulacionService` exige `potencia > 0`. Tampoco se aceptan `radio < 0`, `tiempoCarga <= 0`, tipo de aldeano distinto de `ARMERO`/`GRANJERO`, `nivelHerreria` fuera de `1..5`, vida negativa ni revivir un aldeano muerto.

                        ## Errores
                        - `400 REGLA_DE_JUEGO`: `ReglaDeJuegoException`, invariante de dominio incumplido.
                        - `404 ENTIDAD_NO_ENCONTRADA`: `EntidadNoEncontradaException`, id inexistente.
                        - `400 ERROR_DE_DOMINIO`: fallback de `MinecraftException`.

                        ## POO-09 — APIs cruzadas
                        - **A1 (este servicio)** orquesta el escenario; **A2 (Elias)** valida Creeper/Zombie con distancia y rango; **A3 (Marcelo)** detona por posiciones y daña a Zombies y Cerdos.
                        - `POST /minecraft/escenarios/explosiones-cruzadas` recibe ids ya creados en A2 y A3, llama a ambos y guarda en H2 de A1 **sólo si los dos aceptan**.
                        - Si uno rechaza, A1 devuelve `502 COMPANERO_RECHAZO` con `origen` (`A2 Elias` o `A3 Marcelo`), `estadoCompanero` y `codigoCompanero`; H2 de A1 queda igual.
                        - Advertencia: A2 y A3 sí consumen sus propias entidades cuando aceptan; un reintento requiere ids frescos en esos servicios.
                        - `GET /minecraft/escenarios/diagnostico` demuestra el primer contacto leyendo `/v3/api-docs` de ambos compañeros.
                        - `503 COMPANERO_NO_CONFIGURADO`: falta `ELIAS_API_URL` o `MARCELO_API_URL`.
                        """,
                contact = @Contact(name = "Alexandre Avalos"),
                license = @License(name = "Apache 2.0")),
        tags = {
                @Tag(name = "aldeanos", description = "CRUD de aldeanos persistidos en H2. POST, PUT, PATCH y DELETE escriben; GET sólo lee."),
                @Tag(name = "simulaciones", description = "Simulación de explosión. Escribe en H2: persiste la vida resultante de todos los aldeanos."),
                @Tag(name = "escenarios", description = "Challenge POO-09. Consulta OpenAPI y APIs de A2/A3; guarda en H2 de A1 sólo con doble aceptación."),
                @Tag(name = "dominio", description = "Endpoints de sólo lectura sobre el dominio y la interfaz Danable.")
        })
public class OpenApiConfig {
}
