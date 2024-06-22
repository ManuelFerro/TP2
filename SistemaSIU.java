package aed;

public class SistemaSIU {

    private Trie<Integer> estudiantes; // el diccionario con todos los estudiantes y la cantidad de materias donde se han inscripto

    private Trie<materias_c> carreras; // el diccionario con los nombres de todas las carreras y sus materias

    private ListaEnlazada<materias_c> materiasPorCarrera; // la lista con todos los grupos de materias por carrera

    // sobre el uso de Integer y no int en estudiantes: el tipo pasado no puede ser primitivo en tipos genéricos, por ello se usó Integer

    // constructor del SistemaSIU vacio
    public SistemaSIU(){
        estudiantes = new Trie<Integer>();
        carreras = new Trie<materias_c>();
        materiasPorCarrera = new ListaEnlazada<materias_c>();
    }

    // clase materias_c, funge como reemplazo sintáctico
    // representa el diccionario de los nombres de las materias de una carrera, con la materia a la que se refieren como valor 

    public class materias_c {
        Trie<Materia> dato;

        // constructor de materias_c vacía
        private materias_c() {
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
        throw new UnsupportedOperationException("Método no implementado aún");	    
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
