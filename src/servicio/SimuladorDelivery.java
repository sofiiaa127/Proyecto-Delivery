package servicio;

import model.Pedido;
import model.Repartidor;
import model.Restaurante;
import utils.GeneradorDatos;
import utils.ConsoleHelper;

/**
 * Prueba por consola de toda la logica del simulador (sin interfaz grafica).
 * Util para verificar colas, prioridad, hash, grafo y reporte.
 */
public class SimuladorDelivery {

    public static void main(String[] args) {
        System.out.println("---- SIMULADOR DE DELIVERY (consola) ----\n");

        DeliveryService servicio = new DeliveryService();
        GeneradorDatos generador = new GeneradorDatos();

        for (Repartidor repartidor : generador.generarRepartidores(4)) {
            if (ConsoleHelper.validarRepartidor(repartidor)) {
                servicio.agregarRepartidor(repartidor);
            }
        }
        for (Restaurante restaurante : generador.generarRestaurantes()) {
            servicio.agregarRestaurante(restaurante);
        }

        Pedido[] pedidos = generador.generarPedidos(6);
        for (Pedido pedido : pedidos) {
            if (ConsoleHelper.validarPedido(pedido)) {
                servicio.registrarPedido(pedido);
                System.out.println("Registrado pedido #" + pedido.getId()
                        + (pedido.isEsVip() ? " [VIP]" : " [Normal]"));
            }
        }

        System.out.println("\nPedidos pendientes: " + servicio.obtenerPedidosPendientes());
        System.out.println("\nProcesando...");
        ResultadoCiclo resultado;
        while ((resultado = servicio.procesarSiguientePedido()).isExito()) {
            System.out.println("  " + resultado.getMensaje()
                    + "  (" + resultado.getRuta().getMinutosTotales() + " min)");
        }

        System.out.println("\nTop 5 restaurantes:");
        for (String linea : servicio.obtenerTop5Restaurantes()) {
            System.out.println("  " + linea);
        }

        ReporteService reporte = new ReporteService();
        boolean ok = reporte.generarReporteDia(servicio.obtenerTodosPedidos(),
                servicio.obtenerTotalPedidos(), "reportes/pedidos_dia.txt");
        System.out.println("\nReporte generado: " + ok);
        System.out.println("\n---- FIN ----");
    }
}
