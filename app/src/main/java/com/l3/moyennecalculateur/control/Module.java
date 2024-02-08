package com.l3.moyennecalculateur.control;

public class Module {
    private String intitule;
    private int coefficient;
    private double emd, td, tp, moyenne;

    public Module(String intitule, int coefficient, double emd, double td, double tp) {
        this.setIntitule(intitule);
        this.setCoefficient(coefficient);
        this.setEmd(emd);
        this.setTd(td);
        this.setTp(tp);
        this.setMoyenne();
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public int getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(int coefficient) {
        this.coefficient = coefficient;
    }

    public double getEmd() {
        return emd;
    }

    public void setEmd(double emd) {
        this.emd = emd;
    }

    public double getTd() {
        return td;
    }

    public void setTd(double td) {
        this.td = td;
    }

    public double getTp() {
        return tp;
    }

    public void setTp(double tp) {
        this.tp = tp;
    }

    public double getMoyenne() {
        return moyenne;
    }

    private void setMoyenne() {
       // this.moyenne = emd*2 + (td + tp);
       // this.moyenne /= 4;
        this.moyenne = emd*0.6+((td+tp)/2)*0.4;
    }
}
