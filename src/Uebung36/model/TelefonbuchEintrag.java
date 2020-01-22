package Uebung36.model;

public class TelefonbuchEintrag {

    private String vorname, nachname, strasse, hausNr, plz, ort, telefonNr;

    /**
     * Konstruktor, der alle Werte mit leeren Werten initialisiert
     */
    public TelefonbuchEintrag() {
        this.vorname = "";
        this.nachname = "";
        this.strasse = "";
        this.hausNr = "";
        this.plz = "";
        this.ort = "";
        this.telefonNr = "";
    }

    /**
     * Überladener Konstruktor
     * Initialisiert alle Werte mit leeren Werten und setzt dann die übergebenen Werte,
     * sofern sie ungleich null sind.
     *
     * @param vorname zu setzender Vorname
     * @param nachname zu setzender Nachname
     * @param strasse zu setzende Straße
     * @param hausNr zu setzende Hausnummer
     * @param plz zu setzende Postleitzahl
     * @param ort zu setzender Ort
     * @param telefonNr zu setzende Telefonnummer
     *
     * @throws IllegalArgumentException wird geworfen, wenn einer der Parameter in der Länge den maximalen Text überschreitet.
     */
    public TelefonbuchEintrag(String vorname, String nachname, String strasse, String hausNr, String plz, String ort, String telefonNr) {
        setVorname(vorname);
        setNachname(nachname);
        setStrasse(strasse);
        setHausNr(hausNr);
        setPlz(plz);
        setOrt(ort);
        setTelefonNr(telefonNr);
    }

    /**
     * Gibt den Wert des Vornamens zurück
     * @return vorname String des Vornamens
     */
    public String getVorname() {
        return vorname;
    }

    /**
     * Setzt den Vornamen auf den übergebenen Wert, sofern dieser von null verschieden ist.
     * Des Weiteren darf die Länge von 50 Zeichen nicht überschritten werden
     *
     * @param vorname Der zu setzende Vorname
     * @throws IllegalArgumentException wird geworfen, wenn die Länge des Textes 50 Zeichen übersteigt.
     */
    public void setVorname(String vorname) {
        if (vorname == null || vorname.length() > 50) {
            throw new IllegalArgumentException("Der Vorname darf weder null noch die Länge von 50 Zeichen übersteigen.");
        } else {
            this.vorname = vorname;
        }
    }

    /**
     * Gibt den Wert des Nachnamens zurück
     * @return nachname String des Nachnamens
     */
    public String getNachname() {
        return nachname;
    }

    /**
     * Setzt den Nachnamen auf den übergebenen Wert, sofern dieser von null verschieden ist.
     * Des Weiteren darf die Länge von 50 Zeichen nicht überschritten werden
     *
     * @param nachname Der zu setzende Nachname
     * @throws IllegalArgumentException wird geworfen, wenn die Länge des Textes 50 Zeichen übersteigt.
     */
    public void setNachname(String nachname) {
        if (nachname == null || nachname.length() > 50) {
            throw new IllegalArgumentException("Der Nachname darf weder null noch die Länge von 50 Zeichen übersteigen.");
        } else {
            this.nachname = nachname;
        }
    }

    /**
     * Gibt den Wert der Straße zurück
     * @return strasse String der Straße
     */
    public String getStrasse() {
        return strasse;
    }

    /**
     * Setzt die Straße auf den übergebenen Wert, sofern dieser von null verschieden ist.
     * Des Weiteren darf die Länge von 50 Zeichen nicht überschritten werden
     *
     * @param strasse Die zu setzende Straße
     * @throws IllegalArgumentException wird geworfen, wenn die Länge des Textes 50 Zeichen übersteigt.
     */
    public void setStrasse(String strasse) {
        if (strasse == null || strasse.length() > 50) {
            throw new IllegalArgumentException("Die Straße darf weder null noch die Länge von 50 Zeichen übersteigen.");
        } else {
            this.strasse = strasse;
        }
    }

    /**
     * Gibt den Wert der Hausnummer zurück
     * @return hausnr String der Hausnummer
     */
    public String getHausNr() {
        return hausNr;
    }

    /**
     * Setzt die Hausnummer auf den übergebenen Wert, sofern dieser von null verschieden ist.
     * Des Weiteren darf die Länge von 10 Zeichen nicht überschritten werden
     *
     * @param hausNr Die zu setzende Hausnummer
     * @throws IllegalArgumentException wird geworfen, wenn die Länge des Textes 10 Zeichen übersteigt.
     */
    public void setHausNr(String hausNr) {
        if (hausNr == null || hausNr.length() > 10) {
            throw new IllegalArgumentException("Die Hausnummer darf weder null noch die Länge von 10 Zeichen übersteigen.");
        } else {
            this.hausNr = hausNr;
        }
    }

    /**
     * Gibt den Wert der Postleitzahl zurück
     * @return plz String der Postleitzahl
     */
    public String getPlz() {
        return plz;
    }

    /**
     * Setzt die Postleitzahl auf den übergebenen Wert, sofern dieser von null verschieden ist.
     * Des Weiteren muss die Länge exakt 5 Zeichen betragen.
     *
     * @param plz Die zu setzende Postleitzahl
     * @throws IllegalArgumentException wird geworfen, wenn die Länge des Textes nicht genau 5 Zeichen hat.
     */
    public void setPlz(String plz) {
        if (plz == null || plz.length() != 5) {
            throw new IllegalArgumentException("Die Postleitzahl darf weder null noch eine andere Länge als 5 besitzen.");
        } else {
            this.plz = plz;
        }
    }

    /**
     * Gibt den Wert des Ortes zurück
     * @return ort String des Ortes
     */
    public String getOrt() {
        return ort;
    }

    /**
     * Setzt den Ort auf den übergebenen Wert, sofern dieser von null verschieden ist.
     * Des Weiteren darf die Länge von 50 Zeichen nicht überschritten werden
     *
     * @param ort Der zu setzende Ort
     * @throws IllegalArgumentException wird geworfen, wenn die Länge des Textes 50 Zeichen übersteigt.
     */
    public void setOrt(String ort) {
        if (ort == null || ort.length() > 50) {
            throw new IllegalArgumentException("Der Ort darf weder null noch die Länge von 50 Zeichen übersteigen.");
        } else {
            this.ort = ort;
        }
    }

    /**
     * Gibt den Wert der Telefonnummer zurück
     * @return telefonnr String der Telefonnummer
     */
    public String getTelefonNr() {
        return telefonNr;
    }

    /**
     * Setzt die Telefonnummer auf den übergebenen Wert, sofern dieser von null verschieden ist.
     * Des Weiteren darf die Länge von 30 Zeichen nicht überschritten werden. Die Mindestlänge ist 3 und
     * die gesamte Zeichenkette muss aus Ziffern bestehen.
     *
     * @param telefonNr Die zu setzende Telefonnummer
     * @throws IllegalArgumentException wird geworfen, wenn die Länge des Textes 30 Zeichen übersteigt, weniger als 3 ist oder ein nicht numerischer Wert vorkommt.
     */
    public void setTelefonNr(String telefonNr) {
        // Test auf null und der Minimalen Länge
        if (telefonNr == null || telefonNr.length() < 3) {
            throw new IllegalArgumentException("Die Telefonnummer darf weder null kürzer als 3 Ziffern sein.");

        // Test der maximalen Länge. Die Länge beträgt an dieser Stelle mindestens 3 Zeichen
        } else if (telefonNr.length() > 30) {
            throw new IllegalArgumentException("Die Telefonnummer darf eine Länge von 30 zeichen nicht übersteigen.");

        // Die Länge beträgt an dieser Stelle mindestens 3 und maximal 30 Zeichen
        } else {

            // Alle Zeichen durchprobieren und prüfen, ob es sich hierbei ausschließlich um Ziffern handelt
            for (char ziffer : telefonNr.toCharArray()) {
                if (ziffer < '0' || ziffer > '9') {
                    throw new IllegalArgumentException("Die Telefonnummer darf nur aus Ziffern bestehen.");
                }
            }

            // Wenn die Funktion bis hierhin durchgelaufen ist, dann ist die neue Telefonnummer in Ordnung und kann gesetzt werden
            this.telefonNr = telefonNr;
        }
    }
}