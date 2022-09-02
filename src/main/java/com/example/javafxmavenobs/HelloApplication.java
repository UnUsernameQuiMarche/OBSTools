package com.example.javafxmavenobs;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class HelloApplication extends Application {
    public static Parser myParser;
    public static String appName;
    public static String appVersion;
    public static String appAuthor;
    public static String appDescription;
    public static String url_version;

    public static Date enddate;


    @Override
    public void start(Stage stage) throws IOException {
        readinfo();
        if (CheckVersion()){
        //if (true) {
            //CreateAlertBox((enddate.getTime() - new Date().getTime()) / (1000 * 60 * 60 * 24) + " jours restants avant la fin de la version d'essai");
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle(appName);
            stage.getIcons().add(new Image("/icon_orange.png"));
            stage.setScene(scene);
            stage.show();
        } else {
            CreateAlertBox("Merci de mettre l'application à jour");
        }
    }

    public static void main(String[] args) {
        launch();
    }

    public static void CreateAlertBox(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void readinfo() {
        try {
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("info.json");
            JSONParser jsonParser = new JSONParser();
            assert inputStream != null;
            JSONObject jsonObject = (JSONObject) jsonParser.parse(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            appName = jsonObject.get("name").toString();
            appVersion = jsonObject.get("version").toString();
            appDescription = jsonObject.get("description").toString();
            url_version = jsonObject.get("url_version").toString();
            JSONObject author = (JSONObject) jsonObject.get("author");
            appAuthor = author.get("name").toString();
            enddate = new SimpleDateFormat("dd/MM/yyyy").parse(jsonObject.get("end_date").toString());
        } catch (Exception e) {
            CreateAlertBox("Erreur lors du chargement de l'application");
        }
    }

    private boolean CheckVersion(){
        return enddate.after(new Date());
        /*
        try {
            URL lien = new URL(url_version);
            Scanner s = new Scanner(lien.openStream());
            while (s.hasNextLine()) {
                String ligne = s.nextLine();
                if (ligne.contains(appVersion)){
                    return true;
                }
            }
        } catch (Exception e) {
            CreateAlertBox(e.toString());
        }
        return false;
         */
    }
}