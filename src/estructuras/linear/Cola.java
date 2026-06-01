package estructuras.linear;


 //Estructura lineal manual que implementa el comportamiento FIFO.
 //Permite agregar elementos al final y retirar elementos del frente
 //@param <TipoDato> tipo de dato almacenado en la cola
 
public class Cola<TipoDato> {

    private Nodo<TipoDato> frente;
    private Nodo<TipoDato> finalCola;
    private int numeroElementos;

    
     //Crea una cola vacía
    
    public Cola() {
        this.frente = null;
        this.finalCola = null;
        this.numeroElementos = 0;
    }

    
     //Agrega un elemento al final de la cola
     //@param nuevoElemento dato que se va a encolar
    
    public void encolar(TipoDato nuevoElemento) {
        Nodo<TipoDato> nuevoNodo = new Nodo<>(nuevoElemento);

        if (estaVacia()) {
            frente = nuevoNodo;
            finalCola = nuevoNodo;
        } else {
            finalCola.asignarSiguiente(nuevoNodo);
            finalCola = nuevoNodo;
        }

        numeroElementos++;
    }

      //Retira y devuelve el elemento del frente de la cola
      //@return elemento del frente, o null si la cola está vacía
    
    public TipoDato desencolar() {
        if (estaVacia()) {
            return null;
        }

        TipoDato datoDelFrente = frente.obtenerDato();
        frente = frente.obtenerSiguiente();

        if (frente == null) {
            finalCola = null;
        }

        numeroElementos--;
        return datoDelFrente;
    }

    
     //Indica si la cola no contiene elementos
     
    public boolean estaVacia() {
        return frente == null;
    }

    
     //Obtiene el elemento del frente sin retirarlo
   
    public TipoDato consultarFrente() {
        if (estaVacia()) {
            return null;
        }

        return frente.obtenerDato();
    }

    
     //Obtiene la cantidad de elementos almacenados
    
    public int obtenerNumeroElementos() {
        return numeroElementos;
    }
}