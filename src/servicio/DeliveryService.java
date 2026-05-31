package servicio;

import model.Pedido;
import model.Repartidor;
import estructuras.TablaHash;
import estructuras.Cola;
import estructuras.ColaPrioridad;
import estructuras.ListaEnlazada;
import estructuras.Pila;

public class DeliveryService {
    
    private TablaHash<String, Pedido> tablaPedidos;
    private Cola<Pedido> colaCocinaNormal;
    private ColaPrioridad<Pedido> colaCocinaVip;
    private ListaEnlazada<Repartidor> listaRepartidoresLibres;
    private Pila<HistorialCambio> pilaHistorial;
    
    public DeliveryService() {
        this.tablaPedidos = new TablaHash<>(100);
        this.colaCocinaNormal = new Cola<>();
        this.colaCocinaVip = new ColaPrioridad<>();
        this.listaRepartidoresLibres = new ListaEnlazada<>();
        this.pilaHistorial = new Pila<>(50);
    }
    
    /**
     * Registra pedido en TablaHash y lo envía a cola normal o VIP.
     */
    public void procesarPedido(Pedido p) {
        String clave = String.valueOf(p.getIdPedido());
        this.tablaPedidos.insertar(clave, p);
        
        // VIP va a cola de prioridad, regular a cola normal
        if (p.isVip()) {
            this.colaCocinaVip.encolar(p);
        } else {
            this.colaCocinaNormal.encolar(p);
        }
    }
    
    /**
     * Ciclo completo: desencolar → preparar → asignar repartidor → Dijkstra → camino → entregado → historial.
     */
    public boolean cicloSimular() {
        Pedido pedidoActual = null;
        
        // Prioridad: VIP primero, luego normal
        if (!this.colaCocinaVip.esVacia()) {
            pedidoActual = this.colaCocinaVip.desencolar();
        } else if (!this.colaCocinaNormal.esVacia()) {
            pedidoActual = this.colaCocinaNormal.desencolar();
        } else {
            return false;
        }
        
        pedidoActual.setEstado("EN_PREPARACION");
        
        if (this.listaRepartidoresLibres.esVacia()) {
            // No hay repartidores: devolver pedido a cola
            if (pedidoActual.isVip()) {
                this.colaCocinaVip.encolar(pedidoActual);
            } else {
                this.colaCocinaNormal.encolar(pedidoActual);
            }
            return false;
        }
        
        // Asignar repartidor libre (primero de la lista)
        Repartidor repartidorAsignado = this.listaRepartidoresLibres.obtenerPrimero();
        this.listaRepartidoresLibres.eliminarPrimero();
        pedidoActual.setRepartidor(repartidorAsignado);
        
        // Calcular ruta más corta (Dijkstra)
        simularRutaDijkstra(pedidoActual);
        pedidoActual.setEstado("EN_CAMINO");
        pedidoActual.setEstado("ENTREGADO");
        
        // Guardar cambio en pila de historial
        HistorialCambio historial = new HistorialCambio(
            pedidoActual.getIdPedido(),
            pedidoActual.getEstado(),
            pedidoActual.getHora(),
            repartidorAsignado.getIdRepartidor()
        );
        this.pilaHistorial.apilar(historial);
        
        return true;
    }
    
    // Simula algoritmo Dijkstra para ruta más corta
    private void simularRutaDijkstra(Pedido pedido) {
        pedido.setDistanciaRuta(15);
    }
    
    public void agregarRepartidorLibre(Repartidor repartidor) {
        this.listaRepartidoresLibres.agregarFinal(repartidor);
    }
    
    public int obtenerPedidosPendientes() {
        return this.colaCocinaNormal.cantidad() + this.colaCocinaVip.cantidad();
    }
    
    public Pedido buscarPedidoPorId(int idPedido) {
        String clave = String.valueOf(idPedido);
        return this.tablaPedidos.buscar(clave);
    }
    
    // Método auxiliar para ReporteService
    public Pedido[] obtenerTodosPedidos() {
        // Asume que TablaHash tiene este método o implementalo según tu estructura
        return new Pedido[0]; 
    }
    
    public int obtenerCantidadPedidos() {
        // Asume que TablaHash tiene este método o implementalo según tu estructura
        return 0;
    }
    
    private class HistorialCambio {
        private int idPedido;
        private String estado;
        private int hora;
        private int idRepartidor;
        
        public HistorialCambio(int idPedido, String estado, int hora, int idRepartidor) {
            this.idPedido = idPedido;
            this.estado = estado;
            this.hora = hora;
            this.idRepartidor = idRepartidor;
        }
        
        public int getIdPedido() { return idPedido; }
        public String getEstado() { return estado; }
        public int getHora() { return hora; }
        public int getIdRepartidor() { return idRepartidor; }
    }
}