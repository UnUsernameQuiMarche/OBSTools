package com.example.javafxmavenobs;

import org.json.simple.JSONObject;

import java.awt.*;
import java.awt.event.KeyEvent;

public class MsgTexte extends MsgBrut {

    MsgTexte(JSONObject obj) {
        super(obj);
    }

    MsgTexte(String contenu, NomMacro macro) {
        super(contenu, macro);
    }

    @Override
    public void insert() throws Exception {
        Robot robot = new Robot();

        String clipboard = getClipboard();
        int n = CompterNombreDeLigne(clipboard);

        setClipboard(getDescription() + "\n\n" + clipboard);

        ChangementOnglet(robot);

        // Ajouter les actions AVANT
        Macro(robot, true);

        Coller(robot);

        MettreEnGras(robot, n);

        // Ajouter les actions APRES
        Macro(robot, false);

        robot.delay(300);

        setClipboard(clipboard);
    }

    private int CompterNombreDeLigne(String texte) {
        String[] lines = texte.split("\r\n|\r|\n");
        return lines.length;
    }

    private void MettreEnGras(Robot robot, int nbLigne) throws Exception {
        Thread.sleep(300);
        robot.keyPress(KeyEvent.VK_SHIFT);
        for (int i = 0; i < nbLigne; i++) {
            robot.keyPress(KeyEvent.VK_UP);
            robot.keyRelease(KeyEvent.VK_UP);
        }
        robot.keyRelease(KeyEvent.VK_SHIFT);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_B);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_B);
    }

    @Override
    public String getColor() {
        return "#0000FF";
    }

}
