# Respuestas de ejemplo — POO-09

Ejemplos obtenidos el 23 de noviembre de 2026 con los tres servicios levantados en `8080` (Elias), `8081` (Marcelo) y `8082` (A1). Los campos `respuestaElias` y `respuestaMarcelo` se guardan como texto en H2; A1 normaliza `codigo`/`tipo`/`code` ajenos al campo `codigoCompanero`.

## Feliz — `201 Created`

Pedido:

```json
{
  "creeperEliasId": 1,
  "zombieEliasId": 2,
  "distanciaElias": 1,
  "creeperMarceloId": 1
}
```

Respuesta de A1:

```json
{
  "id": 1,
  "creeperEliasId": 1,
  "zombieEliasId": 2,
  "distanciaElias": 1,
  "creeperMarceloId": 1,
  "estadoElias": 200,
  "codigoElias": "OK",
  "estadoMarcelo": 200,
  "codigoMarcelo": "OK",
  "registradaEn": "2026-11-23T11:43:12.212729911"
}
```

H2 de A1: una fila nueva en `jugadas_cruzadas`. Elias dejó su Creeper en vida `0` y su Zombie en `11`; Marcelo dejó Creeper `0`, Zombie `11` y Cerdo `4`.

## Rechazo de A2 — `502 Bad Gateway`

Pedido con `distanciaElias: 4` y rango `3`. Elias responde `422` con `FUERA_DE_ALCANCE`; A1 no llama a Marcelo y no guarda.

```json
{
  "tipo": "COMPANERO_RECHAZO",
  "origen": "A2 Elias",
  "mensaje": "A2 (Elias) rechazó la explosión [FUERA_DE_ALCANCE]: El Zombie está a 4 bloques y el alcance es de 3",
  "estado": 502,
  "estadoCompanero": 422,
  "codigoCompanero": "FUERA_DE_ALCANCE",
  "ruta": "/minecraft/escenarios/explosiones-cruzadas",
  "marcaTemporal": "2026-11-23T11:43:16.128356508"
}
```

## Rechazo de A3 — `502 Bad Gateway`

Pedido con el mismo `creeperMarceloId` ya detonado; se usan ids nuevos en A2 para que Elias acepte. Marcelo responde `400 ENTIDAD_DESAPARECIDA`; A1 no guarda.

```json
{
  "tipo": "COMPANERO_RECHAZO",
  "origen": "A3 Marcelo",
  "mensaje": "A3 (Marcelo) rechazó la explosión [ENTIDAD_DESAPARECIDA]: La entidad ya desapareció",
  "estado": 502,
  "estadoCompanero": 400,
  "codigoCompanero": "ENTIDAD_DESAPARECIDA",
  "ruta": "/minecraft/escenarios/explosiones-cruzadas",
  "marcaTemporal": "2026-11-23T11:43:18.358247506"
}
```

## Diagnóstico — `200 OK`

```json
{
  "elias": {
    "origen": "A2 Elias",
    "baseUrl": "http://localhost:8080",
    "estado": 200,
    "titulo": "Elías Pont · API Minecraft",
    "version": "POO-09-OpenAPI"
  },
  "marcelo": {
    "origen": "A3 Marcelo",
    "baseUrl": "http://localhost:8081",
    "estado": 200,
    "titulo": "Minecraft de Marcelo Romero — entidades y explosiones",
    "version": "1.0.0"
  }
}
```

## Configuración faltante — `503 Service Unavailable`

```json
{
  "tipo": "COMPANERO_NO_CONFIGURADO",
  "mensaje": "Falta la URL de A2 (Elias): configurar apis.elias.base-url o ELIAS_API_URL.",
  "estado": 503,
  "ruta": "/minecraft/escenarios/explosiones-cruzadas",
  "marcaTemporal": "2026-11-22T22:13:00.123456"
}
```
