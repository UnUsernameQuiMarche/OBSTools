package com.example.javafxmavenobs;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.nio.file.*;
import java.util.Objects;

public class Parser {

    private static List<ActionEDS> ListeDesActions;

    private List<MsgBrut> ListeDeMessage;

    private List<Onglet> ListeDesOnglets;

    private List<MesBoutons> ListeBouton;

    public Parser() throws Exception{
        List<MsgBrut> ListeDeMessage = new ArrayList<>();
        List<ActionEDS> liste_actioneds = new ArrayList<>();
        List<Onglet> liste_onglets = new ArrayList<>();

        File f = new File("./perso.json");
        InputStream inputStream;
        if (f.isFile() && f.canRead()) {
            inputStream = new FileInputStream(f);
        } else {
            inputStream = this.getClass().getClassLoader().getResourceAsStream("msgv2.json");
        }
        JSONParser jsonParser = new JSONParser();
        assert inputStream != null;
        JSONObject jsonObject = (JSONObject) jsonParser.parse(new InputStreamReader(inputStream, StandardCharsets.UTF_8));


        JSONArray action = (JSONArray) jsonObject.get("actioneds");

        for (Object a : action) {
            JSONObject obj = (JSONObject) a;
            liste_actioneds.add(new ActionEDS(obj));
        }

        liste_actioneds.sort((a, b) -> a.getTitre().compareTo(b.getTitre()));
        ListeDesActions = liste_actioneds;

        JSONArray onglets = (JSONArray) jsonObject.get("onglets");
        for (Object a : onglets) {
            JSONObject obj = (JSONObject) a;
            liste_onglets.add(new Onglet(obj));
        }

        this.ListeDeMessage = ListeDeMessage;
        this.ListeDesOnglets = liste_onglets;
        this.getListeMesButtons();
    }

    public List<MsgBrut> getListeDeMessage() {
        return ListeDeMessage;
    }

    HashMap<String, Formulaire> ListeMacro = new HashMap<String, Formulaire>();

    public List<ActionEDS> getListeDesActions() {
        return ListeDesActions;
    }

    public List<Onglet> getListeDesOnglets() {
        return ListeDesOnglets;
    }

    private String toJSON() {
        JSONObject jsonObject = new JSONObject();

        JSONArray texte = new JSONArray();

        JSONArray action = new JSONArray();
        for (ActionEDS t : getListeDesActions()) {
            JSONObject obj = t.toJSON();
            action.add(obj);
        }

        JSONArray onglets = new JSONArray();
        for (Onglet t : getListeDesOnglets()) {
            JSONObject obj = t.toJSON();
            onglets.add(obj);
        }

        jsonObject.put("actioneds", action);
        jsonObject.put("onglets", onglets);

        return jsonObject.toJSONString();
    }

    public void SaveFile() {
        String s = this.toJSON();
        Path p = Paths.get("./perso.json");
        File file = p.toFile();


        try (FileOutputStream fos = new FileOutputStream(file);
             OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
             BufferedWriter writer = new BufferedWriter(osw)) {

            writer.append(s);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void DeleteOnglet(Onglet onglet) {
        this.ListeDesOnglets.remove(onglet);
    }

    public void UpOnglet(Onglet onglet) {
        int index = this.ListeDesOnglets.indexOf(onglet);
        if (index > 0) {
            this.ListeDesOnglets.remove(onglet);
            this.ListeDesOnglets.add(index - 1, onglet);
        }
    }

    public void DownOnglet(Onglet onglet) {
        int index = this.ListeDesOnglets.indexOf(onglet);
        if (index < this.ListeDesOnglets.size() - 1) {
            this.ListeDesOnglets.remove(onglet);
            this.ListeDesOnglets.add(index + 1, onglet);
        }
    }

    private void getListeMesButtons(){
        List<MesBoutons> liste_mesbuttons = new ArrayList<>();
        for (Onglet o : this.ListeDesOnglets) {
            for (Section g : o.getSection()) {
                liste_mesbuttons.addAll(g.getButtons());
            }
        }
        this.ListeBouton = liste_mesbuttons;
    }

    public List<MesBoutons> getListeBouton() {
        return ListeBouton;
    }

    public static ActionEDS getActionEDSbyTitre(String titre){
        for(ActionEDS a : ListeDesActions){
            if(Objects.equals(a.getTitre(), titre)){
                return a;
            }
        }
        return null;
    }

    public MesBoutons getButtonByDesc(String id){
        for(MesBoutons btn : this.ListeBouton){
            if(Objects.equals(btn.getDescription(), id)){
                return btn;
            }
        }
        return null;
    }

    public boolean ActionEDSUsed(ActionEDS a){
        for (MesBoutons b : this.ListeBouton) {
            if(Objects.equals(b.getActioneds(), a)){
                return true;
            }
        }
        return false;
    }

    public boolean ActionEDSExist(String titre){
        for(ActionEDS a : ListeDesActions){
            if(Objects.equals(a.getTitre(), titre)){
                return true;
            }
        }
        return false;
    }

    /*
    public void AjoutMacro (String nom) {
        Formulaire f = new Formulaire(nom);
        this.ListeMacro.put(nom, f);

    }*/

}
