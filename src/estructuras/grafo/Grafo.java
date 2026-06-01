package estructuras.grafo;

public class Grafo {
    private Vertice[] zonas;
    private int cantidadZonas;
    private final int MAX_ZONAS = 10; 

    public Grafo() {
        this.zonas = new Vertice[MAX_ZONAS];
        this.cantidadZonas = 0;
    }

    public void agregarZona(String nombreZona) {
        if (cantidadZonas < MAX_ZONAS) {
            zonas[cantidadZonas] = new Vertice(nombreZona);
            cantidadZonas++;
        } else {
            System.out.println("No se pueden agregar más zonas al mapa.");
        }
    }

    public void conectarZonas(int indiceOrigen, int indiceDestino, int pesoMinutos) {
        if (indiceOrigen >= 0 && indiceOrigen < cantidadZonas && 
            indiceDestino >= 0 && indiceDestino < cantidadZonas) {
            
            // Grafo no dirigido (Bidireccional)
            zonas[indiceOrigen].agregarArista(zonas[indiceDestino], pesoMinutos);
            zonas[indiceDestino].agregarArista(zonas[indiceOrigen], pesoMinutos);
        }
    }

    public Vertice[] getZonas() {
        return zonas;
    }

    public int getCantidadZonas() {
        return cantidadZonas;
    }
}