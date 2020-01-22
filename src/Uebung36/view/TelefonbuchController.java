package Uebung36.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import Uebung36.datenbank.beans.TelefonbuchEintragBean;
import Uebung36.model.TelefonbuchEintrag;
import Uebung36.printing.Drucken;
import Uebung36.printing.TelefonbuchAusgabe;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.awt.*;
import java.util.ArrayList;
import java.util.Optional;

public class TelefonbuchController {

    @FXML
    private TextField tfVorname;

    @FXML
    private TextField tfNachname;

    @FXML
    private TextField tfStrasse;

    @FXML
    private TextField tfHausNr;

    @FXML
    private TextField tfPlz;

    @FXML
    private TextField tfOrt;

    @FXML
    private TextField tfTelefon;

    @FXML
    private Button btnVorherigerEintrag;

    @FXML
    private Button btnNaechsterEintrag;

    private ArrayList<TelefonbuchEintrag> telefonbuch;
    private int angezeigterEintrag;

    @FXML
    void loeschenEintrag() {
        // Löschbestätigung abfragen
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Löschen bestätigen");
        alert.setHeaderText(null);
        alert.setContentText("Möchten Sie den aktuellen Telefonbucheintrag wirklich löschen?");
        Optional<ButtonType> op = alert.showAndWait();

        // Es soll nur gelöscht werden, wenn der Benutzer "Ok" angeklickt hat
        if (op.isPresent() && op.get() == ButtonType.OK) {

            // Aktuellen Eintrag herausfinden
            TelefonbuchEintrag zuLoeschen = telefonbuch.get(angezeigterEintrag);

            // Eintrag aus der Datenbank löschen
            if (!TelefonbuchEintragBean.delete(zuLoeschen)) {
                // Fehlermeldung ausgeben
                alertAnzeigen("Fehler beim Löschen", "Der aktuelle Eintrag konnte aus der Datenbank nicht gelöscht werden.");

                // Löschen abbrechen
                return;
            }

            // Eintrag entfernen
            telefonbuch.remove(zuLoeschen);

            // Wenn der letzte Eintrag im Telefonbuch gelöscht wird, muss ein neuer leerer Eintrag generiert werden
            if (telefonbuch.size() == 0) {
                telefonbuch.add(new TelefonbuchEintrag());
            }

            // Zeiger korrigieren, wenn der in der Reihenfolge letzte Eintrag im Telefonbuch entfernt wird
            if (angezeigterEintrag >= telefonbuch.size()) {
                angezeigterEintrag = telefonbuch.size() - 1;
            }

            // Aktuellen TelefonbuchEintrag anzeigen
            anzeigen();
        }
    }

    @FXML
    void naechsterEintrag() {
        try {
            // Alten Eintrag speichern
            eintragSpeichern();

            // Positionsindex verändern
            if (angezeigterEintrag < telefonbuch.size() - 1) {
                angezeigterEintrag++;
            }

            // Aktuellen Eintrag anzeigen
            anzeigen();
        } catch (IllegalArgumentException e) {
            alertAnzeigen("Ungültiger Eingabewert", e.getMessage());
        }
    }

    @FXML
    void neuerEintrag() {
        try {
            // Alten Eintrag speichern
            eintragSpeichern();

            // Neuen Eintrag am Ende des Telefonbuchs erzeugen
            telefonbuch.add(new TelefonbuchEintrag());

            // Angezeigt werden soll der letzte Eintrag. Denn dies ist der neue Eintrag
            angezeigterEintrag = telefonbuch.size()-1;

            // Aktuellen Eintrag anzeigen
            anzeigen();
        } catch (IllegalArgumentException e) {
            alertAnzeigen("Ungültiger Eingabewert", e.getMessage());
        }
    }

    @FXML
    void vorherigerEintrag() {
        try {
            // Alten Eintrag speichern
            eintragSpeichern();

            // Positionsindex verändern
            if (angezeigterEintrag > 0) {
                angezeigterEintrag--;
            }

            // Aktuellen Eintrag anzeigen
            anzeigen();
        } catch (IllegalArgumentException e) {
            alertAnzeigen("Ungültiger Eingabewert", e.getMessage());
        }
    }

    @FXML
    void druckeEintrag() {
        TelefonbuchEintrag aktEintrag = telefonbuch.get(angezeigterEintrag);
        String ausgabe = TelefonbuchAusgabe.ausgeben(aktEintrag);
        Drucken.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 11));
        Drucken.drucken(ausgabe);
    }

    @FXML
    void druckeTelefonbuch() {
        String ausgabe = TelefonbuchAusgabe.ausgeben(telefonbuch);
        Drucken.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 11));
        Drucken.drucken(ausgabe);
    }

    @FXML
    void initialize() {
        assert tfVorname != null : "fx:id=\"tfVorname\" was not injected: check your FXML file 'Telefonbuch.fxml'.";
        assert tfNachname != null : "fx:id=\"tfNachname\" was not injected: check your FXML file 'Telefonbuch.fxml'.";
        assert tfStrasse != null : "fx:id=\"tfStrasse\" was not injected: check your FXML file 'Telefonbuch.fxml'.";
        assert tfHausNr != null : "fx:id=\"tfHausNr\" was not injected: check your FXML file 'Telefonbuch.fxml'.";
        assert tfPlz != null : "fx:id=\"tfPlz\" was not injected: check your FXML file 'Telefonbuch.fxml'.";
        assert tfOrt != null : "fx:id=\"tfOrt\" was not injected: check your FXML file 'Telefonbuch.fxml'.";
        assert tfTelefon != null : "fx:id=\"tfTelefon\" was not injected: check your FXML file 'Telefonbuch.fxml'.";
        assert btnVorherigerEintrag != null : "fx:id=\"btnVorherigerEintrag\" was not injected: check your FXML file 'Telefonbuch.fxml'.";
        assert btnNaechsterEintrag != null : "fx:id=\"btnNaechsterEintrag\" was not injected: check your FXML file 'Telefonbuch.fxml'.";

        // Telefonbuch initialisieren und laden
        ladeTelefonbuch();
        angezeigterEintrag = 0;

        // Den aktuellen Eintrag anzeigen
        anzeigen();
    }

    /**
     * Initialisiert und läd das Telefonbuch
     */
    private void ladeTelefonbuch() {
        // Telefonbuch aus der Datenbank laden
        telefonbuch = TelefonbuchEintragBean.getTelefonbuch();

        // Sollte wider erwarten das uebungen.telefonbuch null sein, so wird es hier neu initialisiert
        if (telefonbuch == null) {
            telefonbuch = new ArrayList<>();

            // Testeinträge erzeugen
            telefonbuch.add(new TelefonbuchEintrag("Vorname", "Nachname", "str", "99", "12345", "Testort", "0921461230570"));
            telefonbuch.add(new TelefonbuchEintrag("Vorname1", "Nachname", "str", "99", "12345", "Testort", "0921461230571"));
            telefonbuch.add(new TelefonbuchEintrag("Vorname2", "Nachname", "str", "99", "12345", "Testort", "0921461230572"));
            telefonbuch.add(new TelefonbuchEintrag("Vorname3", "Nachname", "str", "99", "12345", "Testort", "0921461230573"));
        }

        // Wenn im Telefonbuch kein Eintrag vorhanden ist, muss für unsere Oberfläche ein leerer Eintrag generiert werden
        if (telefonbuch.size() == 0) {
            telefonbuch.add(new TelefonbuchEintrag());
        }
    }

    /**
     * Zeigt den aktuell in der internen Datenstruktur ausgewählten Wert in der Oberfläche an
     * Die Buttons nächsterEintrag und vorherigerEintrag werden je nach Position deaktiviert.
     */
    private void anzeigen() {
        // Anzuzeigender Eintrag heraussuchen
        TelefonbuchEintrag eintragZumAnzeigen = telefonbuch.get(angezeigterEintrag);

        // Eintrag in der Oberfläche anzeigen
        tfVorname.setText(eintragZumAnzeigen.getVorname());
        tfNachname.setText(eintragZumAnzeigen.getNachname());
        tfStrasse.setText(eintragZumAnzeigen.getStrasse());
        tfHausNr.setText(eintragZumAnzeigen.getHausNr());
        tfPlz.setText(eintragZumAnzeigen.getPlz());
        tfOrt.setText(eintragZumAnzeigen.getOrt());
        tfTelefon.setText(eintragZumAnzeigen.getTelefonNr());

        // Buttons: Vorheriger und Nächster Eintrag
        btnVorherigerEintrag.setDisable(angezeigterEintrag <= 0);
        btnNaechsterEintrag.setDisable(angezeigterEintrag >= telefonbuch.size()-1);
    }

    /**
     * Speichert den aktuell angezeigten Eintrag in der Internen Datenstruktur ab und auch ggf. in einer Datenbank.
     *
     *  @throws IllegalArgumentException werden geworfen, wenn beim Speichern ein Fehler aufgetreten ist
     */
    private void eintragSpeichern() {
        // Aktuelles "TelefonbuchEintrag"-Objekt holen
        TelefonbuchEintrag eintragZumSpeichern = telefonbuch.get(angezeigterEintrag);

        // Werte aus der Oberfläche auslesen und in den TelefonbuchEintrag schreiben
        eintragZumSpeichern.setVorname(tfVorname.getText());
        eintragZumSpeichern.setNachname(tfNachname.getText());
        eintragZumSpeichern.setStrasse(tfStrasse.getText());
        eintragZumSpeichern.setHausNr(tfHausNr.getText());
        eintragZumSpeichern.setPlz(tfPlz.getText());
        eintragZumSpeichern.setOrt(tfOrt.getText());
        eintragZumSpeichern.setTelefonNr(tfTelefon.getText());

        // Telefonnummer überprüfen - Diese darf im gesamten Telefonbuch nur einmal vorkommen
        for (TelefonbuchEintrag eintrag : telefonbuch) {
            if (eintragZumSpeichern != eintrag && eintragZumSpeichern.getTelefonNr().equals(eintrag.getTelefonNr())) {
                throw new IllegalArgumentException("Die Telefonnummer muss eindeutig sein.\nJede Nummer kann nur einmal benutzt werden.");
            }
        }

        // Eintrag in der Datenbank speichern
        if (!TelefonbuchEintragBean.save(eintragZumSpeichern)) {
            throw new IllegalArgumentException("Fehler beim Speichern des aktuellen Eintrages in der Datenbank.");
        }
    }

    /**
     * Zeigt ein Fehlerfenster mit dem übergebenen Titel und der übergebenen Nachricht an.
     *
     * @param title Titel des Fensters
     * @param message Mitteilung des Fensters
     */
    private void alertAnzeigen(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}