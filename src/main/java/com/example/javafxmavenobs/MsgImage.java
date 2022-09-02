package com.example.javafxmavenobs;

import org.json.simple.JSONObject;

import java.awt.*;
import java.awt.event.KeyEvent;

public class MsgImage extends MsgBrut {

    MsgImage(JSONObject obj) {
        super(obj);
    }

    MsgImage(String contenu, NomMacro macro) {
        super(contenu, macro);
    }

    @Override
    public void insert() throws Exception {
        Robot robot = new Robot();

        ChangementOnglet(robot);

        // Ajouter les actions AVANT
        Macro(robot, true);

        Coller(robot);

        SauterLigneAvantImage(robot, 2);

        setClipboard(getDescription());

        Coller(robot);

        // Ajouter les actions APRES
        Macro(robot, false);
    }

    private void SauterLigneAvantImage(Robot robot, int nbLigne) throws Exception {
        Thread.sleep(800);

        for (int i = 0; i < 3; i++) {
            Thread.sleep(300);
            robot.keyPress(KeyEvent.VK_LEFT);
            robot.keyRelease(KeyEvent.VK_LEFT);
        }

        for (int i = 0; i < nbLigne; i++) {
            Thread.sleep(300);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            robot.keyPress(KeyEvent.VK_UP);
            robot.keyRelease(KeyEvent.VK_UP);
        }
    }

    @Override
    public String getColor() {
        return "#FF0000";
    }
}
