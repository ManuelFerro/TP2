package aed;

import aed.SistemaSIU.CargoDocente;
public class Plantel {

    // atributo que representa la cantidad de docentes de cada tipo
    private int[] docentes;

    // constructor del plantel con datos de entrada
    public Plantel() {
        docentes = new int[4];
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
