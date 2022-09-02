package com.example.javafxmavenobs;

import org.json.simple.JSONObject;

import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class MsgBrut extends ElementInsertion {
    protected NomMacro Macro;

    MsgBrut(JSONObject obj) {
        super(obj.get("contenu").toString());

        this.Macro = NomMacro.getMacroByName((String) obj.get("macro"));
    }

    MsgBrut(String contenu, NomMacro macro) {
        super(contenu);

        this.Macro = macro;
    }

    public void insert() throws Exception {
        Robot robot = new Robot();
        String temp = getClipboard();

        robot.delay(300);

        setClipboard(this.getDescription());
        ChangementOnglet(robot);
        // Ajouter les actions AVANT
        Macro(robot, true);
        Coller(robot);
        // Ajouter les actions APRES
        Macro(robot, false);

        robot.delay(300);

        setClipboard(temp);
    }

    protected void Macro(Robot robot, boolean avant) throws Exception {
        if (this.Macro != null) {
            Formulaire f = null;
            if (this.Macro == NomMacro.T5) {
                f = new FormulaireT5();
            } else if (this.Macro == NomMacro.A2) {
                f = new FormulaireA2();
            } else if (this.Macro == NomMacro.A9) {
                f = new FormulaireA9();
            }
            if (f != null) {
                if (avant) {
                    f.avant(robot);
                } else {
                    f.apres(robot);
                }
            }
        }
    }
    protected void Coller(Robot robot) throws Exception {
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_V);
    }

    public NomMacro getMacro() {
        return Macro;
    }

    protected void ChangementOnglet(Robot robot) throws InterruptedException {
        robot.keyPress(KeyEvent.VK_ALT);
        robot.keyPress(KeyEvent.VK_TAB);
        robot.keyRelease(KeyEvent.VK_ALT);
        robot.keyRelease(KeyEvent.VK_TAB);

        Thread.sleep(300);
    }

    protected String getClipboard() throws IOException, UnsupportedFlavorException {
        String result = "";
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable contents = clipboard.getContents(null);
        boolean hasTransferableText = (contents != null) && contents.isDataFlavorSupported(DataFlavor.stringFlavor);
        if (hasTransferableText) {
            result = (String) contents.getTransferData(DataFlavor.stringFlavor);
        }
        return result;
    }

    protected void setClipboard(String str) {
        StringSelection selection = new StringSelection(str);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
    }

    public String getColor() {
        return "#000000";
    }




    public void setMacro(NomMacro macro) {
        Macro = macro;
    }
}
