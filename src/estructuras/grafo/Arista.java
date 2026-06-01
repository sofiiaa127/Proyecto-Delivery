package estructuras.grafo;

public class Arista {
    private Vertice destino;
    private int pesoMinutos; 
    private Arista siguiente; 

    public Arista(Vertice destino, int pesoMinutos) {
        this.destino = destino;
        this.pesoMinutos = pesoMinutos;
        this.siguiente = null;
    }

    public Vertice getDestino() { return destino; }
    public int getPesoMinutos() { return pesoMinutos; }
    public Arista getSiguiente() { return siguiente; }
    public void setSiguiente(Arista siguiente) { this.siguiente = siguiente; }
}