package aed;

import aed.SistemaSIU.CargoDocente;
public class Plantel {

    //INVARIANTE DE REPRESENTACIÓN:

    //El plantel de docentes es un array fijo de números y de longitud igual a 4,
    //donde cada posición representa la cantidad de docentes de un específico cargo en la materia.
    //Siempre representan los mismos 4 cargos (PROF, JTP, AY1, AY2).
    //La cantidad de docentes es siempre un numero mayor o igual a 0.
    //El mínimo del plantel de docentes representa el cupo de una materia.
    //No hay permutaciones, a cada cargo siempre le corresponde la misma posición.

    // atributo que representa la cantidad de docentes de cada tipo
    private int[] docentes;

    // constructor del plantel con datos de entrada
    public Plantel() {
        docentes = new int[4];
    }

    public int[] docentes() {
        return docentes;
    }

    public void agregarDocente(CargoDocente cargo){
        docentes[cargo.ordinal()] ++;
    }

    // método para calcular el cupo
    public int cupo() {

        int cupoAY2 = docentes[CargoDocente.AY2.ordinal()] * 30;
        int cupoAY1 = docentes[CargoDocente.AY1.ordinal()] * 20;
        int cupoJTP = docentes[CargoDocente.JTP.ordinal()] * 100;
        int cupoPROF = docentes[CargoDocente.PROF.ordinal()] * 250;

        return cupoAY2 + cupoAY1 + cupoJTP + cupoPROF;
    }
}
