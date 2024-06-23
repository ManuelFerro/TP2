package aed;

public class SistemaSIU {

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
        AY2, // ayudante de segunda
        AY1, // ayudante de primera
        JTP, // jefe de trabajo práctico
        PROF // profesor
    }

    public SistemaSIU(InfoMateria[] infoMaterias, String[] libretasUniversitarias){
        //Trie<conjuntoMaterias> carreras = this.carreras;
        //Trie<Integer> estudiantes = this.estudiantes;

        for (int i = 0; i < infoMaterias.length; i++) { // por cada infoMateria en infoMaterias

            Materia nuevaMateria = new Materia(); // creo una nueva Materia vacía y la guardo en una variable interna "nuevaMateria"
            // notar que cada vez que itere sobre infoMaterias, la variable pasará a guardar una nueva Materia vacía

            InfoMateria infoMateria = infoMaterias[i]; // obtengo infoMateria
            ParCarreraMateria[] paresCarreraMateria = infoMateria.getParesCarreraMateria(); // extraigo los pares carrera-materia
            int longitud = paresCarreraMateria.length; // obtengo la cantidad de pares carrera-materia que tiene

            for (int j = 0; j < longitud; j++) { // por cada parCarreraMateria en infoMateria;

                ParCarreraMateria parCarreraMateria = paresCarreraMateria[j]; // obtengo parCarreraMateria
                
                String carrera = parCarreraMateria.carrera; // obtengo la carrera a modificar o definir
                String nombreMateria = parCarreraMateria.nombreMateria; // obtengo el nombre de la materia a definir

                if ((carreras == null)) {
                    Trie<conjuntoMaterias> nuevasCarreras = new Trie<>();
                    carreras = nuevasCarreras;
                }
                if ( !(carreras.pertenece(carrera)) ) { // si carrera aún no está definida en carreras o carreras es nulo

                    conjuntoMaterias materiasDeC = new conjuntoMaterias(); // creo un nuevo conjunto de materias
                    carreras.definir(carrera, materiasDeC); // en el dicc de carreras defino la tupla clave-valor (carrera, nuevo conjunto de materias)
                }

                conjuntoMaterias materiasDeC = carreras.obtener(carrera); // obtengo el conjunto de materias de la carrera

                materiasDeC.dato.definir(nombreMateria, nuevaMateria); // allí guardo la tupla clave-valor (nombre de la materia, Materia)

                Trie<conjuntoMaterias> nombresMateria = nuevaMateria.nombresMateria(); // obtengo el conjunto de los nombres de la materia
                nombresMateria.definir(nombreMateria, materiasDeC); // allí defino la tupla clave-valor (nombre de materia, conjunto de materias de c)
            }
        }
        for (int i = 0; i < libretasUniversitarias.length; i++) { // por cada LU

            if ((estudiantes == null)) {
                Trie<Integer> nuevosEstudiantes = new Trie<>();
                estudiantes = nuevosEstudiantes;
            }
            
            String LU = libretasUniversitarias[i]; // obtengo la LU a guardar
            estudiantes.definir(LU, 0); // guardo la tupla clave valor (LU, cantidad de materias inscripto)
            // notar que la cantidad de materias inscripto es siempre 0 al iniciar el sistema porque aún nadie se anotó
        }  
    }

    public void inscribir(String estudiante, String carrera, String materia){

        Integer cantidadMateriasInscripto = estudiantes.obtener(estudiante); //obtiene la cantidad de materias a las que está inscripto el estudiante
        estudiantes.definir(materia, cantidadMateriasInscripto + 1); // suma 1 en el valor que contaviliza

        conjuntoMaterias materiasDeC = carreras.obtener(carrera); // obtiene el conjunto de materias de la carrera C
        Materia Materia = materiasDeC.dato.obtener(materia); // obtiene la materia a la que refiere el nombre de materia dado

        ListaEnlazada<String> estudiantesAnotados = Materia.estudiantesAnotados(); // obtiene la lista de estudiantes anotados en la materia
        estudiantesAnotados.agregarAtras(estudiante); // agrega al estudiante a esta lista 
    }

    public int inscriptos(String materia, String carrera){
        throw new UnsupportedOperationException("Método no implementado aún");	    
    }

    public void agregarDocente(CargoDocente cargo, String carrera, String materia){
        throw new UnsupportedOperationException("Método no implementado aún");	    
    }

    public int[] plantelDocente(String materia, String carrera){
        throw new UnsupportedOperationException("Método no implementado aún");	    
    }

    public boolean excedeCupo(String materia, String carrera){
        throw new UnsupportedOperationException("Método no implementado aún");	    
    }

    public String[] carreras(){
        return this.carreras.recorrer();
    }

    public String[] materias(String carrera){
        Trie<Materia> materiasDeC = carreras.obtener(carrera).dato;
        
        return materiasDeC.recorrer();
    }

    public int materiasInscriptas(String estudiante){
        throw new UnsupportedOperationException("Método no implementado aún");	    
    }

    public void cerrarMateria(String materia, String carrera){

        conjuntoMaterias materiasDeC = carreras.obtener(carrera); // obtiene el conjunto de materias de C

        Materia materiaACerrar = materiasDeC.dato.obtener(materia); // obtiene la materia a cerrar

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
            nombreActual = iterador.nombreActual(); // actualizo nombreActual al siguiente nombre
            MateriasDeC = iterador.siguiente(); // actualizo materiasDeC al siguiente conjunto. Asi, actualizo la tupla (clave,valor)

            significadosBorrados++; // sumo 1 a los borrados
        } // al terminar el while, la materia ya no está guardada en ningún conjunto de materias de ninguna carrera
    }
}
