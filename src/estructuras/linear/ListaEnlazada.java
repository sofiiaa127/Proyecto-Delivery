package estructuras.linear;


//Representa una lista enlazada simple implementada de forma manual
//Permite almacenar y recorrer elementos sin usar librerías de Java
//@param <TipoDato> tipo de dato almacenado en la lista
 
public class ListaEnlazada<TipoDato> {

    private Nodo<TipoDato> cabeza;
    private int numeroElementos;

    
     //Crea una lista enlazada vacía.
    
    
    public ListaEnlazada() {
        this.cabeza = null;
        this.numeroElementos = 0;
    }

    
     //Inserta un elemento al final de la lista
     //@param nuevoElemento dato que se agregará a la lista

    public void insertarAlFinal(TipoDato nuevoElemento) {
        Nodo<TipoDato> nuevoNodo = new Nodo<>(nuevoElemento);

        if (estaVacia()) {
            cabeza = nuevoNodo;
        } else {
            Nodo<TipoDato> nodoActual = cabeza;

            while (nodoActual.obtenerSiguiente() != null) {
                nodoActual = nodoActual.obtenerSiguiente();
            }

            nodoActual.asignarSiguiente(nuevoNodo);
        }

        numeroElementos++;
    }

    
     //Indica si la lista no tiene elementos.
    
    public boolean estaVacia() {
        return cabeza == null;
    }

    
     //Obtiene el primer nodo de la lista.
    
    public Nodo<TipoDato> obtenerCabeza() {
        return cabeza;
    }

    
     //Obtiene la cantidad de elementos almacenados.
    
    public int obtenerNumeroElementos() {
        return numeroElementos;
    }
}