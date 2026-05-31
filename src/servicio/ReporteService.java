package servicio;

import java.io.FileWriter;
import java.io.IOException;
import model.Pedido;
import utils.SelectionSort;

public class ReporteService {
    
    private DeliveryService deliveryService;
    private SelectionSort selectionSort;
    
    public ReporteService(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
        this.selectionSort = new SelectionSort();
    }
    
    /**
     * Genera reporte pedidos_dia.txt con pedidos ordenados por hora (usando SelectionSort).
     */
    public boolean generarReporteDia(Pedido[] pedidos, int nPedidos) {
        if (nPedidos == 0) {
            return false;
        }
        
        // ORDENAR: Usar SelectionSort para ordenar por hora (Fase 3)
        selectionSort.ordenarPorHora(pedidos, nPedidos);
        
        FileWriter escritor = null;
        try {
            escritor = new FileWriter("pedidos_dia.txt");
            
            escritor.write("========================================\n");
            escritor.write("       REPORTE DE PEDIDOS DEL DIA       \n");
            escritor.write("========================================\n");
            escritor.write("Total de pedidos: " + nPedidos + "\n");
            escritor.write("Ordenados por: HORA (ascendente)\n");
            escritor.write("----------------------------------------\n");
            escritor.write("\n");
            
            // Escribir cada pedido ordenado
            for (int i = 0; i < nPedidos; i++) {
                Pedido pedido = pedidos[i];
                
                escritor.write("Pedido #" + (i + 1) + "\n");
                escritor.write("  ID: " + pedido.getIdPedido() + "\n");
                escritor.write("  Hora: " + pedido.getHora() + "\n");
                escritor.write("  Prioridad: " + (pedido.isVip() ? "VIP" : "Regular") + "\n");
                escritor.write("  Estado: " + pedido.getEstado() + "\n");
                
                if (pedido.getRepartidor() != null) {
                    escritor.write("  Repartidor: " + pedido.getRepartidor().getNombre() + "\n");
                } else {
                    escritor.write("  Repartidor: No asignado\n");
                }
                
                escritor.write("----------------------------------------\n");
            }
            
            // Resumen estadístico
            escritor.write("\n");
            escritor.write("========================================\n");
            escritor.write("           RESUMEN ESTADISTICO          \n");
            escritor.write("========================================\n");
            
            int pedidosVip = contarPedidosVip(pedidos, nPedidos);
            int pedidosRegulares = nPedidos - pedidosVip;
            int pedidosEntregados = contarPedidosEntregados(pedidos, nPedidos);
            
            escritor.write("Pedidos VIP: " + pedidosVip + "\n");
            escritor.write("Pedidos Regulares: " + pedidosRegulares + "\n");
            escritor.write("Pedidos Entregados: " + pedidosEntregados + "\n");
            escritor.write("Pedidos Pendientes: " + (nPedidos - pedidosEntregados) + "\n");
            escritor.write("========================================\n");
            
            escritor.close();
            return true;
            
        } catch (IOException e) {
            e.printStackTrace();
            if (escritor != null) {
                try {
                    escritor.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            return false;
        }
    }
    
    private int contarPedidosVip(Pedido[] pedidos, int n) {
        int contador = 0;
        for (int i = 0; i < n; i++) {
            if (pedidos[i].isVip()) {
                contador++;
            }
        }
        return contador;
    }
    
    private int contarPedidosEntregados(Pedido[] pedidos, int n) {
        int contador = 0;
        for (int i = 0; i < n; i++) {
            if ("ENTREGADO".equals(pedidos[i].getEstado())) {
                contador++;
            }
        }
        return contador;
    }
}