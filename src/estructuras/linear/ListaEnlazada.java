package estructuras.linear;

//Permite almacenar y recorrer elementos sin usar librerias de Java.
public class ListaEnlazada<TipoDato> {

    private Nodo<TipoDato> cabeza;
    private int numeroElementos;

    public ListaEnlazada() {
        this.cabeza = null;
        this.numeroElementos = 0;
    }

    public void insertarAlFinal(TipoDato nuevoElemento) {
        Nodo<TipoDato> nuevoNodo = new Nodo<>(nuevoElemento);

        if (estaVacia()) {
            cabeza = nuevoNodo;
        } else {
            Nodo<TipoDato> nodoActual = cabeza;
            while (nodoActual.obtenerSiguiente() != null) {
                nodoActual = nodoActual.obtenerSiguiente();
            }
            nodoActual.asignarSiguiente(nuevoNodo);
        }

        numeroElementos++;
    }

    public boolean estaVacia() {
        return cabeza == null;
    }

    public Nodo<TipoDato> obtenerCabeza() {
        return cabeza;
    }

    public int obtenerNumeroElementos() {
        return numeroElementos;
    }

    public Object[] aArreglo() {
        Object[] elementos = new Object[numeroElementos];
        Nodo<TipoDato> actual = cabeza;
        int i = 0;
        while (actual != null) {
            elementos[i++] = actual.obtenerDato();
            actual = actual.obtenerSiguiente();
        }
        return elementos;
    }
}