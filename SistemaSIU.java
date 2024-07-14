package aed;

public class SistemaSIU {

    //INVARIANTE DE REPRESENTACIÓN:
        //Es decir, cada clave en el Trie de estudiantes debe ser única,
        //y representar una libreta universitaria válida.
        
        //En otras palabras, cada valor asociado a una clave
        //en el Trie de estudiantes debe ser un entero no negativo.
        
        //No hay carreras duplicadas, cada clave en el Trie de carreras debe ser única,
        //y representar el nombre de una carrera válida.
        
        //Cada valor en el Trie de carreras debe ser una instancia
        //de conjuntoMaterias que contiene un Trie de Materia.
        
        //Cada clave en el Trie dentro de conjuntoMaterias debe ser única,
        //y representar el nombre de una materia válida dentro de la carrera correspondiente.
        
        //Para cada carrera en el Trie de carreras,
        //las materias dentro de su conjuntoMaterias deben estar correctamente asociadas a la carrera respectiva.



    private Trie<Integer> estudiantes; // el diccionario con todos los estudiantes y la cantidad de materias donde se han inscripto

    private Trie<conjuntoMaterias> carreras; // el diccionario con los nombres de todas las carreras y sus materias

    // sobre el uso de Integer y no int en estudiantes: el tipo pasado no puede ser primitivo en tipos genéricos, por ello se usó Integer

    // constructor del SistemaSIU vacio
    public SistemaSIU(){
        estudiantes = new Trie<Integer>();
        carreras = new Trie<conjuntoMaterias>();
    }

    // clase conjuntoMaterias, funge como reemplazo sintáctico
    // representa el conjunto de los nombres de las materias de una carrera, con la materia a la que se refieren como valor, reepresentados como un diccionario 

    public class conjuntoMaterias {
        Trie<Materia> dato;

        // constructor de conjuntoMaterias vacía
        private conjuntoMaterias() {
            dato = new Trie<Materia>();
        }

        public Trie<Materia> dato(){
            return dato;
        }
    }

    enum CargoDocente{
        PROF,
        JTP,
        AY1,
        AY2
    }

    public SistemaSIU(InfoMateria[] infoMaterias, String[] libretasUniversitarias){

        // por cada infoMateria en infoMaterias
        for (int i = 0; i < infoMaterias.length; i++) {  // O( Σc∈C( |c| ∗ |Mc| )
            
            // crea una nueva Materia vacía y la guarda en una variable interna "nuevaMateria"
            Materia nuevaMateria = new Materia(); 
            // notar que cada vez que se itere sobre infoMaterias, la variable pasará a guardar una nueva Materia vacía
            
            // obtiene infoMateria
            InfoMateria infoMateria = infoMaterias[i]; 
            // extrae los pares carrera-materia
            ParCarreraMateria[] paresCarreraMateria = infoMateria.getParesCarreraMateria(); 
            // obtiene la cantidad de pares carrera-materia que tiene
            int longitud = paresCarreraMateria.length; 
            
            // por cada parCarreraMateria en infoMateria;
            for (int j = 0; j < longitud; j++) {        //O ( Σm∈M Σn∈Nm ( |n| ) )
                
                // obtiene parCarreraMateria
                ParCarreraMateria parCarreraMateria = paresCarreraMateria[j]; 
                
                // obtiene la carrera a modificar o definir
                String carrera = parCarreraMateria.carrera; 
                // obtiene el nombre de la materia a definir
                String nombreMateria = parCarreraMateria.nombreMateria;

                // si carreras es nulo
                if ((carreras == null)) {

                    // crea un nuevo diccionario de carreras
                    Trie<conjuntoMaterias> nuevasCarreras = new Trie<>();
                    // define a carreras como igual al diccionario creado
                    carreras = nuevasCarreras;
                }

                // si carrera aún no está definida en carreras
                if ( !(carreras.pertenece(carrera)) ) { 

                    // crea un nuevo conjunto de materias
                    conjuntoMaterias materiasDeC = new conjuntoMaterias(); 
                    // en el dicc de carreras define la tupla clave-valor (carrera, nuevo conjunto de materias)
                    carreras.definir(carrera, materiasDeC); 
                }

                // obtiene el conjunto de materias de la carrera
                conjuntoMaterias materiasDeC = carreras.obtener(carrera); 
                // allí guarda la tupla clave-valor (nombre de la materia, Materia)
                materiasDeC.dato.definir(nombreMateria, nuevaMateria);

                // obtiene el conjunto de los nombres de la materia
                Trie<ListaEnlazada<conjuntoMaterias>> nombresMateria = nuevaMateria.nombresMateria();

                // si el nombre de la materia aún no pertenece al conjunto de nombres de la materia
                if ( !(nombresMateria.pertenece(nombreMateria))) {
                    // crea una nueva lista de conjuntos de materias
                    ListaEnlazada<conjuntoMaterias> nuevaListaConjuntosMaterias = new ListaEnlazada<>();
                    // define listaConjuntosMaterias en los nombres de la materia
                    nombresMateria.definir(nombreMateria, nuevaListaConjuntosMaterias);   
                }
                
                // obtiene la lista de conjuntos de materias que contienen el nombre de la materia
                ListaEnlazada<conjuntoMaterias> listaConjuntosMaterias = nombresMateria.obtener(nombreMateria);

                // agrega las materias de C a la lista
                listaConjuntosMaterias.agregarAtras(materiasDeC);
                // notar que el orden de agregado no importa pues estas listas no se recorrerán sino hasta el procedimiento cerrarMateria
                // donde se deberá borrar el nombre de todos los conjuntos independientemente de su orden de llegada.

                // en el conjunto de los nombres de la materia define
                // la tupla clave-valor (nombre de materia, lista de conjuntos (contiene a las materias de c))
                nombresMateria.definir(nombreMateria, listaConjuntosMaterias);
            }
        }
        // por cada LU
        for (int i = 0; i < libretasUniversitarias.length; i++) {       //O(E)

            // si estudiantes es nulo
            if ((estudiantes == null)) {
                // creo un nuevo diccionario de estudiantes
                Trie<Integer> nuevosEstudiantes = new Trie<>();
                // defino a estudiantes como igual al diccionario creado
                estudiantes = nuevosEstudiantes;
            }
            
            String LU = libretasUniversitarias[i]; 
            // guardo la tupla clave valor (LU, cantidad de materias inscripto)
            estudiantes.definir(LU, 0);

            // notar que la cantidad de materias inscripto es siempre 0 al iniciar el sistema porque aún nadie se anotó
        }  

        //Complejidad = O ( Σc∈C( |c| ∗ |Mc| ) + Σm∈M Σn∈Nm ( |n| ) + E)
    }

    public void inscribir(String estudiante, String carrera, String materia){

        //obtiene la cantidad de materias a las que está inscripto el estudiante
        Integer cantidadMateriasInscripto = estudiantes.obtener(estudiante);    //O(1) por ser una lista acotada.
        // suma 1 en el valor que contaviliza
        estudiantes.definir(estudiante, cantidadMateriasInscripto + 1);       //O(1) por ser una lista acotada.

        // obtiene el conjunto de materias de la carrera C
        conjuntoMaterias materiasDeC = carreras.obtener(carrera);     //O(|C|), se recorre la carrera hasta llegar al nodo final y entra en el trie de materias de tal carrera.
        // obtiene la materia a la que refiere el nombre de materia dado
        Materia Materia = materiasDeC.dato.obtener(materia);        //O(|M|), se recorre la materia de entrada y llega al nodo final con las caracteristicas de tal materia.
        
        // obtiene la lista de estudiantes anotados en la materia
        ListaEnlazada<String> estudiantesAnotados = Materia.estudiantesAnotados();      //O(1) asignación.
        // agrega al estudiante a esta lista
        estudiantesAnotados.agregarAtras(estudiante);       //O(1) porque es agregar atras en una lista enlazada.

        //Complejidad = O(|C| + |M|)
    }

    public int inscriptos(String materia, String carrera){
        
        conjuntoMaterias materiasDeC = carreras.obtener(carrera);    //O(|C|), se recorre la carrera hasta llegar al nodo final y entra en el trie de materias de tal carrera.
        Materia Materia = materiasDeC.dato.obtener(materia);        //O(|M|), se recorre la materia de entrada y llega al nodo final con las caracteristicas de tal materia.
        ListaEnlazada<String> estudiantesAnotados = Materia.estudiantesAnotados();      //O(1) asignación.

        return estudiantesAnotados.longitud();        

        //Complejidad = O(|C| + |M|)
    }

    public void agregarDocente(CargoDocente cargo, String carrera, String materia){

        conjuntoMaterias materiasDeC = carreras.obtener(carrera);          //O(|C|), se recorre la carrera hasta llegar al nodo final y entra en el trie de materias de tal carrera.
        Materia Materia = materiasDeC.dato.obtener(materia);              //O(|M|), se recorre la materia de entrada y llega al nodo final con las caracteristicas de tal materia.
        Plantel plantel = Materia.plantel();         //O(1) asignación.

        plantel.agregarDocente(cargo);     //O(1) porque estamos indexando en un array y modificando solo su valor.

        //Complejidad = O(|C| + |M|)
    }

    public int[] plantelDocente(String materia, String carrera){

        conjuntoMaterias materiasDeC = carreras.obtener(carrera);        //O(|C|), se recorre la carrera hasta llegar al nodo final y entra en el trie de materias de tal carrera.
        Materia Materia = materiasDeC.dato.obtener(materia);            //O(|M|), se recorre la materia de entrada y llega al nodo final con las caracteristicas de tal materia.
        Plantel plantel = Materia.plantel();        //O(1) asignación.
        
        return plantel.docentes();

        //Complejidad = O(|C| + |M|)
    }

    public boolean excedeCupo(String materia, String carrera){
        conjuntoMaterias materiasDeC = carreras.obtener(carrera);         //O(|C|), se recorre la carrera hasta llegar al nodo final y entra en el trie de materias de tal carrera.
        Materia Materia = materiasDeC.dato.obtener(materia);             //O(|M|), se recorre la materia de entrada y llega al nodo final con las caracteristicas de tal materia.
        ListaEnlazada<String> estudiantesAnotados = Materia.estudiantesAnotados();        //O(1) asignación.
        Plantel plantel = Materia.plantel();       //O(1) asignación.
        
        return plantel.cupo() < estudiantesAnotados.longitud();

        //Complejidad = O(|C| + |M|)
    }

    public String[] carreras(){
        return this.carreras.recorrer();         //O( Σc∈C(|c|) ), ya que la función "recorrer()" itera en todas las carreras, lo cual justifica la sumatoria de la longitud de carreras, y tambien las recolecta para agregarlas en un array, que es O(1).
                                                //El algoritmo de "recorrer()" devuelve el array con las carreras ordenadas de forma lexicográfica.
                            
        //Complejidad = O( Σc∈C(|c|) )
    }


    public String[] materias(String carrera){
        Trie<Materia> materiasDeC = carreras.obtener(carrera).dato;      //O(|C|), se recorre la carrera hasta llegar al nodo final y entra en el trie de materias de tal carrera.
        
        return materiasDeC.recorrer();          //O( Σmc∈Mc(|Mc|) ), mismo razonamiento que en la función "carreras()", pero esta vez recorrido en el trie de materias de tal carrera.
    
        //Complejidad = O( |C| + Σmc∈Mc(|Mc|) )
    }

    public int materiasInscriptas(String estudiante) {
        if (estudiantes.pertenece(estudiante)) {               //O(1) por ser una lista acotada.
            return estudiantes.obtener(estudiante);           //O(1) por ser una lista acotada.
        }
        return 0;

        //Complejidad = O(1)
    }

    public void cerrarMateria(String materia, String carrera){

        // obtiene el conjunto de materias de C
        conjuntoMaterias materiasDeC = carreras.obtener(carrera);
        // obtiene la materia a cerrar
        Materia materiaACerrar = materiasDeC.dato.obtener(materia);

        // inicializa un iterador sobre la lista de estudiantes de la materia a cerrar
        Iterador<String> iteradorListaEstudiantes = materiaACerrar.estudiantesAnotados().iterador();

        // recorre la lista de estudiantes de la materia
        while (iteradorListaEstudiantes.haySiguiente()) { 

            // obtiene una libreta universitaria
            String LU = iteradorListaEstudiantes.siguiente();
            // obtiene las materias a las que se anotó la LU
            int materiasInscripto = estudiantes.obtener(LU);
            // le resta 1 a la cantidad de materias a las que se anotó la LU
            estudiantes.definir(LU, materiasInscripto - 1); 
        }

        // obtiene el diccionario de los nombres de la materia a cerrar; sus tuplas clave-valor son (nombreMateria, materiasDeC)
        Trie<ListaEnlazada<conjuntoMaterias>> nombresMateriaACerrar = materiaACerrar.nombresMateria();

        // inicializa enteros que representan los significados por borrar y los borrados hasta ahora. Se usarán para un while
        int significadosPorBorrar = nombresMateriaACerrar.cantidadSignificados();
        int significadosBorrados = 0;

        // inicializa un iterador en el diccionario de los nombres de la materia a cerrar,
        // este va dedolviendo la lista con los conjuntos de materias donde están las materias de la carrera actual a medida que avanza
        Iterador<ListaEnlazada<conjuntoMaterias>> iteradorMateriasDeC = nombresMateriaACerrar.iterador();

        // como la primer iteración siempre es null, se realiza primero para que luego responda con elementos no nulos
        iteradorMateriasDeC.siguiente();

        // en este punto el iterador está en el primer nombre de la materia, al igual que el anotador interno
        String nombreActual = iteradorMateriasDeC.nombreActual(); // pide el nombre antes de iterar nuevamente

        // itera otra vez, así ya tiene un valor no nulo. obtiene la lista de conjuntos de materias (contiene el correspondiente a la carrera actual)
        ListaEnlazada<conjuntoMaterias> listaConjuntos_Actual = iteradorMateriasDeC.siguiente();

        // notar que ahora nombreActual y listaConjuntos_Actual forman la primera tupla clave-valor

        // recorre el diccionario de los nombres y va sacandolo de los conjuntos de las carreras a medida que los encuentra
        while (significadosBorrados < significadosPorBorrar) {

            // recorre la lista de conjuntos donde está el nombre actual, borrandolos cada vez
            while (0 < listaConjuntos_Actual.longitud()) {
                // obtiene el primero de la lista de conjuntos
                conjuntoMaterias materiasDeC_Actual = listaConjuntos_Actual.primero();
                // borra el conjunto obtenido de la lista de conjuntos
                listaConjuntos_Actual.eliminar(0);

                // borra el nombre actual del diccionario de materias de la carrera actual
                materiasDeC_Actual.dato.eliminar(nombreActual);   
            }
            // notar que, en el peor caso, la lista tiene una longitud igual a la cantidad de carreras que tenga la facultad: "#C".
            // En la consigna nunca se aclara si dicho conjunto es acotado o no, pero supondremos que es acotado.
            // Tomamos esta postura ya que consideramos que en la práctica el número de carreras de grado que la facultad imparte
            // se encuentrá más acotado que la longitud de los nombres de las mismas, de sus materias, o la cantidad de alumnos que las cursen,
            // haciendo que estos valores temrinen siendo los que definan la complejidad en lugar de #C.

            // actualiza nombreActual al siguiente nombre
            nombreActual = iteradorMateriasDeC.nombreActual();
            // actualiza materiasDeC al siguiente conjunto. Asi, actualizo la tupla (clave,valor)
            ListaEnlazada<conjuntoMaterias> actualizacionListaConjuntos = iteradorMateriasDeC.siguiente();
            listaConjuntos_Actual = actualizacionListaConjuntos;

            significadosBorrados++; // suma 1 a los borrados
        } // al terminar el while, la materia ya no está guardada en ningún conjunto de materias de ninguna carrera
    }
}
