package com.example.javafxmavenobs;

import javafx.scene.control.Button;
import org.json.simple.JSONObject;

import java.util.Objects;

public class MesBoutons extends Conteneur {
    private String TexteDuBouton;

    private MsgBrut Message;

    private Button Bouton;

    private MesBoutons ProchaineAction;

    private Onglet onglet;

    private ActionEDS ActionEDSCorrespondante;

    private String ProchaineActionId;

    public MesBoutons(JSONObject obj, Onglet onglet) {
        super(obj);
        this.TexteDuBouton = obj.get("texte").toString();
        this.onglet = onglet;
        this.ActionEDSCorrespondante = Parser.getActionEDSbyTitre(obj.get("actionedsid").toString());
        this.ProchaineActionId = (String) obj.get("nextsid");

        String type = obj.get("type").toString();
        if (Objects.equals(type, "MsgBrut")) {
            this.Message = new MsgBrut(obj);
        } else if (Objects.equals(type, "MsgTexte")) {
            this.Message = new MsgTexte(obj);
        } else if (Objects.equals(type, "MsgImage")) {
            this.Message = new MsgImage(obj);
        } else if (Objects.equals(type, "MsgTexteFormatted")) {
            this.Message = new MsgTexteFormatted(obj);
        }
    }

    public MesBoutons(String txtBtn, Onglet onglet, ActionEDS actEDS, String next, MsgBrut msg, String desc) {
        super(desc);
        this.TexteDuBouton = txtBtn;
        this.onglet = onglet;
        this.ActionEDSCorrespondante = actEDS;
        this.ProchaineActionId = next;
        this.Message = msg;
    }
    public String getTexteDuBouton() {
        return TexteDuBouton;
    }

    public MsgBrut getMessage() {
        return Message;
    }

    public void setBouton(Button bouton) {
        this.Bouton = bouton;
    }

    public Button getBouton() {
        return Bouton;
    }

    public void setProchaineActionId(String prochaineAction) {
        ProchaineActionId = prochaineAction;
    }

    public MesBoutons getProchaineAction() {
        return ProchaineAction;
    }

    public Onglet getOnglet() {
        return onglet;
    }

    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();

        obj.put("texte", this.TexteDuBouton);
        obj.put("nextsid", this.getProchaineActionId());
        obj.put("nom", this.getDescription());
        obj.put("actionedsid", this.getActioneds().getTitre());
        //obj.put("id", this.Id);
        obj.put("type", this.Message.getClass().getSimpleName());
        obj.put("contenu", this.Message.getDescription());
        obj.put("macro", this.Message.getMacro().toString());

        return obj;
    }
    public ActionEDS getActioneds() {
        return this.ActionEDSCorrespondante;
    }

    public void setActioneds(ActionEDS actioneds) {
        this.ActionEDSCorrespondante = actioneds;
    }

    public void setTexteDuBouton(String texteDuBouton) {
        this.TexteDuBouton = texteDuBouton;
    }

    public String getProchaineActionId() {
        return this.ProchaineActionId;
    }

    public void setMessage(MsgBrut message) {
        this.Message = message;
    }

    public void setProchaineAction(MesBoutons buttonById) {
        this.ProchaineAction = buttonById;
    }
}
