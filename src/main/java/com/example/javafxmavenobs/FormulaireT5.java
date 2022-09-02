package com.example.javafxmavenobs;

import java.awt.*;
import java.awt.event.KeyEvent;

public class FormulaireT5 extends Formulaire {

    public FormulaireT5() {
    }

    @Override
    public void RemplirDirectement() throws Exception {
        Robot robot = new Robot();

        ChangementOnglet(robot);

        apres(robot);
    }

    public void FormatageContact(Robot robot) throws Exception {
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_A);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_A);

        robot.delay(300);

        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_C);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_C);

        robot.delay(300);

        String[] texte = getClipboard().split(",");

        setClipboard(texte[0] + ',' + texte[texte.length - 1]);

        robot.delay(300);

        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_V);
    }

    @Override
    public void apres(Robot robot) throws Exception {

        Tab(1, robot);

        Down(4, robot);

        Tab(1, robot);

        robot.delay(300);

        FormatageContact(robot);
    }

    @Override
    public void avant(Robot robot) throws Exception {
        // Se placer sur le champ horaire
        // Tab(4, robot);

        // robot.delay(300);
    }


}
