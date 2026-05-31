package servicio;

import model.Pedido;
import model.Repartidor;
import utils.GeneradorDatos;
import utils.ConsoleHelper;

public class SimuladorDelivery {

    /**
     * Método principal para probar todo el sistema de delivery.
     * Crea pedidos de prueba, ejecuta simulación y genera reporte.
     */
    public static void main(String[] args) {
        System.out.println("=== INICIANDO SIMULADOR DE DELIVERY ===\n");
        
        // Crear instancias de servicios
        DeliveryService deliveryService = new DeliveryService();
        GeneradorDatos generador = new GeneradorDatos();
        
        // Agregar repartidores libres
        Repartidor[] repartidores = generador.generarRepartidores(5);
        for (int i = 0; i < repartidores.length; i++) {
            if (repartidores[i] != null) {
                deliveryService.agregarRepartidorLibre(repartidores[i]);
            }
        }
        
        // Crear y procesar 5 pedidos de prueba
        Pedido[] pedidosPrueba = generador.generarPedidos(5);
        
        System.out.println("Procesando pedidos...");
        for (int i = 0; i < pedidosPrueba.length; i++) {
            if (pedidosPrueba[i] != null) {
                // Validar pedido antes de procesar
                if (ConsoleHelper.validarPedido(pedidosPrueba[i])) {
                    deliveryService.procesarPedido(pedidosPrueba[i]);
                    System.out.println("Pedido #" + pedidosPrueba[i].getIdPedido() + 
                                       " procesado (" + (pedidosPrueba[i].isVip() ? "VIP" : "Normal") + ")");
                } else {
                    System.out.println("Pedido #" + pedidosPrueba[i].getIdPedido() + " INVÁLIDO, saltando...");
                }
            }
        }
        
        System.out.println("\nPedidos pendientes en cocina: " + deliveryService.obtenerPedidosPendientes());
        
        // Ejecutar ciclos de simulación hasta procesar todos los pedidos
        System.out.println("\nEjecutando simulación...");
        int ciclos = 0;
        while (deliveryService.cicloSimular()) {
            ciclos++;
            System.out.println("Ciclo " + ciclos + " completado");
        }
        
        System.out.println("Simulación finalizada en " + ciclos + " ciclos");
        
        // Generar reporte .txt
        System.out.println("\nGenerando reporte...");
        ReporteService reporteService = new ReporteService(deliveryService);
        boolean reporteExitoso = reporteService.generarReporteDia(pedidosPrueba, pedidosPrueba.length);
        
        if (reporteExitoso) {
            System.out.println("✓ Reporte generado exitosamente: pedidos_dia.txt");
        } else {
            System.out.println("✗ Error al generar reporte");
        }
        
        System.out.println("\n=== SIMULACIÓN FINALIZADA ===");
    }
}