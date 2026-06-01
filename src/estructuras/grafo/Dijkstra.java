package estructuras.grafo;


public class Dijkstra {

    public void calcularRuta(Grafo mapa, int indiceOrigen, int indiceDestino) {
        int n = mapa.getCantidadZonas();
        Vertice[] zonas = mapa.getZonas();

        int[] distancias = new int[n];
        int[] zonasPrevias = new int[n];
        boolean[] visitados = new boolean[n];

        
        for (int i = 0; i < n; i++) {
            distancias[i] = Integer.MAX_VALUE;
            zonasPrevias[i] = -1;
            visitados[i] = false;
        }

        distancias[indiceOrigen] = 0;

      
        for (int i = 0; i < n - 1; i++) {
            int u = obtenerVerticeConMenorDistancia(distancias, visitados, n);
            if (u == -1) break; 

            visitados[u] = true;

            Arista actual = zonas[u].getPrimeraArista();
            while (actual != null) {
                int v = obtenerIndicePorVertice(zonas, actual.getDestino(), n);
                int peso = actual.getPesoMinutos();

                if (!visitados[v] && distancias[u] != Integer.MAX_VALUE && 
                    distancias[u] + peso < distancias[v]) {
                    
                    distancias[v] = distancias[u] + peso;
                    zonasPrevias[v] = u; 
                }
                actual = actual.getSiguiente();
            }
        }

        imprimirResultado(zonas, indiceOrigen, indiceDestino, distancias, zonasPrevias);
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

    private void imprimirResultado(Vertice[] zonas, int origen, int destino, int[] distancias, int[] previos) {
        if (distancias[destino] == Integer.MAX_VALUE) {
            System.out.println("No se encontró una ruta hacia el cliente.");
            return;
        }

        System.out.print("Ruta más rápida desde [" + zonas[origen].getNombreZona() + "] hasta [" + zonas[destino].getNombreZona() + "]: ");
        imprimirCaminoRecursivo(zonas, destino, previos);
        System.out.println("\n-> Tiempo total de entrega: " + distancias[destino] + " minutos.");
    }

    private void imprimirCaminoRecursivo(Vertice[] zonas, int actual, int[] previos) {
        if (actual == -1) return;
        imprimirCaminoRecursivo(zonas, previos[actual], previos);
        System.out.print(zonas[actual].getNombreZona() + " -> ");
    }
}