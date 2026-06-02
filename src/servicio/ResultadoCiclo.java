package servicio;

import model.Pedido;
import model.Repartidor;
import estructuras.grafo.ResultadoDijkstra;

/**
 * Resultado de procesar un pedido en un ciclo de la simulacion.
 * Reune el pedido atendido, el repartidor asignado, la ruta calculada por
 * Dijkstra y un mensaje descriptivo para mostrar en la interfaz.
 */
public class ResultadoCiclo {

    private final boolean exito;
    private final String mensaje;
    private final Pedido pedido;
    private final Repartidor repartidor;
    private final ResultadoDijkstra ruta;

    public ResultadoCiclo(boolean exito, String mensaje, Pedido pedido,
        Repartidor repartidor, ResultadoDijkstra ruta) {
        this.exito = exito;
        this.mensaje = mensaje;
        this.pedido = pedido;
        this.repartidor = repartidor;
        this.ruta = ruta;
    }

    public static ResultadoCiclo fallo(String mensaje) {
        return new ResultadoCiclo(false, mensaje, null, null, null);
    }

    public boolean isExito() {
        return exito;
    }

    public String getMensaje() {
        return mensaje;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public Repartidor getRepartidor() {
        return repartidor;
    }

    public ResultadoDijkstra getRuta() {
        return ruta;
    }
}
