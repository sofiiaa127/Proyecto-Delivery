package servicio;

import model.Pedido;
import model.Repartidor;
import model.Restaurante;
import model.EstadoPedido;

import estructuras.hash.TablaHash;
import estructuras.hash.EntradaHash;
import estructuras.linear.Cola;
import estructuras.linear.ColaPrioridad;
import estructuras.linear.ListaEnlazada;
import estructuras.linear.Pila;
import estructuras.linear.Nodo;
import estructuras.grafo.Grafo;
import estructuras.grafo.Dijkstra;
import estructuras.grafo.ResultadoDijkstra;
import estructuras.arbol.ArbolBinario;

/**
 * Servicio principal del simulador de delivery. Coordina todas las estructuras:
 *
 *  - TablaHash: consulta de pedidos por ID en O(1) promedio.
 *  - Cola: pedidos normales (FIFO, primero en llegar primero en cocina).
 *  - ColaPrioridad(heap): pedidos VIP, sale primero el de mayor prioridad.
 *  - ListaEnlazada: repartidores (se marcan libre/ocupado sin reasignar arreglos).
 *  - Pila: historial de cambios de estado (deshacer / auditar).
 *  - Grafo + Dijkstra: mapa de zonas y ruta mas corta restaurante -> cliente.
 *  - ArbolBinario: ranking (Top 5) de restaurantes por calificacion.
 */
public class DeliveryService {

    private final TablaHash<String, Pedido> tablaPedidos;
    private final Cola<Pedido> colaCocinaNormal;
    private final ColaPrioridad<Pedido> colaCocinaVip;
    private final ListaEnlazada<Repartidor> listaRepartidores;
    private final ListaEnlazada<Restaurante> listaRestaurantes;
    private final Pila<CambioEstado> pilaHistorial;
    private final Grafo grafoMapa;
    private final Dijkstra dijkstra;
    private final ArbolBinario rankingRestaurantes;

    public DeliveryService() {
        this.tablaPedidos = new TablaHash<>(100);
        this.colaCocinaNormal = new Cola<>();
        this.colaCocinaVip = new ColaPrioridad<>();
        this.listaRepartidores = new ListaEnlazada<>();
        this.listaRestaurantes = new ListaEnlazada<>();
        this.pilaHistorial = new Pila<>();
        this.grafoMapa = new Grafo();
        this.dijkstra = new Dijkstra();
        this.rankingRestaurantes = new ArbolBinario();
        inicializarMapa();
    }

    /** Construye el mapa de 10 zonas (barrios) con sus distancias en minutos. */
    private void inicializarMapa() {
        String[] zonas = {
            "Centro", "Norte", "Barrio Bolivar", "Chapinero", "La 20",
            "El obando", "Bella Vista", "El cadillal", "Las Americas", "La Ladera"
        };
        for (String zona : zonas) {
            grafoMapa.agregarZona(zona);
        }

        // Conexiones (origen, destino, minutos). 
        grafoMapa.conectarZonas(0, 1, 8);   // Centro - Norte
        grafoMapa.conectarZonas(0, 2, 7);   // Centro - Barrio Bolivar
        grafoMapa.conectarZonas(0, 5, 5);   // Centro - El Obando
        grafoMapa.conectarZonas(1, 8, 6);   // Norte - Las Americas
        grafoMapa.conectarZonas(5, 8, 4);   // El Obando - Las americas
        grafoMapa.conectarZonas(5, 3, 9);   // El Obando - Chapinero
        grafoMapa.conectarZonas(1, 7, 10);  // Norte - El cadillal
        grafoMapa.conectarZonas(8, 7, 7);   // Las Americas - El cadillal
        grafoMapa.conectarZonas(2, 6, 6);   // Barrio Bolivar - Bella Vista
        grafoMapa.conectarZonas(6, 9, 5);   // Bella Vista - La Ladera
        grafoMapa.conectarZonas(2, 9, 8);   // Barrio Bolivar - La Ladera
        grafoMapa.conectarZonas(0, 4, 6);   // Centro - La 20
        grafoMapa.conectarZonas(4, 6, 7);   // La 20 - Bella Vista
        grafoMapa.conectarZonas(3, 8, 8);   // Chapinero - Las Americas
        grafoMapa.conectarZonas(4, 7, 9);   // La 20 - El cadillal
    }

    /**
     * Registra el pedido en la TablaHash y lo encola en cocina:
     * a la cola de prioridad si es VIP, o a la cola normal si es regular.
     */
    public void registrarPedido(Pedido pedido) {
        String clave = String.valueOf(pedido.getId());
        tablaPedidos.put(clave, pedido);

        if (pedido.isEsVip()) {
            colaCocinaVip.insertar(pedido);
        } else {
            colaCocinaNormal.encolar(pedido);
        }
        apilarCambio(pedido);
    }

    /**
     * Procesa el siguiente pedido: desencola (VIP primero) -> EN_PREPARACION ->
     * asigna repartidor libre -> Dijkstra restaurante->cliente -> EN_CAMINO ->
     * ENTREGADO. Cada cambio de estado se apila en el historial.
     */
    public ResultadoCiclo procesarSiguientePedido() {
        Pedido pedido;
        if (!colaCocinaVip.estaVacia()) {
            pedido = colaCocinaVip.extraerMaximo();
        } else if (!colaCocinaNormal.estaVacia()) {
            pedido = colaCocinaNormal.desencolar();
        } else {
            return ResultadoCiclo.fallo("No hay pedidos en cocina.");
        }

        pedido.setEstado(EstadoPedido.EN_PREPARACION);
        apilarCambio(pedido);

        Repartidor repartidor = buscarRepartidorLibre();
        if (repartidor == null) {
            // Sin repartidores: se devuelve el pedido a su cola correspondiente.
            pedido.setEstado(EstadoPedido.PENDIENTE);
            if (pedido.isEsVip()) {
                colaCocinaVip.insertar(pedido);
            } else {
                colaCocinaNormal.encolar(pedido);
            }
            return ResultadoCiclo.fallo("No hay repartidores libres para el pedido #" + pedido.getId());
        }

        repartidor.setLibre(false);
        pedido.setRepartidor(repartidor);

        // Ruta mas corta desde el restaurante hasta la zona del cliente.
        int origen = grafoMapa.getIndiceZona(getZonaCocina());
        int destino = grafoMapa.getIndiceZona(pedido.getZonaDestino());
        ResultadoDijkstra ruta = dijkstra.calcular(grafoMapa, origen, destino);
        pedido.setMinutosEntrega(ruta.existeRuta() ? ruta.getMinutosTotales() : 0);

        pedido.setEstado(EstadoPedido.EN_CAMINO);
        apilarCambio(pedido);

        pedido.setEstado(EstadoPedido.ENTREGADO);
        apilarCambio(pedido);

        // El repartidor entrega y queda libre nuevamente en la zona del cliente.
        repartidor.setZonaActual(pedido.getZonaDestino());
        repartidor.setLibre(true);

        return new ResultadoCiclo(true, "Pedido #" + pedido.getId() + 
        " entregado.", pedido, repartidor, ruta);
    }

    private Repartidor buscarRepartidorLibre() {
        Nodo<Repartidor> actual = listaRepartidores.obtenerCabeza();
        while (actual != null) {
            Repartidor repartidor = actual.obtenerDato();
            if (repartidor.isLibre()) {
                return repartidor;
            }
            actual = actual.obtenerSiguiente();
        }
        return null;
    }

    private void apilarCambio(Pedido pedido) {
        pilaHistorial.apilar(new CambioEstado(pedido.getId(), pedido.getEstado(), pedido.getHoraIngreso()));
    }

    //  REPARTIDORES / RESTAURANTES
    public void agregarRepartidor(Repartidor repartidor) {
        listaRepartidores.insertarAlFinal(repartidor);
    }

    public void agregarRestaurante(Restaurante restaurante) {
        listaRestaurantes.insertarAlFinal(restaurante);
        // Se guarda la calificacion *10 para mantener el arbol con enteros.
        rankingRestaurantes.insertar(restaurante.getNombre(), (int) Math.round(restaurante.getCalificacion() * 10));
    }

    public String[] obtenerTop5Restaurantes() {
        return rankingRestaurantes.obtenerTop5();
    }

    //  CONSULTAS
    public Pedido buscarPedidoPorId(int idPedido) {
        return tablaPedidos.get(String.valueOf(idPedido));
    }

    public int obtenerPedidosPendientes() {
        return colaCocinaNormal.obtenerNumeroElementos() + colaCocinaVip.obtenerNumeroElementos();
    }

    /** Recorre los buckets de la TablaHash para devolver todos los pedidos. */
    public Pedido[] obtenerTodosPedidos() {
        Pedido[] pedidos = new Pedido[tablaPedidos.size()];
        EntradaHash<String, Pedido>[] buckets = tablaPedidos.getBuckets();
        int indice = 0;
        for (EntradaHash<String, Pedido> bucket : buckets) {
            EntradaHash<String, Pedido> actual = bucket;
            while (actual != null) {
                pedidos[indice++] = actual.getValor();
                actual = actual.getSiguiente();
            }
        }
        return pedidos;
    }

    //Calcula una ruta aleatoria 
    public ResultadoDijkstra calcularRuta(String zonaOrigen, String zonaDestino) {
        int origen = grafoMapa.getIndiceZona(zonaOrigen);
        int destino = grafoMapa.getIndiceZona(zonaDestino);
        return dijkstra.calcular(grafoMapa, origen, destino);
    }

    public String getZonaCocina() {
        return "Centro";
    }

    //Accesos para la interfaz (solo lectura) 

    public Object[] obtenerColaNormal()   { return colaCocinaNormal.aArreglo(); }
    public Object[] obtenerColaVip()       { return colaCocinaVip.aArreglo(); }
    public Object[] obtenerRepartidores()  { return listaRepartidores.aArreglo(); }
    public Object[] obtenerRestaurantes()  { return listaRestaurantes.aArreglo(); }
    public Object[] obtenerHistorial()     { return pilaHistorial.aArreglo(); }

    public int obtenerTotalPedidos()       { return tablaPedidos.size(); }
    public Grafo getGrafo()                { return grafoMapa; }
}
