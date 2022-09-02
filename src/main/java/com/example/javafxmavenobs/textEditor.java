package com.example.javafxmavenobs;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class textEditor {
    @FXML
    public TextArea ContenuMsg;
    @FXML
    public TextField TexteMsg;
    @FXML
    public TextField TitreMsg;
    @FXML
    public ChoiceBox<TypeMessage> TypeMsg;
    @FXML
    public ChoiceBox<ActionEDS> ActionEDSMsg;
    @FXML
    public ChoiceBox BoutonSuivMsg;
    @FXML
    public ChoiceBox<NomMacro> MacroMsg;

    private MesBoutons monBtn;

    private Section maSection;

    private Onglet monOnglet;

    private HelloController helloControler;

    private ModifBouton modifBouton;

    @FXML
    public void Valider() {
        boolean erreur = false;
        if (!ContenuMsg.getText().isEmpty() && !TexteMsg.getText().isEmpty() && !TitreMsg.getText().isEmpty()) {
            MsgBrut MsgBrutNew = null;
            if (TypeMsg.getValue() == TypeMessage.MsgTexte){
                MsgBrutNew = new MsgTexte(ContenuMsg.getText(), MacroMsg.getValue());
            } else if (TypeMsg.getValue() == TypeMessage.MsgTexteFormatted){
                MsgBrutNew = new MsgTexteFormatted(ContenuMsg.getText(), MacroMsg.getValue());
            } else if (TypeMsg.getValue() == TypeMessage.MsgImage){
                MsgBrutNew = new MsgImage(ContenuMsg.getText(), MacroMsg.getValue());
            } else if (TypeMsg.getValue() == TypeMessage.MsgBrut){
                MsgBrutNew = new MsgBrut(ContenuMsg.getText(), MacroMsg.getValue());
            }

            MesBoutons next = null;
            if (BoutonSuivMsg.getValue().getClass().getSimpleName().equals("MesBoutons")) {
                next = (MesBoutons) BoutonSuivMsg.getValue();
            }

            String nextid = null;
            if (next != null) {
                nextid = next.getDescription();
            }

            if (monBtn != null) {
                monBtn.setDescription(TitreMsg.getText());
                assert next != null;
                monBtn.setProchaineActionId(nextid);
                monBtn.setActioneds(ActionEDSMsg.getValue());
                monBtn.setTexteDuBouton(TexteMsg.getText());
                monBtn.setMessage(MsgBrutNew);
            } else {
                if (HelloApplication.myParser.getButtonByDesc(TitreMsg.getText()) == null) {
                    MesBoutons monBtn = new MesBoutons(
                            TexteMsg.getText(),
                            monOnglet,
                            ActionEDSMsg.getValue(),
                            nextid,
                            MsgBrutNew,
                            TitreMsg.getText()
                    );
                    this.maSection.AddButton(monBtn);
                } else {
                    HelloApplication.CreateAlertBox("Un bouton possède la même description. Merci d'en mettre une autre");
                    erreur = true;
                }
            }

            if(!erreur) {
                if (helloControler != null) {
                    HelloApplication.myParser.SaveFile();
                    this.helloControler.MiseEnPage();
                } else if (modifBouton != null) {
                    modifBouton.ListeBouton.getItems().clear();
                    modifBouton.ListeBouton.getItems().addAll(this.maSection.getButtons());
                }
                CloseWindow();
            }
        } else {
            HelloApplication.CreateAlertBox("Merci de remplir tous les champs");
        }


    }

    @FXML
    public void Annuler() {
        CloseWindow();
    }

    public void setmonBtn(MesBoutons btn) {
        this.monBtn = btn;
        ActionEDSMsg.getItems().addAll(HelloApplication.myParser.getListeDesActions());
        TypeMsg.getItems().addAll(TypeMessage.values());
        MacroMsg.getItems().addAll(NomMacro.values());
        BoutonSuivMsg.getItems().addAll(HelloApplication.myParser.getListeBouton());
        BoutonSuivMsg.getItems().add("Aucun");
        BoutonSuivMsg.getSelectionModel().select("Aucun");

        ActionEDSMsg.getSelectionModel().select(0);
        TypeMsg.getSelectionModel().select(0);
        MacroMsg.getSelectionModel().select(NomMacro.Aucune);
        if (btn != null){
            ContenuMsg.setText(this.monBtn.getMessage().getDescription());
            TexteMsg.setText(this.monBtn.getTexteDuBouton());
            TitreMsg.setText(this.monBtn.getDescription());
            ActionEDSMsg.getSelectionModel().select(this.monBtn.getActioneds());
            TypeMsg.getSelectionModel().select(TypeMessage.getTypeByName(this.monBtn.getMessage().getClass().getSimpleName()));
            MacroMsg.getSelectionModel().select(this.monBtn.getMessage().getMacro());
            if (this.monBtn.getProchaineAction() != null) {
                BoutonSuivMsg.getSelectionModel().select(this.monBtn.getProchaineAction());
            }
        }
    }

    public void setmaSection(Section grp) {
        this.maSection = grp;
    }

    public void setmonOnglet(Onglet ong) {
        this.monOnglet = ong;
    }

    private void CloseWindow() {
        Stage stage = (Stage) ContenuMsg.getScene().getWindow();
        stage.close();
    }

    public void setHelloControler(HelloController helloControler) {
        this.helloControler = helloControler;
    }

    public void setModifBouton(ModifBouton modifBouton) {
        this.modifBouton = modifBouton;
    }
}
