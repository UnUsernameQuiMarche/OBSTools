package com.example.javafxmavenobs;

import javafx.scene.control.Tab;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Onglet extends Conteneur {
    private List<Section> sections;
    private Tab tab;

    public Onglet(JSONObject obj) {
        super(obj);

        JSONArray groupes = (JSONArray) obj.get("groupes");
        List<Section> liste_sections = new ArrayList<>();
        for (Object b : groupes) {
            JSONObject obj2 = (JSONObject) b;
            liste_sections.add(new Section(obj2, this));
        }
        this.sections = liste_sections;
    }

    public Onglet(String description) {
        super(description);
        this.sections = new ArrayList<>();
    }

    public List<Section> getSection() {
        return sections;
    }

    public void setTab(Tab tab) {
        this.tab = tab;
    }

    public Tab getTab() {
        return tab;
    }

    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();

        obj.put("nom", this.getDescription());

        JSONArray mes_groupes = new JSONArray();
        for (Section b : this.sections) {
            mes_groupes.add(b.toJSON());
        }
        obj.put("groupes", mes_groupes);

        return obj;
    }

    public void DeleteGroupe(Section section) {
        this.sections.remove(section);
    }

    public void UpGroupe(Section section) {
        int index = this.sections.indexOf(section);
        if (index > 0) {
            this.sections.remove(index);
            this.sections.add(index - 1, section);
        }
    }

    public void DownGroupe(Section section) {
        int index = this.sections.indexOf(section);
        if (index < this.sections.size() - 1) {
            this.sections.remove(index);
            this.sections.add(index + 1, section);
        }
    }
}
