package com.malicia.mrg.assistant.photo.repertoire;

import java.util.List;

public class SeanceRepertoire {

    private String repertoire;
    private String nomunique;
    private int uniteDeJour;
    private int nbMaxParUniteDeJour;
    private List<Integer> ratioStarMax;
    private List<String> zoneValeurAdmise;
    private boolean rapprochermentNewOk;

    // Getters and Setters
    public String getRepertoire() {
        return repertoire;
    }

    public void setRepertoire(String repertoire) {
        this.repertoire = repertoire;
    }

    public String getNomunique() {
        return nomunique;
    }

    public void setNomunique(String nomunique) {
        this.nomunique = nomunique;
    }

    public int getUniteDeJour() {
        return uniteDeJour;
    }

    public void setUniteDeJour(int uniteDeJour) {
        this.uniteDeJour = uniteDeJour;
    }

    public int getNbMaxParUniteDeJour() {
        return nbMaxParUniteDeJour;
    }

    public void setNbMaxParUniteDeJour(int nbMaxParUniteDeJour) {
        this.nbMaxParUniteDeJour = nbMaxParUniteDeJour;
    }

    public List<Integer> getRatioStarMax() {
        return ratioStarMax;
    }

    public void setRatioStarMax(List<Integer> ratioStarMax) {
        this.ratioStarMax = ratioStarMax;
    }

    public List<String> getZoneValeurAdmise() {
        return zoneValeurAdmise;
    }

    public void setZoneValeurAdmise(List<String> zoneValeurAdmise) {
        this.zoneValeurAdmise = zoneValeurAdmise;
    }

    public boolean isRapprochermentNewOk() {
        return rapprochermentNewOk;
    }

    public void setRapprochermentNewOk(boolean rapprochermentNewOk) {
        this.rapprochermentNewOk = rapprochermentNewOk;
    }
}

