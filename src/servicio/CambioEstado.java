package servicio;

import model.EstadoPedido;

//Representa un cambio de estado de un pedido. Se guarda en la Pila de historial
public class CambioEstado {

    private final int idPedido;
    private final EstadoPedido estado;
    private final String hora;

    public CambioEstado(int idPedido, EstadoPedido estado, String hora) {
        this.idPedido = idPedido;
        this.estado = estado;
        this.hora = hora;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public String getHora() {
        return hora;
    }

    @Override
    public String toString() {
        return "Pedido #" + idPedido + "  ->  " + estado + "   (" + hora + ")";
    }
}
