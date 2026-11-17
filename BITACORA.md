# Bitácora de asistencia de inteligencia artificial

- **Fechas:** 8 de noviembre de 2026 (entrega inicial) y 16 de noviembre de 2026 (corrección posterior a la devolución).
- **Asistente o agente:** OpenCode (CLI).
- **Modelo exacto del LLM:** `opencode/big-pickle`.
- **Modalidad:** asistencia en la implementación completa de la entrega POO-08: dependencias, entidades JPA, repositorio, servicios, DTOs, controllers, jerarquía de excepciones, colección de Insomnia y documentación; y corrección de los criterios observados por la cátedra.

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

## Nota

El código generado con asistencia se compiló con `./mvnw test` y se ejecutó con `./mvnw spring-boot:run`. En la entrega inicial se validaron manualmente el CRUD, la simulación, la persistencia posterior al `GET`, el `POST` ilegal, el id inexistente y los dos handlers de excepción. En la corrección se verificaron además la posición no numérica (`400`), la negativa a revivir un aldeano muerto (`400`), la persistencia tras reiniciar y las columnas `POSICION_X`, `POSICION_Y` y `POSICION_Z` de tipo `DOUBLE PRECISION` en H2.
