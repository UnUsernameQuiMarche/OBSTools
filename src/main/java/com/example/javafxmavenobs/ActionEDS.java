package com.example.javafxmavenobs;

import org.json.simple.JSONObject;

import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class ActionEDS extends ElementInsertion {

    private int retard_action;
    private String balise;

    private String titre;

    public ActionEDS(JSONObject obj) {
        super(obj.get("contenu").toString());
        this.titre = obj.get("titre").toString();
        this.retard_action = Integer.parseInt(obj.get("duree").toString());
        this.balise = obj.get("balise").toString();
    }

    public ActionEDS(String ma_desc, String ma_balise, int retard, String mon_titre){
        super(ma_desc);
        balise = ma_balise;
        retard_action = retard;
        titre = mon_titre;
    }

    private String getMessage() {
        LocalDateTime calendar = LocalDateTime.now().plusHours(retard_action);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM HH:mm");

        if (retard_action > 0) {
            if (calendar.getHour() > 17) {
                calendar = calendar.plusDays(1).withHour(8).withMinute(0);
            } else if (calendar.getHour() < 8) {
                calendar = calendar.withHour(8).withMinute(0);
            }

            if (calendar.getDayOfWeek().getValue() > 5) {
                calendar = calendar.plusDays(8 - calendar.getDayOfWeek().getValue());
            }
        }

        if (Objects.equals(this.balise, "")) {
            return getDescription() + " " + formatter.format(calendar);
        } else {
            return "<" + balise + ">" + getDescription() + " " + formatter.format(calendar) + "</" + balise + ">";
        }
    }

    public void insert() throws Exception {
        Robot robot = new Robot();

        setClipboard(getMessage());

        ChangementOnglet(robot);

        Coller(robot);
    }

    void Coller(Robot robot) throws Exception {
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_V);
    }

    void ChangementOnglet(Robot robot) throws InterruptedException {
        robot.keyPress(KeyEvent.VK_ALT);
        robot.keyPress(KeyEvent.VK_TAB);
        robot.keyRelease(KeyEvent.VK_ALT);
        robot.keyRelease(KeyEvent.VK_TAB);

        Thread.sleep(300);
    }

    String getClipboard() throws IOException, UnsupportedFlavorException {
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

    @Override
    public String toString() {
        return getTitre();
    }

    protected JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        //obj.put("id", this.id);
        obj.put("contenu", this.getDescription());
        obj.put("duree", this.retard_action);
        obj.put("balise", this.balise);
        obj.put("titre", this.getTitre());

        return obj;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public int getRetard() {
        return retard_action;
    }

    public String getBalise() {
        return balise;
    }

    public void setBalise(String balise) {
        this.balise = balise;
    }

    public void setRetard(int retard) {
        this.retard_action = retard;
    }
}
