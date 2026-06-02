package utils;

import model.Pedido;
import model.Repartidor;


//validacion de datos de entrada.
public class ConsoleHelper {

    public static boolean validarId(int id) {
        return id >= 0;
    }

    public static boolean validarTexto(String texto) {
        return texto != null && !texto.trim().isEmpty();
    }

    //Valida que la prioridad este en el rango permitido 
    public static boolean validarPrioridad(int prioridad) {
        return prioridad >= 1 && prioridad <= 5;
    }

    public static boolean validarPedido(Pedido pedido) {
        if (pedido == null) {
            return false;
        }
        return validarId(pedido.getId())
                && validarTexto(pedido.getDescripcion())
                && validarPrioridad(pedido.getPrioridad())
                && validarTexto(pedido.getZonaDestino());
    }

    public static boolean validarRepartidor(Repartidor repartidor) {
        if (repartidor == null) {
            return false;
        }
        return validarTexto(repartidor.getId()) && validarTexto(repartidor.getNombre());
    }
}
