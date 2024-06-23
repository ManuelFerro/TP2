package aed;

public class Trie<T> {

    private Nodo raiz; // el único atributo es el nodo, pero dentro implementamos la estructura del Trie
    private int cantidadSignificados;

    // clase interna Nodo, lo implementamos con atributos que permiten desarrollar la estructura de un Trie
    private class Nodo {

        private T significado; // el significado almacenado en el nodo (si hay uno)
        private Nodo[] hijos; // arreglo de nodos hijos
        private Nodo ancestro;

        // constructor del nodo vacío
        private Nodo() {
            hijos = new Trie.Nodo[256]; // arma un arreglo de Nodos de 256 espacios, uno por cada caracter ASCII
            significado = null;
            ancestro = null;
        }
    }

    // constructor del trie
    public Trie() {
        raiz = new Nodo(); // inicializa la raíz del trie
        cantidadSignificados = 0;
    }

    // método para obtener la cantidad de significados
    public int cantidadSignificados(){
        return cantidadSignificados;
    }

    // método para definir una palabra.
    // nota: si se pasa una palabra que ya estaba guardada, se la redefine

    public void definir(String palabra, T valor) {

        Nodo actual = raiz;
        int indice = 0;

        while ( indice < palabra.length() ) { // recorre cada carácter de la palabra

            char c = palabra.charAt(indice); // obtiene el carácter actual
            int valorASCII = c; // convierte el carácter a su valor ASCII
            
            if (actual.hijos[valorASCII] == null) { // si no hay nodo hijo para el carácter (implica que antes no estaba definido)
                cantidadSignificados ++; // suma 1 a la cantidad de significados

                Nodo nodoNuevo = new Nodo(); // crea un nuevo nodo
                nodoNuevo.ancestro = actual;
                actual.hijos[valorASCII] = nodoNuevo;
            }
            actual = actual.hijos[valorASCII]; // avanza al siguiente nodo
            indice++;
        }
        actual.significado = valor; // define el significado como el valor (lo redefine si la clave ya pertenecía)
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

        Nodo actual = nodoFinal(palabra); // busca el nodo al que se le quiere borrar el significado
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
                    borrados ++; // cuenta cuántos se borraron, evita que se borren varios significados
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
        cantidadSignificados --;
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

    public String[] recorrer(){

        String[] res = new String[cantidadSignificados]; // inicializa la respuesta y pide tantos espacios como significados haya. luego se irá completando
        int posicionDeGuardado = 0; // un int que indicará en qué posición del array respuesta se debe guardar el significado

        Nodo actual = raiz; // arranca desde la raiz
        int indice = 0; // indica en qué hijo está
        String anotador = ""; // donde se iran anotando las letras

        while (indice < 257) { // recorre los 256 hijos en orden, + 1 caso si no encuentra hijos

            if (actual.hijos[indice] != null) { // si encuentra un hijo no vacio

                actual = actual.hijos[indice]; // baja en el Trie
                char c = (char) indice; // toma el caracter correspondiente al nodo actual
                indice = -1; // resetea el indice para recorrer los hijos del nuevo nodo desde -1 (al final del while le sumo 1 y empieza desde 0 en el próximo)

                StringBuffer sb = new StringBuffer(anotador); // inicia un stringBuffer que permite modificar el anotador
                sb.append(c); // anota una letra
                anotador = sb.toString(); // iguala el anotaror al String modificado

                if (actual.significado != null) { // si cuando baja encuentra un significado
                    res[posicionDeGuardado] = anotador; // añade lo anotado a res
                    posicionDeGuardado ++; // avanza a la siguiente psoción de guardado
                }
            }
            if (indice == 256) { // no encuentra hijo no nulo

                if (actual.ancestro != null) { // si no es la raiz

                    actual = actual.ancestro; // sube en el Trie
                    char c = anotador.charAt(anotador.length() - 2); // obtiene la anteúltima letra
                    indice = c; // iguala el indice al valor ASCII de c, asi sigue recorriendo desde donde se había quedado en el nodo anterior

                    StringBuffer sb = new StringBuffer(anotador); // inicia un stringBuffer que permite modificar un String
                    sb.deleteCharAt(sb.length() - 1); // le borra una letra
                    anotador = sb.toString(); // iguala el anotaror al String modificado
                }
            }
            
            indice ++;
        }

        return res;
    }
}

//falta el invariante
