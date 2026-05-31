package estructuras.linear;


//Guarda un dato y una referencia al siguiente nodo
//@param <TipoDato> tipo de dato almacenado en el nodo

public class Nodo<TipoDato> {

    private TipoDato dato;
    private Nodo<TipoDato> siguiente;

    
    //Crea un nodo con un dato inicial
    //@param dato valor que se almacenará en el nodo
     
    public Nodo(TipoDato dato) {
        this.dato = dato;
        this.siguiente = null;
    }

    public TipoDato obtenerDato() {
        return dato;
    }

    
    //Reemplaza el dato almacenado en el nodo
    //@param dato nuevo valor para el nodo
    
    public void asignarDato(TipoDato dato) {
        this.dato = dato;
    }


    public Nodo<TipoDato> obtenerSiguiente() {
        return siguiente;
    }


    public void asignarSiguiente(Nodo<TipoDato> siguiente) {
        this.siguiente = siguiente;
    }
}