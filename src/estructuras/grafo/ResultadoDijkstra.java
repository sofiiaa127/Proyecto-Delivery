package estructuras.grafo;

/**
 * Resultado del algoritmo de Dijkstra.
 * Guarda la ruta mas corta y el tiempo total.
 */


public class ResultadoDijkstra {

    private final int[] ruta;
    private final int minutosTotales;
    private final boolean existeRuta;

    public ResultadoDijkstra(int[] ruta, int minutosTotales, boolean existeRuta) {
        this.ruta = ruta;
        this.minutosTotales = minutosTotales;
        this.existeRuta = existeRuta;
    }

    public int[] getRuta() {
        return ruta;
    }

    public int getMinutosTotales() {
        return minutosTotales;
    }

    public boolean existeRuta() {
        return existeRuta;
    }
}
