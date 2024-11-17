package com.malicia.mrg.assistant.photo.repertoire;

public class SeanceRepertoire {

    private String path;  // Path or description of the repertoire
    private String description; // Optional description for the repertoire

    // Constructor
    public SeanceRepertoire(String path, String description) {
        this.path = path;
        this.description = description;
    }

    // Getters and Setters
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "SeanceRepertoire{path='" + path + "', description='" + description + "'}";
    }
}