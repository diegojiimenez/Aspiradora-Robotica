import java.util.Random;

public class AspiradoraRobotica {
    private char[][] superficie;
    private int posicionFila;
    private int posicionColumna;
    private int bateria;
    private int capacidadBolsa;
    private int basuraRecogida;
    private int pasos;
    private int suciedadInicial;

    public AspiradoraRobotica(int filas, int columnas, int capacidadBolsa) {
        superficie = new char[filas][columnas];
        posicionFila = 0;
        posicionColumna = 0;
        bateria = filas * columnas * 5;
        this.capacidadBolsa = capacidadBolsa;
        basuraRecogida = 0;
        pasos = 0;
        suciedadInicial = 0;

        Random random = new Random();

        for (int i = 0; i < superficie.length; i++) {
            for (int j = 0; j < superficie[i].length; j++) {
                if (random.nextInt(10) < 7) {
                    superficie[i][j] = '0'; // Zona sucia
                    suciedadInicial++;
                } else {
                    superficie[i][j] = '.'; // Zona limpia
                }
            }
        }

        superficie[random.nextInt(filas)][random.nextInt(columnas)] = 'M'; // Mueble
    }

    public void moverAspiradora() {
        int filas = superficie.length;
        int columnas = superficie[0].length;

        int direccion = new Random().nextInt(4); // 0: arriba, 1: abajo, 2: izquierda, 3: derecha

        if (direccion == 0 && posicionFila > 0 && superficie[posicionFila - 1][posicionColumna] != 'M') { // Mover hacia arriba
            posicionFila--;
        } else if (direccion == 1 && posicionFila < filas - 1 && superficie[posicionFila + 1][posicionColumna] != 'M') { // Mover hacia abajo
            posicionFila++;
        } else if (direccion == 2 && posicionColumna > 0 && superficie[posicionFila][posicionColumna - 1] != 'M') { // Mover hacia la izquierda
            posicionColumna--;
        } else if (direccion == 3 && posicionColumna < columnas - 1 && superficie[posicionFila][posicionColumna + 1] != 'M') { // Mover hacia la derecha
            posicionColumna++;
        }

        if (superficie[posicionFila][posicionColumna] == '0') { // Hay suciedad en la posición actual
            superficie[posicionFila][posicionColumna] = '.';
            basuraRecogida++;
            bateria--;
        }

        pasos++;
        bateria--;

        if (basuraRecogida >= capacidadBolsa) { // Bolsa llena
            finalizarLimpieza("La bolsa de basura está llena.");
        }

        if (bateria <= 0) { // Batería agotada
            finalizarLimpieza("La batería se ha agotado.");
        }
    }

    public void finalizarLimpieza(String motivo) {
        System.out.println("---- Limpieza finalizada ----");
        System.out.println("Basura restante: " + (suciedadInicial - basuraRecogida));
        System.out.println("Motivo: " + motivo);
    }

    public void mostrarGraficoInicial() {
        System.out.println("---- Gráfico inicial ----");
        System.out.println("Superficie:");
        for (char[] fila : superficie) {
            for (char c : fila) {
                System.out.print(c + " ");
            }
            System.out.println();
        }
        System.out.println("Posición de la aspiradora: (" + posicionFila + "," + posicionColumna + ")");
        System.out.println("Basura inicial: " + suciedadInicial);
        System.out.println("Batería inicial: " + bateria);
        System.out.println("Capacidad de la bolsa de basura: " + capacidadBolsa);
        System.out.println();
    }

    public void mostrarGraficoFinal(boolean limpiezaCompleta, String motivo) {
        System.out.println("---- Gráfico final ----");
        System.out.println("Superficie:");
        for (char[] fila : superficie) {
            for (char c : fila) {
                System.out.print(c + " ");
            }
            System.out.println();
        }
        System.out.println("Posición de la aspiradora: (" + posicionFila + "," + posicionColumna + ")");
        System.out.println("Basura recogida: " + basuraRecogida);
        System.out.println("Basura restante: " + (suciedadInicial - basuraRecogida));
        System.out.println("Batería restante: " + bateria);
        if (limpiezaCompleta) {
            System.out.println("Limpieza completada exitosamente.");
        } else {
            System.out.println("La limpieza no se ha completado. Motivo: " + motivo);
        }
    }

    public boolean esLimpiezaCompleta() {
        for (char[] fila : superficie) {
            for (char c : fila) {
                if (c == '0') {
                    return false;
                }
            }
        }
        return true;
    }

    public void iniciarLimpieza() {
        mostrarGraficoInicial();

        while (!esLimpiezaCompleta() && basuraRecogida < capacidadBolsa && bateria > 0) {
            moverAspiradora();
        }

        boolean limpiezaCompleta = esLimpiezaCompleta();
        String motivo = "";
        if (!limpiezaCompleta) {
            if (basuraRecogida >= capacidadBolsa) {
                motivo = "La bolsa de basura está llena.";
            } else if (bateria <= 0) {
                motivo = "La batería se ha agotado.";
            }
        }

        mostrarGraficoFinal(limpiezaCompleta, motivo);
    }

    public static void main(String[] args) {
        AspiradoraRobotica aspiradora = new AspiradoraRobotica(8, 7, 15);
        aspiradora.iniciarLimpieza();
    }
}
