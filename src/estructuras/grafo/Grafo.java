package estructuras.grafo;

/**
 * Grafo no dirigido y ponderado que representa el mapa de barrios.
 * Cada vertice es una zona y cada arista el tiempo en minutos entre dos zonas.
 */


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
            System.out.println("No se pueden agregar mas zonas al mapa.");
        }
    }

    public void conectarZonas(int indiceOrigen, int indiceDestino, int pesoMinutos) {
        if (indiceOrigen >= 0 && indiceOrigen < cantidadZonas &&
            indiceDestino >= 0 && indiceDestino < cantidadZonas) {

            // Grafo no dirigido (bidireccional)
            zonas[indiceOrigen].agregarArista(zonas[indiceDestino], pesoMinutos);
            zonas[indiceDestino].agregarArista(zonas[indiceOrigen], pesoMinutos);
        }
    }

     // Devuelve el indice de una zona buscando por su nombre, o -1 si no existe. 
    public int getIndiceZona(String nombreZona) {
        for (int i = 0; i < cantidadZonas; i++) {
            if (zonas[i].getNombreZona().equals(nombreZona)) {
                return i;
            }
        }
        return -1;
    }

    public Vertice[] getZonas() {
        return zonas;
    }

    public int getCantidadZonas() {
        return cantidadZonas;
    }
}
