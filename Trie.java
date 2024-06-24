package aed;

public class Trie<T> {

    private Nodo raiz; // dentro implementamos la estructura del Trie
    private int cantidadSignificados; // cuenta cuántos significados hay dentro del nodo

    // clase privada Nodo, se implementó con atributos que permiten formar la estructura de un Trie
    private class Nodo { 
    // la clase es privada pero con métodos públicos para usarlos luego en SistemaSIU

        private T significado; // el significado almacenado en el nodo (si hay uno)
        private Nodo[] hijos; // arreglo de nodos hijos
        private Nodo ancestro; // el nodo ancestro del nodo (si es la raiz es nulo)

        // constructor del nodo vacío
        private Nodo() {
            hijos = new Trie.Nodo[256]; // arma un arreglo de Nodos de 256 espacios, uno por cada caracter ASCII
            significado = null;
            ancestro = null;
        }

        // método para obtener el array de hijos del nodo
        public Nodo[] hijos(){
            return hijos;
        }

        // método para obtener el significado del nodo
        public T significado(){
            return significado;
        }

        // método para obtener el ancestro del nodo
        public Nodo ancestro(){
            return ancestro;
        }
    }

    // constructor del trie
    public Trie() {
        raiz = new Nodo(); // inicializa la raíz del trie
        cantidadSignificados = 0;
    }

    // método para obtener la raíz
    public Nodo raiz(){
        return raiz;
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

        if (!pertenece(palabra)) { // si la palabra no pertenecía antes
            cantidadSignificados ++; // symo 1 a la cantidad de significados
        }

        while ( indice < palabra.length() ) { // recorre cada carácter de la palabra

            char c = palabra.charAt(indice); // obtiene el carácter actual
            int valorASCII = c; // convierte el carácter a su valor ASCII
            
            if (actual.hijos[valorASCII] == null) { // si no hay nodo hijo para el carácter (implica que antes no estaba definido)
                ; // suma 1 a la cantidad de significados

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

        while (indice < palabra.length()){ // recorre cada carácter de la palabra

            char c = palabra.charAt(indice); // obtiene el carácter actual
            int valorASCII = c; // convierte el carácter a su valor ASCII

            if (actual.hijos[valorASCII] != null) { // si el siguiente no es nulo
                actual = actual.hijos[valorASCII]; // avanza al siguiente nodo
            }
            indice ++;
        }

        return actual;
    }

    // método para eliminar una palabra del trie

    public void eliminar(String palabra) {

        Nodo actual = nodoFinal(palabra); // busca el nodo al que se le quiere borrar el significado
        int indice = 0;
        int borrados = 0;

        if ( !(tieneHijos(actual)) ) { // si el nodo no tiene hijos

            while ( (indice < palabra.length()) && (borrados < 1) ) { // sube por el Trie buscando su primer ancestro con más de 1 hijo
                actual = actual.ancestro; // sube 1 por el Trie
                indice ++;

                if (cantidadHijos(actual) > 1) { // cuando lo encuentre

                    char c = palabra.charAt(palabra.length() - indice); // obtiene el caracter del nodo actual
                    int valorASCII = c; // convierte el carácter a su valor ASCII

                    actual.hijos[valorASCII] = null; // corta la rama de donde venía
                    cantidadSignificados --;
                    borrados ++; // cuenta cuántos se borraron, evita que se borren varios significados
                }
            } // notar que si sale del while entonces ya está en la raíz
            if (borrados < 1) { // si llegó a la raíz y aún no borró nada 

                char c = palabra.charAt(palabra.length() - indice); // obtiene el carácter actual
                int valorASCII = c; // convierte el carácter a su valor ASCII

                actual.hijos[valorASCII] = null; // corta la rama a borrar
                cantidadSignificados --;
                borrados ++;
                
            }
        }
        else { // si el nodo a borrar tiene hijos
            if (borrados < 1) { // podría haberse borrado el significado del nodo donde había terminado la variable actual, con la guarda se evitsa esto
                actual.significado = null;
                
                cantidadSignificados --;
                borrados ++;
            }
        }
    }

    // método privado para contar la cantidad de hijos. Al ser comparaciones y recorridos d elongitudes acotadas, la complejidad queda en O(1)

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

    // booleano privado que indica si un nodo tiene hijos, funge de reemplazo sintáctico

    private boolean tieneHijos(Nodo Nodo){
        return cantidadHijos(Nodo) > 0;
    }

    // método que devuelve un array de las claves (son siempre strings) ordenadas lexicográficamente

    public String[] recorrer(){

        String[] res = new String[cantidadSignificados]; // inicializa la respuesta y pide tantos espacios como significados haya. luego se irá completando
        int posicionDeGuardado = 0; // un int que indicará en qué posición del array respuesta se debe guardar el significado

        Nodo actual = raiz; // arranca desde la raiz
        int indice = 0; // indica en qué hijo está
        String anotador = ""; // donde se iran anotando las letras

        while (indice < 257) { // recorre los 256 hijos en orden, + 1 caso si no encuentra hijos

            if (indice < 256) { // si el índice está en rango del arreglo de hijos

                if (actual.hijos[indice] != null) { // cuando encuentra un hijo no vacio

                    actual = actual.hijos[indice]; // baja en el Trie
                    char c = (char) indice; // toma el caracter correspondiente al nodo actual
                    indice = -1; // resetea el indice para recorrer los hijos del nuevo nodo desde -1
                    // (al final del while le sumo 1 y empieza desde 0 en el próximo)
    
                    StringBuffer sb = new StringBuffer(anotador); // inicia un stringBuffer que permite modificar el anotador
                    sb.append(c); // anota una letra
                    anotador = sb.toString(); // actualiza el anotador
    
                    if (actual.significado != null) { // si cuando baja encuentra un significado
                        res[posicionDeGuardado] = anotador; // añade lo anotado a res
                        posicionDeGuardado ++; // avanza a la siguiente posición de guardado
                    }
                }
            }
            if (indice == 256) { // si no encuentra hijo

                if (actual.ancestro != null) { // si no es la raiz

                    actual = actual.ancestro; // sube en el Trie
                    char c = anotador.charAt(anotador.length() - 1); // obtiene la última letra
                    indice = c; // iguala el indice al valor ASCII de c, asi sigue recorriendo desde donde se había quedado en el nodo anterior

                    StringBuffer sb = new StringBuffer(anotador); // inicia un stringBuffer que permite modificar un String
                    sb.deleteCharAt(sb.length() - 1); // le borra una letra
                    anotador = sb.toString(); // actualiza el anotador
                }
            }
            
            indice ++;
        }

        return res;
    }

    // clase privada iterador del Trie, con métodos públicos

    private class TrieIterador implements Iterador<T> {
        private String anotador; // donde se iran anotando las letras y formando los nombres

        Nodo puntero = raiz; // arranca desde la raiz

        int indiceHijosPuntero = 0; // indica en qué hijo está en el nodo actual (o si no había hijos en caso de valer 256)
        int cuentaPasos = 0; // indica cuántos pasos se dió en cada iteración, es para evitar dar más de 1 paso a la vez

        boolean terminoDeIterar = (indiceHijosPuntero == 257); // booleano que indica si terminó de recorrer el Trie
        boolean esVacio = !(tieneHijos(raiz)) && (puntero.ancestro == null);

    public boolean haySiguiente() {
        return !(terminoDeIterar) || !(esVacio); // hay siguiente si no es vacio o si terminó de iterar
    }

    public T siguiente() {
        T significado = puntero.significado; // notar que en la primer iteración siempre devuelve null

        while ( (indiceHijosPuntero < 257) && (cuentaPasos < 1) ) { // recorre los 256 hijos en orden,
                                                                    // + 1 caso si no encuentra hijos (frena si ya dio un paso)

            if (indiceHijosPuntero < 256) { // si el índice está en rango del arreglo de hijos

                if (puntero.hijos[indiceHijosPuntero] != null) { // cuando encuentra un hijo no vacio

                    if (anotador == null) {
                        String nuevoAnotador = "";
                        anotador = nuevoAnotador;
                    }
                    char c = (char) indiceHijosPuntero; // toma el caracter correspondiente al nodo actual
                    StringBuffer sb = new StringBuffer(anotador); // inicia un stringBuffer que permite modificar el anotador
                    sb.append(c); // anota una letra
                    anotador = sb.toString(); // actualiza el anotador

                    puntero = puntero.hijos[indiceHijosPuntero]; // baja en el Trie
                    indiceHijosPuntero = -1; // resetea el indice para recorrer los hijos del nuevo nodo desde -1
                    // (al final del while le sumo 1 y empieza desde 0 en el próximo)
    
                    if (puntero.significado != null) { // si cuando baja encuentra un significado
                        cuentaPasos ++; // suma 1 al cuentaPasos, deteniendo la iteración
                    }
                }
            }
            if (indiceHijosPuntero == 256) { // si no encuentra hijo

                if (puntero.ancestro != null) { // si no es la raiz
    
                    puntero = puntero.ancestro; // sube en el Trie

                    char c = anotador.charAt(anotador.length() - 1); // obtiene la última letra
                    indiceHijosPuntero = c; // iguala el indice al valor ASCII de c, asi sigue recorriendo desde donde se había quedado en el nodo anterior
    
                    StringBuffer sb = new StringBuffer(anotador); // inicia un stringBuffer que permite modificar un String
                    sb.deleteCharAt(sb.length() - 1); // le borra una letra
                    anotador = sb.toString(); // actualiza el anotador
                }
            }
            
        indiceHijosPuntero ++; // llega a valer 257 solo si no encuentra más hijos y está en la raiz, es decir, si termina de iterar
        }
        cuentaPasos = 0; // resetea el cuenta pasos para que inicie desde 0 en la próxima iteración
        return significado;
    }

    public String nombreActual(){
        return anotador;
    }

    public boolean hayAnterior(){
        throw new UnsupportedOperationException("Método no implementado aún");
    }
    public T anterior(){
        throw new UnsupportedOperationException("Método no implementado aún");	
    }
    // dejamos la sfunciones "hayAnterior" y "Anterior" sin implementar porque no la sibamos a usar en ninguna otra clase,
    // solo necesitabamos el método "siguiente"
    }

    public Iterador<T> iterador() {
        return new TrieIterador();
    }
}

//falta el invariante

