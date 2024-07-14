package aed;

// import java.util.*;

public class ListaEnlazada<T> implements Secuencia<T> {

    //INVARIANTE DE REPRESENTACIÓN:

    //La longitud de la lista enlazada es igual a la cantidad de nodos.
    //No pueden haber ciclos.
    //Si se avanza del primer nodo al siguiente y asi sucesivamente, se llega al último.
    //Si se aretrocede desde el último al anterior y asi sucesivamente, se llega al primero.
    //El siguiente del último es null al igual que el anterior del primero es null.

    private Nodo primero;
    private Nodo ultimo;
    private int longitud;

    private class Nodo {

        T valor;
        Nodo siguiente;
        Nodo anterior;

        Nodo(T v){
            valor = v;
            siguiente = null;
            anterior = null;
        }
    }

    public ListaEnlazada() {
        primero = null;
        ultimo = null;
        longitud = 0;
    }

    public int longitud() {
        return longitud;
    }

    public void agregarAdelante(T elem) {

        Nodo nodoNuevo = new Nodo(elem);

        if (primero == null){
            primero = nodoNuevo;
            ultimo = nodoNuevo;
        } else {
            nodoNuevo.siguiente = primero;
            nodoNuevo.siguiente.anterior = nodoNuevo;
            primero = nodoNuevo;
        }
        longitud ++;
    }

    public void agregarAtras(T elem) {

        Nodo nodoNuevo = new Nodo(elem);

        if (primero == null){
            primero = nodoNuevo;
            ultimo = nodoNuevo;
        } else {
            nodoNuevo.anterior = ultimo;
            nodoNuevo.anterior.siguiente = nodoNuevo;
            ultimo = nodoNuevo;
        }
        longitud ++;
    }

    public T obtener(int i) {

        int indice = 0;
        Nodo actual = primero;
        Nodo res = primero;

        while (indice < longitud) {

            if (indice == i) {
                res = actual;
            }
            actual = actual.siguiente;
            indice ++;   
        }
        return res.valor;
    }

    public T primero(){
        return primero.valor;
    }

    public void eliminar(int i) { // notar que es complejidad O(1) si se borra el primer elemento
        Nodo actual = primero;
        Nodo previo = primero;
        int j = 0;

        while (j < i) {

            previo = actual;
            actual = actual.siguiente;
            j ++;
        }
        if (i == 0) {
            primero = actual.siguiente;
        } else {
            previo.siguiente = actual.siguiente;
        }
        longitud --;
    }

    public void modificarPosicion(int indice, T elem) {
        int i = 0;
        Nodo actual = primero;

        while (i < longitud) {

            if (i == indice) {
                actual.valor = elem;
            }
            actual = actual.siguiente;
            i ++;   
        }
    }

    public ListaEnlazada<T> copiar() {
        ListaEnlazada<T> copia = new ListaEnlazada<>();
        Nodo actual = primero;

        while (actual != null) {
            copia.agregarAtras(actual.valor);
            actual = actual.siguiente;
        }
        return copia;
    }

    public ListaEnlazada(ListaEnlazada<T> lista) {
        Nodo actual = lista.primero;

        while (actual != null) {
            agregarAtras(actual.valor);
            actual = actual.siguiente; 
        }
    }
    
    @Override
    public String toString() {
        Nodo actual = primero;

        String res = "[";

        while (actual != null) {
            res += actual.valor;
            if (actual.siguiente != null) {
                res += ", ";
            }
            actual = actual.siguiente;
        }
        res += "]";
        return res;
    }

    private class ListaIterador implements Iterador<T> {
        Nodo puntero = primero; // el puntero apunta "entre medias" de 2 casillas
                                // no a una es específico.

        public boolean haySiguiente() {
            return puntero != null;
        }
        
        public boolean hayAnterior() {

            if ( haySiguiente() ){

                return puntero.anterior != null;

            } else {
                return ultimo != null;
            }
        }

        public T siguiente() {
            T val = puntero.valor;
            puntero = puntero.siguiente;
            return val;
        }
        

        public T anterior() {

            if ( haySiguiente() ){
                T valorAnterior = puntero.anterior.valor;
                puntero = puntero.anterior;
                return valorAnterior; 
            }else{
                puntero = ultimo;
                return puntero.valor;
            }

        }
        public String nombreActual(){
            throw new UnsupportedOperationException("Método no implementado aún"); // se agregó para machear la nueva estructura del iterador
        }
    }

    public Iterador<T> iterador() {
        return new ListaIterador();
    }
}
