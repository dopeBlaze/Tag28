package Uebung36.printing;

import Uebung36.model.TelefonbuchEintrag;

import java.util.ArrayList;

public class TelefonbuchAusgabe {

    /**
     * Formatiert einen einzelnen Eintrag als String
     *
     * @param eintrag zu formatierender Eintrag
     * @return Den formatierten Eintrag als text
     */
    public static String ausgeben(TelefonbuchEintrag eintrag) {
        // Parameter testen
        if (eintrag == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();

        sb.append("Name:          " + eintrag.getNachname() + ", " + eintrag.getVorname() + "\n");
        sb.append("Straße/HausNr: " + eintrag.getStrasse() + " " + eintrag.getHausNr() + "\n");
        sb.append("Plz/Ort:       " + eintrag.getPlz() + " " + eintrag.getOrt() + "\n");
        sb.append("TelefonNr:     " + eintrag.getTelefonNr());

        return sb.toString();
    }

    /**
     * Formatiert eine Liste von Telefonbucheinträgen als String
     *
     * @param telefonbuch zu formatierende Liste
     * @return Die formatierte Liste als Text
     */
    public static String ausgeben(ArrayList<TelefonbuchEintrag> telefonbuch) {
        // Parameter testen
        if (telefonbuch == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();

        sb.append("Inhalt des Telefonbuchs:\n");
        sb.append("\n--------------------------------------------------\n\n");

        // Ausgabe der einzelnen Einträge
        for (TelefonbuchEintrag eintrag : telefonbuch) {
            sb.append(ausgeben(eintrag));
            sb.append("\n\n--------------------------------------------------\n\n");
        }

        return sb.toString();
    }
}
