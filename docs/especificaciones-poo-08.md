# Especificaciones aplicadas — POO-08 (Minecraft)

**Alumno:** Alexandre Avalos (`duw4ng`)  
**Dominio:** Minecraft  
**Repositorio:** <https://github.com/duw4ng/arar-taller-git-2026>  
**Commit de la solución:** <https://github.com/duw4ng/arar-taller-git-2026/commit/7f8e72f4beef27b0ba30023dc156c0318e90f0b5>  
**Bitácora:** <https://github.com/duw4ng/arar-taller-git-2026/blob/main/BITACORA.md>

## 1. Objetivo

Publicar un servicio HTTP con Spring Boot sobre el modelado de Minecraft que:

- persista en H2 la familia de los aldeanos (`Aldeano`, `AldeanoArmero`, `AldeanoGranjero`);
- exponga un CRUD REST completo sobre esa familia;
- ejecute y persista una simulación de explosión con dos reglas del juego;
- distinga por HTTP los errores de dos excepciones hermanas del dominio;
- use la interfaz de dominio `Danable` en un endpoint de lectura;
- deje una colección de Insomnia versionada para reproducir las pruebas.

## 2. Arranque

```bash
./mvnw spring-boot:run
```

Servicio: <http://localhost:8080>  
Consola H2: <http://localhost:8080/h2-console>  
JDBC URL: `jdbc:h2:file:./data/entidades` — usuario `sa` — contraseña vacía.

## 3. URLs de la API

| Método | URL | Descripción |
|---|---|---|
| `GET` | `/` | Servicio vivo. |
| `GET` | `/minecraft/trabajo` | Mensajes polimórficos de `trabajar()` sobre lo persistido. |
| `POST` | `/minecraft/aldeanos` | Crea un armero o un granjero según el campo `tipo`. |
| `GET` | `/minecraft/aldeanos` | Lista la colección persistida. Acepta `?profesion=Armero`. |
| `GET` | `/minecraft/aldeanos/{id}` | Busca por id. |
| `PUT` | `/minecraft/aldeanos/{id}` | Reemplaza el recurso completo. |
| `PATCH` | `/minecraft/aldeanos/{id}` | Actualiza sólo los campos presentes. |
| `DELETE` | `/minecraft/aldeanos/{id}` | Borra la fila. |
| `GET` | `/minecraft/danables` | Endpoint que usa la interfaz `Danable`. |
| `POST` | `/minecraft/simulaciones/explosion` | Simula y persiste una explosión. |

## 4. Cuerpos de ejemplo

Crear un armero:

```json
{
  "tipo": "ARMERO",
  "nombre": "Pedro",
  "vida": 20,
  "posicion": "5,64,0",
  "alimentacion": "Carne",
  "nivelHerreria": 3
}
```

Crear un granjero:

```json
{
  "tipo": "GRANJERO",
  "nombre": "Juan",
  "vida": 25,
  "posicion": "20,64,0",
  "alimentacion": "Pan",
  "tipoCultivo": "Trigo"
}
```

Simular una explosión:

```json
{
  "potencia": 30,
  "radio": 30,
  "posicionCreeper": "0,64,0",
  "tiempoCarga": 3
}
```

## 5. Invariantes

1. El `id` lo asigna H2/JPA; el JSON de creación no lo lleva.
2. `nombre`, `alimentacion`, `profesion` y `tipoCultivo` no pueden estar vacíos.
3. `vida` no puede ser negativa.
4. `posicion` debe poder leerse como `x,y,z` numérico.
5. `nivelHerreria` debe estar entre `1` y `5`.
6. No se puede cambiar el subtipo (`ARMERO`/`GRANJERO`) de un aldeano existente.
7. El constructor sin argumentos que usa JPA es `protected`; los constructores de negocio son `public` y validan.
8. `@PrePersist` y `@PreUpdate` vuelven a validar antes de escribir en la base.

## 6. Reglas de negocio

1. **Onda expansiva:** el daño de la explosión es `potencia - distancia` (nunca negativo y `0` fuera del radio).
2. **Mitigación por oficio:** el armero resta `nivelHerreria * 2` por armadura; el granjero recibe el daño completo y, si sobrevive, queda en pánico.

Ambas reglas se implementan en métodos del dominio (`Creeper.danoPorOnda`, `AldeanoArmero.resistirExplosion`, `AldeanoGranjero.resistirExplosion`). El controller y el servicio sólo orquestan y persisten.

## 7. Excepciones y respuestas HTTP

| Excepción | Disparador de prueba | HTTP | `tipo` |
|---|---|---|---|
| `ReglaDeJuegoException` | `POST` con `nivelHerreria = 99` o simulación con `potencia = 0`. | `400` | `REGLA_DE_JUEGO` |
| `EntidadNoEncontradaException` | `GET /minecraft/aldeanos/999999`. | `404` | `ENTIDAD_NO_ENCONTRADA` |
| `MinecraftException` | Fallback de la jerarquía. | `400` | `ERROR_DE_DOMINIO` |

## 8. Cómo probarlo

1. Arrancar con `./mvnw spring-boot:run`.
2. Importar `insomnia/POO-08-arar-taller-git-2026.json` en Insomnia.
3. Seleccionar el entorno `Base Environment` (`baseUrl = http://localhost:8080`).
4. Ejecutar `GET /` y confirmar `200`.
5. Crear un armero con `POST`; copiar el `id` devuelto a la variable `aldeanoId`.
6. Crear un granjero (opcional) para tener las dos hijas.
7. Listar con `GET /minecraft/aldeanos` y confirmar que ambos aparecen.
8. Buscar uno con `GET /minecraft/aldeanos/{id}`.
9. Actualizar con `PUT` y `PATCH`; repetir el `GET` para ver el cambio persistido.
10. Ejecutar el `POST` ilegal y confirmar `400 REGLA_DE_JUEGO`; listar de nuevo y verificar que la colección no creció.
11. Pedir un id inexistente y confirmar `404 ENTIDAD_NO_ENCONTRADA`.
12. Ejecutar `GET /minecraft/danables` y observar armeros, granjeros y Creeper bajo el tipo `Danable`.
13. Ejecutar `POST /minecraft/simulaciones/explosion` y luego `GET /minecraft/aldeanos`: la vida bajó y el granjero quedó con `enPanico = true`.
14. Ejecutar `DELETE` y luego `GET` por el mismo id para confirmar `404`.
15. Abrir la consola H2 y ver la tabla `aldeanos`.

## 9. Colección de Insomnia

Ruta versionada: `insomnia/POO-08-arar-taller-git-2026.json`.

Incluye: un pedido de servicio vivo, dos creaciones válidas (armero y granjero), listado, búsqueda por id, `PUT`, `PATCH`, `DELETE`, un `POST` ilegal, un id inexistente, una simulación ilegal, el endpoint de la interfaz `Danable`, la simulación en el caso feliz y el `GET` posterior que muestra el estado persistido.

## 10. Bitácora de IA

Se trabajó con OpenCode, modelo `opencode/big-pickle`. El detalle de los prompts está en `BITACORA.md`.
