# Bitácora de asistencia de inteligencia artificial

- **Fechas:** 8 de noviembre de 2026 (entrega inicial), 16 de noviembre de 2026 (corrección posterior a la devolución) y 22 de noviembre de 2026 (POO-09: OpenAPI y Swagger).
- **Asistente o agente:** OpenCode (CLI).
- **Modelo exacto del LLM:** `opencode/big-pickle`.
- **Modalidad:** asistencia en la implementación completa de la entrega POO-08: dependencias, entidades JPA, repositorio, servicios, DTOs, controllers, jerarquía de excepciones, colección de Insomnia y documentación; corrección de los criterios observados por la cátedra; y extensión POO-09 con OpenAPI/Swagger.

## Resumen de prompts

1. **Pedido inicial.** «Usando mi carpeta `arar-taller-git-2026` quiero que hagas lo siguiente: OO-08 — CRUD REST, H2, reglas de juego y excepciones…» junto con el enunciado, la rúbrica, el template de paquetes y los entregables. Se pidió publicar un servicio Spring Boot sobre el modelado de Minecraft, con persistencia H2 de tres clases, CRUD REST, dos reglas de negocio, simulación persistida, jerarquía de excepciones, interfaz de dominio, colección de Insomnia, README, bitácora y especificaciones.

2. **Lectura de contexto.** Se solicitó revisar el repositorio existente, el `README.md` de POO-06, el `pom.xml`, las propiedades, el modelado de Minecraft y los controllers previos para continuar sobre ese trabajo sin romper la herencia, la sobrecarga ni la sobreescritura ya documentadas.

3. **Implementación.** Se pidió agregar `spring-boot-starter-data-jpa` y el driver H2; mapear la familia `Aldeano`/`AldeanoArmero`/`AldeanoGranjero`; crear `AldeanoRepository`, servicios, DTOs y controllers REST; modelar las reglas de explosión en el dominio; crear la jerarquía `MinecraftException` → `ReglaDeJuegoException` y `EntidadNoEncontradaException`; y traducir cada tipo de excepción con un handler HTTP distinto.

4. **Insomnia y documentación.** Se pidió generar una colección importable con el CRUD, un `POST` ilegal, un id inexistente, el endpoint de la interfaz, la simulación y un pedido por cada excepción hija; actualizar el README con las propiedades de H2, el diagrama Mermaid y las respuestas a «Qué hay que pensar»; crear `BITACORA.md`; y preparar las especificaciones para Classroom.

5. **Verificación.** Se pidió compilar, ejecutar las pruebas y levantar el servicio para recorrer el CRUD, la simulación y los errores con `curl`, comprobando que el estado quedara persistido en H2.

## Corrección posterior a la devolución (16 de noviembre de 2026)

1. **Lectura de la devolución.** Se pegó la corrección completa de la cátedra (18/24, nota 7,5) y se pidió corregir los criterios 2, 3 y 8 sin romper los criterios que ya estaban en 3.

2. **Capa service y excepciones.** Se pidió sacar del dominio toda validación, regla de juego y lanzamiento de excepciones; mover las validaciones a `AldeanoService`; mover las dos reglas de la explosión a `SimulacionService`; y hacer que `IndexController` y `DanableController` dependan sólo de `AldeanoService`, nunca del repositorio.

3. **Invariantes reales.** Se pidió reemplazar la posición `String` por una `Posicion` numérica (`@Embeddable` con `x`, `y`, `z` en columnas `double`), parseada y validada en el service; y rechazar cualquier `PUT` o `PATCH` que intente revivir a un aldeano con `vida = 0`.

4. **Documentación.** Se pidió actualizar el diagrama Mermaid y el README para que reflejen que el controller sólo delega en `SimulacionService`, que el repositorio se usa sólo desde los services y que la posición es numérica; agregar la licencia; y actualizar las especificaciones y esta bitácora.

5. **Verificación.** Se pidió compilar y levantar el servicio para probar con `curl` el CRUD, la posición inválida, el `nivelHerreria = 99`, la simulación, la negativa a revivir, las dos excepciones hijas, el endpoint `Danable`, la persistencia tras reiniciar y el esquema numérico de la tabla en H2.

## POO-09: OpenAPI y Swagger (22 de noviembre de 2026)

1. **Pedido.** Se pidió revisar el repositorio <https://github.com/duw4ng/arar-taller-git-2026>, identificar la versión de Spring Boot, agregar una versión compatible de springdoc-openapi, habilitar JSON/YAML y Swagger UI, documentar el CRUD y la simulación con cuerpos, restricciones, respuestas y errores, explicar las dos reglas con ejemplos aceptado y rechazado, indicar qué operaciones escriben en H2, conservar el comportamiento existente, verificar arranque y pruebas, y actualizar README y bitácora.

2. **Versión y dependencia.** El `pom.xml` tenía Spring Boot `4.1.1` y Java `21`. Se agregó `org.springdoc:springdoc-openapi-starter-webmvc-ui:3.1.1`, de la línea 3.x built sobre Boot 4.1.x. La dependencia trae swagger-core `2.2.55` y Swagger UI `5.32.14`.

3. **Habilitación.** Se agregaron en `application.properties` las rutas `/v3/api-docs`, `/v3/api-docs.yaml` y `/swagger-ui.html`. Se creó `rest/config/OpenApiConfig.java` con `@OpenAPIDefinition`: título POO-09, autor, licencia, las dos reglas, los ejemplos y la tabla de operaciones que modifican H2. No se cambió ninguna firma ni lógica de negocio.

4. **Documentación.** Se anotaron los cinco controllers y los DTOs con `@Tag`, `@Operation`, `@ApiResponses`, `@Parameter`, `@RequestBody` de OpenAPI, `@Schema`, `@ExampleObject` y `DiscriminatorMapping`. La simulación dice explícitamente que ejecuta `saveAll` y persiste la vida resultante; los `GET` se marcan como sólo lectura. Se agregaron pruebas que verifican que el JSON y el YAML se generan y que el contrato contiene CRUD, simulación y `Danable`.

5. **Verificación.** `./mvnw test` terminó con `Tests run: 3, Failures: 0, Errors: 0`. Dado que en Spring Boot 4 `AutoConfigureMockMvc` pasó a `org.springframework.boot.webmvc.test.autoconfigure`, se ajustó esa importación del test. Se ejecutó `./mvnw spring-boot:run` con H2 en memoria para no tocar la base del repo: `/v3/api-docs`, `/v3/api-docs.yaml` y `/swagger-ui.html` respondieron `200`. Con datos de prueba, el `POST` de explosión válido devolvió `200` y dejó vida `0` en el armero y `15` con `enPanico = true` en el granjero, confirmado por `GET /minecraft/aldeanos`; el `POST` con `potencia = 0` devolvió `400 REGLA_DE_JUEGO`.

## POO-09: APIs cruzadas (22 de noviembre de 2026)

1. **Revisión del enunciado real.** Se pidió revisar `ejercicio-poo-09-apis-cruzadas-2026-11-16.md` del repositorio de la cátedra. Se detectó que POO-09 no era sólo OpenAPI/Swagger: el challenge exige un mapa A1/A2/A3, consumo de dos APIs de compañeros, primer contacto con sus OpenAPI, un escenario propio, H2 de A1 escrito sólo con doble aceptación, colección de Insomnia y el formulario de Classroom.

2. **Reglas recibidas.** A2 = Elias Pont: Creeper y Zombie vivos, `0 <= distancia <= rango`, daño `max(1, 12 - 3 * distancia)`, `POST /api/hostiles/{creeperId}/explosiones` con `{"zombieId","distancia"}`, `200` con escritura o `422 FUERA_DE_ALCANCE` sin escritura. A3 = Marcelo Romero: crear entidades con `POST /api/minecraft/entidades`, detonar con `POST /api/minecraft/entidades/{id}/explosion` sin cuerpo, daño `12 - 3 * floor(distancia)` a `<= 3` bloques, `400 ENTIDAD_DESAPARECIDA` si se repite o `400 EXPLOSION_INVALIDA` si el detonador no es Creeper; también escribe en su H2 cuando acepta.

3. **Diseño del escenario.** A1 recibe ids ya creados en A2 y A3 en `POST /minecraft/escenarios/explosiones-cruzadas`; no inventa ni crea entidades ajenas. Se decidió propagar/atribuir los rechazos con `502 COMPANERO_RECHAZO` y campos `origen`, `estadoCompanero` y `codigoCompanero`, en lugar de devolver el `422`/`400` crudo del compañero, para que el handler propio distinga A2 de A3. Los puertos informados por los compañeros son: Elias `8080` (repo <https://github.com/eliasleonardo11-dotcom/EP-taller-git-2026>, commit `15f97c7`) y Marcelo `8081` (commit `6ccc6ea06c82cc5fb684b8d299ec82cc55e0721d`). A1 pasó a `8082` y esos valores quedaron como defaults en `application.properties` con override por `PORT`, `ELIAS_API_URL` y `MARCELO_API_URL`.

4. **Implementación.** Se agregaron `JugadaCruzada` y su repositorio; `EliasClient`, `MarceloClient` y `CuerpoCompanero` con `RestClient`; `CompaneroRechazoException` con hijas `EliasRechazoException`/`MarceloRechazoException` y `CompaneroNoConfiguradoException`; `EscenarioCruzadoService` con `@Transactional` para que un rechazo deje el H2 de A1 intacto; y `EscenarioCruzadoController` con el escenario, el listado, la consulta por id y `GET /minecraft/escenarios/diagnostico`, que lee los `/v3/api-docs` ajenos como primer contacto.

5. **Efectos y advertencias.** La única operación nueva que escribe en H2 de A1 es el `POST` del escenario, que inserta en `jugadas_cruzadas` tras doble `200`. A2 y A3 sí consumen sus entidades cuando aceptan; se documentó que un reintento requiere ids frescos en esos servicios y que no hay rollback distribuido.

6. **Verificación.** `./mvnw test` volvió a terminar con `Tests run: 3, Failures: 0, Errors: 0`; el contrato incluye ahora los paths del escenario y del diagnóstico. Se corrigieron dos problemas de integración: este proyecto no expone bean `ObjectMapper` (los clientes y el servicio crean el suyo) y `RestClient` no convierte respuestas a `JsonNode` sin converters de Jackson (ahora se lee `String` y se parsea con `CuerpoCompanero.parsear`). Se levantaron los tres servicios en `8080`, `8081` y `8082` con H2 en memoria desde los commits reales de Elias y Marcelo. El diagnóstico mostró los títulos y versiones de los dos OpenAPI ajenos. Con ids creados en esos servicios, el escenario feliz devolvió `201` y pasó la tabla `jugadas_cruzadas` de 0 a 1 fila; Elias quedó con Creeper `0` y Zombie `11`, Marcelo con `0`, `11` y `4`. El rechazo de A2 devolvió `502`, `origen A2 Elias`, `422 FUERA_DE_ALCANCE`; el de A3 devolvió `502`, `origen A3 Marcelo`, `400 ENTIDAD_DESAPARECIDA`. En ambos rechazos la tabla de A1 siguió en 1 fila.

## Nota

El código generado con asistencia se compiló con `./mvnw test` y se ejecutó con `./mvnw spring-boot:run`. En la entrega inicial se validaron manualmente el CRUD, la simulación, la persistencia posterior al `GET`, el `POST` ilegal, el id inexistente y los dos handlers de excepción. En la corrección se verificaron además la posición no numérica (`400`), la negativa a revivir un aldeano muerto (`400`), la persistencia tras reiniciar y las columnas `POSICION_X`, `POSICION_Y` y `POSICION_Z` de tipo `DOUBLE PRECISION` en H2.
