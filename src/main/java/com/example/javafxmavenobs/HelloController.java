package com.example.javafxmavenobs;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class HelloController {

    @FXML
    private ChoiceBox<ActionEDS> ChoiceMsg;
    @FXML
    private Tab TabAutre;
    @FXML
    private TabPane Onglets;

    private List<MesBoutons> ListeDesBoutons;


    public void initialize() {
        try {
            // Création de l'objet Parser
            HelloApplication.myParser = new Parser();

            this.MiseEnPage();

            SingleSelectionModel<Tab> selectionModel;
            selectionModel = Onglets.getSelectionModel();
            selectionModel.select(0);

            // Initialisation de la liste des boutons
            //this.ListeDesBoutons = Arrays.asList(T51, T52, T53, T54, T55, T56, T57, T58, T59, T510, T511, Com1, Com2, Com3, Com4, Com5, Com6, Com7, Com8, Com9, Com10, Com11, Com12, Com13, A21, A22, A23, A24, A25, A26, A27, A28, FormatT5, FormatA2);

        } catch (Exception e) {
            HelloApplication.CreateAlertBox("Nous n'avons pas pu charger le fichier");
        }

        // Initialisation des différents messages d'ActionEDS dans le sélecteur
        ChoiceMsg.getItems().setAll(HelloApplication.myParser.getListeDesActions());
        // On se place sur le premier message dans le sélecteur
        ChoiceMsg.getSelectionModel().select(0);
    }

    public void UpdateChoiceMsg() {
        ChoiceMsg.getItems().setAll(HelloApplication.myParser.getListeDesActions());
        ChoiceMsg.getSelectionModel().select(0);
    }

    @FXML
    protected void ActionEnCours(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            try {
                // Récupération et insertion de l'ActionEDS correspondant à l'élément du sélecteur
                ChoiceMsg.getValue().insert();

                // On se remet sur le premier onglet
                // On se remet sur le premier onglet
                SingleSelectionModel<Tab> selectionModel = Onglets.getSelectionModel();
                selectionModel.select(0);
            } catch (Exception e) {
                HelloApplication.CreateAlertBox("Le texte n'a pas pu être inséré");
            }
        } else {
            // OuvrirFenetreEdition(ChoiceMsg.getValue());
        }
    }

    @FXML
    protected void ChangeTab(){
        try {
            // On déverrouille tous les boutons
            this.EnableAllButtons();
        } catch (Exception e){
            // TODO Auto-generated catch block
        }
    }

    private void EnableAllButtons(){
        for(MesBoutons btn : this.ListeDesBoutons){
            btn.getBouton().setDisable(false);
        }
    }

    private void DisableAllButtons(Onglet o){
        for (Section g : o.getSection()){
            for (MesBoutons btn : g.getButtons()){
                btn.getBouton().setDisable(true);
            }
        }
        for(MesBoutons btn : this.ListeDesBoutons){
            btn.getBouton().setDisable(true);
        }
    }

    @FXML
    protected void btnFormatageT5(){
        Formulaire f = new FormulaireT5();
        try{
            f.RemplirDirectement();
        } catch (Exception e){
            HelloApplication.CreateAlertBox("Erreur d'éxécution");
        }
    }

    @FXML
    protected void btnFormatageA2() {
        Formulaire f = new FormulaireA2();
        try{
            f.RemplirDirectement();
        } catch (Exception e){
            HelloApplication.CreateAlertBox("Erreur d'execution");
        }
    }

    private void OuvrirFenetreEdition(MesBoutons btn) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("text_editor.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(btn.getDescription());
            stage.getIcons().add(new Image("/icon_orange.png"));
            stage.setScene(new Scene(root1));

            Object controller = fxmlLoader.getController();
            ((textEditor) controller).setmonBtn(btn);
            ((textEditor) controller).setHelloControler(this);

            stage.show();
        } catch (Exception e) {
            HelloApplication.CreateAlertBox("Impossible de modifier ce message");
        }
    }

    @FXML
    protected void RestaurationMsg() throws Exception {
        File f = new File("./perso.json");
        if (f.exists()) {
            if (f.delete()) {
                HelloApplication.myParser = new Parser();
                this.MiseEnPage();
                HelloApplication.CreateAlertBox("Restauration réussie");
            } else {
                HelloApplication.CreateAlertBox("Restauration impossible, redémarrez l'application et réessayez");
            }
        } else {
            HelloApplication.CreateAlertBox("La configuration actuelle est déjà celle par défaut");
        }
    }

    @FXML
    protected void About(){
        HelloApplication.CreateAlertBox(HelloApplication.appName + " - version " + HelloApplication.appVersion + "\n\nCette application a été créée par " + HelloApplication.appAuthor);
    }

    @FXML
    protected void Close() {
        Stage stage = (Stage) ChoiceMsg.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void ModifBouton(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("personaliser.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Modifier les boutons");
            stage.getIcons().add(new Image("/icon_orange.png"));
            stage.setScene(new Scene(root1));

            Object controller = fxmlLoader.getController();
            ((ModifBouton) controller).setHelloController(this);

            stage.show();
        } catch (Exception e) {
            HelloApplication.CreateAlertBox("Impossible de modifier les messages");
        }
    }

    protected void MiseEnPage(){
        int nbOnglets = Onglets.getTabs().size();
        if (nbOnglets > 1) {
            Onglets.getTabs().subList(0, nbOnglets - 1).clear();
        }

        List<Onglet> listeOnglets = HelloApplication.myParser.getListeDesOnglets();
        List<MesBoutons> listeBoutons = new ArrayList<>();

        // On ajoute les onglets a Onglets
        for(Onglet o : listeOnglets){
            Tab tab = new Tab(o.getDescription());
            tab.closableProperty().setValue(false);
            tab.setOnSelectionChanged(event -> ChangeTab());
            o.setTab(tab);

            // On insert une VBox dans la tab
            VBox vbox = new VBox();
            vbox.setPadding(new Insets(20, 20, 20, 20));
            vbox.setSpacing(10);
            tab.setContent(vbox);
            vbox.setAlignment(Pos.CENTER);
            // On ajoute des labels dans la VBox pour chacun des groupes de l'onglet
            for(Section g : o.getSection()){
                Label label = new Label(g.getDescription());
                vbox.getChildren().add(label);

                //On crée des boutons du groupe dans la VBox on les insere dans un GridPane de 3 colonnes
                GridPane grid = new GridPane();
                grid.setVgap(10);
                grid.setHgap(10);
                grid.setAlignment(Pos.CENTER);
                vbox.getChildren().add(grid);

                // On ajoute les boutons dans le GridPane
                for(int i = 0; i < g.getButtons().size(); i++){
                    MesBoutons b = g.getButtons().get(i);
                    Button button = new Button(b.getTexteDuBouton());
                    b.setBouton(button);
                    listeBoutons.add(b);
                    button.setPrefWidth(1000);
                    button.setId(b.getDescription());
                    button.setStyle("-fx-text-fill: "+ b.getMessage().getColor() + ";");
                    // On ajoute un tooltip pour chaque bouton
                    Tooltip tooltip = new Tooltip(b.getMessage().getDescription());
                    button.setTooltip(tooltip);

                    button.setOnMouseReleased(event ->
                    {
                        if (event.getButton() == MouseButton.PRIMARY)
                        {
                            this.ClickGauche(b);
                        } else if (event.getButton() == MouseButton.SECONDARY)
                            this.ClickDroit(b);
                        }
                    );

                    grid.add(button, i % 3, i / 3);
                }

                // On ajoute un separate sauf pour le dernier groupe
                if(g != o.getSection().get(o.getSection().size()-1)){
                    Separator separator = new Separator();
                    separator.setOrientation(Orientation.HORIZONTAL);
                    vbox.getChildren().add(separator);
                }
            }



            Onglets.getTabs().add(Onglets.getTabs().size() - 1, tab);

        }

        this.ListeDesBoutons = listeBoutons;
        for (MesBoutons btn : listeBoutons) {
            btn.setProchaineAction(HelloApplication.myParser.getButtonByDesc(btn.getProchaineActionId()));
        }
        // On se place sur le premier onglet
        Onglets.getSelectionModel().select(0);
    }

    private void ClickGauche(MesBoutons btn){
        try{
            // On effectue l'action correspondante au bouton
            btn.getMessage().insert();
            // On place le sélecteur sur le texte correspondant à l'action
            ChoiceMsg.getSelectionModel().select(btn.getActioneds());

            SingleSelectionModel<Tab> selectionModel;
            selectionModel = Onglets.getSelectionModel();
            if (btn.getProchaineActionId() != null) {
                selectionModel.select(btn.getProchaineAction().getOnglet().getTab());
                DisableAllButtons(btn.getProchaineAction().getOnglet());

                Button b = btn.getProchaineAction().getBouton();
                if (b != null) {
                    b.setDisable(false);
                }
            } else {
                // On se place sur l'onglet pour mettre l'ActionEDS
                selectionModel = Onglets.getSelectionModel();
                selectionModel.select(TabAutre);
            }

        }catch (Exception e){
            HelloApplication.CreateAlertBox("Le texte n'a pas pu être inséré");
        }
    }

    private void ClickDroit(MesBoutons btn){
        OuvrirFenetreEdition(btn);
    }

    /*
    @FXML
    private void btnAppeler(){
        Formulaire f = new FormulaireAppeler();
        try {
            f.RemplirDirectement();
        }
        catch (Exception e){
            HelloApplication.CreateAlertBox("Impossible de remplir le formulaire");
        }
    }
    */

    @FXML
    protected void ModifAction() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AcEDSeditor.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Modifier les actions");
            stage.getIcons().add(new Image("/icon_orange.png"));
            stage.setScene(new Scene(root1));

            Object controller = fxmlLoader.getController();
            ((ModifAction) controller).setHelloController(this);


            stage.show();
        } catch (Exception e) {
            HelloApplication.CreateAlertBox("Impossible de modifier les actions");
        }
    }
}