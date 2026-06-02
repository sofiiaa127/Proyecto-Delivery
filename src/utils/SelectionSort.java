package utils;

import model.Pedido;

/**
 * Ordenamiento por seleccion (Selection Sort)
 * Ordena pedidos por hora de ingreso de forma ascendente. Complejidad O(n^2).
 */
public class SelectionSort {

    public void ordenarPorHora(Pedido[] pedidos, int n) {
        for (int i = 0; i < n - 1; i++) {
            int indiceMinimo = i;

            for (int j = i + 1; j < n; j++) {
                if (esMenor(pedidos[j], pedidos[indiceMinimo])) {
                    indiceMinimo = j;
                }
            }

            if (indiceMinimo != i) {
                Pedido temporal = pedidos[i];
                pedidos[i] = pedidos[indiceMinimo];
                pedidos[indiceMinimo] = temporal;
            }
        }
    }

    public void ordenarPorHora(Pedido[] pedidos) {
        ordenarPorHora(pedidos, pedidos.length);
    }

    //Compara dos pedidos por su hora de ingreso 
    private boolean esMenor(Pedido a, Pedido b) {
        return a.getHoraIngreso().compareTo(b.getHoraIngreso()) < 0;
    }
}
