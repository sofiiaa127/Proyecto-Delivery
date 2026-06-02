package estructuras.linear;

/**
 * Cola de prioridad implementada como un monticulo (heap) maximo manual.
 * Inserta elementos segun su prioridad y extrae siempre el mas prioritario.
 */


public class ColaPrioridad<TipoDato extends Comparable<TipoDato>> {

    private static final int CAPACIDAD_INICIAL = 11;

    private TipoDato[] monticulo;
    private int numeroElementos;

    @SuppressWarnings("unchecked")
    public ColaPrioridad() {
        this.monticulo = (TipoDato[]) new Comparable[CAPACIDAD_INICIAL];
        this.numeroElementos = 0;
    }

    //Inserta un elemento respetando la prioridad 
    public void insertar(TipoDato nuevoElemento) {
        if (numeroElementos >= monticulo.length - 1) {
            duplicarCapacidad();
        }
        numeroElementos++;
        monticulo[numeroElementos] = nuevoElemento;
        flotar(numeroElementos);
    }

    //Extrae y devuelve el elemento con mayor prioridad.
    public TipoDato extraerMaximo() {
        if (estaVacia()) {
            return null;
        }
        TipoDato elementoMaximo = monticulo[1];
        monticulo[1] = monticulo[numeroElementos];
        monticulo[numeroElementos] = null;
        numeroElementos--;
        if (numeroElementos > 0) {
            hundir(1);
        }
        return elementoMaximo;
    }

    public boolean estaVacia() {
        return numeroElementos == 0;
    }

    public TipoDato consultarMaximo() {
        if (estaVacia()) {
            return null;
        }
        return monticulo[1];
    }

    public int obtenerNumeroElementos() {
        return numeroElementos;
    }

    //Devuelve los elementos del monticulo en un arreglo,sin extraerlos(cola VIP)
    public Object[] aArreglo() {
        Object[] elementos = new Object[numeroElementos];
        for (int i = 0; i < numeroElementos; i++) {
            elementos[i] = monticulo[i + 1];
        }
        return elementos;
    }

    private void flotar(int posicionActual) {
        while (posicionActual > 1 &&
                monticulo[posicionActual].compareTo(monticulo[posicionActual / 2]) > 0) {
            intercambiar(posicionActual, posicionActual / 2);
            posicionActual = posicionActual / 2;
        }
    }

    private void hundir(int posicionActual) {
        while (2 * posicionActual <= numeroElementos) {
            int posicionHijoMayor = 2 * posicionActual;
            if (posicionHijoMayor < numeroElementos &&
                    monticulo[posicionHijoMayor].compareTo(monticulo[posicionHijoMayor + 1]) < 0) {
                posicionHijoMayor++;
            }
            if (monticulo[posicionActual].compareTo(monticulo[posicionHijoMayor]) >= 0) {
                break;
            }
            intercambiar(posicionActual, posicionHijoMayor);
            posicionActual = posicionHijoMayor;
        }
    }

    private void intercambiar(int posicionUno, int posicionDos) {
        TipoDato temporal = monticulo[posicionUno];
        monticulo[posicionUno] = monticulo[posicionDos];
        monticulo[posicionDos] = temporal;
    }

    @SuppressWarnings("unchecked")
    private void duplicarCapacidad() {
        TipoDato[] nuevoMonticulo = (TipoDato[]) new Comparable[monticulo.length * 2];
        System.arraycopy(monticulo, 0, nuevoMonticulo, 0, monticulo.length);
        monticulo = nuevoMonticulo;
    }
}
