package com.example.javafxmavenobs;

import org.json.simple.JSONObject;

public abstract class Conteneur {
    private String Description;

    public Conteneur(JSONObject obj) {
        this.Description = obj.get("nom").toString();
    }

    public Conteneur(String description) {
        this.Description = description;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    @Override
    public String toString() {
        return this.getDescription();
    }
}
