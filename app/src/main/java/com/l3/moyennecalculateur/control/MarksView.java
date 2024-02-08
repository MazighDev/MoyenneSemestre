package com.l3.moyennecalculateur.control;

public class MarksView {
    private String intitule;
    private int coefficient;
    private double moyenne,emd,td,tp;


    public MarksView(String intitule, int coefficient, double moyenne) {
        this.intitule = intitule;
        this.coefficient = coefficient;
        this.moyenne = moyenne;
        this.emd =emd;
        this.td =td;
        this.tp = tp;
    }

    public String getIntitule() {
        return intitule;
    }

    public int getCoefficient() {
        return coefficient;
    }

    public double getMoyenne() {
        return moyenne;
    }

    public double getEmd() {
        return emd;
    }

    public double getTd() {
        return td;
    }

    public double getTp() {
        return tp;
    }
}
