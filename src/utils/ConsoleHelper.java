package utils;

public class ConsoleHelper {

    /**
     * Valida que la hora esté entre 0 y 23 (formato 24 horas).
     */
    public static boolean validarHora(int hora) {
        return hora >= 0 && hora <= 23;
    }
    
    /**
     * Valida que un ID no sea negativo.
     */
    public static boolean validarId(int id) {
        return id >= 0;
    }
    
    /**
     * Valida que un texto no sea null ni esté vacío.
     */
    public static boolean validarTexto(String texto) {
        if (texto == null) {
            return false;
        }
        return !texto.trim().isEmpty();
    }
    
    /**
     * Valida un pedido completo (hora, id, prioridad).
     */
    public static boolean validarPedido(model.Pedido pedido) {
        if (pedido == null) {
            return false;
        }
        
        if (!validarId(pedido.getIdPedido())) {
            return false;
        }
        
        if (!validarHora(pedido.getHora())) {
            return false;
        }
        
        return true;
    }
    
    /**
     * Valida un repartidor completo (id, nombre).
     */
    public static boolean validarRepartidor(model.Repartidor repartidor) {
        if (repartidor == null) {
            return false;
        }
        
        if (!validarId(repartidor.getIdRepartidor())) {
            return false;
        }
        
        if (!validarTexto(repartidor.getNombre())) {
            return false;
        }
        
        return true;
    }
}