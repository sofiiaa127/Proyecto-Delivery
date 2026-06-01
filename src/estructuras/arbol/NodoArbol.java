package estructuras.arbol;


public class NodoArbol {
    private String nombreRestaurante;
    private int calificacion;
    private NodoArbol izquierdo;
    private NodoArbol derecho;

    public NodoArbol(String nombreRestaurante, int calificacion) {
        this.nombreRestaurante = nombreRestaurante;
        this.calificacion = calificacion;
        this.izquierdo = null;
        this.derecho = null;
    }

    public String getNombreRestaurante() { return nombreRestaurante; }
    public int getCalificacion() { return calificacion; }
    public NodoArbol getIzquierdo() { return izquierdo; }
    public void setIzquierdo(NodoArbol izquierdo) { this.izquierdo = izquierdo; }
    public NodoArbol getDerecho() { return derecho; }
    public void setDerecho(NodoArbol derecho) { this.derecho = derecho; }
}