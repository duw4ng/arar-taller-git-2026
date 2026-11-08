# Bitácora de asistencia de inteligencia artificial

- **Fecha:** 8 de noviembre de 2026.
- **Asistente o agente:** OpenCode (CLI).
- **Modelo exacto del LLM:** `opencode/big-pickle`.
- **Modalidad:** asistencia en la implementación completa de la entrega POO-08: dependencias, entidades JPA, repositorio, servicios, DTOs, controllers, jerarquía de excepciones, colección de Insomnia y documentación.

## Resumen de prompts

1. **Pedido inicial.** «Usando mi carpeta `arar-taller-git-2026` quiero que hagas lo siguiente: OO-08 — CRUD REST, H2, reglas de juego y excepciones…» junto con el enunciado, la rúbrica, el template de paquetes y los entregables. Se pidió publicar un servicio Spring Boot sobre el modelado de Minecraft, con persistencia H2 de tres clases, CRUD REST, dos reglas de negocio, simulación persistida, jerarquía de excepciones, interfaz de dominio, colección de Insomnia, README, bitácora y especificaciones.

2. **Lectura de contexto.** Se solicitó revisar el repositorio existente, el `README.md` de POO-06, el `pom.xml`, las propiedades, el modelado de Minecraft y los controllers previos para continuar sobre ese trabajo sin romper la herencia, la sobrecarga ni la sobreescritura ya documentadas.

3. **Implementación.** Se pidió agregar `spring-boot-starter-data-jpa` y el driver H2; mapear la familia `Aldeano`/`AldeanoArmero`/`AldeanoGranjero`; crear `AldeanoRepository`, servicios, DTOs y controllers REST; modelar las reglas de explosión en el dominio; crear la jerarquía `MinecraftException` → `ReglaDeJuegoException` y `EntidadNoEncontradaException`; y traducir cada tipo de excepción con un handler HTTP distinto.

4. **Insomnia y documentación.** Se pidió generar una colección importable con el CRUD, un `POST` ilegal, un id inexistente, el endpoint de la interfaz, la simulación y un pedido por cada excepción hija; actualizar el README con las propiedades de H2, el diagrama Mermaid y las respuestas a «Qué hay que pensar»; crear `BITACORA.md`; y preparar las especificaciones para Classroom.

5. **Verificación.** Se pidió compilar, ejecutar las pruebas y levantar el servicio para recorrer el CRUD, la simulación y los errores con `curl`, comprobando que el estado quedara persistido en H2.

## Nota

El código generado con asistencia se compiló con `./mvnw test` y se ejecutó con `./mvnw spring-boot:run`. Se validaron manualmente el CRUD, la simulación, la persistencia posterior al `GET`, el `POST` ilegal, el id inexistente y los dos handlers de excepción.
