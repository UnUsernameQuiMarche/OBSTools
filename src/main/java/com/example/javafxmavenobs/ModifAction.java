package com.example.javafxmavenobs;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Objects;

public class ModifAction {

    @FXML
    private ChoiceBox<ActionEDS> ChoiceEDS;
    @FXML
    private TextField txtDesc;
    @FXML
    private TextField txtMsg;
    @FXML
    private TextField txtBalise;
    @FXML
    private TextField txtRetard;
    private HelloController helloControler;

    public void initialize() {
        ChoiceEDS.getItems().add(new ActionEDS("", "", 0, "(Nouvelle Action EDS)"));
        ChoiceEDS.getItems().addAll(HelloApplication.myParser.getListeDesActions());

        ChoiceEDS.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (!Objects.equals(newValue.getTitre(), "(Nouvelle Action EDS)")) {
                    txtDesc.setText(newValue.getDescription());
                    txtMsg.setText(newValue.getTitre());
                    txtBalise.setText(newValue.getBalise());
                    txtRetard.setText(String.valueOf(newValue.getRetard()));
                } else {
                    txtDesc.setText("");
                    txtMsg.setText("");
                    txtBalise.setText("");
                    txtRetard.setText("");
                }
            }
        });

        txtRetard.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtRetard.setText(newValue.replaceAll("\\D", ""));
            }
        });

        ChoiceEDS.getSelectionModel().select(0);
    }

    @FXML
    protected void BtnSave() {
        try {
            save();
        } catch (Exception e) {
            HelloApplication.CreateAlertBox(e.getMessage());
        }
    }

    private void save() throws Exception {
        // Si l'un des champs est vide
        if (txtDesc.getText().isEmpty() || txtMsg.getText().isEmpty() || txtRetard.getText().isEmpty() || ChoiceEDS.getSelectionModel().getSelectedItem() == null || Objects.equals(txtMsg.getText(), "(Nouvelle Action EDS)")) {
            throw new Exception("Veuillez remplir tous les champs");
        } else {
            // Si l'action est une nouvelle action
            if (ChoiceEDS.getSelectionModel().getSelectedItem().getTitre().equals("(Nouvelle Action EDS)")) {
                if (HelloApplication.myParser.ActionEDSExist(txtMsg.getText())) {
                    throw new Exception("L'action EDS existe déjà");
                } else {
                    // On crée une nouvelle action
                    ActionEDS newAction = new ActionEDS(txtDesc.getText(), txtBalise.getText(), Integer.parseInt(txtRetard.getText()), txtMsg.getText());
                    // On l'ajoute à la liste des actions
                    HelloApplication.myParser.getListeDesActions().add(newAction);
                }

            } else {
                // On modifie l'action
                ActionEDS selectedAction = ChoiceEDS.getSelectionModel().getSelectedItem();
                selectedAction.setDescription(txtDesc.getText());
                selectedAction.setTitre(txtMsg.getText());
                selectedAction.setBalise(txtBalise.getText());
                selectedAction.setRetard(Integer.parseInt(txtRetard.getText()));
            }
            // On vide ChoiceEDS
            ChoiceEDS.getItems().clear();
            ChoiceEDS.getItems().add(new ActionEDS("", "", 0, "(Nouvelle Action EDS)"));
            ChoiceEDS.getItems().addAll(HelloApplication.myParser.getListeDesActions());
            ChoiceEDS.getSelectionModel().select(0);
        }
    }

    @FXML
    protected void BtnSup() {
        if (ChoiceEDS.getSelectionModel().getSelectedItem().getTitre().equals("(Nouvelle Action EDS)")) {
            txtDesc.setText("");
            txtMsg.setText("");
            txtBalise.setText("");
            txtRetard.setText("");
        } else {
            if (HelloApplication.myParser.ActionEDSUsed(ChoiceEDS.getSelectionModel().getSelectedItem())) {
                HelloApplication.CreateAlertBox("Cette action est utilisée dans un des scripts, il est donc impossible de la supprimer");
            } else {
                HelloApplication.myParser.getListeDesActions().remove(ChoiceEDS.getSelectionModel().getSelectedItem());
                ChoiceEDS.getItems().clear();
                ChoiceEDS.getItems().add(new ActionEDS("", "", 0, "(Nouvelle Action EDS)"));
                ChoiceEDS.getItems().addAll(HelloApplication.myParser.getListeDesActions());
                ChoiceEDS.getSelectionModel().select(0);
            }
        }
    }

    @FXML
    private void BtnValider() {
        if (txtDesc.getText().isEmpty() && txtMsg.getText().isEmpty() && txtBalise.getText().isEmpty() && txtRetard.getText().isEmpty() && Objects.equals(ChoiceEDS.getSelectionModel().getSelectedItem().getTitre(), "(Nouvelle Action EDS)")) {
            this.close(true);
        } else {
            try {
                save();
                this.close(true);
            } catch (Exception e) {
                HelloApplication.CreateAlertBox("Impossible de sauvegarder l'action");
            }
        }
    }


    @FXML
    private void BtnAnnuler() {
        this.close(false);
    }

    private void close(boolean save) {
        if (save) {
            HelloApplication.myParser.SaveFile();
        }
        try {
            HelloApplication.myParser = new Parser();
        } catch (Exception e) {
            HelloApplication.CreateAlertBox("Erreur lors de la fermeture de la fenêtre");
        }
        if (save) {
            this.helloControler.UpdateChoiceMsg();
        }
        // Fermer la fenêtre courante
        Stage stage = (Stage) txtMsg.getScene().getWindow();
        stage.close();
    }

    public void setHelloController(HelloController hello) {
        this.helloControler = hello;
    }
}
