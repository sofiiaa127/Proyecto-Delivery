# Proyecto Delivery 

Este proyecto implementa un sistema de gestión y optimización de rutas de entregas a doicilio (delivery), diseñado para conectar restaurantes, repartidores y clientes de manera eficiente utilizando algoritmos de grafos.

## Integrantes
Lenoree Sofia Muñoz Lopez
Luna Valentina Prado Lopez
Jenifer Natalia Samboni Lara 
Jose Arturo Avirama Cruz 
Danny Valeria Riascos Dorado 
Beyker Ceron 

## Estructura del proyecto 
ProyectoDelivery/
│
├── src/
│   │
│   ├── model/
│   │   ├── Pedido.java
│   │   ├── Repartidor.java
│   │   ├── EstadoPedido.java
│   │   ├── Zona.java
│   │   └── Restaurante.java
│   │
│   ├── estructuras/
│   │   ├── linear/
│   │   │   ├── Nodo.java
│   │   │   ├── ListaEnlazada.java
│   │   │   ├── Cola.java
│   │   │   ├── Pila.java
│   │   │   └── ColaPrioridad.java
│   │   │
│   │   ├── hash/
│   │   │   ├── EntradaHash.java
│   │   │   └── TablaHash.java
│   │   │
│   │   ├── arbol/
│   │   │   ├── NodoArbol.java
│   │   │   └── ArbolBinario.java
│   │   │
│   │   └── grafo/
│   │       ├── Vertice.java
│   │       ├── Arista.java
│   │       ├── Grafo.java
│   │       └── Dijkstra.java
│   │
│   ├── servicio/
│   │   ├── DeliveryService.java
│   │   ├── SimuladorDelivery.java
│   │   └── ReporteService.java
│   │
│   ├── utils/
│   │   ├── SelectionSort.java
│   │   ├── GeneradorDatos.java
│   │   └── ConsoleHelper.java
│   │
│   ├── ui/
│   │   ├── MenuConsola.java
│   │   └── PanelSwing.java
│   │
│   └── main/
|       ├──Main.java
│
├── reportes/
│   └── pedidos_dia.txt
│
├── README.md

## Conceptos vistos en clase Implementados 
Lista enlazada
Cola
Cola de prioridad
Pila
Tabla hash
Árbol binario
Grafo ponderado
Dijkstra

## Tecnologías utilizadas
Java
Visual Studio Code
Chat GPT 
Lovable 