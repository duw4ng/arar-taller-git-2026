# Especificaciones POO-09 — APIs cruzadas

## Grupo

| Rol | Estudiante | Repositorio | URL base |
|---|---|---|---|
| A1 | Alexandre Avalos | <https://github.com/duw4ng/arar-taller-git-2026> | `http://localhost:8082` |
| A2 | Elias Pont | <https://github.com/eliasleonardo11-dotcom/EP-taller-git-2026> | `http://localhost:8080` |
| A3 | Marcelo Romero | <https://github.com/mnri1904/mnri-taller-git-2026> | `http://localhost:8081` |

Commits de los compañeros: A2 [`15f97c7`](https://github.com/eliasleonardo11-dotcom/EP-taller-git-2026/commit/15f97c7); A3 [`6ccc6ea06c82cc5fb684b8d299ec82cc55e0721d`](https://github.com/mnri1904/mnri-taller-git-2026/commit/6ccc6ea06c82cc5fb684b8d299ec82cc55e0721d). Commit de A1: [`2f29708`](https://github.com/duw4ng/arar-taller-git-2026/commit/2f29708979225397823ab90c27447032bca4536a).

## Escenario en una frase

Con un Creeper y un Zombie ya creados en la API de Elias y un Creeper ya creado en la API de Marcelo, A1 detona en ambos servicios con una misma jugada; guarda la jugada en su H2 **sólo si Elias y Marcelo devuelven `200`**.

## Reglas que se cruzan

- **A2 (Elias):** Creeper y Zombie vivos, `0 <= distancia <= rango`; daño `max(1, 12 - 3 * distancia)`; Creeper queda con vida `0`. `POST /api/hostiles/{creeperId}/explosiones` con `{"zombieId":2,"distancia":1}` → `200` y escribe en su H2; distancia `4` con rango `3` → `422 FUERA_DE_ALCANCE` y no escribe.
- **A3 (Marcelo):** Creeper vivo detona una vez; daño `12 - 3 * floor(distancia)` a Zombies y Cerdos vivos a `<= 3` bloques. `POST /api/minecraft/entidades/{id}/explosion` sin cuerpo → `200` y escribe en su H2; id ya detonado → `400 ENTIDAD_DESAPARECIDA`; id de Zombie como detonador → `400 EXPLOSION_INVALIDA`.
- **A1 (este servicio):** no copia las reglas ajenas ni crea entidades ajenas; arma los pedidos desde los OpenAPI leídos, llama a A2 y A3 y persiste el resultado sólo con doble aceptación.

## Cómo levantar y probar

```bash
export ELIAS_API_URL=http://localhost:8080
export MARCELO_API_URL=http://localhost:8081
export PORT=8082
./mvnw spring-boot:run
```

Los mismos valores ya están en `application.properties`, así que los `export` son opcionales. Elias se levanta con `./mvnw spring-boot:run` en su repo; Marcelo en el suyo.

1. Primer contacto: `GET http://localhost:8082/minecraft/escenarios/diagnostico` debe mostrar `titulo` y `version` de los dos OpenAPI ajenos.
2. Crear, en sus propios servicios, un Creeper y un Zombie para Elias y un Creeper para Marcelo; copiar los ids.
3. Pedido feliz:

```http
POST http://localhost:8082/minecraft/escenarios/explosiones-cruzadas
Content-Type: application/json

{
  "creeperEliasId": 1,
  "zombieEliasId": 2,
  "distanciaElias": 1,
  "creeperMarceloId": 1
}
```

4. Comprobar `201` y la fila en `GET http://localhost:8082/minecraft/escenarios/explosiones-cruzadas`.
5. Repetir con `distanciaElias: 4` para provocar el rechazo de A2; repetir con un `creeperMarceloId` ya detonado para provocar el rechazo de A3. En ambos casos A1 responde `502` y `GET /minecraft/escenarios/explosiones-cruzadas` sigue igual.

## Efectos en H2 y errores

| Operación | Efecto real |
|---|---|
| `POST /minecraft/escenarios/explosiones-cruzadas` | Inserta en `jugadas_cruzadas` de A1 **sólo si A2 y A3 aceptan**. No es una validación sin efectos. |
| `GET /minecraft/escenarios/diagnostico` | Lee OpenAPI ajenos; no escribe. |
| Rechazo de A2 o A3 | `502 COMPANERO_RECHAZO`; A1 no guarda; el cuerpo trae `origen`, `estadoCompanero` y `codigoCompanero`. |
| URL vacía | `503 COMPANERO_NO_CONFIGURADO`. |

No hay rollback distribuido: si un compañero acepta y el otro rechaza, ese compañero ya consumió sus entidades; A1 queda sin cambios y el reintento necesita ids nuevos.

## Verificación realizada

El 23 de noviembre de 2026 se levantaron los tres servicios con H2 en memoria desde los commits indicados. El diagnóstico leyó los OpenAPI reales: `Elías Pont · API Minecraft` (`POO-09-OpenAPI`) y `Minecraft de Marcelo Romero — entidades y explosiones` (`1.0.0`). Con ids creados en A2 y A3:

- Escenario feliz: `201`, `jugadas_cruzadas` pasó de 0 a 1 fila; Elias `0`/`11`, Marcelo `0`/`11`/`4`.
- Rechazo de A2: `502`, `origen A2 Elias`, `422 FUERA_DE_ALCANCE`; A1 siguió en 1 fila.
- Rechazo de A3: `502`, `origen A3 Marcelo`, `400 ENTIDAD_DESAPARECIDA`; A1 siguió en 1 fila.

Respuestas completas en `docs/respuestas-ejemplo-poo-09.md`.

## Entregables

- Colección: `insomnia/POO-09-arar-taller-git-2026.json`.
- Respuestas de ejemplo: `docs/respuestas-ejemplo-poo-09.md`.
- Bitácora: `BITACORA.md`.
- Contrato: `/v3/api-docs` y `/swagger-ui.html`.
- Corrección POO-08: `18/24` (nota `7,5/10`). Observaciones cerradas en los commits [`f837d12`](https://github.com/duw4ng/arar-taller-git-2026/commit/f837d12) y [`6eca763`](https://github.com/duw4ng/arar-taller-git-2026/commit/6eca763), ya incluidos en el estado actual: los controllers dependen sólo de services; las validaciones, reglas y excepciones viven en `AldeanoService` y `SimulacionService`, y las entidades no lanzan excepciones; `Posicion` es `@Embeddable` con coordenadas `double` validadas en `PosicionParser`; no se puede revivir un aldeano con `vida = 0`; y README, diagrama Mermaid y licencia están actualizados. El puntaje ya publicado no se modifica.
