# arar-taller-git-2026
./mvnw spring-boot:run

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