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
        // inicializa la raíz del trie
        raiz = new Nodo(); 
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

        Nodo actual = raiz;             //O(1) asignación.
        int indice = 0;                //O(1) asignación.

        // si la palabra no pertenecía antes
        if (!pertenece(palabra)) {        //O(|p|) ya que la función pertenece recorre toda la palabra.
            // sumo 1 a la cantidad de significados
            cantidadSignificados ++;        //O(1) asignación.
        }
        // recorre cada carácter de la palabra
        while ( indice < palabra.length() ) {           //O(|p|), al recorrer toda la palabra en el Trie, su complejidad sera la longitud de tal palabra

            // obtiene el carácter actual
            char c = palabra.charAt(indice);       //O(1) asignación.
            // convierte el carácter a su valor ASCII 
            int valorASCII = c;                  //O(1) asignación.
            
            // si no hay nodo hijo para el carácter (implica que antes no estaba definido)
            if (actual.hijos[valorASCII] == null) {            //O(1) por ser una pregunta y una indexación.
                ; // suma 1 a la cantidad de significados

                // crea un nuevo nodo
                Nodo nodoNuevo = new Nodo();                //O(1)??
                nodoNuevo.ancestro = actual;               //O(1) asignación.
                actual.hijos[valorASCII] = nodoNuevo;     //O(1) por ser una pregunta y una indexación.
            }
            // avanza al siguiente nodo
            actual = actual.hijos[valorASCII];        //O(1) por ser una pregunta y una indexación.
            indice++;        //O(1) asignación.
        }

        // define el significado como el valor (lo redefine si la clave ya pertenecía)
        actual.significado = valor;         //O(1) asignación.
    }

    // método para saber si una palabra pertenece

    public boolean pertenece(String palabra) {

        Nodo actual = raiz;            //O(1) asignación.
        int indice = 0;               //O(1) asignación.

        // recorre cada carácter de la palabra
        while ( indice < palabra.length() ) {    //O(|p|), al recorrer toda la palabra en el Trie, su complejidad sera la longitud de tal palabra

            // obtiene el carácter actual
            char c = palabra.charAt(indice);      //O(1) asignación.
            // convierte el carácter a su valor ASCII
            int valorASCII = c;             //O(1) asignación.
            
            // si no hay nodo hijo para el carácter, no está definido el resto de la palabra
            if (actual.hijos[valorASCII] == null) {        //O(1) por ser una pregunta y una indexación.
                return false; 
            }
            // avanza al siguiente nodo
            actual = actual.hijos[valorASCII];         //O(1) asignación e indexación.
            indice++;                                 //O(1) asignación.
        }
        if (actual.significado == null) {           //O(1) por ser una pregunta
            return false;            
        }
        else {
            return true;
        }
    }
    
    public T obtener(String palabra) {
        // devuelve el significado del nodo final
        return nodoFinal(palabra).significado;     //O(|p|) ya que la función nodoFinal recorre toda la palabra.
    }

    private Nodo nodoFinal(String palabra) {

        Nodo actual = raiz;        //O(1) asignación.
        int indice = 0;           //O(1) asignación.

        // recorre cada carácter de la palabra
        while (indice < palabra.length()){          //O(|p|), al recorrer toda la palabra en el Trie, su complejidad sera la longitud de tal palabra

            // obtiene el carácter actual
            char c = palabra.charAt(indice);       //O(1) asignación.
            // convierte el carácter a su valor ASCII
            int valorASCII = c;                  //O(1) asignación.

            // si el siguiente no es nulo, avanza al siguiente nodo
            if (actual.hijos[valorASCII] != null) {      //O(1) por ser una pregunta y una indexación.
                actual = actual.hijos[valorASCII];      //O(1) por ser una asignación e indexación.
            }
            indice ++;                  //O(1) asignación.
        }
        return actual;
    }

    // método para eliminar una palabra del trie

    public void eliminar(String palabra) {

        // busca el nodo al que se le quiere borrar el significado
        Nodo actual = nodoFinal(palabra);       //O(|p|) ya que la función nodoFinal recorre toda la palabra.
        int indice = 0;            //O(1) asignación.
        int borrados = 0;         //O(1) asignación.

        // si el nodo no tiene hijos
        if ( !tieneHijos(actual) ) {         //O(1) ya que la función tieneHijos va recorriendo un array acotodo de 256 elementos

            // sube por el Trie buscando su primer ancestro con más de 1 hijo
            while ( (indice < palabra.length()) && (borrados < 1) ) {     //O(|p|), al recorrer toda la palabra en el Trie, su complejidad sera la longitud de tal palabra
                // sube 1 nodo por el Trie
                actual = actual.ancestro;        //O(1) asignación.
                indice ++;                      //O(1) asignación.

                // cuando encuentra el ancestro con más de 1 hijo
                if (cantidadHijos(actual) > 1) {      //O(1) ya que la función cantidadHijos va recorriendo un array acotodo de 256 elementos

                    // obtiene el caracter del nodo actual
                    char c = palabra.charAt(palabra.length() - indice);       //O(1) asignación.
                    // convierte el carácter a su valor ASCII
                    int valorASCII = c;                                    //O(1) asignación.

                    // corta la rama de donde venía
                    actual.hijos[valorASCII] = null;                   //O(1) por ser una asignación e indexación.
                    // resta a la cantidad de significados
                    cantidadSignificados --;                        //O(1) asignación.
                    // cuenta cuántos se borraron, evita que se borren varios significados
                    borrados ++;                                 //O(1) asignación.
                }
                // notar que si sale del while entonces ya está en la raíz
            }   

            // si llegó a la raíz y aún no borró nada 
            if (borrados < 1) { 

                // obtiene el carácter actual
                char c = palabra.charAt(palabra.length() - indice);      //O(1) asignación.
                // convierte el carácter a su valor ASCII
                int valorASCII = c;                                    //O(1) asignación.

                // corta la rama a borrar
                actual.hijos[valorASCII] = null;            //O(1) por ser una asignación e indexación.
                // resta a la cantidad de significados
                cantidadSignificados --;                 //O(1) asignación.
                // cuenta cuántos se borraron, evita que se borren varios significados
                borrados ++;                          //O(1) asignación.
            }

            
        } else {   // si el nodo a borrar tiene hijos

            // podría haberse borrado el significado del nodo donde había terminado la variable actual,
            // con la guarda se evita esto
            if (borrados < 1) { 
                actual.significado = null;     //O(1) asignación.
                cantidadSignificados --;      //O(1) asignación.
                borrados ++;                 //O(1) asignación.
            }
        }
    }

    // método privado para contar la cantidad de hijos. 
    //Al ser comparaciones y recorridos de longitudes acotadas, la complejidad queda en O(1)

    private int cantidadHijos(Nodo nodo) {
        int i = 0;      //O(1) asignación.
        int res = 0;   //O(1) asignación.

        while ( i < nodo.hijos.length ) {     //O(1) ya que recorre un array de 256 posiciones, por lo tanto hace 256 iteraciones y O(256) = O(1)
            if (nodo.hijos[i] != null) {     //O(1) por ser una pregunta y una indexación.
                res ++;     //O(1) asignación.
            }
            i ++;        //O(1) asignación.
        }
        return res;
    }

    // booleano privado que indica si un nodo tiene hijos, finge de reemplazo sintáctico

    private boolean tieneHijos(Nodo Nodo){
        return cantidadHijos(Nodo) > 0;      //O(1) ya que la complejidad de cantidadHijos es O(1) y preguntar si el resultado es mayor a 0 también.
    }

    // método que devuelve un array de las claves (son siempre strings) ordenadas lexicográficamente

    public String[] recorrer(){

        // inicializa la respuesta y pide tantos espacios como significados haya. luego se irá completando
        String[] res = new String[cantidadSignificados];      //O(1) asignación.
        // un int que indicará en qué posición del array respuesta se debe guardar el significado
        int posicionDeGuardado = 0;       //O(1) asignación.

        // arranca desde la raiz
        Nodo actual = raiz;            //O(1) asignación.
        // indica en qué hijo está
        int indice = 0;             //O(1) asignación.
        // donde se iran anotando las letras
        String anotador = "";    //O(1) asignación.

        // recorre los 256 hijos en orden, + 1 caso si no encuentra hijos
        while (indice < 257) {       //O(1) ya que itera 257 veces, entonces O(257) = O(1)

            // si el índice está en rango del arreglo de hijos
            if (indice < 256) {   //O(1) ya que itera 256 veces, entonces O(256) = O(1)

                // cuando encuentra un hijo no vacio
                if (actual.hijos[indice] != null) {     //O(1) por ser una pregunta y una indexación.   

                    // baja en el Trie
                    actual = actual.hijos[indice];      //O(1) por ser una asignación e indexación.
                    // toma el caracter correspondiente al nodo actual
                    char c = (char) indice;         //O(1) asignación.
                    // resetea el indice para recorrer los hijos del nuevo nodo desde -1
                    indice = -1;                  //O(1) asignación.
                    // (al final del while le sumo 1 y empieza desde 0 en el próximo)
                    
                    // inicia un stringBuffer que permite modificar el anotador
                    StringBuffer sb = new StringBuffer(anotador);       //O(1) asignación.
                    // anota una letra
                    sb.append(c);                                    //O(1) asignación.
                    // actualiza el anotador
                    anotador = sb.toString();                     //O(1) asignación.
                    
                    // si cuando baja encuentra un significado
                    if (actual.significado != null) {       //O(1) por ser una pregunta
                        // añade lo anotado a res
                        res[posicionDeGuardado] = anotador;     //O(1) por ser una asignación e indexación.
                        // avanza a la siguiente posición de guardado
                        posicionDeGuardado ++;        //O(1) asignación.
                    }
                }
            }
            // si no encuentra hijo
            if (indice == 256) { 

                // si no es la raiz
                if (actual.ancestro != null) {

                    // sube en el Trie
                    actual = actual.ancestro;        //O(1) asignación.
                    // obtiene la última letra
                    char c = anotador.charAt(anotador.length() - 1);        //O(1) asignación.
                    // iguala el indice al valor ASCII de c, asi sigue recorriendo desde donde se había quedado en el nodo anterior
                    indice = c;         //O(1) asignación.

                    // inicia un stringBuffer que permite modificar un String
                    StringBuffer sb = new StringBuffer(anotador);        //O(1) asignación.
                    // le borra una letra
                    sb.deleteCharAt(sb.length() - 1);        //O(1) asignación.
                    // actualiza el anotador 
                    anotador = sb.toString();            //O(1) asignación.
                }
            }
            indice ++;          //O(1) asignación.
        }
        return res;
    }

    // clase privada iterador del Trie, con métodos públicos

    private class TrieIterador implements Iterador<T> {
        // donde se iran anotando las letras y formando los nombres
        private String anotador; 

        // arranca desde la raiz
        Nodo puntero = raiz; 

        // indica en qué hijo está en el nodo actual (o si no había hijos en caso de valer 256)
        int indiceHijosPuntero = 0; 
        // indica cuántos pasos se dió en cada iteración, es para evitar dar más de 1 paso a la vez
        int cuentaPasos = 0; 

        // booleano que indica si terminó de recorrer el Trie
        boolean terminoDeIterar = (indiceHijosPuntero == 257);
        // booleano que indica si el nodo es vacio
        boolean esVacio = !(tieneHijos(raiz)) && (puntero.ancestro == null);

    public boolean haySiguiente() {
        // hay siguiente si no es vacio o si terminó de iterar
        return !(terminoDeIterar) || !(esVacio);
    }

    public T siguiente() {
        // notar que en la primer iteración siempre devuelve null
        T significado = puntero.significado; 

        // recorre los 256 hijos en orden,
        // + 1 caso si no encuentra hijos (frena si ya dio un paso)
        while ( (indiceHijosPuntero < 257) && (cuentaPasos < 1) ) {                                                             

            // si el índice está en rango del arreglo de hijos
            if (indiceHijosPuntero < 256) { 

                // cuando encuentra un hijo no vacio
                if (puntero.hijos[indiceHijosPuntero] != null) { 

                    if (anotador == null) {
                        String nuevoAnotador = "";
                        anotador = nuevoAnotador;
                    }
                    // toma el caracter correspondiente al nodo actual
                    char c = (char) indiceHijosPuntero;
                    // inicia un stringBuffer que permite modificar el anotador
                    StringBuffer sb = new StringBuffer(anotador); 
                    // anota una letra
                    sb.append(c);
                    // actualiza el anotador
                    anotador = sb.toString();

                    // baja en el Trie
                    puntero = puntero.hijos[indiceHijosPuntero]; 
                    // resetea el indice para recorrer los hijos del nuevo nodo desde -1
                    indiceHijosPuntero = -1; 
                    // (al final del while le sumo 1 y empieza desde 0 en el próximo)
                    
                    // si cuando baja encuentra un significado
                    if (puntero.significado != null) { 
                        // suma 1 al cuentaPasos, deteniendo la iteración
                        cuentaPasos ++; 
                    }
                }
            }
            // si no encuentra hijo
            if (indiceHijosPuntero == 256) { 

                // si no es la raiz
                if (puntero.ancestro != null) { 
                    
                    // sube en el Trie
                    puntero = puntero.ancestro; 

                    // obtiene la última letra
                    char c = anotador.charAt(anotador.length() - 1); 
                    // iguala el indice al valor ASCII de c, asi sigue recorriendo desde donde se había quedado en el nodo anterior
                    indiceHijosPuntero = c; 
                    
                    // inicia un stringBuffer que permite modificar un String
                    StringBuffer sb = new StringBuffer(anotador); 
                    // le borra una letra
                    sb.deleteCharAt(sb.length() - 1); 
                    // actualiza el anotador
                    anotador = sb.toString();
                }
            } 
        // llega a valer 257 solo si no encuentra más hijos y está en la raiz, es decir, si termina de iterar
        indiceHijosPuntero ++;
        }
        // resetea el cuenta pasos para que inicie desde 0 en la próxima iteración
        cuentaPasos = 0; 
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
    
    // dejamos las funciones "hayAnterior" y "Anterior" sin implementar porque no la sibamos a usar en ninguna otra clase,
    // solo necesitabamos el método "siguiente"
    
    }


    public Iterador<T> iterador() {
        return new TrieIterador();
    }
}
