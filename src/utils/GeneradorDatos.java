package utils;

import model.Pedido;
import model.Repartidor;

public class GeneradorDatos {

    private int contadorPedidos;
    private int contadorRepartidores;
    
    public GeneradorDatos() {
        this.contadorPedidos = 1;
        this.contadorRepartidores = 1;
    }
    
    /**
     * Genera un arreglo de pedidos de prueba con diferentes horas y prioridades.
     */
    public Pedido[] generarPedidos(int cantidad) {
        Pedido[] pedidos = new Pedido[cantidad];
        
        // Horas variadas para probar ordenamiento
        int[] horasPrueba = {10, 14, 9, 18, 12, 15, 8, 20, 11, 16};
        
        for (int i = 0; i < cantidad; i++) {
            int hora = horasPrueba[i % horasPrueba.length];
            boolean esVip = (i % 2 == 0); // Alternar VIP/Normal
            
            Pedido pedido = new Pedido();
            pedido.setIdPedido(contadorPedidos++);
            pedido.setHora(hora);
            pedido.setVip(esVip);
            pedido.setEstado("PENDIENTE");
            
            pedidos[i] = pedido;
        }
        
        return pedidos;
    }
    
    /**
     * Genera un arreglo de repartidores de prueba.
     */
    public Repartidor[] generarRepartidores(int cantidad) {
        Repartidor[] repartidores = new Repartidor[cantidad];
        
        String[] nombresPrueba = {
            "Juan Pérez", "María García", "Carlos López", 
            "Ana Rodríguez", "Pedro Martínez", "Lucía Hernández",
            "Luis Torres", "Sofía Ramírez", "Diego Flores", "Camila Castro"
        };
        
        for (int i = 0; i < cantidad; i++) {
            String nombre = nombresPrueba[i % nombresPrueba.length];
            
            Repartidor repartidor = new Repartidor();
            repartidor.setIdRepartidor(contadorRepartidores++);
            repartidor.setNombre(nombre);
            repartidor.setDisponible(true);
            
            repartidores[i] = repartidor;
        }
        
        return repartidores;
    }
    
    /**
     * Genera un pedido específico con parámetros personalizados.
     */
    public Pedido generarPedidoPersonalizado(int id, int hora, boolean esVip) {
        Pedido pedido = new Pedido();
        pedido.setIdPedido(id);
        pedido.setHora(hora);
        pedido.setVip(esVip);
        pedido.setEstado("PENDIENTE");
        
        return pedido;
    }
    
    /**
     * Genera un repartidor específico con parámetros personalizados.
     */
    public Repartidor generarRepartidorPersonalizado(int id, String nombre) {
        Repartidor repartidor = new Repartidor();
        repartidor.setIdRepartidor(id);
        repartidor.setNombre(nombre);
        repartidor.setDisponible(true);
        
        return repartidor;
    }
}