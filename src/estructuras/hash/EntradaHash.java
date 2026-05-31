package estructuras.hash;

/**
 * Clase que representa un nodo (par clave-valor) dentro de la Tabla Hash.
 * Permite el manejo de colisiones mediante el método de encadenamiento.
 *
 * @param <K> Tipo de la clave (ID del Pedido)
 * @param <V> Tipo del valor (Objeto Pedido)
 */
public class EntradaHash<K, V> {
    private final K clave;
    private V valor;
    private EntradaHash<K, V> siguiente;

    /**
     * Constructor para inicializar una nueva entrada en la tabla.
     */
    public EntradaHash(K clave, V valor) {
        this.clave = clave;
        this.valor = valor;
        this.siguiente = null;
    }

    // Métodos de acceso (Getters y Setters) públicos para integración interpaquetes
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