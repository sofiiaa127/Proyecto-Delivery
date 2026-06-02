package estructuras.arbol;
/**
 * Arbol Binario de Busqueda para rankear restaurantes por calificacion.
 * La calificacion se guarda como entero 
 * El Top 5 se obtiene con un recorrido inverso (in-order de mayor a menor).
 */


public class ArbolBinario {
    private NodoArbol raiz;
    private int contadorTop;
    private String[] bufferTop;

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
        if (calificacion < nodo.getCalificacion()) {
            nodo.setIzquierdo(insertarRecursivo(nodo.getIzquierdo(), nombre, calificacion));
        } else {
            nodo.setDerecho(insertarRecursivo(nodo.getDerecho(), nombre, calificacion));
        }
        return nodo;
    }

    public String[] obtenerTop5() {
        bufferTop = new String[5];
        contadorTop = 0;
        recorridoInversoBuffer(raiz);

        String[] resultado = new String[contadorTop];
        for (int i = 0; i < contadorTop; i++) {
            resultado[i] = bufferTop[i];
        }
        return resultado;
    }

    private void recorridoInversoBuffer(NodoArbol nodo) {
        if (nodo != null && contadorTop < 5) {
            recorridoInversoBuffer(nodo.getDerecho());
            if (contadorTop < 5) {
                double estrellas = nodo.getCalificacion() / 10.0;
                bufferTop[contadorTop] = (contadorTop + 1) + ". " + nodo.getNombreRestaurante()
                        + "  -  " + String.format("%.1f", estrellas) + " estrellas";
                contadorTop++;
            }
            recorridoInversoBuffer(nodo.getIzquierdo());
        }
    }
    
    public void mostrarTop5() {
        String[] top = obtenerTop5();
        System.out.println("--- Top 5 Restaurantes ---");
        for (String linea : top) {
            System.out.println(linea);
        }
    }
}
