package servicio;

import java.io.FileWriter;
import java.io.IOException;
import model.Pedido;
import model.EstadoPedido;
import utils.SelectionSort;

/**
 * Genera el reporte .txt de los pedidos del dia, ordenados por hora con
 * Selection Sort (ordenamiento manual).
 */
public class ReporteService {

    private final SelectionSort selectionSort;

    public ReporteService() {
        this.selectionSort = new SelectionSort();
    }

    //Genera el archivo de reporte en la ruta indicada.
    public boolean generarReporteDia(Pedido[] pedidos, int nPedidos, String rutaArchivo) {
        if (pedidos == null || nPedidos == 0) {
            return false;
        }

        selectionSort.ordenarPorHora(pedidos, nPedidos);

        try (FileWriter escritor = new FileWriter(rutaArchivo)) {
            escritor.write("---------------------------------------\n");
            escritor.write("       REPORTE DE PEDIDOS DEL DIA       \n");
            escritor.write("---------------------------------------\n");
            escritor.write("Total de pedidos: " + nPedidos + "\n");
            escritor.write("Ordenados por: HORA (ascendente)\n");
            escritor.write("----------------------------------------\n");

            for (int i = 0; i < nPedidos; i++) {
                Pedido pedido = pedidos[i];
                escritor.write("Pedido #" + (i + 1) + "\n");
                escritor.write("  ID: " + pedido.getId() + "\n");
                escritor.write("  Descripcion: " + pedido.getDescripcion() + "\n");
                escritor.write("  Hora: " + pedido.getHoraIngreso() + "\n");
                escritor.write("  Prioridad: " + pedido.getPrioridad()
                        + (pedido.isEsVip() ? " (VIP)" : " (Regular)") + "\n");
                escritor.write("  Zona destino: " + pedido.getZonaDestino() + "\n");
                escritor.write("  Estado: " + pedido.getEstado() + "\n");
                if (pedido.getRepartidor() != null) {
                    escritor.write("  Repartidor: " + pedido.getRepartidor().getNombre() + "\n");
                    escritor.write("  Tiempo de entrega: " + pedido.getMinutosEntrega() + " min\n");
                } else {
                    escritor.write("  Repartidor: No asignado\n");
                }
                escritor.write("----------------------------------------\n");
            }

            int vip = contar(pedidos, nPedidos, true, null);
            int entregados = contar(pedidos, nPedidos, false, EstadoPedido.ENTREGADO);

            escritor.write("\n--------------------------------------\n");
            escritor.write("           RESUMEN ESTADISTICO          \n");
            escritor.write("--------------------------------------\n");
            escritor.write("Pedidos VIP: " + vip + "\n");
            escritor.write("Pedidos Regulares: " + (nPedidos - vip) + "\n");
            escritor.write("Pedidos Entregados: " + entregados + "\n");
            escritor.write("Pedidos Pendientes: " + (nPedidos - entregados) + "\n");
            escritor.write("----------------------------------------\n");

            return true;
        } catch (IOException e) {
            System.err.println("Error al generar el reporte: " + e.getMessage());
            return false;
        }
    }

    private int contar(Pedido[] pedidos, int n, boolean porVip, EstadoPedido estado) {
        int total = 0;
        for (int i = 0; i < n; i++) {
            if (porVip && pedidos[i].isEsVip()) {
                total++;
            } else if (!porVip && pedidos[i].getEstado() == estado) {
                total++;
            }
        }
        return total;
    }
}
