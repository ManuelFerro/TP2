package aed;

public class SistemaSIU {

    private Trie<Integer> estudiantes; // el diccionario con todos los estudiantes y la cantidad de materias donde se han inscripto

    private Trie<conjuntoMaterias> carreras; // el diccionario con los nombres de todas las carreras y sus materias

    private Trie<conjuntoMaterias> materiasPorCarrera; // el diccionario con todos los conjuntos de materias por carrera

    // sobre el uso de Integer y no int en estudiantes: el tipo pasado no puede ser primitivo en tipos genéricos, por ello se usó Integer

    // constructor del SistemaSIU vacio
    public SistemaSIU(){
        estudiantes = new Trie<Integer>();
        carreras = new Trie<conjuntoMaterias>();
        materiasPorCarrera = new Trie<conjuntoMaterias>();
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

        for (int i = 0; i < infoMaterias.length; i++) { // por cada infoMateria en infoMaterias

            InfoMateria infoMateria = infoMaterias[i]; // obtengo infoMateria
            ParCarreraMateria[] paresCarreraMateria = infoMateria.getParesCarreraMateria();
            int longitud = paresCarreraMateria.length;

            for (int j = 0; j < longitud; j++) { // por cada parCarreraMateria en infoMateria;

                ParCarreraMateria parCarreraMateria = paresCarreraMateria[j]; // obtengo parCarreraMateria
                
                String carrera = parCarreraMateria.carrera; // obtengo la carrera a modificar o definir
                String nombreMateria = parCarreraMateria.nombreMateria; // obtengo el nombre de la materia a definir

                boolean carreraAunNoDefinida = !(carreras.pertenece(carrera));

                if ( carreraAunNoDefinida ) { // si carrera aún no está definida en carreras

                    conjuntoMaterias materiasDeC = new conjuntoMaterias(); // creo un nuevo conjunto de materias
                    carreras.definir(carrera, materiasDeC); // en el dicc de carreras defino la clave carrera, que tiene por valor el conjunto de materias nuevo
                }

                conjuntoMaterias materiasDeC = carreras.obtener(carrera); // obtengo el conjunto de materias de la carrera
                
                boolean esPrimero = (j == 0); // una variable booleana que me dice si estoy en el primer elemento de infoMateria,

                if (esPrimero) { // si es la primera vez que defino la materia

                    Materia nuevaMateria = new Materia(); // abro una nueva materia
                    materiasDeC.dato.definir(nombreMateria, nuevaMateria); // guardo como clave al nombre de la materia, como valor la materia

                    Trie<conjuntoMaterias> nombresMateria = nuevaMateria.nombresMateria(); // obtengo los nombres de la materia
                    nombresMateria.definir(nombreMateria, materiasDeC); // defino la tupla clave-valor (nombre de materia, conjunto de materias de c) en los nombres de la materia
                }
            }
        }
        for (int i = 0; i < libretasUniversitarias.length; i++) {
            
            String LU = libretasUniversitarias[i];
            estudiantes.definir(LU, 0);
        }  
    }

    public void inscribir(String estudiante, String carrera, String materia){

        Integer cantidadMateriasInscripto = estudiantes.obtener(estudiante);
        estudiantes.definir(materia, cantidadMateriasInscripto + 1); // sumo 1 en el valor que contaviliza

        ;
    }

    public void agregarDocente(CargoDocente cargo, String carrera, String materia){
        throw new UnsupportedOperationException("Método no implementado aún");	    
    }

    public int[] plantelDocente(String materia, String carrera){
        throw new UnsupportedOperationException("Método no implementado aún");	    
    }

    public void cerrarMateria(String materia, String carrera){
        throw new UnsupportedOperationException("Método no implementado aún");	    
    }

    public int inscriptos(String materia, String carrera){
        throw new UnsupportedOperationException("Método no implementado aún");	    
    }

    public boolean excedeCupo(String materia, String carrera){
        throw new UnsupportedOperationException("Método no implementado aún");	    
    }

    public String[] carreras(){
        throw new UnsupportedOperationException("Método no implementado aún");	    
    }

    public String[] materias(String carrera){
        throw new UnsupportedOperationException("Método no implementado aún");	    
    }

    public int materiasInscriptas(String estudiante){
        throw new UnsupportedOperationException("Método no implementado aún");	    
    }
}
