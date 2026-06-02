package estructuras.linear;

/**
 * Estructura lineal manual con comportamiento LIFO.
 * Permite apilar elementos y retirar siempre el ultimo agregado.
 */
public class Pila<TipoDato> {

    private Nodo<TipoDato> cima;
    private int numeroElementos;

    public Pila() {
        this.cima = null;
        this.numeroElementos = 0;
    }

    public void apilar(TipoDato nuevoElemento) {
        Nodo<TipoDato> nuevoNodo = new Nodo<>(nuevoElemento);
        nuevoNodo.asignarSiguiente(cima);
        cima = nuevoNodo;
        numeroElementos++;
    }

    public TipoDato desapilar() {
        if (estaVacia()) {
            return null;
        }
        TipoDato datoDeLaCima = cima.obtenerDato();
        cima = cima.obtenerSiguiente();
        numeroElementos--;
        return datoDeLaCima;
    }

    public boolean estaVacia() {
        return cima == null;
    }

    public TipoDato consultarCima() {
        if (estaVacia()) {
            return null;
        }
        return cima.obtenerDato();
    }

    public int obtenerNumeroElementos() {
        return numeroElementos;
    }

    //Invierte el contenido de la pila (de la cima hacia el fondo) en un arreglo, sin desapilar.
    public Object[] aArreglo() {
        Object[] elementos = new Object[numeroElementos];
        Nodo<TipoDato> actual = cima;
        int i = 0;
        while (actual != null) {
            elementos[i++] = actual.obtenerDato();
            actual = actual.obtenerSiguiente();
        }
        return elementos;
    }
}
