package com.example.javafxmavenobs;

import java.awt.*;

public class FormulaireA9 extends Formulaire{

    public FormulaireA9() {
    }

    @Override
    public void RemplirDirectement() throws Exception {
    }

    @Override
    public void avant(Robot robot) throws Exception{
        Tab(2, robot);

        Down(4, robot);

        Tab(1, robot);

        robot.delay(300);
    }
}
