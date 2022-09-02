package com.example.javafxmavenobs;

public abstract class ElementInsertion {

    private String description;

    public ElementInsertion(String texte) {
        this.description = texte;
    }

    public abstract void insert() throws Exception;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
