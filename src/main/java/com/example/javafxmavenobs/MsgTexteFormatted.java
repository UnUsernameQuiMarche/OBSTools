package com.example.javafxmavenobs;

import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MsgTexteFormatted extends MsgTexte {

    MsgTexteFormatted(JSONObject obj) {
        super(obj);
    }

    MsgTexteFormatted(String contenu, NomMacro macro) {
        super(contenu, macro);
    }

    @Override
    public void insert() throws Exception {
        setClipboard(formatClipBoard());
        super.insert();
    }

    private String formatClipBoard() throws Exception {
        String[] list = getClipboard().split(" ");
        List<String> out = new ArrayList<>();
        List<String> mot = Arrays.asList("Line","User", "Service", "Active Time", "Idle Time");
        StringBuilder outString = new StringBuilder();
        for (String s : list) {
            if (s.length() > 0) {
                out.add(s);
            }
        }

        for (int i = 0; i < out.size() - 1; i++) {
            outString.append(mot.get(i)).append(" : ").append(out.get(i)).append("\n");
        }

        return outString.toString().trim();
    }

}
