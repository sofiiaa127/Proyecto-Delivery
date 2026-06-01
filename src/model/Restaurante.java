package model;

public class Restaurante {

    private String id;
    private String nombre;
    // valor entre 1.0 y 5.0
    private double calificacion;

    public Restaurante(String id, String nombre, double calificacion) {
        this.id = id;
        this.nombre = nombre;
        this.calificacion = calificacion;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(double calificacion) {
        this.calificacion = calificacion;
    }

    @Override
    public String toString() {
        return "Restaurante{id='" + id + "', nombre='" + nombre + "', calificacion=" + calificacion + "}";
    }
}

//Arturo Avirama
