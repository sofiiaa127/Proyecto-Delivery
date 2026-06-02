package estructuras.linear;

/**
 * Estructura lineal manual con comportamiento FIFO.
 * Permite agregar elementos al final y retirar elementos del frente.
 */


public class Cola<TipoDato> {
    private Nodo<TipoDato> frente;
    private Nodo<TipoDato> finalCola;
    private int numeroElementos;

    public Cola() {
        this.frente = null;
        this.finalCola = null;
        this.numeroElementos = 0;
    }

    //Agrega un elemento al final de la cola. 
    public void encolar(TipoDato nuevoElemento) {
        Nodo<TipoDato> nuevoNodo = new Nodo<>(nuevoElemento);

        if (estaVacia()) {
            frente = nuevoNodo;
            finalCola = nuevoNodo;
        } else {
            finalCola.asignarSiguiente(nuevoNodo);
            finalCola = nuevoNodo;
        }
        numeroElementos++;
    }

    // Retira y devuelve el elemento del frente (o null si esta vacia).
    public TipoDato desencolar() {
        if (estaVacia()) {
            return null;
        }

        TipoDato datoDelFrente = frente.obtenerDato();
        frente = frente.obtenerSiguiente();

        if (frente == null) {
            finalCola = null;
        }

        numeroElementos--;
        return datoDelFrente;
    }

    public boolean estaVacia() {
        return frente == null;
    }

    public TipoDato consultarFrente() {
        if (estaVacia()) {
            return null;
        }
        return frente.obtenerDato();
    }

    public int obtenerNumeroElementos() {
        return numeroElementos;
    }

    //Invierte el contenido de la cola de frente a final en un arreglo, sin retirar elementos. 
    public Object[] aArreglo() {
        Object[] elementos = new Object[numeroElementos];
        Nodo<TipoDato> actual = frente;
        int i = 0;
        while (actual != null) {
            elementos[i++] = actual.obtenerDato();
            actual = actual.obtenerSiguiente();
        }
        return elementos;
    }
}
