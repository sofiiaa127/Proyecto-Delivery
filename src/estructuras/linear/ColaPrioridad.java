package estructuras.linear;


 //Permite insertar elementos según su prioridad y extraer siempre el más prioritario
 //@param <TipoDato> tipo de dato que define su orden natural con Comparable
 
public class ColaPrioridad<TipoDato extends Comparable<TipoDato>> {

    private static final int CAPACIDAD_INICIAL = 11;

    private TipoDato[] monticulo;
    private int numeroElementos;

    
     //Crea una cola de prioridad vacía.
     
    @SuppressWarnings("unchecked")
    public ColaPrioridad() {
        this.monticulo = (TipoDato[]) new Comparable[CAPACIDAD_INICIAL];
        this.numeroElementos = 0;
    }

    
     //Inserta un elemento respetando la prioridad.
     //@param nuevoElemento elemento que se agregará
    
    public void insertar(TipoDato nuevoElemento) {
        if (numeroElementos >= monticulo.length - 1) {
            duplicarCapacidad();
        }

        numeroElementos++;
        monticulo[numeroElementos] = nuevoElemento;
        flotar(numeroElementos);
    }

    
     //Extrae y devuelve el elemento con mayor prioridad
    
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

    
     //Indica si la cola de prioridad no tiene elementos
    
    public boolean estaVacia() {
        return numeroElementos == 0;
    }

    
     //Obtiene el elemento con mayor prioridad sin retirarlo.
    
    public TipoDato consultarMaximo() {
        if (estaVacia()) {
            return null;
        }

        return monticulo[1];
    }

    
     //Devuelve la cantidad de elementos almacenados
     
    public int obtenerNumeroElementos() {
        return numeroElementos;
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