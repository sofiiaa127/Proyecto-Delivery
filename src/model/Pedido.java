package model;

public class Pedido {

    private int id;
    private String descripcion;
    // prioridad del 1 al 5, donde 5 es la mas urgente
    private int prioridad;
    private EstadoPedido estado;
    private String zonaDestino;
    private String horaIngreso;
    private boolean esVip;

    public Pedido(int id, String descripcion, int prioridad, String zonaDestino, String horaIngreso, boolean esVip) {
        this.id = id;
        this.descripcion = descripcion;
        this.prioridad = prioridad;
        this.zonaDestino = zonaDestino;
        this.horaIngreso = horaIngreso;
        this.esVip = esVip;
        this.estado = EstadoPedido.PENDIENTE;
    }

    public int getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    public String getZonaDestino() {
        return zonaDestino;
    }

    public String getHoraIngreso() {
        return horaIngreso;
    }

    public boolean isEsVip() {
        return esVip;
    }

    @Override
    public String toString() {
        return "Pedido{id=" + id + ", descripcion='" + descripcion + "', prioridad=" + prioridad +
               ", estado=" + estado + ", zona='" + zonaDestino + "', hora='" + horaIngreso + "', vip=" + esVip + "}";
    }
}

//Arturo Avirama