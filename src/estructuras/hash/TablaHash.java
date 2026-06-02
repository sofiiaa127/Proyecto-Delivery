package estructuras.hash;

public class TablaHash<K, V> {
    private EntradaHash<K, V>[] buckets;
    private int capacidad;
    private int tamaño;
    
    // Factor de carga óptimo para equilibrar uso de memoria y rendimiento en las búsquedas
    private static final double FACTOR_CARGA_LIMITE = 0.75;

    //Constructor que recibe una capacidad inicial específica.
    @SuppressWarnings("unchecked")
    public TablaHash(int capacidadInicial) {
        this.capacidad = capacidadInicial;
        this.buckets = (EntradaHash<K, V>[]) new EntradaHash[capacidad];
        this.tamaño = 0;
    }

    public TablaHash() {
        this(16); //Inicializa la tabla con un tamaño estándar de 16.
    }

    //Determina la posición (índice) dentro del arreglo.
    private int calcularIndice(K clave) {
        if (clave == null) {
            return 0;
        }
        return Math.abs(clave.hashCode()) % capacidad;
    }

    //Inserta un nuevo elemento o actualiza el valor si la clave ya existe.
    public void put(K clave, V valor) {
        int indice = calcularIndice(clave);
        EntradaHash<K, V> cabeza = buckets[indice];

        // Buscar si la clave ya se encuentra registrada en la lista enlazada 
        while (cabeza != null) {
            if (cabeza.getClave().equals(clave)) {
                cabeza.setValor(valor);
                return;
            }
            cabeza = cabeza.getSiguiente();
        }

        // Si es una clave nueva, se crea el nodo y se inserta al inicio de la lista 
        EntradaHash<K, V> nuevoNodo = new EntradaHash<>(clave, valor);
        nuevoNodo.setSiguiente(buckets[indice]);
        buckets[indice] = nuevoNodo;
        tamaño++;

        if ((double) tamaño / capacidad >= FACTOR_CARGA_LIMITE) {
            redimensionar();
        }
    }

    //Recupera el valor asociado a una clave en tiempo un timepo promedio 
    public V get(K clave) {
        int indice = calcularIndice(clave);
        EntradaHash<K, V> actual = buckets[indice];

        while (actual != null) {
            if (actual.getClave().equals(clave)) {
                return actual.getValor();
            }
            actual = actual.getSiguiente();
        }
        return null; // Retorna null si la clave no existe
    }

    //Elimina una entrada de la tabla basándose en su clave y retorna su valor asociado.
    public V remove(K clave) {
        int indice = calcularIndice(clave);
        EntradaHash<K, V> actual = buckets[indice];
        EntradaHash<K, V> anterior = null;

        while (actual != null) {
            if (actual.getClave().equals(clave)) {
                if (anterior == null) {
                    buckets[indice] = actual.getSiguiente();
                } else {
                    anterior.setSiguiente(actual.getSiguiente());
                }
                tamaño--;
                return actual.getValor();
            }
            anterior = actual;
            actual = actual.getSiguiente();
        }
        return null;
    }

    /**
     * Duplica el tamaño del arreglo interno y reorganiza todos los elementos
     * para disminuir las colisiones cuando la tabla se va llenando.
     */
    @SuppressWarnings("unchecked")
    private void redimensionar() {
        int nuevaCapacidad = capacidad * 2;
        EntradaHash<K, V>[] viejosBuckets = buckets;
        
        this.capacidad = nuevaCapacidad;
        this.buckets = (EntradaHash<K, V>[]) new EntradaHash[nuevaCapacidad];
        this.tamaño = 0;

        // Transfiere los elementos viejos enlazándolos a nuevos índices
        for (int i = 0; i < viejosBuckets.length; i++) {
            EntradaHash<K, V> actual = viejosBuckets[i];
            while (actual != null) {
                put(actual.getClave(), actual.getValor());
                actual = actual.getSiguiente();
            }
        }
    }

    public int size() {
        return tamaño;
    }

    public boolean isEmpty() {
        return tamaño == 0;
    }
    
    public EntradaHash<K, V>[] getBuckets() {
        return this.buckets;
    }
}