package aed;
public class Trie<T> {

    private Nodo raiz; // el único atributo es el nodo, pero dentro implementamos la estructura del Trie

    // clase interna Nodo, lo implementamos con atributos que permiten desarrollar la estructura de un Trie
    private class Nodo {

        private T significado; // el significado almacenado en el nodo (si hay uno)
        private Nodo[] hijos; // arreglo de nodos hijos
        private Nodo ancestro;

        // constructor del nodo vacío
        private Nodo() {
            this.hijos = new Nodo[256]; // arma un arreglo de Nodos de 256 espacios, uno por cada caracter ASCII
            this.significado = null;
            this.ancestro = null;
        }
    }

    // constructor del trie
    public Trie() {
        raiz = new Nodo(); // inicializa la raíz del trie
    }

    // método para definir una palabra.
    // nota: si se pasa una palabra que ya estaba guardada, se la redefine

    public void definir(String palabra, T valor) {

        Nodo actual = raiz;
        int indice = 0;

        while ( indice < palabra.length() ) { // recorre cada carácter de la palabra

            char c = palabra.charAt(indice); // obtiene el carácter actual
            int valorASCII = c; // convierte el carácter a su valor ASCII
            
            if (actual.hijos[valorASCII] == null) { // si no hay nodo hijo para el carácter

                Nodo nodoNuevo = new Nodo(); // crea un nuevo nodo
                nodoNuevo.ancestro = actual;
                actual.hijos[valorASCII] = nodoNuevo;
            }
            actual = actual.hijos[valorASCII]; // avanza al siguiente nodo
            indice++;
        }
        actual.significado = valor; // define el significado como el valor
    }

    // método para saber si una palabra pertenece

    public boolean pertenece(String palabra) {

        Nodo actual = raiz;
        int indice = 0;

        while ( indice < palabra.length() ) { // recorre cada carácter de la palabra

            char c = palabra.charAt(indice); // obtiene el carácter actual
            int valorASCII = c; // convierte el carácter a su valor ASCII
            
            if (actual.hijos[valorASCII] == null) { // si no hay nodo hijo para el carácter
                return false; // no está definido el resto de la palabra
            }
            actual = actual.hijos[valorASCII]; // avanza al siguiente nodo
            indice++;
        }
        if (actual.significado == null) {
            return false;            
        }
        else {
            return true;
        }
    }
    
    public T obtener(String palabra) {
        return nodoFinal(palabra).significado; // devuelve el significado del nodo final
    }

    private Nodo nodoFinal(String palabra) {

        Nodo actual = raiz;
        int indice = 0;

        while ( indice < palabra.length() ) { // recorre cada carácter de la palabra

            char c = palabra.charAt(indice); // obtiene el carácter actual
            int valorASCII = c; // convierte el carácter a su valor ASCII

            actual = actual.hijos[valorASCII]; // avanza al siguiente nodo
            indice ++;
        }

        return actual;
    }

    // método para eliminar una palabra del trie

    public void eliminar(String palabra) {

        Nodo actual = nodoFinal(palabra); // busca el nodo al que le quieor borrar el significado
        int indice = 0;
        int borrados = 0;

        if (cantidadHijos(actual) == 0) { // si el nodo no tiene hijos

            while ( (indice < palabra.length()) && (borrados < 1) ) { // sube por el Trie buscando su primer ancestro con más de 1 hijo
                actual = actual.ancestro; // sube 1 por el Trie
                indice ++;

                if (cantidadHijos(actual) > 1) { // cuando lo encuentre

                    char c = palabra.charAt(palabra.length() - indice); // obtiene el caracter del nodo actual
                    int valorASCII = c; // convierte el carácter a su valor ASCII

                    actual.hijos[valorASCII] = null; // corta la rama de donde venía
                    borrados ++; // cuenta cuántos se borraros, evita que se borren varios significados
                }
            }
            if (borrados < 1) { // si sale del while entonces ya está en la raíz

                char c = palabra.charAt(palabra.length() - indice); // obtiene el carácter actual
                int valorASCII = c; // convierte el carácter a su valor ASCII

                actual.hijos[valorASCII] = null; // corta la rama a borrar
                borrados ++;
            }
        }
        else { // si el nodo a borrar tiene hijos
            if (borrados < 1) { // podría haber borrado el significado del nodo donde había terminado la variable actual, con la guarda evito esto
                actual.significado = null;
            }
        }
    }

    private int cantidadHijos(Nodo nodo) {
        int i = 0;
        int res = 0;

        while ( i < nodo.hijos.length ) {
            if (nodo.hijos[i] != null) {
                res ++;
            }
            i ++;
        }
        return res;
    }

    public ListaEnlazada<String> recorrer(){
        Nodo actual = raiz;
        int indice = 0;
        ListaEnlazada<String> res = null;

        return res;
    }
}

//falta el invariante
