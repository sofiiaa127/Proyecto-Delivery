package model;


//Representa un pedido del sistema de delivery :
//a mayor prioridad, mayor "valor", por lo que el pedido mas urgente sale primero.
public class Pedido implements Comparable<Pedido> {


    private int id;
    private String descripcion;
    private int prioridad;
    private EstadoPedido estado;
    private String zonaDestino;
    private String horaIngreso;
    private boolean esVip;




    private Repartidor repartidor;
    private int minutosEntrega;


    public Pedido(int id, String descripcion, int prioridad, String zonaDestino,
    String horaIngreso, boolean esVip) {


        this.id = id;
        this.descripcion = descripcion;
        this.prioridad = prioridad;
        this.zonaDestino = zonaDestino;
        this.horaIngreso = horaIngreso;
        this.esVip = esVip;
        this.estado = EstadoPedido.PENDIENTE;
        this.repartidor = null;
        this.minutosEntrega = 0;
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


    public Repartidor getRepartidor() {
        return repartidor;
    }


    public void setRepartidor(Repartidor repartidor) {
        this.repartidor = repartidor;
    }


    public int getMinutosEntrega() {
        return minutosEntrega;
    }


    public void setMinutosEntrega(int minutosEntrega) {
        this.minutosEntrega = minutosEntrega;
    }


    /**
     * Compara por prioridad para el heap de la cola de prioridad.
     * Devuelve positivo cuando este pedido es mas urgente que el otro,
     * de modo que extraerMaximo() entregue siempre el mas prioritario.
     */
    public int compareTo(Pedido otro) {
        return Integer.compare(this.prioridad, otro.prioridad);
    }


    public String toString() {
        return "Pedido{id=" + id + ", descripcion='" + descripcion + "', prioridad=" + prioridad +
        ", estado=" + estado + ", zona='" + zonaDestino + "', hora='" + horaIngreso + 
        "', vip=" + esVip + "}";
    }
}


//Arturo Avirama