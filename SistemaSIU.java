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
    // representa el cjnto de los nombres de las materias de una carrera, con la materia a la que se refieren como valor, reepresentados como un diccionario 

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
        //Trie<conjuntoMaterias> carreras = this.carreras;
        //Trie<Integer> estudiantes = this.estudiantes;



                                    

        // por cada infoMateria en infoMaterias
        for (int i = 0; i < infoMaterias.length; i++) {  // O( Σc∈C( |c| ∗ |Mc| )
            
            // creo una nueva Materia vacía y la guardo en una variable interna "nuevaMateria"
            Materia nuevaMateria = new Materia(); 
            // notar que cada vez que itere sobre infoMaterias, la variable pasará a guardar una nueva Materia vacía
            
            // obtengo infoMateria
            InfoMateria infoMateria = infoMaterias[i]; 
            // extraigo los pares carrera-materia
            ParCarreraMateria[] paresCarreraMateria = infoMateria.getParesCarreraMateria(); 
            // obtengo la cantidad de pares carrera-materia que tiene
            int longitud = paresCarreraMateria.length; 
            
            // por cada parCarreraMateria en infoMateria;
            for (int j = 0; j < longitud; j++) {        //O ( Σm∈M Σn∈Nm ( |n| ) )
                
                // obtengo parCarreraMateria
                ParCarreraMateria parCarreraMateria = paresCarreraMateria[j]; 
                
                // obtengo la carrera a modificar o definir
                String carrera = parCarreraMateria.carrera; 
                // obtengo el nombre de la materia a definir
                String nombreMateria = parCarreraMateria.nombreMateria;

                if ((carreras == null)) {
                    Trie<conjuntoMaterias> nuevasCarreras = new Trie<>();
                    carreras = nuevasCarreras;
                }
                // si carrera aún no está definida en carreras o carreras es nulo
                if ( !(carreras.pertenece(carrera)) ) { 

                    // creo un nuevo conjunto de materias
                    conjuntoMaterias materiasDeC = new conjuntoMaterias(); 
                    // en el dicc de carreras defino la tupla clave-valor (carrera, nuevo conjunto de materias)
                    carreras.definir(carrera, materiasDeC); 
                }

                // obtengo el conjunto de materias de la carrera
                conjuntoMaterias materiasDeC = carreras.obtener(carrera); 
                
                // allí guardo la tupla clave-valor (nombre de la materia, Materia)
                materiasDeC.dato.definir(nombreMateria, nuevaMateria); 
                // obtengo el conjunto de los nombres de la materia
                Trie<conjuntoMaterias> nombresMateria = nuevaMateria.nombresMateria();
                // allí defino la tupla clave-valor (nombre de materia, conjunto de materias de c) 
                nombresMateria.definir(nombreMateria, materiasDeC); 
                
            }
        }
        // por cada LU
        for (int i = 0; i < libretasUniversitarias.length; i++) {       //O(E)

            if ((estudiantes == null)) {
                Trie<Integer> nuevosEstudiantes = new Trie<>();
                estudiantes = nuevosEstudiantes;
            }
            
            String LU = libretasUniversitarias[i]; 
            estudiantes.definir(LU, 0); // guardo la tupla clave valor (LU, cantidad de materias inscripto)
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

        conjuntoMaterias materiasDeC = carreras.obtener(carrera); // obtiene el conjunto de materias de C

        Materia materiaACerrar = materiasDeC.dato.obtener(materia); // obtiene la materia a cerrar

        Iterador<String> ListaEstudiantes = materiaACerrar.estudiantesAnotados().iterador(); // inicializa un iterador sobre la lista de estudiantes

        while (ListaEstudiantes.haySiguiente()) { // recorre la lista de estudiantes de la materia

            String LU = ListaEstudiantes.siguiente(); // obtiene una libreta universitaria
            int materiasInscripto = estudiantes.obtener(LU); // obtiene las materias a las que se anotó la LU
            estudiantes.definir(LU, materiasInscripto - 1); // le resta 1 a la cantidad de materias a las que se anotó la LU
        }

        Trie<conjuntoMaterias> nombresMateriaACerrar = materiaACerrar.nombresMateria(); // obtiene el diccionario de los nombres de la materia a cerrar
        int significadosPorBorrar = nombresMateriaACerrar.cantidadSignificados(); 
        int significadosBorrados = 0;// enteros que representas los significados por borrar y los borrados hasta ahora. Se usarán para un while

        // recorre el diccionario de los nombres y va sacandolo de los conjuntos de las carreras a medida que los encuentra

        Iterador<conjuntoMaterias> iterador = nombresMateriaACerrar.iterador(); // inicializa un iterador en el diccionario de los nombres de la materia a cerrar
        iterador.siguiente(); // como la primer iteración siempre es null, la realizo ahora para que luego me responda con elementos no nulos
        
        String nombreActual = iterador.nombreActual(); // en este punto el iterador está en el primer nombre de la materia,
                                                        // al igual que el anotador interno, por ello pido el nombre antes de iterar nuevamente

        conjuntoMaterias MateriasDeC = iterador.siguiente(); // itera otra vez, así ya tiene un valor no nulo. obtiene el conjunto de materias de la carrera c
        // notar que ahora nombreActual y MateriasDeC forman la primera tupla clave-valor

        while (significadosBorrados < significadosPorBorrar) {

            materiasDeC.dato.eliminar(nombreActual); // borro el nombre actual del diccionario de materias de la carrera C
            nombresMateriaACerrar.eliminar(nombreActual);

            nombreActual = iterador.nombreActual(); // actualizo nombreActual al siguiente nombre
            MateriasDeC = iterador.siguiente(); // actualizo materiasDeC al siguiente conjunto. Asi, actualizo la tupla (clave,valor)

            significadosBorrados++; // sumo 1 a los borrados
        } // al terminar el while, la materia ya no está guardada en ningún conjunto de materias de ninguna carrera
    }
}

