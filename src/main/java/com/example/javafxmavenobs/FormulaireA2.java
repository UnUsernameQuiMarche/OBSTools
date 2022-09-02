package com.example.javafxmavenobs;

import java.awt.*;

public class FormulaireA2 extends Formulaire{

    public FormulaireA2() {
    }

    @Override
    public void RemplirDirectement() throws Exception {
        Robot robot = new Robot();

        ChangementOnglet(robot);

        avant(robot);
    }

    @Override
    public void avant(Robot robot) throws Exception{
        // Se placer sur le champ horaire
        // Tab(9, robot);
        Tab(1, robot);

        Down(3, robot);

        Tab(1, robot);

        Down(7, robot);

        Tab(2, robot);

        Down(4, robot);

        Tab(4, robot);

        robot.delay(300);
    }
}
