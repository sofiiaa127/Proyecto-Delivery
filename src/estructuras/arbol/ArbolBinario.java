package estructuras.arbol;

public class ArbolBinario {
    private NodoArbol raiz;
    private int contadorTop; 

    public ArbolBinario() {
        this.raiz = null;
    }

    public void insertar(String nombre, int calificacion) {
        raiz = insertarRecursivo(raiz, nombre, calificacion);
    }

    private NodoArbol insertarRecursivo(NodoArbol nodo, String nombre, int calificacion) {
        if (nodo == null) {
            return new NodoArbol(nombre, calificacion);
        }
        // Las calificaciones menores van a la izquierda, mayores a la derecha
        if (calificacion < nodo.getCalificacion()) {
            nodo.setIzquierdo(insertarRecursivo(nodo.getIzquierdo(), nombre, calificacion));
        } else {
            nodo.setDerecho(insertarRecursivo(nodo.getDerecho(), nombre, calificacion));
        }
        return nodo;
    }

    public void mostrarTop5() {
        System.out.println("--- Top 5 Restaurantes ---");
        contadorTop = 0;
        recorridoInverso(raiz);
    }

    private void recorridoInverso(NodoArbol nodo) {
        if (nodo != null && contadorTop < 5) {
            recorridoInverso(nodo.getDerecho()); 
            if (contadorTop < 5) {
                System.out.println((contadorTop + 1) + ". " + nodo.getNombreRestaurante() + 
                                   " - Calificación: " + nodo.getCalificacion() + " estrellas");
                contadorTop++;
            }
            
            recorridoInverso(nodo.getIzquierdo());
        }
    }
}