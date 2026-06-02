package utils;

import model.Pedido;
import model.Repartidor;
import model.Restaurante;

/**
 * Genera datos de prueba (pedidos, repartidores y restaurantes) coherentes
 * con el modelo real del proyecto. 
 */
public class GeneradorDatos {

    private int contadorPedidos = 1;

    private static final String[] ZONAS_CLIENTE = {
        "Norte", "Barrio Bolivar", "Chapinero", "La 20",
        "El Obando", "Bella Vista", "El Cadillal", "Las Americas", "La Ladera"
    };

    private static final String[] PLATOS = {
        "Hamburguesa doble", "Pizza familiar", "Sushi mixto", "Ensalada cesar",
        "Pollo asado", "Tacos al pastor", "Pasta carbonara", "Arroz chino",
        "Bandeja paisa", "Sopa de mariscos"
    };

    private static final String[] HORAS = {
        "08:10", "09:45", "10:30", "11:05", "12:20",
        "13:15", "14:40", "15:25", "16:50", "18:00"
    };

    //Genera la cantidad  de pedidos con prioridad, zona y hora variadas.
    public Pedido[] generarPedidos(int cantidad) {
        Pedido[] pedidos = new Pedido[cantidad];
        for (int i = 0; i < cantidad; i++) {
            int id = contadorPedidos++;
            String descripcion = PLATOS[i % PLATOS.length];
            int prioridad = (i % 5) + 1;                 // 1..5
            String zona = ZONAS_CLIENTE[i % ZONAS_CLIENTE.length];
            String hora = HORAS[i % HORAS.length];
            boolean esVip = (i % 3 == 0);                // 1 de cada 3 es VIP

            pedidos[i] = new Pedido(id, descripcion, prioridad, zona, hora, esVip);
        }
        return pedidos;
    }

    //Genera la cantidad de repartidores ubicados en distintas zonas. 
    public Repartidor[] generarRepartidores(int cantidad) {
        String[] nombres = {
            "Juan Perez", "Maria Garcia", "Carlos Lopez", "Ana Rodriguez",
            "Pedro Martinez", "Lucia Hernandez", "Luis Torres", "Sofia Ramirez"
        };
        Repartidor[] repartidores = new Repartidor[cantidad];
        for (int i = 0; i < cantidad; i++) {
            String id = "R" + (i + 1);
            String nombre = nombres[i % nombres.length];
            String zona = ZONAS_CLIENTE[i % ZONAS_CLIENTE.length];
            repartidores[i] = new Repartidor(id, nombre, zona);
        }
        return repartidores;
    }

    //Genera restaurantes con calificaciones variadas para el ranking. 
    public Restaurante[] generarRestaurantes() {
        return new Restaurante[] {
            new Restaurante("RES1", "La Brasa Dorada", 4.7),
            new Restaurante("RES2", "Sushi Zen", 4.9),
            new Restaurante("RES3", "Pizza Napoli", 4.2),
            new Restaurante("RES4", "El Buen Taco", 3.8),
            new Restaurante("RES5", "Pasta Bella", 4.5),
            new Restaurante("RES6", "Mar y Tierra", 4.1),
            new Restaurante("RES7", "Burger House", 4.6)
        };
    }
}
 