
import generico.Utils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class juegoFinal {
    
    // Configuración del mapa
    private static final int MAP_WIDTH = 40;
    private static final int MAP_HEIGHT = 30;
    private static char[][] gameMap = new char[MAP_HEIGHT][MAP_WIDTH];
    private static String[][] locationDescriptions = new String[MAP_HEIGHT][MAP_WIDTH];
    private static int playerX = 20;
    private static int playerY = 15;
    private static Map<String, String> inventory = new HashMap<>();
    private static int playerHealth = 100;
    private static boolean gameOver = false;
    
    private static List<Enemy> enemies = new ArrayList<>();
    
    // Lugares especiales
    private static final char BEACH = 'P';
    private static final char CAVE = 'C';
    private static final char JUNGLE = 'J';
    private static final char RUINS = 'R';
    private static final char MOUNTAIN = 'M';

    public static void main(String[] args) {
        Utils.limpiarConsola();
        showGameTitle();
        initializeGame();
        playGame();
    }

    private static void showGameTitle() {
        Utils.establecerColorCian();
        System.out.println("╔══════════════════════════════════════════════════╗");
        Utils.establecerColorAmarillo();
        System.out.println("║  █████╗ ██╗   ██╗███████╗███╗   ██╗████████╗██╗  ║");
        System.out.println("║ ██╔══██╗██║   ██║██╔════╝████╗  ██║╚══██╔══╝██║  ║");
        System.out.println("║ ███████║██║   ██║█████╗  ██╔██╗ ██║   ██║   ██║  ║");
        System.out.println("║ ██╔══██║╚██╗ ██╔╝██╔══╝  ██║╚██╗██║   ██║   ╚═╝  ║");
        System.out.println("║ ██║  ██║ ╚████╔╝ ███████╗██║ ╚████║   ██║   ██╗  ║");
        System.out.println("║ ╚═╝  ╚═╝  ╚═══╝  ╚══════╝╚═╝  ╚═══╝   ╚═╝   ╚═╝  ║");
        Utils.establecerColorVerde();
        System.out.println("║               ISLA DEL TESORO PERDIDO            ║");
        Utils.establecerColorCian();
        System.out.println("╚══════════════════════════════════════════════════╝");
        Utils.reiniciarColores();
        System.out.println("\nPresiona ENTER para comenzar tu aventura...");
        Utils.leerString(""); 
    }

    private static void initializeGame() {
        initializeMap();
        initializeEnemies();
        initializeInventory();
    }

    private static void initializeMap() {
        // Rellenar el mapa con terreno básico
        for (int y = 0; y < MAP_HEIGHT; y++) {
            for (int x = 0; x < MAP_WIDTH; x++) {
                int rand = Utils.generarNumeroAleatorio(100);
                if (rand < 60) {
                    gameMap[y][x] = '.';  // Tierra plana
                    locationDescriptions[y][x] = "Camino despejado";
                } else if (rand < 80) {
                    gameMap[y][x] = 'T';  // Árbol
                    locationDescriptions[y][x] = "Bosque denso";
                } else if (rand < 90) {
                    gameMap[y][x] = '~';  // Agua
                    locationDescriptions[y][x] = "Río cristalino";
                } else {
                    gameMap[y][x] = '^';  // Montaña
                    locationDescriptions[y][x] = "Terreno montañoso";
                }
            }
        }

        // Agregar lugares especiales
        addSpecialLocation(25, 20, BEACH, "Playa Brisa Dorada", 
            "Una extensa playa de arena blanca, bañada por aguas cristalinas.");
        addSpecialLocation(8, 12, CAVE, "Cueva del Susurro", 
            "Una oscura caverna donde el viento silba a través de estrechas grietas.");
        addSpecialLocation(12, 22, JUNGLE, "Jungla Esmeralda", 
            "Un denso bosque tropical con árboles colosales y lianas que cuelgan.");
        addSpecialLocation(15, 8, RUINS, "Ruinas del Eclipse", 
            "Restos de una antigua civilización que alguna vez floreció en la isla.");
        addSpecialLocation(30, 5, MOUNTAIN, "Montaña del Vigía", 
            "El punto más alto de la isla, ofreciendo una vista panorámica.");
        
        // Agregar un río sinuoso
        for (int x = 8; x < 32; x++) {
            int y = 15 + (int)(4 * Math.sin(x * 0.25));
            gameMap[y][x] = '~';
            gameMap[y+1][x] = '~';
            locationDescriptions[y][x] = "Río caudaloso";
            locationDescriptions[y+1][x] = "Río caudaloso";
        }
    }

    private static void addSpecialLocation(int x, int y, char symbol, String name, String description) {
        for (int dy = -3; dy <= 3; dy++) {
            for (int dx = -3; dx <= 3; dx++) {
                int nx = x + dx;
                int ny = y + dy;
                if (nx >= 0 && nx < MAP_WIDTH && ny >= 0 && ny < MAP_HEIGHT) {
                    if (dx == 0 && dy == 0) {
                        gameMap[ny][nx] = symbol;
                        locationDescriptions[ny][nx] = description;
                    } else if (Math.abs(dx) == 3 || Math.abs(dy) == 3) {
                        gameMap[ny][nx] = 'T';
                        locationDescriptions[ny][nx] = "Bosque que rodea " + name;
                    } else {
                        gameMap[ny][nx] = '.';
                        locationDescriptions[ny][nx] = "Camino que lleva a " + name;
                    }
                }
            }
        }
    }

    private static void initializeEnemies() {
        enemies.clear();
        enemies.add(new Enemy("Araña gigante", 30, 8, 10, 12));
        enemies.add(new Enemy("Pirata fantasma", 45, 12, 25, 20));
        enemies.add(new Enemy("Serpiente venenosa", 35, 10, 12, 22));
        enemies.add(new Enemy("Lobo de jungla", 40, 15, 15, 8));
        enemies.add(new Enemy("Murciélago grande", 25, 7, 30, 5));
    }

    private static void initializeInventory() {
        inventory.clear();
        inventory.put("Linterna", "Una linterna que ilumina áreas oscuras");
        inventory.put("Pico", "Un pico para escalar montañas o romper rocas");
        inventory.put("Mapa Antiguo", "Un mapa de la isla con marcas misteriosas");
        inventory.put("Botella con agua", "Agua fresca que restaura salud");
    }

    private static void playGame() {
        while (!gameOver && playerHealth > 0) {
            gameCycle();
        }
        showGameOver();
    }

    private static void gameCycle() {
        Utils.limpiarConsola();
        drawMap();
        showPlayerStatus();
        showMainMenu();
    }

    private static void drawMap() {
        // Aumentar el área visible alrededor del jugador (de 9x9 a 15x15)
        int viewRadius = 20; // Radio de visión (7 casillas en cada dirección)
        int startX = Math.max(0, playerX - viewRadius);
        int endX = Math.min(MAP_WIDTH, playerX + viewRadius + 1);
        int startY = Math.max(0, playerY - viewRadius);
        int endY = Math.min(MAP_HEIGHT, playerY + viewRadius + 1);
        
        Utils.establecerColorAzul();
        System.out.println("╔════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                         MAPA DE LA ISLA                           ║");
        System.out.println("╠════════════════════════════════════════════════════════════════════╣");
        
        String truncatedDesc = locationDescriptions[playerY][playerX];
        if (truncatedDesc.length() > 60) {
            truncatedDesc = truncatedDesc.substring(0, 57) + "...";
        }
        System.out.println("║ Ubicación: " + String.format("%-60s", truncatedDesc) + "║");
        System.out.println("╠════════════════════════════════════════════════════════════════════╣");
        
        for (int y = startY; y < endY; y++) {
            System.out.print("║ ");
            for (int x = startX; x < endX; x++) {
                if (x == playerX && y == playerY) {
                    Utils.establecerColorVerde();
                    System.out.print('@');
                    Utils.establecerColorAzul();
                    continue;
                }
                
                boolean enemyDrawn = false;
                for (Enemy enemy : enemies) {
                    if (enemy.getX() == x && enemy.getY() == y) {
                        Utils.establecerColorRojo();
                        System.out.print('E');
                        Utils.establecerColorAzul();
                        enemyDrawn = true;
                        break;
                    }
                }
                if (enemyDrawn) continue;
                
                // [Resto del código para dibujar los símbolos del mapa...]
                switch (gameMap[y][x]) {
                    case CAVE:
                        Utils.establecerColorMangenta();
                        System.out.print('C');
                        Utils.establecerColorAzul();
                        break;
                    case BEACH:
                        Utils.establecerColorAmarillo();
                        System.out.print('P');
                        Utils.establecerColorAzul();
                        break;
                    case RUINS:
                        Utils.establecerColorRojo();
                        System.out.print('R');
                        Utils.establecerColorAzul();
                        break;
                    case JUNGLE:
                        Utils.establecerColorVerde();
                        System.out.print('J');
                        Utils.establecerColorAzul();
                        break;
                    case MOUNTAIN:
                        Utils.establecerColorBlanco();
                        System.out.print('M');
                        Utils.establecerColorAzul();
                        break;
                    case 'T':
                        Utils.establecerColorVerde();
                        System.out.print('♠');
                        Utils.establecerColorAzul();
                        break;
                    case '~':
                        Utils.establecerColorCian();
                        System.out.print('≈');
                        Utils.establecerColorAzul();
                        break;
                    case '^':
                        Utils.establecerColorBlanco();
                        System.out.print('▲');
                        Utils.establecerColorAzul();
                        break;
                    default:
                        System.out.print(gameMap[y][x]);
                }
            }
            System.out.println(" ║");
        }
        
        System.out.println("╠════════════════════════════════════════════════════════════════════╣");
        System.out.println("║ Leyenda: @-Tú E-Enemigo ♠-Árbol ≈-Río ▲-Montaña C-Cueva P-Playa  ║");
        System.out.println("║ R-Ruinas J-Jungla M-Montaña                                      ║");
        System.out.println("╚════════════════════════════════════════════════════════════════════╝");
        Utils.reiniciarColores();
    }

    private static void showPlayerStatus() {
        Utils.establecerColorAmarillo();
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║                    ESTADO                        ║");
        System.out.println("╠══════════════════════════════════════════════════╣");
        
        // Barra de salud
        int healthBars = (int)((playerHealth / 100.0) * 20);
        System.out.print("║ Salud: [");
        Utils.establecerColorVerde();
        for (int i = 0; i < healthBars; i++) System.out.print("█");
        Utils.establecerColorRojo();
        for (int i = healthBars; i < 20; i++) System.out.print(" ");
        Utils.establecerColorAmarillo();
        System.out.println("] " + playerHealth + "/100 ║");
        
        System.out.println("║ Posición: (" + playerX + ", " + playerY + ")                           ║");
        System.out.println("╚══════════════════════════════════════════════════╝");
        Utils.reiniciarColores();
    }

    private static void showMainMenu() {
        int option = Utils.leerRangosEnteros(
            "\n╔══════════════════════════════════════════════════╗\n" +
            "║              ¿QUÉ DESEAS HACER?                ║\n" +
            "╠══════════════════════════════════════════════════╣\n" +
            "║ 1 - Norte               2 - Sur                 ║\n" +
            "║ 3 - Este                4 - Oeste               ║\n" +
            "║ 5 - Ver inventario      6 - Usar item           ║\n" +
            "║ 7 - Buscar en el área   8 - Explorar ubicación  ║\n" +
            "║ 9 - Seleccionar lugar específico                ║\n" +
            "╚══════════════════════════════════════════════════╝\n" +
            "Selecciona una opción (1-9): ", 1, 9);

        switch (option) {
            case 1: movePlayer(0, -1); break;
            case 2: movePlayer(0, 1); break;
            case 3: movePlayer(1, 0); break;
            case 4: movePlayer(-1, 0); break;
            case 5: showInventory(); break;
            case 6: useItem(); break;
            case 7: searchArea(); break;
            case 8: exploreLocation(); break;
            case 9: selectSpecificLocation(); break;
        }
    }

    private static void movePlayer(int dx, int dy) {
        int newX = playerX + dx;
        int newY = playerY + dy;
    
        // Verificar límites del mapa
        if (newX < 0 || newX >= MAP_WIDTH || newY < 0 || newY >= MAP_HEIGHT) {
            showMessage("¡No puedes ir más allá! El océano te detiene.", "rojo");
            return;
        }
    
        // Verificar terreno
        char terrain = gameMap[newY][newX];
        if (terrain == '~') {
            showMessage("¡Necesitas un bote para cruzar el río!", "azul");
            return;
        } else if (terrain == '^') {
            showMessage("¡El terreno es demasiado empinado para escalar!", "blanco");
            return;
        } else if (terrain == 'T') {
            showMessage("Avanzas lentamente por el denso bosque...", "verde");
            if (Utils.generarNumeroAleatorio(100) < 30) {
                Enemy forestEnemy = new Enemy("Criatura del bosque", 25, 8, newX, newY);
                startCombat(forestEnemy);
                return;
            }
        }
    
        // Verificar enemigos
        for (Enemy enemy : enemies) {
            if (enemy.getX() == newX && enemy.getY() == newY) {
                startCombat(enemy);
                return;
            }
        }
    
        // Mover al jugador
        playerX = newX;
        playerY = newY;
    
        // Mostrar descripción si es lugar especial
        if (gameMap[playerY][playerX] != '.' && gameMap[playerY][playerX] != 'T') {
            showLocationDescription(gameMap[playerY][playerX]);
        }
    }

    private static void showMessage(String message, String color) {
        switch (color.toLowerCase()) {
            case "rojo": Utils.establecerColorRojo(); break;
            case "verde": Utils.establecerColorVerde(); break;
            case "azul": Utils.establecerColorAzul(); break;
            case "amarillo": Utils.establecerColorAmarillo(); break;
            case "magenta": Utils.establecerColorMangenta(); break;
            case "cyan": Utils.establecerColorCian(); break;
            case "blanco": Utils.establecerColorBlanco(); break;
            default: Utils.establecerColorAmarillo();
        }
        
        System.out.println("\n╔══════════════════════════════════════════════════╗");
        if (message.length() > 48) {
            message = message.substring(0, 45) + "...";
        }
        System.out.println("║ " + String.format("%-48s", message) + " ║");
        System.out.println("╚══════════════════════════════════════════════════╝");
        Utils.reiniciarColores();
        Utils.pausar();
    }

    private static void showInventory() {
        Utils.limpiarConsola();
        Utils.establecerColorAmarillo();
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║                  INVENTARIO                      ║");
        System.out.println("╠══════════════════════════════════════════════════╣");
        
        if (inventory.isEmpty()) {
            System.out.println("║ No tienes items en tu inventario              ║");
        } else {
            for (Map.Entry<String, String> entry : inventory.entrySet()) {
                String itemLine = "║ " + entry.getKey() + ": " + entry.getValue();
                if (itemLine.length() > 52) {
                    itemLine = itemLine.substring(0, 49) + "...";
                }
                System.out.println(String.format("%-54s", itemLine) + "║");
            }
        }
        
        System.out.println("╚══════════════════════════════════════════════════╝");
        Utils.reiniciarColores();
        Utils.pausar();
    }

    private static void useItem() {
        if (inventory.isEmpty()) {
            showMessage("No tienes items para usar", "rojo");
            return;
        }
    
        List<String> items = new ArrayList<>(inventory.keySet());
        System.out.println("\n╔══════════════════════════════════════════════════╗");
        System.out.println("║               SELECCIONA UN ITEM                  ║");
        System.out.println("╠══════════════════════════════════════════════════╣");
        
        for (int i = 0; i < items.size(); i++) {
            System.out.println("║ " + (i+1) + " - " + String.format("%-46s", items.get(i)) + "║");
        }
        System.out.println("║ 0 - Cancelar                                    ║");
        System.out.println("╚══════════════════════════════════════════════════╝");
        
        int choice = Utils.leerRangosEnteros("Selecciona un item (1-" + items.size() + ") o 0 para cancelar: ", 0, items.size());
        
        if (choice == 0) return;
        
        String selectedItem = items.get(choice-1);
        
        switch (selectedItem) {
            case "Botella con agua":
                playerHealth = Math.min(100, playerHealth + 30);
                showMessage("Bebiste agua. Salud +30!", "verde");
                inventory.remove(selectedItem);
                break;
            case "Linterna":
                showMessage("La linterna ilumina el área alrededor", "amarillo");
                break;
            case "Pico":
                showMessage("El pico podría ser útil para escalar montañas", "amarillo");
                break;
            case "Mapa Antiguo":
                showMessage("Estudias el mapa antiguo...", "azul");
                showMapLegend();
                break;
            default:
                showMessage("Usaste: " + selectedItem, "azul");
        }
    }

    private static void showMapLegend() {
        Utils.limpiarConsola();
        Utils.establecerColorAmarillo();
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║               LEYENDA DEL MAPA ANTIGUO           ║");
        System.out.println("╠══════════════════════════════════════════════════╣");
        System.out.println("║ P - Playa Brisa Dorada                          ║");
        System.out.println("║ C - Cueva del Susurro                            ║");
        System.out.println("║ J - Jungla Esmeralda                             ║");
        System.out.println("║ R - Ruinas del Eclipse                           ║");
        System.out.println("║ M - Montaña del Vigía                            ║");
        System.out.println("║ X - Posición del tesoro (según la leyenda)       ║");
        System.out.println("╚══════════════════════════════════════════════════╝");
        Utils.reiniciarColores();
        Utils.pausar();
    }

    private static void useItemInCombat() {
        if (inventory.isEmpty()) {
            showMessage("No tienes items para usar", "rojo");
            return;
        }
        
        List<String> combatItems = new ArrayList<>();
        for (Map.Entry<String, String> entry : inventory.entrySet()) {
            if (entry.getKey().equals("Botella con agua") || 
                entry.getKey().equals("Hierbas medicinales")) {
                combatItems.add(entry.getKey());
            }
        }
        
        if (combatItems.isEmpty()) {
            showMessage("No tienes items útiles en combate", "rojo");
            return;
        }
        
        System.out.println("\n╔══════════════════════════════════════════════════╗");
        System.out.println("║              ITEMS ÚTILES EN COMBATE              ║");
        System.out.println("╠══════════════════════════════════════════════════╣");
        
        for (int i = 0; i < combatItems.size(); i++) {
            System.out.println("║ " + (i+1) + " - " + String.format("%-46s", combatItems.get(i)) + "║");
        }
        System.out.println("║ 0 - Cancelar                                    ║");
        System.out.println("╚══════════════════════════════════════════════════╝");
        
        int choice = Utils.leerRangosEnteros("Selecciona un item (1-" + combatItems.size() + ") o 0 para cancelar: ", 0, combatItems.size());
        
        if (choice == 0) return;
        
        String selectedItem = combatItems.get(choice-1);
        
        switch (selectedItem) {
            case "Botella con agua":
                playerHealth = Math.min(100, playerHealth + 30);
                showMessage("Bebiste agua. Salud +30!", "verde");
                inventory.remove(selectedItem);
                break;
            case "Hierbas medicinales":
                playerHealth = Math.min(100, playerHealth + 20);
                showMessage("Usaste hierbas medicinales. Salud +20!", "verde");
                inventory.remove(selectedItem);
                break;
        }
    }

    private static void searchArea() {
        int chance = Utils.generarNumeroAleatorio(100);
        
        if (chance < 30) {
            showMessage("No encuentras nada interesante aquí", "amarillo");
        } else if (chance < 60) {
            String[] commonItems = {"Hierbas medicinales", "Piedra afilada", "Cuerda", "Fruta fresca"};
            String foundItem = commonItems[Utils.generarNumeroAleatorio(commonItems.length)];
            inventory.put(foundItem, "Encontrado en " + locationDescriptions[playerY][playerX]);
            showMessage("¡Encontraste " + foundItem + "!", "verde");
        } else if (chance < 90) {
            playerHealth = Math.min(100, playerHealth + 10);
            showMessage("Encontraste hierbas medicinales. Salud +10!", "verde");
        } else {
            Enemy newEnemy = new Enemy("Criatura sorpresa", 20, 5, playerX, playerY);
            showMessage("¡Una " + newEnemy.getName() + " aparece!", "rojo");
            startCombat(newEnemy);
        }
    }

    private static void exploreLocation() {
        char location = gameMap[playerY][playerX];
        if (location == CAVE || location == BEACH || location == RUINS || 
            location == JUNGLE || location == MOUNTAIN) {
            showLocationDescription(location);
        } else {
            showMessage("Estás en: " + locationDescriptions[playerY][playerX], "azul");
        }
    }

    private static void showLocationDescription(char locationType) {
        String title = "";
        String description = "";
        
        switch (locationType) {
            case BEACH:
                title = "Playa Brisa Dorada";
                description = "Una extensa playa de arena blanca, bañada por aguas cristalinas. " +
                             "El sol brilla intensamente y el sonido de las olas es relajante. " +
                             "Ves algunas conchas marinas y algas en la orilla.";
                break;
            case CAVE:
                title = "Cueva del Susurro";
                description = "Una oscura caverna donde el viento silba a través de estrechas grietas. " +
                             "El aire es frío y húmedo, y escuchas gotas de agua cayendo en la distancia. " +
                             "Hay extrañas marcas en las paredes.";
                break;
            case JUNGLE:
                title = "Jungla Esmeralda";
                description = "Un denso bosque tropical con árboles colosales y lianas que cuelgan. " +
                             "El aire es cálido y húmedo, lleno de sonidos de animales exóticos. " +
                             "La vegetación es tan densa que apenas pasa la luz del sol.";
                break;
            case RUINS:
                title = "Ruinas del Eclipse";
                description = "Restos de una antigua civilización que alguna vez floreció en la isla. " +
                             "Ves columnas rotas, escaleras que no llevan a ninguna parte y símbolos misteriosos. " +
                             "Hay una extraña energía en este lugar.";
                break;
            case MOUNTAIN:
                title = "Montaña del Vigía";
                description = "El punto más alto de la isla, ofreciendo una vista panorámica. " +
                             "Desde aquí puedes ver toda la isla y el océano que la rodea. " +
                             "El viento sopla fuerte y el aire es más frío.";
                break;
        }
        
        Utils.limpiarConsola();
        Utils.establecerColorAmarillo();
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║ " + String.format("%-48s", title) + " ║");
        System.out.println("╠══════════════════════════════════════════════════╣");
        
        // Dividir la descripción en líneas de máximo 48 caracteres
        int maxLength = 48;
        for (int i = 0; i < description.length(); i += maxLength) {
            String line = description.substring(i, Math.min(i + maxLength, description.length()));
            System.out.println("║ " + String.format("%-48s", line) + " ║");
        }
        
        System.out.println("╚══════════════════════════════════════════════════╝");
        Utils.reiniciarColores();
        Utils.pausar();
    }

    private static void selectSpecificLocation() {
        int location = selectLocation();
        switch (location) {
            case 1: if (moveToLocation(25, 20)) showLocationDescription(BEACH); break;
            case 2: if (moveToLocation(8, 12)) showLocationDescription(CAVE); break;
            case 3: if (moveToLocation(12, 22)) showLocationDescription(JUNGLE); break;
            case 4: if (moveToLocation(15, 8)) showLocationDescription(RUINS); break;
            case 5: if (moveToLocation(30, 5)) showLocationDescription(MOUNTAIN); break;
        }
    }
    
    private static int selectLocation() {
        Utils.limpiarConsola();
        Utils.establecerColorAmarillo();
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║         SELECCIONAR LUGAR ESPECÍFICO             ║");
        System.out.println("╠══════════════════════════════════════════════════╣");
        System.out.println("║ 1 - Playa Brisa Dorada                          ║");
        System.out.println("║ 2 - Cueva del Susurro                           ║");
        System.out.println("║ 3 - Jungla Esmeralda                            ║");
        System.out.println("║ 4 - Ruinas del Eclipse                          ║");
        System.out.println("║ 5 - Montaña del Vigía                           ║");
        System.out.println("║ 0 - Cancelar                                    ║");
        System.out.println("╚══════════════════════════════════════════════════╝");
        
        return Utils.leerRangosEnteros("Selecciona un lugar (1-5) o 0 para cancelar: ", 0, 5);
    }
    
    private static boolean moveToLocation(int targetX, int targetY) {
        if (Math.abs(playerX - targetX) <= 5 && Math.abs(playerY - targetY) <= 5) {
            playerX = targetX;
            playerY = targetY;
            return true;
        } else {
            showMessage("Este lugar está demasiado lejos. Debes acercarte primero.", "amarillo");
            return false;
        }
    }

    private static String getRandomItemForLocation(char locationType) {
        switch (locationType) {
            case BEACH:
                return "Concha mágica";
            case CAVE:
                return "Cristal brillante";
            case JUNGLE:
                return "Fruta exótica";
            case RUINS:
                return "Artefacto antiguo";
            case MOUNTAIN:
                return "Piedra preciosa";
            default:
                String[] commonItems = {"Hierbas medicinales", "Piedra afilada", "Cuerda", "Fruta fresca"};
                return commonItems[Utils.generarNumeroAleatorio(commonItems.length)];
        }
    }

    private static void startCombat(Enemy enemy) {
        Utils.limpiarConsola();
        Utils.establecerColorRojo();
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║                    ¡COMBATE!                     ║");
        System.out.println("╠══════════════════════════════════════════════════╣");
        System.out.println("║ Te enfrentas a un " + String.format("%-30s", enemy.getName() + "!") + " ║");
        System.out.println("╚══════════════════════════════════════════════════╝");
        
        int playerAttack = 15 + Utils.generarNumeroAleatorio(5);
        int enemyAttack = enemy.getDamage();
        
        while (playerHealth > 0 && enemy.getHealth() > 0) {
            Utils.establecerColorAmarillo();
            System.out.println("\n╔══════════════════════════════════════════════════╗");
            System.out.println("║ TUS PUNTOS DE VIDA: " + String.format("%-30d", playerHealth) + " ║");
            System.out.println("║ VIDA DEL ENEMIGO: " + String.format("%-32d", enemy.getHealth()) + " ║");
            System.out.println("╠══════════════════════════════════════════════════╣");
            
            int option = Utils.leerRangosEnteros(
                "║ 1 - Atacar       2 - Usar item       3 - Huir       ║\n" +
                "╚══════════════════════════════════════════════════╝\n" +
                "Selecciona una acción: ", 1, 3);
            
            if (option == 1) {
                Utils.establecerColorVerde();
                System.out.println("\n¡ATACAS al " + enemy.getName() + " causando " + playerAttack + " de daño!");
                enemy.takeDamage(playerAttack);
                Utils.reiniciarColores();
            } else if (option == 2) {
                useItemInCombat();
                continue;
            } else {
                if (Utils.generarNumeroAleatorio(100) < 50) {
                    showMessage("¡Lograste escapar del combate!", "verde");
                    return;
                } else {
                    showMessage("¡No pudiste escapar!", "rojo");
                }
            }
            
            if (enemy.getHealth() > 0) {
                Utils.establecerColorRojo();
                System.out.println("\n¡El " + enemy.getName() + " te ataca causando " + enemyAttack + " de daño!");
                playerHealth -= enemyAttack;
                Utils.reiniciarColores();
                
                if (playerHealth <= 30) {
                    showMessage("¡ADVERTENCIA! Salud crítica: " + playerHealth + "/100", "rojo");
                }
            }
        }
        
        if (playerHealth <= 0) {
            gameOver = true;
        } else {
            showMessage("¡VICTORIA! Has derrotado al " + enemy.getName() + "!", "verde");
            if (Utils.generarNumeroAleatorio(100) < 30) {
                try {
                    String item = getRandomItemForLocation(gameMap[playerY][playerX]);
                    showMessage("¡El enemigo soltó un " + item + "!", "amarillo");
                    inventory.put(item, "Obtenido de derrotar un " + enemy.getName());
                } catch (Exception e) {
                    System.err.println("Error al obtener item aleatorio: " + e.getMessage());
                }
            }
            
            if (!enemy.getName().equals("Criatura del bosque") && 
                !enemy.getName().equals("Criatura sorpresa")) {
                enemies.removeIf(e -> e.getX() == enemy.getX() && 
                                     e.getY() == enemy.getY() && 
                                     e.getName().equals(enemy.getName()));
            }
        }
    }

  private static void showGameOver() {
    Utils.limpiarConsola();
    Utils.establecerColorRojo();
    System.out.println("╔════════════════════════════════════════════════════════════════╗");
    System.out.println("║                                                                ║");
    System.out.println("║   ██████   █████  ███    ███ ███████   ██████  ██    ██ ███████ ██████  ║");
    System.out.println("║  ██       ██   ██ ████  ████ ██       ██    ██ ██    ██ ██      ██   ██ ║");
    System.out.println("║  ██   ███ ███████ ██ ████ ██ █████    ██    ██ ██    ██ █████   ██████  ║");
    System.out.println("║  ██    ██ ██   ██ ██  ██  ██ ██       ██    ██  ██  ██  ██      ██   ██ ║");
    System.out.println("║   ██████  ██   ██ ██      ██ ███████   ██████    ████   ███████ ██   ██ ║");
    System.out.println("║                                                                ║");
    System.out.println("╚════════════════════════════════════════════════════════════════╝");
    Utils.establecerColorVerde();
    System.out.println("\n¡Gracias por jugar! Hasta pronto.");
    Utils.reiniciarColores();
}
}

class Enemy {
    private String name;
    private int health;
    private int damage;
    private int x;
    private int y;
    
    public Enemy(String name, int health, int damage, int x, int y) {
        this.name = name;
        this.health = health;
        this.damage = damage;
        this.x = x;
        this.y = y;
    }
    
    public String getName() { return name; }
    public int getHealth() { return health; }
    public int getDamage() { return damage; }
    public int getX() { return x; }
    public int getY() { return y; }
    
    public void takeDamage(int amount) {
        health -= amount;
        if (health < 0) health = 0;
    }
}