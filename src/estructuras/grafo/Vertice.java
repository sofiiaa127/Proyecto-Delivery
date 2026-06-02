package estructuras.grafo;


 //Representa una zona o intersección en el mapa de entregas.
public class Vertice {
    private String nombreZona;
    private Arista primeraArista;

    public Vertice(String nombreZona) {
        this.nombreZona = nombreZona;
        this.primeraArista = null;
    }

    public String getNombreZona() {
        return nombreZona;
    }

    public Arista getPrimeraArista() {
        return primeraArista;
    }

    public void agregarArista(Vertice destino, int pesoMinutos) {
        Arista nuevaArista = new Arista(destino, pesoMinutos);
        nuevaArista.setSiguiente(primeraArista);
        primeraArista = nuevaArista;
    }
}