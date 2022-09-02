package com.example.javafxmavenobs;

import java.awt.*;
import java.awt.event.KeyEvent;

public class template extends Formulaire {

    public template() {
    }

    @Override
    public void avant(Robot robot) throws Exception {
        robot.keyPress(KeyEvent.VK_A); // Maintien la touche "A" enfoncée
        robot.keyRelease(KeyEvent.VK_A); // Relache la touche "A"
        robot.delay(300); // Permet une pause de 300ms
    }

    @Override
    public void apres(Robot robot) throws Exception {
    }

}
