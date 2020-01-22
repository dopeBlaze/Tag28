package Uebung36.datenbank.beans;

import Uebung36.datenbank.Datenbank;
import Uebung36.model.TelefonbuchEintrag;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class TelefonbuchEintragBean {

    private static PreparedStatement pstmtSelect;
    private static PreparedStatement pstmtInsert;
    private static PreparedStatement pstmtUpdate;
    private static PreparedStatement pstmtDelete;

    private static HashMap<TelefonbuchEintrag, String> idListe;

    /**
     * Initialisierungsblock
     * Wird ausgeführt wenn die Klasse erzeugt wird
     */
    static {
        System.out.println("static-Block ausgeführt");

        // Statements vorbereiten
        pstmtSelect = Datenbank.getInstance().prepareStatement("SELECT Vorname, Nachname, Straße, HausNr, PLZ, Ort, TelefonNr FROM telefonbuch;");
        pstmtInsert = Datenbank.getInstance().prepareStatement("INSERT INTO telefonbuch (Vorname, Nachname, Straße, HausNr, PLZ, Ort, TelefonNr) VALUES (?, ?, ?, ?, ?, ?, ?);");
        pstmtUpdate = Datenbank.getInstance().prepareStatement("UPDATE telefonbuch SET Vorname = ?, Nachname = ?, Straße = ?, HausNr = ?, PLZ = ?, Ort = ?, TelefonNr = ? WHERE TelefonNr = ?;");
        pstmtDelete = Datenbank.getInstance().prepareStatement("DELETE FROM telefonbuch WHERE TelefonNr = ?;");

        idListe = new HashMap<>();
    }

    /**
     * Lädt das gesamte Telefonbuch aus der Datenbank und gibt es alls Liste von TelefonbuchEinträgen zurück
     *
     * @return Liste mit allen TelefonbuchEinträgen
     * @throws IllegalArgumentException wird geworfen, wenn intern eine SQL- oder ClassNotFoundException aufgetreten ist.
     */
    public static ArrayList<TelefonbuchEintrag> getTelefonbuch() {
        ArrayList<TelefonbuchEintrag> result = null;

        try {
            // Datenbankabfrage ausführen
            ResultSet rs = pstmtSelect.executeQuery();

            // Result initialisieren
            result = new ArrayList<>();

            // Zurücksetzen der idListe
            idListe.clear();

            // Alle Datensätze abfragen und passend dazu neue Einträge generieren
            while (rs.next()) {
                TelefonbuchEintrag eintrag = new TelefonbuchEintrag(
                        rs.getString("Vorname"),
                        rs.getString("Nachname"),
                        rs.getString("Straße"),
                        rs.getString("HausNr"),
                        rs.getString("PLZ"),
                        rs.getString("Ort"),
                        rs.getString("TelefonNr")
                );
                result.add(eintrag);

                // Objekt der idListe hinzufügen
                idListe.put(eintrag, eintrag.getTelefonNr());
            }

        } catch (SQLException ignored) {}

        return result;
    }

    /**
     * Speichert einen übergebenen TelefonbuchEintrag in der Datenbank. Ob der Eintrag
     * im Telefonbuch schon vorhanden ist oder nicht, also ob ein update oder ein
     * insert-Befehl für die Datenbank ausgeführt werden muss, ist für den Aufruf von der GUI
     * irrelevant. Dies findet diese Methode heraus.
     *
     * @param zuSpeichern TelefonbuchEintrag, der gespeichert werden soll
     * @return true, wenn die Speicherung erfolgreich war, false andernfalls
     */
    public static boolean save(TelefonbuchEintrag zuSpeichern) {
        boolean result = false;

        try {
            PreparedStatement pstmt;

            // Entscheiden, ob INSERT oder UPDATE
            String id = idListe.get(zuSpeichern);

            if (id == null) {
                // INSERT
                pstmt = pstmtInsert;
            } else {
                // UPDATE
                pstmt = pstmtUpdate;
                pstmt.setString(8, id);
            }

            // Das PreparedStatement mit Informationen füttern
            pstmt.setString(1, zuSpeichern.getVorname());
            pstmt.setString(2, zuSpeichern.getNachname());
            pstmt.setString(3, zuSpeichern.getStrasse());
            pstmt.setString(4, zuSpeichern.getHausNr());
            pstmt.setString(5, zuSpeichern.getPlz());
            pstmt.setString(6, zuSpeichern.getOrt());
            pstmt.setString(7, zuSpeichern.getTelefonNr());

            // Ausführen von Insert oder Update
            pstmt.executeUpdate();
            result = true;

            // Neuen oder geänderten Datensatz in der idListe aktualisieren
            idListe.put(zuSpeichern, zuSpeichern.getTelefonNr());

            Datenbank.getInstance().commit();

        } catch (SQLException e) {
            try {
                Datenbank.getInstance().rollback();
            } catch (SQLException ignored) {}
            e.printStackTrace();
            throw new IllegalArgumentException("Fehler beim Speicher des TelefonbuchEintrags in die Datenbank");
        }

        return result;
    }

    /**
     * Löscht einen übergebenen TelefonbuchEintrag aus der Datenbank
     *
     * @param zuLoeschen TelefonbuchEintrag, der gelöscht werden soll
     * @return true, wenn das Löschen erfolgreich war, false andernfalls
     */
    public static boolean delete(TelefonbuchEintrag zuLoeschen) {
        // Ist der Eintrag überhaupt in der Datenbank?
        if (idListe.get(zuLoeschen) == null) {
            // Der Eintrag ist nicht in der Datenbank enthalten und daher war das Löschen erfolgreich
            return true;
        }

        boolean result = false;

        try {
            pstmtDelete.setString(1, zuLoeschen.getTelefonNr());
            pstmtDelete.executeUpdate();
            result = true;

            Datenbank.getInstance().commit();

        } catch (SQLException e) {
            System.err.println("Fehler beim Löschen des TelefonbuchEintrags aus der Datenbank: " + e.getLocalizedMessage());
        }

        return result;
    }
}
