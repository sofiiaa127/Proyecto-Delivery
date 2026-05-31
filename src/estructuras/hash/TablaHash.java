package estructuras.hash;

/**
 * Estructura de Datos propia de Tabla Hash con resolución de colisiones
 * por encadenamiento. Diseñada bajo principios de Clean Code y POO.
 * No utiliza ninguna colección nativa del lenguaje.
 *
 * @param <K> Tipo de la clave
 * @param <V> Tipo del valor
 */
public class TablaHash<K, V> {
    private EntradaHash<K, V>[] buckets;
    private int capacidad;
    private int tamaño;
    
    // Factor de carga óptimo para equilibrar uso de memoria y rendimiento en las búsquedas
    private static final double FACTOR_CARGA_LIMITE = 0.75;

    /**
     * Constructor que recibe una capacidad inicial específica.
     * Resuelve el casteo de arreglos genéricos de forma segura.
     */
    @SuppressWarnings("unchecked")
    public TablaHash(int capacidadInicial) {
        this.capacidad = capacidadInicial;
        this.buckets = (EntradaHash<K, V>[]) new EntradaHash[capacidad];
        this.tamaño = 0;
    }

    /**
     * Constructor por defecto. Inicializa la tabla con un tamaño estándar de 16.
     */
    public TablaHash() {
        this(16);
    }

    /**
     * Función hash matemática para determinar la posición (índice) dentro del arreglo.
     */
    private int calcularIndice(K clave) {
        if (clave == null) {
            return 0;
        }
        // Math.abs evita índices negativos causados por desbordamientos de bits en hashCode()
        return Math.abs(clave.hashCode()) % capacidad;
    }

    /**
     * Inserta un nuevo elemento o actualiza el valor si la clave ya existe.
     */
    public void put(K clave, V valor) {
        int indice = calcularIndice(clave);
        EntradaHash<K, V> cabeza = buckets[indice];

        // Buscar si la clave ya se encuentra registrada en la lista enlazada del bucket
        while (cabeza != null) {
            if (cabeza.getClave().equals(clave)) {
                cabeza.setValor(valor);
                return;
            }
            cabeza = cabeza.getSiguiente();
        }

        // Si es una clave nueva, se crea el nodo y se inserta al inicio de la lista en O(1)
        EntradaHash<K, V> nuevoNodo = new EntradaHash<>(clave, valor);
        nuevoNodo.setSiguiente(buckets[indice]);
        buckets[indice] = nuevoNodo;
        tamaño++;

        // Evaluar si la densidad de colisiones requiere un redimensionamiento
        if ((double) tamaño / capacidad >= FACTOR_CARGA_LIMITE) {
            redimensionar();
        }
    }

    /**
     * Recupera el valor asociado a una clave en tiempo constante promedio O(1).
     */
    public V get(K clave) {
        int indice = calcularIndice(clave);
        EntradaHash<K, V> actual = buckets[indice];

        // Recorrido lineal de la sublista enlazada en caso de colisión
        while (actual != null) {
            if (actual.getClave().equals(clave)) {
                return actual.getValor();
            }
            actual = actual.getSiguiente();
        }
        return null; // Retorna null si la clave no existe
    }

    /**
     * Elimina una entrada de la tabla basándose en su clave y retorna su valor asociado.
     */
    public V remove(K clave) {
        int indice = calcularIndice(clave);
        EntradaHash<K, V> actual = buckets[indice];
        EntradaHash<K, V> anterior = null;

        while (actual != null) {
            if (actual.getClave().equals(clave)) {
                // Ajustar los punteros de la lista enlazada para remover el elemento de forma segura
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
     * Duplica el tamaño del arreglo interno y reorganiza (re-hashea) todos los elementos
     * para mitigar colisiones cuando la tabla se va llenando.
     */
    @SuppressWarnings("unchecked")
    private void redimensionar() {
        int nuevaCapacidad = capacidad * 2;
        EntradaHash<K, V>[] viejosBuckets = buckets;
        
        this.capacidad = nuevaCapacidad;
        this.buckets = (EntradaHash<K, V>[]) new EntradaHash[nuevaCapacidad];
        this.tamaño = 0;

        

        // Transferir los elementos viejos mapeándolos a sus nuevos índices
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

    /**
     * Expone el arreglo de buckets de forma controlada.
     * Permite a capas de servicios externas (como reportes) volcar la información
     * sin violar las restricciones de no usar iteradores nativos de Java.
     */
    public EntradaHash<K, V>[] getBuckets() {
        return this.buckets;
    }
}