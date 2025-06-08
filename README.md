# 🏝️ Aventura: Isla del Tesoro Perdido

Un juego de aventura textual desarrollado en Java donde exploras una misteriosa isla llena de peligros, tesoros y secretos por descubrir.

## 📋 Tabla de Contenidos
- [Descripción](#descripción)
- [Características](#características)
- [Requisitos](#requisitos)
- [Instalación](#instalación)
- [Cómo Jugar](#cómo-jugar)
- [Mecánicas del Juego](#mecánicas-del-juego)
- [Mapa y Ubicaciones](#mapa-y-ubicaciones)
- [Sistema de Combate](#sistema-de-combate)
- [Items y Inventario](#items-y-inventario)
- [Estructura del Código](#estructura-del-código)

## 🎮 Descripción

**Aventura: Isla del Tesoro Perdido** es un juego de aventura por turnos donde el jugador explora una isla generada proceduralmente. El objetivo es sobrevivir mientras exploras diferentes ubicaciones, combates enemigos, recolectas items y descubres los secretos de la isla.

## ✨ Características

- **Mapa procedural**: Isla de 40x30 casillas generada aleatoriamente
- **Exploración libre**: Movimiento en las 4 direcciones cardinales
- **Sistema de combate**: Enfréntate a diversos enemigos
- **Inventario dinámico**: Recolecta y usa diferentes items
- **Ubicaciones especiales**: 5 lugares únicos con sus propias características
- **Interfaz colorida**: Uso de colores ANSI para una mejor experiencia visual
- **Sistema de salud**: Gestiona tu salud durante la aventura

## 🔧 Requisitos

- **Java 8** o superior
- **Clase Utils** (proporcionada externamente para manejo de entrada/salida y colores)
- Terminal que soporte colores ANSI

## 📦 Instalación

1. Clona o descarga el código fuente
2. Asegúrate de tener la clase `Utils` en el paquete `generico`
3. Compila el proyecto:
   ```bash
   javac juego/*.java generico/*.java
   ```
4. Ejecuta el juego:
   ```bash
   java juego.juegoFinal
   ```

## 🎯 Cómo Jugar

### Controles Básicos
- **1-4**: Movimiento (Norte, Sur, Este, Oeste)
- **5**: Ver inventario
- **6**: Usar item del inventario
- **7**: Buscar en el área actual
- **8**: Explorar ubicación detalladamente
- **9**: Seleccionar lugar específico (si está cerca)

### Objetivo
Explora la isla, sobrevive a los peligros, recolecta tesoros y descubre todos los secretos que esconde la **Isla del Tesoro Perdido**.

## ⚙️ Mecánicas del Juego

### Sistema de Salud
- Inicias con **100 puntos de salud**
- La salud se reduce al recibir daño en combate
- Puedes restaurar salud usando items como "Botella con agua"
- Si tu salud llega a 0, el juego termina

### Exploración
- El mapa se revela conforme te mueves
- Diferentes terrenos ofrecen distintas experiencias:
  - **Bosques (♠)**: Posibles encuentros con criaturas
  - **Ríos (≈)**: No navegables sin equipo especial
  - **Montañas (▲)**: Terreno difícil de escalar
  - **Caminos (.)**: Terreno seguro y rápido

### Búsqueda
- Busca en cualquier área para encontrar items
- **30%** probabilidad de no encontrar nada
- **30%** probabilidad de encontrar items comunes
- **30%** probabilidad de encontrar hierbas medicinales
- **10%** probabilidad de encuentro sorpresa con enemigo

## 🗺️ Mapa y Ubicaciones

### Ubicaciones Especiales

| Símbolo | Nombre | Descripción |
|---------|--------|-------------|
| **P** | Playa Brisa Dorada | Extensa playa de arena blanca con aguas cristalinas |
| **C** | Cueva del Susurro | Oscura caverna con misteriosos ecos y marcas extrañas |
| **J** | Jungla Esmeralda | Denso bosque tropical con vegetación exuberante |
| **R** | Ruinas del Eclipse | Restos de una antigua civilización perdida |
| **M** | Montaña del Vigía | Punto más alto de la isla con vista panorámica |

### Leyenda del Mapa
- **@**: Tu posición
- **E**: Enemigo
- **♠**: Árboles/Bosque
- **≈**: Río/Agua
- **▲**: Montaña
- **.**: Camino despejado

## ⚔️ Sistema de Combate

### Mecánicas de Combate
- **Combate por turnos**: Tu turno, luego el del enemigo
- **Opciones en combate**:
  - **Atacar**: Causa 15-19 puntos de daño
  - **Usar item**: Utiliza items curativos del inventario
  - **Huir**: 50% probabilidad de escapar exitosamente

### Enemigos
| Enemigo | Salud | Daño | Ubicación |
|---------|-------|------|-----------|
| Araña gigante | 30 | 8 | (10, 12) |
| Pirata fantasma | 45 | 12 | (25, 20) |
| Serpiente venenosa | 35 | 10 | (12, 22) |
| Lobo de jungla | 40 | 15 | (15, 8) |
| Murciélago grande | 25 | 7 | (30, 5) |

## 🎒 Items y Inventario

### Items Iniciales
- **Linterna**: Ilumina áreas oscuras
- **Pico**: Útil para escalar montañas
- **Mapa Antiguo**: Revela información sobre ubicaciones especiales
- **Botella con agua**: Restaura 30 puntos de salud

### Items Encontrados
- **Hierbas medicinales**: +20 salud
- **Piedra afilada**: Item de utilidad
- **Cuerda**: Item de utilidad
- **Fruta fresca**: Alimento básico
- **Items especiales por ubicación**:
  - Concha mágica (Playa)
  - Cristal brillante (Cueva)
  - Fruta exótica (Jungla)
  - Artefacto antiguo (Ruinas)
  - Piedra preciosa (Montaña)

## 🏗️ Estructura del Código

### Clase Principal: `juegoFinal`

#### Constantes y Variables Globales
```java
private static final int MAP_WIDTH = 40;
private static final int MAP_HEIGHT = 30;
private static char[][] gameMap;
private static int playerX, playerY;
private static Map<String, String> inventory;
private static int playerHealth = 100;
```

#### Métodos Principales

**Inicialización**
- `initializeGame()`: Configura el juego completo
- `initializeMap()`: Genera el mapa proceduralmente
- `initializeEnemies()`: Coloca enemigos en el mapa
- `initializeInventory()`: Configura items iniciales

**Interfaz de Usuario**
- `showGameTitle()`: Muestra el título del juego
- `drawMap()`: Renderiza el mapa con colores
- `showPlayerStatus()`: Muestra salud y posición
- `showMainMenu()`: Menú principal de opciones

**Mecánicas de Juego**
- `movePlayer()`: Gestiona movimiento y colisiones
- `startCombat()`: Sistema completo de combate
- `searchArea()`: Búsqueda de items en el área
- `useItem()`: Sistema de uso de items

**Exploración**
- `exploreLocation()`: Exploración detallada de ubicaciones
- `selectSpecificLocation()`: Teletransporte a ubicaciones cercanas
- `showLocationDescription()`: Descripciones detalladas de lugares

### Clase `Enemy`

Representa los enemigos del juego con sus propiedades básicas:
```java
class Enemy {
    private String name;
    private int health, damage, x, y;
    // Métodos getter y takeDamage()
}
```

## 🎨 Características Técnicas

- **Colores ANSI**: Interfaz colorida usando la clase `Utils`
- **Generación procedural**: Mapa único en cada partida
- **Gestión de memoria**: Uso eficiente de estructuras de datos
- **Modularidad**: Código organizado en métodos especializados
- **Manejo de errores**: Validación de entrada y límites del mapa

## 🤝 Contribuciones

Este es un proyecto educativo. Si deseas mejorarlo:
1. Agrega nuevos tipos de enemigos
2. Implementa más items y sus funcionalidades
3. Crea nuevas ubicaciones especiales
4. Mejora el sistema de combate
5. Añade objetivos y misiones

## 📝 Notas de Desarrollo

- El juego utiliza una matriz 2D para representar el mapa
- Los colores se manejan a través de la clase `Utils` externa
- El sistema de coordenadas usa (0,0) en la esquina superior izquierda
- La generación del mapa combina aleatoriedad con ubicaciones fijas

---

**¡Disfruta tu aventura en la Isla del Tesoro Perdido!** 🏴‍☠️⚓
