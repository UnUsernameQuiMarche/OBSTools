package com.example.javafxmavenobs;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Section extends Conteneur {

    private List<MesBoutons> buttons;

    public Section(JSONObject obj, Onglet onglet) {
        super(obj);
        JSONArray mes_boutons = (JSONArray) obj.get("buttons");
        List<MesBoutons> liste_boutons = new ArrayList<>();
        for (Object c : mes_boutons) {
            JSONObject obj2 = (JSONObject) c;
            liste_boutons.add(new MesBoutons(obj2, onglet));
        }
        this.buttons = liste_boutons;
    }

    public Section(String description) {
        super(description);
        this.buttons = new ArrayList<>();
    }

    public List<MesBoutons> getButtons() {
        return buttons;
    }

    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();

        obj.put("nom", this.getDescription());

        JSONArray mes_boutons = new JSONArray();
        for (MesBoutons b : this.buttons) {
            mes_boutons.add(b.toJSON());
        }
        obj.put("buttons", mes_boutons);


        return obj;
    }

    public void DeleteButton(MesBoutons button) {
        this.buttons.remove(button);
    }

    public void DownMesButton(MesBoutons button) {
        int index = this.buttons.indexOf(button);
        if (index < this.buttons.size() - 1) {
            MesBoutons tmp = this.buttons.get(index + 1);
            this.buttons.set(index + 1, button);
            this.buttons.set(index, tmp);
        }
    }

    public void UpMesButton(MesBoutons button) {
        int index = this.buttons.indexOf(button);
        if (index > 0) {
            MesBoutons tmp = this.buttons.get(index - 1);
            this.buttons.set(index - 1, button);
            this.buttons.set(index, tmp);
        }
    }

    public void AddButton(MesBoutons button) {
        this.buttons.add(button);
    }
}
