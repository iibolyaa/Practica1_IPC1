import java.util.Scanner;
import java.util.Random;

public class pacman {
    public static void main(String[] args){
        Scanner nc = new Scanner(System.in);

        int op = 0;
        int puntos = 0;
        int filas = 0;
        int columnas = 0;
        var tamano = "";
        var nombre = "";
        int contador = -1;
        boolean flag = true;
        String [][] jugadores = new String[10][2];
        int indice = 0;

        while(flag == true){
            System.out.println("==== MENÚ INICIO ====");
            System.out.println("1. Iniciar juego");
            System.out.println("2. Historial de partidas");
            System.out.println("3. Salir");
            op = Integer.parseInt(nc.nextLine());

            switch (op) {
                case 1:
                    System.out.println("Ingrese su nombre");
                    nombre = nc.nextLine();
                    indice = verificarjugador(jugadores, contador, nombre);
                    if(indice <= contador){
                        puntos = Integer.parseInt(jugadores[indice][1]);
                    }else{
                        contador = indice;
                    }

                    System.out.println("====== ESPECIFICAR TABLERO ======");
                    System.out.println("Ingrese el tamaño del tablero:");
                    System.out.println("Grande (G)");
                    System.out.println("Pequeño (P)");
                    tamano = nc.nextLine();

                    switch (tamano) {
                        case "G":
                            filas = 10;
                            columnas = 10;
                            break;
                        case "P":
                            filas = 5;
                            columnas = 6;
                            break;
                        default:
                            System.out.println("Opción no válida");
                            break;
                    }

                    //Creación de matriz (tablero)
                    char[][] tablero = new char[filas][columnas];
                    juego(filas, columnas, tablero, tamano, puntos, nombre, jugadores, contador, flag);
                    break;
                case 2:
                    partidas(jugadores, contador);
                    break;
                case 3:
                    System.out.println("Saliendo...");
                    flag = false;
                    break;
                default:
                    System.out.println("Opción no válida");
            }
        }
    }

    public static void juego(int filas, int columnas, char[][] tablero, String tamano, int puntos, String nombre, String[][] jugadores, int contador, boolean flag){
        Scanner nc = new Scanner(System.in);
        Random rand = new Random();

        int conteopremios = 0;
        int premios1 = 0;
        int premios2 = 0;
        int paredes = 0;
        int trampas = 0;
        int a = 0;
        int b = 0;
        int c = 0;
        int posx = 0;
        int posy = 0;
        int pacManX = 0;
        int pacManY = 0;
        int vidas = 0;
        String lectura = " ";

        System.out.println("======= ESPECIFICAR TABLERO =======");
        System.out.println("Ingrese los siguientes valores");
        System.out.println("Tablero: " + tamano);
        System.out.println("Premios [1-40]: ");
        a = Integer.parseInt(nc.nextLine());
        System.out.println("Paredes [1-20]: ");
        b = Integer.parseInt(nc.nextLine());
        System.out.println("Trampas [1-20]: ");
        c = Integer.parseInt(nc.nextLine());

        // Cantidad de premios, paredes y fantasmas
        premios1 = (int)(filas*columnas*((double)(a/2)*0.01));
        premios2 = (int)(filas*columnas*((double)(a/2)*0.01));
        paredes = (int)(filas*columnas*(b*0.01));
        trampas = (int)(filas*columnas*(c*0.01));

        // Conteo de premios
        conteopremios = premios1 + premios2;

        // Guardar valores en matriz

        for (int i = 0; i<filas; i++){
            for (int j = 0; j<columnas; j++){
                tablero[i][j] = ' ';
            }
        }

        for (int i = 0; i < premios1; i++){
            do{
                posx = rand.nextInt(filas);
                posy = rand.nextInt(columnas);
            }while(tablero[posx][posy] != ' ');
            tablero[posx][posy] = '0';
        }

        for (int i = 0; i < premios2; i++){
            do{
                posx = rand.nextInt(filas);
                posy = rand.nextInt(columnas);
            }while(tablero[posx][posy] != ' ');
            tablero[posx][posy] = '$';
        }

        for (int i = 0; i < paredes; i++){
            do{
                posx = rand.nextInt(filas);
                posy = rand.nextInt(columnas);
            }while(tablero[posx][posy] != ' ');
            tablero[posx][posy] = 'X';
        }

        for (int i = 0; i < trampas; i++){
            do{
                posx = rand.nextInt(filas);
                posy = rand.nextInt(columnas);
            }while(tablero[posx][posy] != ' ');
            tablero[posx][posy] = '@';
        }

        System.out.println("Ingrese la posición inicial del juego");
        System.out.println("Filas:");
        pacManX = Integer.parseInt(nc.nextLine());
        System.out.println("Columna:");
        pacManY = Integer.parseInt(nc.nextLine());

        //Imprimir tablero

        vidas = 3;

        while(vidas>0 && conteopremios>0){

            System.out.println("USUARIO: " + nombre);
            System.out.println("PUNTEO: " + puntos);
            System.out.println("VIDAS: " + vidas);

            tablero[pacManX][pacManY] = '<';

            for (int i = 0; i<=columnas*3; i++){
                System.out.print("_");
            }

            System.out.println(" ");

            for (int i = 0; i<filas; i++) {
                System.out.print("|");
                for (int j = 0; j<columnas; j++) {
                    System.out.print(" ");
                    System.out.print(tablero[i][j]);
                    System.out.print(" ");
                }
                System.out.println("|");
            }

            for (int i = 0; i <= columnas * 3; i++) {
                System.out.print("_");
            }

            System.out.println(" ");
            lectura = nc.nextLine();

            tablero[pacManX][pacManY] = ' ';

            //Controles
            if(lectura.equals("F")) {

                System.out.println("========= PAUSA =========");
                System.out.println("Seleccione una opción:");
                System.out.println("1. Regresar");
                System.out.println("2. Terminar partida");
                int op = Integer.parseInt(nc.nextLine());

                if(op == 1) {
                    continue;
                }else if(op == 2){
                    historial(puntos, nombre, contador, jugadores);
                    return;
                }
            }else if (lectura.equals("8")){ // Arriba
                pacManX = pacManX - 1;
            } else if (lectura.equals("5")) { // Abajo
                pacManX = pacManX + 1;
            } else if(lectura.equals("6")){ // Derecha
                pacManY = pacManY + 1;
            }  else if(lectura.equals("4")){ // Izquierda
                pacManY = pacManY - 1;
            }else{
                System.out.println("Opción no válida");
                break;
            }

            if(pacManX == -1) {
                pacManX = filas - 1;
            }else if(pacManX == filas){
                pacManX = 0;
            }else if(pacManY == columnas){
                pacManY = 0;
            }else if(pacManY == -1){
                pacManY = columnas-1;
            }

            if(tablero[pacManX][pacManY] == '0'){
                puntos = puntos + 10;
                conteopremios = conteopremios - 1;
            }else if(tablero[pacManX][pacManY] == '$') {
                puntos = puntos + 15;
                conteopremios = conteopremios - 1;
            }else if(tablero[pacManX][pacManY] == '@'){
                vidas = vidas - 1;
            }

            if(tablero[pacManX][pacManY] == 'X'){
                switch(lectura){
                    case "8":
                        pacManX = pacManX + 1;
                        break;
                    case "5":
                        pacManX = pacManX - 1;
                        break;
                    case "6":
                        pacManY = pacManY - 1;
                        break;
                    case "4":
                        pacManY = pacManY + 1;
                        break;
                }
            }
        }

        //Registrar partida
        historial(puntos, nombre, contador, jugadores);

        if(conteopremios == 0){
            System.out.println("¡Felicidades! Has ganado");
        } else if (vidas == 0) {
            System.out.println("Has perdido");
        }
    }

    // Registrar jugadores con su respectivo punteo
    public static void historial(int puntos, String nombre, int contador, String[][] jugadores){
        jugadores[contador][0] = nombre;
        String punteo = Integer.toString(puntos);
        jugadores[contador][1]= punteo;
    }

    // Mostrar los jugadores registrados
    public static void partidas(String[][] jugadores, int contador){
        int i = 0;
        int j = 0;

        System.out.print("No.    Usuario    Punteo");
        System.out.println(" ");

        for(i = 0; i <= contador; i++){
            System.out.print((j + 1) + ".     ");
            for(j = 0; j < 2; j++){
                System.out.print(jugadores[i][j] + "     ");
            }
        }
        System.out.println(" ");
    }

    // Comprobar si el jugador ya está dentro del arreglo
    public static int verificarjugador(String[][] jugadores, int contador, String nombre){
        for(int i = 0; i <= contador; i++){
            if(jugadores[i][0] != null && jugadores[i][0].equals(nombre)){
                return i;
            }
        }
        return contador + 1;
    }
}
