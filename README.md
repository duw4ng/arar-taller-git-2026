# arar-taller-git-2026
./mvnw spring-boot:run

## Sobrecarga y Sobreescritura (POO-06)

### 1. Sobreescritura de Métodos (`Overriding` - Polimorfismo Dinámico)

* **Definición:** Ocurre en una relación de herencia (Padre-Hija). La clase hija redefine el comportamiento de un método heredado manteniendo exactamente la **misma firma** (mismo nombre, mismos parámetros y mismo tipo de retorno). Se resuelve en **tiempo de ejecución** (*Runtime*).
* **Cambios e implementación en el proyecto:**
  * **Método Abstracto `trabajar()`:** 
    * Declarado en la clase abstracta base `Aldeano` con la firma `public abstract String trabajar()`.
    * Sobreescrito con `@Override` en `AldeanoArmero` para retornar la lógica de forja en el alto horno.
    * Sobreescrito con `@Override` en `AldeanoGranjero` para retornar la lógica de cosecha y compostador.
    * **Uso REST:** Permite que `AldeanoController` invoque `trabajar()` sobre una lista genérica `List<Aldeano>` sin conocer la clase concreta de cada objeto.
  * **Método Abstracto `actuar()`:** 
    * Declarado en `Entidad` y sobreescrito a lo largo de la jerarquía (`Aldeano`, `Zombie`, `Esqueleto`, `Creeper`, `Cerdo`, `Jugador`) para dar una lógica contextual a cada criatura.
  * **Método `atacar()`:** 
    * Definido en `EntidadHostil` y sobreescrito en `Esqueleto` (para especificar el uso de arco a distancia) y en `Zombie` (para verificar si el zombie es hostil antes de llamar a `super.atacar()`).

---

### 2. Sobrecarga (`Overloading` - Polimorfismo Estático)

* **Definición:** Ocurre dentro de una misma clase (o jerarquía) cuando existen dos o más métodos o constructores con el **mismo nombre pero distinta firma** (diferente cantidad, tipo o secuencia de parámetros). Se resuelve en **tiempo de compilación** (*Compile-time*).
* **Cambios e implementación en el proyecto:**
  * **Sobrecarga de Constructores:**
    * Permite inicializar las entidades con diferentes niveles de detalle según la fuente de datos.
    * Se diferencian constructores simples (sin argumentos o con valores por defecto) de constructores sobrecargados con parámetros completos (`nombre`, `vida`, `posicion`, etc.).
    * Las clases hijas (`AldeanoArmero`, `AldeanoGranjero`) invocan al constructor sobrecargado del padre mediante `super(...)` para garantizar que la inicialización del estado base y las reglas de dominio (validación de vida y posición) se mantengan protegidas.

---

### Cuadro Comparativo de Resumen

| Criterio | Sobrecarga (*Overloading*) | Sobreescritura (*Overriding*) |
| :--- | :--- | :--- |
| **Relación de Clases** | Ocurre en la misma clase. | Requiere herencia (Clase Padre e Hija). |
| **Firma del Método** | Debe cambiar la lista de parámetros. | Debe ser **idéntica** (nombre, parámetros y retorno). |
| **Resolución** | Tiempo de compilación (*Static binding*). | Tiempo de ejecución (*Dynamic binding*). |
| **Anotación** | No utiliza anotaciones. | Utiliza `@Override`. |
| **Ejemplo en el Proyecto** | Constructores de `AldeanoArmero` / `AldeanoGranjero`. | `trabajar()` en `AldeanoArmero` y `AldeanoGranjero`. |
## Diagrama de Clases (Modelado POO)

```mermaid
classDiagram
    class Entidad {
        <<abstract>>
        +String nombre
        +int vida
        +String posicion
        +moverse() void
        +recibirDaño(int cantidad) void
        +morir() void
        +actuar()* void
    }
    
    class Jugador {
        +int experiencia
        +String inventario
        +actuar() void
        +atacar() void
    }
    
    class EntidadHostil {
        <<abstract>>
        +int daño
        +String objetivo
        +atacar() void
        +actuar() void
    }
    
    class EntidadPasiva {
        <<abstract>>
        +String alimentacion
        +actuar() void
        +huir() void
    }

    class Zombie {
        +boolean esHostil
        +actuar() void
        +atacar() void
    }

    class Esqueleto {
        +String arma
        +actuar() void
        +atacar() void
    }

    class Creeper {
        +int tiempoCarga
        +actuar() void
        +explotar() void
    }

    class Enderman {
        +boolean puedeTeletransportarse
        +actuar() void
        +teletransportarse() void
    }

    class Cerdo {
        +String color
        +actuar() void
        +comer() void
    }

    class Aldeano {
        <<abstract>>
        -String profesion
        +actuar() void
        +comerciar() void
        +trabajar()* String
    }

    class AldeanoArmero {
        -int nivelHerreria
        +trabajar() String
    }

    class AldeanoGranjero {
        -String tipoCultivo
        +trabajar() String
    }

    Entidad <|-- Jugador
    Entidad <|-- EntidadHostil
    Entidad <|-- EntidadPasiva
    
    EntidadHostil <|-- Zombie
    EntidadHostil <|-- Esqueleto
    EntidadHostil <|-- Creeper
    EntidadHostil <|-- Enderman
    
    EntidadPasiva <|-- Cerdo
    EntidadPasiva <|-- Aldeano
    
    Aldeano <|-- AldeanoArmero
    Aldeano <|-- AldeanoGranjero
    
