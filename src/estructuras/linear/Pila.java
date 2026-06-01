package estructuras.linear;


  //Estructura lineal manual que implementa el comportamiento LIFO
  //Permite apilar elementos y retirar siempre el último elemento agregado
  //@param <TipoDato> tipo de dato almacenado en la pila
 
public class Pila<TipoDato> {

    private Nodo<TipoDato> cima;
    private int numeroElementos;

    
     //Crea una pila vacía
     
    public Pila() {
        this.cima = null;
        this.numeroElementos = 0;
    }

    
     //Inserta un elemento en la cima de la pila
     //@param nuevoElemento dato que se apilará
     
    public void apilar(TipoDato nuevoElemento) {
        Nodo<TipoDato> nuevoNodo = new Nodo<>(nuevoElemento);
        nuevoNodo.asignarSiguiente(cima);
        cima = nuevoNodo;
        numeroElementos++;
    }

    
     //Retira y devuelve el elemento de la cima
    
    public TipoDato desapilar() {
        if (estaVacia()) {
            return null;
        }

        TipoDato datoDeLaCima = cima.obtenerDato();
        cima = cima.obtenerSiguiente();
        numeroElementos--;

        return datoDeLaCima;
    }

    
     //Indica si la pila no tiene elementos
    
    public boolean estaVacia() {
        return cima == null;
    }

    
     //Obtiene el elemento de la cima sin retirarlo
  
    public TipoDato consultarCima() {
        if (estaVacia()) {
            return null;
        }

        return cima.obtenerDato();
    }

    
     //Obtiene la cantidad de elementos almacenados
    
    public int obtenerNumeroElementos() {
        return numeroElementos;
    }
}