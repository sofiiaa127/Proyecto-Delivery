package model;

public class Zona {

    private String id;
    private String nombre;

    public Zona(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Zona{id='" + id + "', nombre='" + nombre + "'}";
    }
}

//Arturo Avirama
