package estructuras.hash;

/**
 * Representa un nodo (par clave-valor) dentro de la Tabla Hash.
 * Permite el manejo de colisiones mediante el método de encadenamiento.
 */


public class EntradaHash<K, V> {
    private final K clave;
    private V valor;
    private EntradaHash<K, V> siguiente;

    //Constructor para inicializar una nueva entrada en la tabla.
    public EntradaHash(K clave, V valor) {
        this.clave = clave;
        this.valor = valor;
        this.siguiente = null;
    }
    
    public K getClave() {
        return clave;
    }

    public V getValor() {
        return valor;
    }

    public void setValor(V valor) {
        this.valor = valor;
    }

    public EntradaHash<K, V> getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(EntradaHash<K, V> siguiente) {
        this.siguiente = siguiente;
    }
}