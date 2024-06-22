package aed;

public class SistemaSIU {

    private Trie<Integer> estudiantes; // el diccionario con todos los estudiantes y la cantidad de materias donde se han inscripto

    private Trie<conjuntoMaterias> carreras; // el diccionario con los nombres de todas las carreras y sus materias

    private ListaEnlazada<conjuntoMaterias> materiasPorCarrera; // la lista con todos los grupos de materias por carrera

    // sobre el uso de Integer y no int en estudiantes: el tipo pasado no puede ser primitivo en tipos genéricos, por ello se usó Integer

    // constructor del SistemaSIU vacio
    public SistemaSIU(){
        estudiantes = new Trie<Integer>();
        carreras = new Trie<conjuntoMaterias>();
        materiasPorCarrera = new ListaEnlazada<conjuntoMaterias>();
    }

    // clase conjuntoMaterias, funge como reemplazo sintáctico
    // representa el cjnto de los nombres de las materias de una carrera, con la materia a la que se refieren como valor, reepresentados como un diccionario 

    public class conjuntoMaterias {
        Trie<Materia> dato;

        // constructor de conjuntoMaterias vacía
        private conjuntoMaterias() {
            dato = new Trie<Materia>();
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
                
                String carrera = parCarreraMateria.carrera;
                String materia = parCarreraMateria.nombreMateria;

                boolean carreraAunNoDefinida = !(carreras.pertenece(carrera));

                if ( carreraAunNoDefinida ) {
                    conjuntoMaterias materiasDeC = new conjuntoMaterias();
                }
            }
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
