package aed;
import aed.SistemaSIU.conjuntoMaterias;

// clase Materia. Abstrae el concepto de una materia de la facultad, con
// su plantel docente (que define su cupo), los alumnos inscriptos a la misma y
// el diccionario de todos sus nombres y en qué carrera están

public class Materia {

    //INVARIANTE DE REPRESENTACIÓN:

    //Todo TAD de Materia debe tener la lista "estudiantesAnotados" que representa los estudiantes inscriptos a la materia,
    //el Trie "nombresMateria" que representa los diferentes nombres que tiene la materia dependiendo de la carrera,
    //y el TAD "plantel" que contiene el abasto de docentes y el cupo de la materia.
    //La lista "estudiantesAnotados" debe contener solo strings no nulos y no debe tener duplicados.
    //El Trie "nombresMateria" debe tener claves que sean strings no nulos y no vacíos,
    //y los valores deben ser instancias válidas de listas enlazadas de "conjuntoMaterias".
    //Cada array de docentes de un plantel de Materia deben ser de la misma longitud y representar los mismos cargos.
    //Todos los estudiantes de un TAD Materia que pertenecen a "estudiantesAnotados" 
    //deben tambien pertenecer a la lista "estudiantesAnotados" de los demas TADs Materia que estan en "nombresMateria".

    private Plantel plantel;
    private ListaEnlazada<String> estudiantesAnotados;
    private Trie<ListaEnlazada<conjuntoMaterias>> nombresMateria;
    // sus valores son listas de conjuntoMaterias, diccionarios que representan el conjunto de materias de una carrera.
    // están en listas enlazadas para permitir que varias carreras se refieran a una materia con el mismo nombre,
    // agregando o quitando mediante punteros, lo que mantiene una complejidad de O(1)

    // constructor de Materia vacía
    public Materia(){
        estudiantesAnotados = new ListaEnlazada<>();
        nombresMateria = new Trie<ListaEnlazada<conjuntoMaterias>>();
        plantel = new Plantel();
    }

    public ListaEnlazada<String> estudiantesAnotados(){
        return estudiantesAnotados;
    }

    public Trie<ListaEnlazada<conjuntoMaterias>> nombresMateria(){
        return nombresMateria;
    }

    public Plantel plantel(){
        return plantel;
    }
}

