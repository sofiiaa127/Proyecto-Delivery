package estructuras.grafo;

/**
 * Algoritmo de Dijkstra para encontrar la ruta mas corta entre dos zonas del mapa.
 */


public class Dijkstra {
    public ResultadoDijkstra calcular(Grafo mapa, int indiceOrigen, int indiceDestino) {
        int n = mapa.getCantidadZonas();
        Vertice[] zonas = mapa.getZonas();

        int[] distancias = new int[n];
        int[] previos = new int[n];
        boolean[] visitados = new boolean[n];

        for (int i = 0; i < n; i++) {
            distancias[i] = Integer.MAX_VALUE;
            previos[i] = -1;
            visitados[i] = false;
        }

        if (indiceOrigen < 0 || indiceDestino < 0) {
            return new ResultadoDijkstra(new int[0], 0, false);
        }

        distancias[indiceOrigen] = 0;

        for (int i = 0; i < n; i++) {
            int u = obtenerVerticeConMenorDistancia(distancias, visitados, n);
            if (u == -1) break;

            visitados[u] = true;

            Arista actual = zonas[u].getPrimeraArista();
            while (actual != null) {
                int v = obtenerIndicePorVertice(zonas, actual.getDestino(), n);
                int peso = actual.getPesoMinutos();

                if (v != -1 && !visitados[v] && distancias[u] != Integer.MAX_VALUE &&
                    distancias[u] + peso < distancias[v]) {

                    distancias[v] = distancias[u] + peso;
                    previos[v] = u;
                }
                actual = actual.getSiguiente();
            }
        }

        if (distancias[indiceDestino] == Integer.MAX_VALUE) {
            return new ResultadoDijkstra(new int[0], 0, false);
        }

        int[] ruta = reconstruirRuta(previos, indiceDestino);
        return new ResultadoDijkstra(ruta, distancias[indiceDestino], true);
    }

    public void calcularRuta(Grafo mapa, int indiceOrigen, int indiceDestino) {
        ResultadoDijkstra resultado = calcular(mapa, indiceOrigen, indiceDestino);
        Vertice[] zonas = mapa.getZonas();

        if (!resultado.existeRuta()) {
            System.out.println("No se encontro una ruta hacia el cliente.");
            return;
        }

        int[] ruta = resultado.getRuta();
        StringBuilder sb = new StringBuilder("Ruta mas rapida: ");
        for (int i = 0; i < ruta.length; i++) {
            sb.append(zonas[ruta[i]].getNombreZona());
            if (i < ruta.length - 1) sb.append(" -> ");
        }
        System.out.println(sb.toString());
        System.out.println("-> Tiempo total de entrega: " + resultado.getMinutosTotales() + " minutos.");
    }

    private int obtenerVerticeConMenorDistancia(int[] distancias, boolean[] visitados, int n) {
        int min = Integer.MAX_VALUE;
        int minIndex = -1;

        for (int v = 0; v < n; v++) {
            if (!visitados[v] && distancias[v] <= min) {
                min = distancias[v];
                minIndex = v;
            }
        }
        return minIndex;
    }

    private int obtenerIndicePorVertice(Vertice[] zonas, Vertice v, int n) {
        for (int i = 0; i < n; i++) {
            if (zonas[i] == v) return i;
        }
        return -1;
    }

    private int[] reconstruirRuta(int[] previos, int destino) {
        int longitud = 0;
        int actual = destino;
        while (actual != -1) {
            longitud++;
            actual = previos[actual];
        }

        int[] ruta = new int[longitud];
        actual = destino;
        for (int i = longitud - 1; i >= 0; i--) {
            ruta[i] = actual;
            actual = previos[actual];
        }
        return ruta;
    }
}
