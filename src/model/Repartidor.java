package model;

public class Repartidor {

    private String id;
    private String nombre;
    private boolean libre;
    private String zonaActual;

    public Repartidor(String id, String nombre, String zonaActual) {
        this.id = id;
        this.nombre = nombre;
        this.zonaActual = zonaActual;
        this.libre = true;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean isLibre() {
        return libre;
    }

    public void setLibre(boolean libre) {
        this.libre = libre;
    }

    public String getZonaActual() {
        return zonaActual;
    }

    public void setZonaActual(String zonaActual) {
        this.zonaActual = zonaActual;
    }

    @Override
    public String toString() {
        return "Repartidor{id='" + id + "', nombre='" + nombre + "', libre=" + libre + ", zona='" + zonaActual + "'}";
    }
}

//Arturo Avirama
