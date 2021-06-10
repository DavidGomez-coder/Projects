package Problema;

import java.util.*;


public class Problema {
    private int[][] mapa;
    private int obstaculo;
    private long semilla;
    private int numFilas;
    private int numColumnas;
    private int filaInicial;
    private int columnaInicial;
    private int filaFinal;
    private int columnaFinal;
    private int costeDiagonal;
    private int costeHorizontal;

    public Problema(long semilla, int filas, int columnas, int n_obstaculo) throws ProblemaException {

        if (n_obstaculo > filas * columnas)
            throw new ProblemaException("Numero de obstaculos es mayor que el numero de nodos");

        this.semilla = semilla;
        this.numFilas = filas;
        this.numColumnas = columnas;
        this.costeHorizontal = 100;
        this.costeDiagonal = 142;

        Random rnd = new Random(semilla);
        mapa = new int[filas][columnas];
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                mapa[i][j] = 0;
            }
        }

        for (int i = 0; i < n_obstaculo; i++) {
            boolean colocado = false;
            while (!colocado) {
                int r1 = rnd.nextInt(filas);
                int r2 = rnd.nextInt(columnas);
                if (mapa[r1][r2] == 0) {
                    mapa[r1][r2] = 1;
                    colocado = true;
                }
            }
        }
        boolean startColocado = false;
        while (!startColocado) {
            int r3 = rnd.nextInt(filas);
            int r4 = rnd.nextInt(columnas);
            if (mapa[r3][r4] == 0) {
                filaInicial = r3;
                columnaInicial = r4;
                startColocado = true;
            }
        }
        boolean goalColocado = false;
        while (!goalColocado) {
            int r5 = rnd.nextInt(filas);
            int r6 = rnd.nextInt(columnas);
            if (mapa[r5][r6] == 0) {
                filaFinal = r5;
                columnaFinal = r6;
                goalColocado = true;
            }
        }
    }


    public void mostrarMapa() {
        System.out.print("--------|");
        for (int i = 0; i < this.getNumFilas(); i++) {
            System.out.print("--------");
        }
        System.out.print("\n");

        for (int j = this.getNumColumnas() - 1; j >= 0; j--) {
            System.out.print(j + "    \t|");
            for (int i = 0; i < this.getNumFilas(); i++) {
                System.out.print("    " + this.getMapa()[i][j] + " \t");
            }
            System.out.print("| \n");
        }

        System.out.print("--------|");
        for (int i = 0; i < this.getNumFilas(); i++) {
            System.out.print("--------");
        }
        System.out.println();
        System.out.print("  MAPA  |");
        for (int i = 0; i < this.getNumFilas(); i++) {

            System.out.print("   " + i + "\t ");
        }
        System.out.print("|\n");


        System.out.print("--------|");
        for (int i = 0; i < this.getNumFilas(); i++) {
            System.out.print("--------");
        }
    }

    public int[][] getMapa() {
        return this.mapa;
    }


    public void setMapa(int[][] mapa) {
        this.mapa = mapa;
    }

    public int getObstaculo() {
        return obstaculo;
    }

    public void setObstaculo(int obstaculo) {
        this.obstaculo = obstaculo;
    }

    public long getSemilla() {
        return semilla;
    }

    public void setSemilla(long semilla) {
        this.semilla = semilla;
    }

    public int getNumFilas() {
        return numFilas;
    }

    public void setNumFilas(int numFilas) {
        this.numFilas = numFilas;
    }

    public int getNumColumnas() {
        return numColumnas;
    }

    public void setNumColumnas(int numColumnas) {
        this.numColumnas = numColumnas;
    }

    public int getFilaInicial() {
        return filaInicial;
    }

    public void setFilaInicial(int filaInicial) {
        this.filaInicial = filaInicial;
    }

    public int getColumnaInicial() {
        return columnaInicial;
    }

    public void setColumnaInicial(int columnaInicial) {
        this.columnaInicial = columnaInicial;
    }

    public int getFilaFinal() {
        return filaFinal;
    }

    public void setFilaFinal(int filaFinal) {
        this.filaFinal = filaFinal;
    }

    public int getColumnaFinal() {
        return columnaFinal;
    }

    public void setColumnaFinal(int columnaFinal) {
        this.columnaFinal = columnaFinal;
    }

    public int getCosteDiagonal() {
        return costeDiagonal;
    }

    public void setCosteDiagonal(int costeDiagonal) {
        this.costeDiagonal = costeDiagonal;
    }

    public int getCosteHorizontal() {
        return costeHorizontal;
    }

    public void setCosteHorizontal(int costeHorizontal) {
        this.costeHorizontal = costeHorizontal;
    }

    public List<Nodo> sucesores(Nodo n, List<Nodo> visitados) {
        List<Nodo> res = new ArrayList<>();
        for (int i = n.getFila() - 1; i <= n.getFila() + 1; i++) {
            for (int j = n.getColumna() - 1; j <= n.getColumna() + 1; j++) {
                Nodo nHijo = new Nodo(i, j);
                if (this.rangoValido(i, j) && !visitados.contains(nHijo) && !(i == n.getFila() && j == n.getColumna())) {
                    nHijo.setPadre(n);
                    res.add(nHijo);
                }
            }
        }

        //obtenemos os sucesores que no est�n bloqueados
        List<Nodo> bloqueados = nodosBloqueados(res, n);
        List<Nodo> sucesores = new ArrayList<>();
        for (Nodo nodo : res) {
            if (!esta(nodo, bloqueados) && this.getMapa()[nodo.getFila()][nodo.getColumna()] == 0) {
                sucesores.add(nodo);
            }
        }
        return sucesores;
    }

    /*
     * Pasamos como par�metros todos los nodos sucesores de n y n
     * Comprobamos si en las direcciones ARRIBA, ABAJO, DERECHA o IZQUIERDA hay un 1 (bloqueada)
     * En caso de que lo haya, bloqueamos las diagonales adyacentes a este nodo n
     *
     * @return devolvemos una lista de nodos bloqueados
     */
    private List<Nodo> nodosBloqueados(List<Nodo> sucesores, Nodo n) {
        List<Nodo> bloqueados = new ArrayList<>(); //inicializamos de nuevo la lista de bloqueados en cada iteraci�n
        for (Nodo nodo : sucesores) {
            if (this.getMapa()[nodo.getFila()][nodo.getColumna()] == 1) {
                if (n.getFila() == nodo.getFila() && n.getColumna() + 1 == nodo.getColumna()) {
                    // nodo de arriba
                    bloqueados.add(new Nodo(n.getFila() + 1, n.getColumna() + 1));
                    bloqueados.add(new Nodo(n.getFila() - 1, n.getColumna() + 1));

                }
                if (n.getFila() + 1 == nodo.getFila() && n.getColumna() == nodo.getColumna()) {
                    // derecha
                    bloqueados.add(new Nodo(n.getFila() + 1, n.getColumna() - 1));
                    bloqueados.add(new Nodo(n.getFila() + 1, n.getColumna() + 1));

                }
                if (n.getFila() == nodo.getFila() && n.getColumna() - 1 == nodo.getColumna()) {
                    // abajo
                    bloqueados.add(new Nodo(n.getFila() + 1, n.getColumna() - 1));
                    bloqueados.add(new Nodo(n.getFila() - 1, n.getColumna() - 1));

                }
                if (n.getFila() - 1 == nodo.getFila() && n.getColumna() == nodo.getColumna()) {
                    // izquierda
                    bloqueados.add(new Nodo(n.getFila() - 1, n.getColumna() + 1));
                    bloqueados.add(new Nodo(n.getFila() - 1, n.getColumna() - 1));

                }
            }
        }
        return bloqueados;
    }

    public boolean rangoValido(int f, int c) {
        return f >= 0 && f < this.getNumFilas() && c >= 0 && c < this.getNumColumnas();
    }

    private boolean esta(Nodo v, List<Nodo> list) {
        boolean ok = false;
        int i = 0;
        while (i < list.size() && !ok) {
            if (list.get(i).equals(v)) {
                ok = true;
            }
            i++;
        }
        return ok;
    }

}
