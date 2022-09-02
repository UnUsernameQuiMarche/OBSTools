package com.example.javafxmavenobs;

import java.awt.*;
import java.awt.event.KeyEvent;

public class FormulaireAppeler extends Formulaire{
    public FormulaireAppeler() {

    }

    @Override
    public void RemplirDirectement() throws Exception {
        Robot robot = new Robot();

        ChangementOnglet(robot);

        avant(robot);
    }

    @Override
    public void avant(Robot robot) throws Exception {
        // Get clipboard contents
        String NumTel = this.getClipboard();
        // Get only the digits
        NumTel = NumTel.replaceAll("\\D", "");
        // Si le numéro de téléphone est vide, on ne fait rien
        if (NumTel.isEmpty()) {
            return;
        }
        // Si le numéro ne commence pas par un 0 (par un indicatif téléphonique)
        if (!NumTel.startsWith("0")) {
            // On ajoute 00 devant le numéro de téléphone
            NumTel = "00" + NumTel;
        }
        // On ajoute le 0 pour signifier à softphone que c'est un appel externe
        NumTel = "0" + NumTel;

        // On maintient SHIFT pour écrire des nombres
        robot.keyPress(KeyEvent.VK_SHIFT);
        // Robot écrit les lettres un par un de NumTel
        for (int i = 0; i < NumTel.length(); i++) {
            robot.keyPress(NumTel.charAt(i));
            robot.keyRelease(NumTel.charAt(i));
            robot.delay(100);
        }
        // On relache SHIFT
        robot.keyRelease(KeyEvent.VK_SHIFT);


    }
}
