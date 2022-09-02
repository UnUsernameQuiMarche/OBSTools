package com.example.javafxmavenobs;

public enum NomMacro {
    Aucune,
    T5,
    A2,
    A9

    public static NomMacro getMacroByName(String name) {
        for (NomMacro macro : NomMacro.values()) {
            if (macro.name().equals(name)) {
                return macro;
            }
        }
        return Aucune;
    }

    // Fonction pour ajouter une macro
}
