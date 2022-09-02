package com.example.javafxmavenobs;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.KeyEvent;

public class FormulaireV2 {

    public FormulaireV2() {
    }

    public void RemplirDirectement() throws Exception {
    }


    String getClipboard() throws Exception {
        String result = "";
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable contents = clipboard.getContents(null);
        boolean hasTransferableText = (contents != null) && contents.isDataFlavorSupported(DataFlavor.stringFlavor);
        if (hasTransferableText) {
            result = (String) contents.getTransferData(DataFlavor.stringFlavor);
        }
        return result;
    }

    void setClipboard(String str) {
        StringSelection selection = new StringSelection(str);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
    }

    void ChangementOnglet(Robot robot) throws InterruptedException {
        robot.keyPress(KeyEvent.VK_ALT);
        robot.keyPress(KeyEvent.VK_TAB);
        robot.keyRelease(KeyEvent.VK_ALT);
        robot.keyRelease(KeyEvent.VK_TAB);

        robot.delay(300);
    }

    public void avant(Robot robot) throws Exception {
    }

    public void apres(Robot robot) throws Exception {
    }
}
