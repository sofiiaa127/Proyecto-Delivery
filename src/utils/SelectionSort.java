package utils;

import model.Pedido;

public class SelectionSort {

    /**
     * Ordena arreglo de Pedidos por hora (ascendente) usando Selection Sort.
     * Complejidad: O(n²)
     */
    public void ordenarPorHora(Pedido[] pedidos, int n) {
        for (int i = 0; i < n - 1; i++) {
            int indiceMinimo = i;
            
            // Buscar el elemento mínimo en la parte no ordenada
            for (int j = i + 1; j < n; j++) {
                if (pedidos[j].getHora() < pedidos[indiceMinimo].getHora()) {
                    indiceMinimo = j;
                }
            }
            
            // Intercambiar si se encontró un mínimo menor
            if (indiceMinimo != i) {
                Pedido temporal = pedidos[i];
                pedidos[i] = pedidos[indiceMinimo];
                pedidos[indiceMinimo] = temporal;
            }
        }
    }
    
    // Sobrecarga: infiere automáticamente el tamaño del arreglo
    public void ordenarPorHora(Pedido[] pedidos) {
        ordenarPorHora(pedidos, pedidos.length);
    }
}