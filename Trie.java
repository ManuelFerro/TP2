package aed;
public class Trie {

    private Nodo raiz; // el único atributo es el nodo, pero dentro implementamos la estructura del Trie

    // clase interna Nodo, lo implementamos con atributos que permiten desarrollar la estructura de un Trie
    private class Nodo {

        private char c; // carácter almacenado en el nodo
        private boolean esPalabra; // indica si es el final de una palabra
        private Nodo[] hijos; // arreglo de nodos hijos

        // constructor del nodo vacío
        private Nodo() {
            this.hijos = new Nodo[256]; // arma un arreglo de Nodos de 256 espacios, uno por cada caracter ASCII
        }

        // constructor partiendo de un caracter
        private Nodo(char c) {

            this.c = c; // guarda c como el caracter almacenado
            esPalabra = false; // inicializa esPalabra como falso
            hijos = new Nodo[256]; // arma un arreglo de Nodos de 256 espacios, uno por cada caracter ASCII
        }
    }

    // constructor del trie
    public Trie() {
        raiz = new Nodo(); // inicializa la raíz del trie
    }

    // método para insertar una palabra

    public void insertar(String palabra) {

        Nodo actual = raiz;
        int indice = 0;

        while ( indice < palabra.length() ) { // recorre cada carácter de la palabra

            char c = palabra.charAt(indice); // obtiene el carácter actual
            int valorASCII = c; // convierte el carácter a su valor ASCII
            
            if (actual.hijos[valorASCII] == null) { // si no hay nodo hijo para el carácter
                actual.hijos[valorASCII] = new Nodo(c); // crea un nuevo nodo
            }
            actual = actual.hijos[valorASCII]; // avanza al siguiente nodo
            indice++;
        }
        actual.esPalabra = true; // marca el final de la palabra
    }

    // método para buscar una palabra, usa un método auxiliar

    public boolean buscar(String palabra) {

        Nodo nodo = nodoFinal(palabra); // obtiene el nodo final de la palabra

        return nodo != null && nodo.esPalabra; // devuelve verdadero si el nodo es el final de una palabra
    }

    public Nodo nodoFinal(String palabra) {

        Nodo actual = raiz;
        int indice = 0;

        while ( indice < palabra.length() ) { // recorre cada carácter de la palabra

            char c = palabra.charAt(indice); // obtiene el carácter actual
            int valorASCII = c; // convierte el carácter a su valor ASCII

            if (actual.hijos[valorASCII] == null) { // si no hay nodo hijo para el carácter
                return null;
            }
            actual = actual.hijos[valorASCII]; // avanza al siguiente nodo
            indice ++;
        }
        return actual; // devuelve el nodo final
    }

    public boolean empiezaCon(String prefijo) {
        return nodoFinal(prefijo) != null; // devuelve verdadero si existe un nodo final para el prefijo
    }

    // método para eliminar una palabra del trie, usa un auxiliar recursivo

    public void eliminar(String palabra) {
        eliminarRecursivo(raiz, palabra, 0);
    }

    private void eliminarRecursivo(Nodo actual, String palabra, int indice) {

        if (indice == palabra.length()) {
            if (actual.esPalabra) {
                actual.esPalabra = false; // desmarcar el nodo como fin de palabra
            }
        }
        
        char c = palabra.charAt(indice);
        int valorASCII = c;
        Nodo nodo = actual.hijos[valorASCII];

        eliminarRecursivo(nodo, palabra, indice + 1);

        if (!nodo.esPalabra && noTieneHijos(nodo)) {// después de la llamada recursiva chequeamos que el nodo hijo se pueda eliminar
            actual.hijos[valorASCII] = null; // elimina la referencia al nodo hijo
        }
    }

    private boolean noTieneHijos(Nodo nodo) {
        int i = 0;

        while ( i < nodo.hijos.length ) {
            if (nodo.hijos[i] != null) {
                return false;
            }
            i ++;
        }
        return true;
    }
}

//falta el invariante
