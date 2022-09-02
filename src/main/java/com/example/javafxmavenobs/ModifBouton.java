package com.example.javafxmavenobs;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;

public class ModifBouton {

    @FXML
    protected ListView<Onglet> ListeOnglet;
    @FXML
    protected ListView<MesBoutons> ListeBouton;
    @FXML
    protected ListView<Section> ListeSection;
    private HelloController helloControler;

    public void initialize() {
        // On ajoute tous les Onglets dans la ListeView
        ListeOnglet.getItems().addAll(HelloApplication.myParser.getListeDesOnglets());

        ListeOnglet.setCellFactory(lv -> {
            ListCell<Onglet> cell = new ListCell<>();
            ContextMenu contextMenu = new ContextMenu();
            MenuItem addItem = new MenuItem();
            MenuItem editItem = new MenuItem();
            MenuItem deleteItem = new MenuItem();
            MenuItem upItem = new MenuItem();
            MenuItem downItem = new MenuItem();
            SeparatorMenuItem sep = new SeparatorMenuItem();

            addItem.setOnAction(event -> {
                this.addOnglet();
            });

            editItem.setOnAction(event -> {
                this.EditerDescriptionOnglet(cell.getItem(), null);
            });

            upItem.setOnAction(event -> {
                // Permet de déplacer l'onglet d'un cran vers le haut
                Onglet onglet = cell.getItem();
                int index = ListeOnglet.getItems().indexOf(onglet);
                if (index > 0) {
                    HelloApplication.myParser.UpOnglet(onglet);
                    ListeOnglet.getItems().remove(index);
                    ListeOnglet.getItems().add(index - 1, onglet);
                    ListeOnglet.getSelectionModel().select(index - 1);
                    // Update listeSection
                    ListeSection.getItems().clear();
                    ListeBouton.getItems().clear();
                    ListeSection.getItems().addAll(cell.getItem().getSection());
                }
            });

            downItem.setOnAction(event -> {
                // Permet de déplacer l'onglet d'un cran vers le bas
                Onglet onglet = cell.getItem();
                int index = ListeOnglet.getItems().indexOf(onglet);
                if (index < ListeOnglet.getItems().size() - 1) {
                    HelloApplication.myParser.DownOnglet(onglet);
                    ListeOnglet.getItems().remove(index);
                    ListeOnglet.getItems().add(index + 1, onglet);
                    ListeOnglet.getSelectionModel().select(index + 1);

                    ListeSection.getItems().clear();
                    ListeBouton.getItems().clear();
                    ListeSection.getItems().addAll(cell.getItem().getSection());
                }
            });

            deleteItem.setOnAction(event -> {
                HelloApplication.myParser.DeleteOnglet(cell.getItem());
                ListeOnglet.getItems().remove(cell.getItem());
                ListeOnglet.getSelectionModel().clearSelection();
                ListeSection.getItems().clear();
                ListeBouton.getItems().clear();
            });

            setContextMenu(cell, contextMenu, addItem, editItem, deleteItem, upItem, downItem, sep);

            cell.setOnMouseReleased(event -> {
                if (!cell.isEmpty()) {
                    ListeSection.getItems().clear();
                    ListeBouton.getItems().clear();
                    ListeSection.getItems().addAll(cell.getItem().getSection());

                    ListeSection.setCellFactory(lv1 -> {
                        ListCell<Section> cell1 = new ListCell<>();
                        ContextMenu contextMenu1 = new ContextMenu();
                        MenuItem addItem1 = new MenuItem();
                        MenuItem editItem1 = new MenuItem();
                        MenuItem deleteItem1 = new MenuItem();
                        MenuItem upItem1 = new MenuItem();
                        MenuItem downItem1 = new MenuItem();
                        SeparatorMenuItem sep1 = new SeparatorMenuItem();

                        addItem1.setOnAction(event1 -> {
                            this.addSection();
                        });

                        editItem1.setOnAction(event1 -> {
                            this.EditerDescriptionSection(cell1.getItem(), null, null);
                        });

                        upItem1.setOnAction(event1 -> {
                            // Permet de déplacer le groupe d'un cran vers le haut
                            Section section = cell1.getItem();
                            int index = ListeSection.getItems().indexOf(section);
                            if (index > 0) {
                                cell.getItem().UpGroupe(section);
                                ListeSection.getItems().remove(index);
                                ListeSection.getItems().add(index - 1, section);
                                ListeSection.getSelectionModel().select(index - 1);

                                // Update listeBouton
                                ListeBouton.getItems().clear();
                                ListeBouton.getItems().addAll(cell1.getItem().getButtons());
                            }
                        });

                        downItem1.setOnAction(event1 -> {
                            // Permet de déplacer le groupe d'un cran vers le bas
                            Section section = cell1.getItem();
                            int index = ListeSection.getItems().indexOf(section);
                            if (index < ListeSection.getItems().size() - 1) {
                                cell.getItem().DownGroupe(section);
                                ListeSection.getItems().remove(index);
                                ListeSection.getItems().add(index + 1, section);
                                ListeSection.getSelectionModel().select(index + 1);

                                // Update listeBouton
                                ListeBouton.getItems().clear();
                                ListeBouton.getItems().addAll(cell1.getItem().getButtons());
                            }
                        });

                        deleteItem1.setOnAction(event1 -> {
                            cell.getItem().DeleteGroupe(cell1.getItem());
                            ListeSection.getItems().remove(cell1.getItem());
                            ListeSection.getSelectionModel().clearSelection();
                            ListeBouton.getItems().clear();
                        });

                        setContextMenu1(cell1, contextMenu1, addItem1, editItem1, deleteItem1, upItem1, downItem1, sep1);

                        cell1.setOnMouseReleased(event1 -> {
                            if (!cell1.isEmpty()) {
                                ListeBouton.getItems().clear();
                                ListeBouton.getItems().addAll(cell1.getItem().getButtons());
                                ListeBouton.setCellFactory(lv2 -> {
                                    ListCell<MesBoutons> cell2 = new ListCell<>();
                                    ContextMenu contextMenu2 = new ContextMenu();
                                    MenuItem addItem2 = new MenuItem();
                                    MenuItem editItem2 = new MenuItem();
                                    MenuItem deleteItem2 = new MenuItem();
                                    MenuItem upItem2 = new MenuItem();
                                    MenuItem downItem2 = new MenuItem();
                                    SeparatorMenuItem sep2 = new SeparatorMenuItem();

                                    addItem2.setOnAction(event2 -> {
                                        this.addBouton();
                                    });

                                    editItem2.setOnAction(event2 -> {
                                        OuvrirFenetreEdition(cell2.getItem(), cell1.getItem(), cell.getItem());
                                    });

                                    deleteItem2.setOnAction(event2 -> {
                                        cell1.getItem().DeleteButton(cell2.getItem());
                                        ListeBouton.getItems().remove(cell2.getItem());
                                        ListeBouton.getSelectionModel().clearSelection();
                                    });

                                    upItem2.setOnAction(event2 -> {
                                        // Permet de déplacer le MesButton d'un cran vers le haut
                                        MesBoutons button = cell2.getItem();
                                        int index = ListeBouton.getItems().indexOf(button);
                                        if (index > 0) {
                                            cell1.getItem().UpMesButton(button);
                                            ListeBouton.getItems().remove(index);
                                            ListeBouton.getItems().add(index - 1, button);
                                            ListeBouton.getSelectionModel().select(null);
                                        }
                                    });

                                    downItem2.setOnAction(event2 -> {
                                        // Permet de déplacer le MesButton d'un cran vers le bas
                                        MesBoutons button = cell2.getItem();
                                        int index = ListeBouton.getItems().indexOf(button);
                                        if (index < ListeBouton.getItems().size() - 1) {
                                            cell1.getItem().DownMesButton(button);
                                            ListeBouton.getItems().remove(index);
                                            ListeBouton.getItems().add(index + 1, button);
                                            ListeBouton.getSelectionModel().select(null);
                                        }
                                    });

                                    setContextMenu2(cell2, contextMenu2, addItem2, editItem2, deleteItem2, upItem2, downItem2, sep2);

                                    return cell2;
                                });
                            }
                        });
                        return cell1;
                    });
                }
            });
            return cell;
        });
    }

    @FXML
    public void BtnValider() {
        this.close(true);
    }

    @FXML
    public void BtnAnnuler() {
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
            this.helloControler.MiseEnPage();
        }
        // Fermer la fenetre courante
        Stage stage = (Stage) ListeOnglet.getScene().getWindow();
        stage.close();
    }

    public void setHelloController(HelloController hello) {
        this.helloControler = hello;
    }

    private void setContextMenu(ListCell<Onglet> cell, ContextMenu contextMenu, MenuItem addItem, MenuItem editItem, MenuItem deleteItem, MenuItem upItem, MenuItem downItem, SeparatorMenuItem sep) {
        addItem.textProperty().bind(Bindings.format("Ajouter un onglet"));
        editItem.textProperty().bind(Bindings.format("Modifier \"%s\"", cell.itemProperty()));
        deleteItem.textProperty().bind(Bindings.format("Supprimer \"%s\"", cell.itemProperty()));
        upItem.textProperty().bind(Bindings.format("Monter"));
        downItem.textProperty().bind(Bindings.format("Descendre"));
        contextMenu.getItems().addAll(addItem, editItem, deleteItem, sep, upItem, downItem);
        cell.textProperty().bind(Bindings.when(cell.itemProperty().isNull()).then("").otherwise(cell.itemProperty().asString()));
        cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
            if (isNowEmpty) {
                cell.setContextMenu(null);
            } else {
                cell.setContextMenu(contextMenu);
            }
        });
    }

    private void setContextMenu1(ListCell<Section> cell, ContextMenu contextMenu, MenuItem addItem, MenuItem editItem, MenuItem deleteItem, MenuItem upItem, MenuItem downItem, SeparatorMenuItem sep) {
        addItem.textProperty().bind(Bindings.format("Ajouter une section"));
        editItem.textProperty().bind(Bindings.format("Modifier \"%s\"", cell.itemProperty()));
        deleteItem.textProperty().bind(Bindings.format("Supprimer \"%s\"", cell.itemProperty()));
        upItem.textProperty().bind(Bindings.format("Monter"));
        downItem.textProperty().bind(Bindings.format("Descendre"));
        contextMenu.getItems().addAll(addItem, editItem, deleteItem, sep, upItem, downItem);
        cell.textProperty().bind(Bindings.when(cell.itemProperty().isNull()).then("").otherwise(cell.itemProperty().asString()));
        cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
            if (isNowEmpty) {
                cell.setContextMenu(null);
            } else {
                cell.setContextMenu(contextMenu);
            }
        });
    }

    private void setContextMenu2(ListCell<MesBoutons> cell, ContextMenu contextMenu, MenuItem addItem, MenuItem editItem, MenuItem deleteItem, MenuItem upItem, MenuItem downItem, SeparatorMenuItem sep) {
        addItem.textProperty().bind(Bindings.format("Ajouter un bouton"));
        editItem.textProperty().bind(Bindings.format("Modifier \"%s\"", cell.itemProperty()));
        deleteItem.textProperty().bind(Bindings.format("Supprimer \"%s\"", cell.itemProperty()));
        upItem.textProperty().bind(Bindings.format("Monter"));
        downItem.textProperty().bind(Bindings.format("Descendre"));
        contextMenu.getItems().addAll(addItem, editItem, deleteItem, sep, upItem, downItem);
        cell.textProperty().bind(Bindings.when(cell.itemProperty().isNull()).then("").otherwise(cell.itemProperty().asString()));
        cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
            if (isNowEmpty) {
                cell.setContextMenu(null);
            } else {
                cell.setContextMenu(contextMenu);
            }
        });
    }

    private void OuvrirFenetreEdition(MesBoutons btn, Section grp, Onglet ong) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("text_editor.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.getIcons().add(new Image("/icon_orange.png"));
            stage.setScene(new Scene(root1));

            Object controller = fxmlLoader.getController();

            if (btn != null){
                stage.setTitle(btn.getDescription());
            } else {
                stage.setTitle("Nouveau bouton");
            }
            ((textEditor) controller).setmonBtn(btn);
            ((textEditor) controller).setmaSection(grp);
            ((textEditor) controller).setmonOnglet(ong);

            ((textEditor) controller).setModifBouton(this);
            stage.show();
        } catch (Exception e) {
            HelloApplication.CreateAlertBox("Impossible de modifier ce message");
        }
    }

    private void EditerDescriptionOnglet(Onglet onglet, List<Onglet> ajouter) {
        // On creer une fenetre avec un champ de texte pour la description de l'onglet
        Stage stage = new Stage();

        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setAlignment(Pos.CENTER);
        Label label = new Label("Entrez le nom de l'onglet");
        TextField textField = new TextField();
        textField.setText(onglet.getDescription());
        textField.setPrefWidth(200);
        textField.setPrefHeight(30);
        Button button = new Button("Valider");
        button.setOnAction(event -> {
            if (textField.getText().length() > 0) {
                onglet.setDescription(textField.getText());
                if (ajouter != null) {
                    ajouter.add(onglet);
                }
                stage.close();
                // Supprimer tous les élements de ListeOnglet
                ListeOnglet.getItems().clear();
                ListeOnglet.getItems().addAll(HelloApplication.myParser.getListeDesOnglets());
                ListeSection.getItems().clear();
                ListeBouton.getItems().clear();
            } else {
                HelloApplication.CreateAlertBox("Le nom de l'onglet ne peut pas être vide");
            }
        });

        vbox.getChildren().addAll(label, textField, button);
        Scene scene = new Scene(vbox, 300, 100);
        stage.setScene(scene);
        stage.setTitle("Edition de l'onglet");
        stage.getIcons().add(new Image("/icon_orange.png"));
        stage.show();
    }

    private void EditerDescriptionSection(Section section, List<Section> ajouter, Onglet onglet) {
        // On creer une fenetre avec un champ de texte pour la description de l'onglet
        Stage stage = new Stage();

        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setAlignment(Pos.CENTER);
        Label label = new Label("Entrez le nom de l'onglet");
        TextField textField = new TextField();
        textField.setText(section.getDescription());
        textField.setPrefWidth(200);
        textField.setPrefHeight(30);
        Button button = new Button("Valider");
        button.setOnAction(event -> {
            if (textField.getText().length() > 0) {
                section.setDescription(textField.getText());
                if (ajouter != null) {
                    ajouter.add(section);
                }
                stage.close();
                ListeSection.getItems().clear();
                ListeSection.getItems().addAll(onglet.getSection());
                ListeBouton.getItems().clear();

            } else {
                HelloApplication.CreateAlertBox("Le nom de la section ne peut pas être vide");
            }
        });

        vbox.getChildren().addAll(label, textField, button);
        Scene scene = new Scene(vbox, 300, 100);
        stage.setScene(scene);
        stage.setTitle("Edition de l'onglet");
        stage.getIcons().add(new Image("/icon_orange.png"));
        stage.show();
    }

    @FXML
    protected void addOnglet() {
        this.EditerDescriptionOnglet(new Onglet(""), HelloApplication.myParser.getListeDesOnglets());
    }

    @FXML
    protected void addSection() {
        if (ListeOnglet.getSelectionModel().getSelectedItem() != null) {
            this.EditerDescriptionSection(new Section(""), ListeOnglet.getSelectionModel().getSelectedItem().getSection(), ListeOnglet.getSelectionModel().getSelectedItem());
        } else {
            HelloApplication.CreateAlertBox("Vous devez d'abord selectionner un onglet");
        }
    }

    @FXML
    protected void addBouton() {
        if(ListeSection.getSelectionModel().getSelectedItem() != null && ListeOnglet.getSelectionModel().getSelectedItem() != null) {
            OuvrirFenetreEdition(null, ListeSection.getSelectionModel().getSelectedItem(), ListeOnglet.getSelectionModel().getSelectedItem());
        } else {
            HelloApplication.CreateAlertBox("Vous devez d'abord selectionner une section");
        }
    }
}