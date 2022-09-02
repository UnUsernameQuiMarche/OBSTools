package com.example.javafxmavenobs;

public enum TypeMessage {
    MsgBrut,
    MsgTexte,
    MsgImage,
    MsgTexteFormatted;

    public static TypeMessage getTypeByName(String name) {
        for (TypeMessage type : TypeMessage.values()) {
            if (type.name().equals(name)) {
                return type;
            }
        }
        return null;
    }
}
