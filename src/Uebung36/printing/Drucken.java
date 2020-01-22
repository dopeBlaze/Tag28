package Uebung36.printing;

import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Drucken implements Printable {

    private static Font font = new Font("Serif", Font.PLAIN, 12);
    private static final int LEFT_MARGIN = 20;

    /**
     * Setzt für alle Druckaufträge eine neue Schriftart
     * @param font zu setzende Schriftart
     */
    public static void setFont(Font font) {
        if (font != null) {
            Drucken.font = font;
        }
    }

    /**
     * Statische Druck-Methode, die einen Druckauftrag anlegt und diesen ausdruckt
     *
     * @param zuDrucken zu druckender Text
     */
    public static void drucken(String zuDrucken) {
        // Eingangstext zerlegen
        String[] zerlegt = zuDrucken.split("\\n");

        // Das eigentliche Drucken starten
        drucken(zerlegt);
    }

    /**
     * Statische Druck-Methode, die einen Druckauftrag anlegt und diesen ausdruckt
     *
     * @param zuDrucken zu druckender Text
     */
    public static void drucken(ArrayList<String> zuDrucken) {
        // ArrayList in ein String-Array umwandeln
        String[] drucken = new String[zuDrucken.size()];

        /*
        // Text mittels Schleife umkopieren
        for (int i = 0; i < zuDrucken.size(); i++) {
            drucken[i] = zuDrucken.get(i);
        }*/

        // Alternativ - Text mittels der eingebauten "toArray"-Funktion umkopieren
        drucken = zuDrucken.toArray(drucken);

        // Das eigentliche Drucken starten
        drucken(drucken);
    }

    /**
     * Statische Druck-Methode, die einen Druckauftrag anlegt und diesen ausdruckt
     *
     * @param zuDrucken zu druckender Text
     */
    public static void drucken(String[] zuDrucken) {
        // Druckauftrag anlegen
        PrinterJob job = PrinterJob.getPrinterJob();

        // Druckinhalt festlegen
        job.setPrintable(new Drucken(zuDrucken));

        // Druckdialog des Betriebssystems öffnen
        boolean ok = job.printDialog();

        if (ok) {
            /*
             * Startet den Druckauftrag und ruft die Methode print des Objektes auf, welches
             * bei setPrintable übergeben wurde.
             */
            try {
                job.print();
            } catch (PrinterException e) {
                System.err.println("Der Druckauftrag wurde nicht erfolgreich abgeschlossen.");
                e.printStackTrace();
            }
        }
    }

    // --------------------------------------------------------------------------

    // Speicherort für die zu druckenden Zeilen
    private String[] auszudruckendeZeilen;

    // Array welches die Positionen der Seitenumbrüche beinhaltet
    private int[] pageBreaks;

    /**
     * Initialisiert die Printable-Implementierung
     *
     * @param zuDrucken String-Array mit den zu druckenden Zeilen
     */
    private Drucken(String[] zuDrucken) {
        if (zuDrucken == null) {
            auszudruckendeZeilen = new String[0];
        } else {
            auszudruckendeZeilen = zuDrucken;
        }
    }

    @Override
    public int print(Graphics g, PageFormat pf, int page) throws PrinterException {
        // Zu druckenden Text laden
        String[] text = auszudruckendeZeilen;

        // Bestimmen des entsprechenden Platzbedarfs (Anzahl Zeilen pro Seite und Anzahl Seiten)
        FontMetrics metrics = g.getFontMetrics(font);
        int zeilenhoehe = metrics.getHeight();

        // Seitenumrüche berechnen
        if (pageBreaks == null) {
            // Anzahl der vollen Zeilen pro Seite herausfinden
            int anzahlZeilenProSeite = (int) pf.getImageableHeight() / zeilenhoehe;

            // Um Platz für die Kopf und Fußzeile zu schaffen
            anzahlZeilenProSeite -= 2;

            // Anzahl der Seitenumbrüche bestimmen
           int anzahlSeitenumbrueche = (text.length - 1) / anzahlZeilenProSeite;

            // Seitenumbruch-Array initialisieren
            pageBreaks = new int[anzahlSeitenumbrueche];

            // Seitenumbrüche festlegen (Vor welchem Zeilenindex bzw. nach welcher Zeile
            // muss ein Umbruch eingefügt werden
            for (int i = 0; i < anzahlSeitenumbrueche; i++) {
                pageBreaks[i] = anzahlZeilenProSeite * (i + 1);
            }
        }

        /*
         * Die Anzahl der Seiten stimmt mit der Anzahl der Einträge im Array pageBreaks fast überein
         * Die genaue Anzahl der Seiten ist eins mehr als das PageBreaks-Array Einträge hat.
         * ABER: Die Länge des pageBreaks-Arrays ist gleich dem Index der letzten druckbaren Seite!
         * Beispiel: 4 seiten sind zu Drucken. Das pageBreaksArray beinhaltet also 3 Seitenumbrüche.
         * 			 Der Index der Seite 4 ist 3 und somit gleich der Länge des pageBreaks-Arrays.
         */
        int maxSeitenIndex = pageBreaks.length;

        if (page > maxSeitenIndex) {
            return Printable.NO_SUCH_PAGE;
        }

        /*
         * Die Drucker können meist nicht an der Position (0,0) drucken, Daher muss eine
         * Umsetzung auf den Druckbereich stattfinden. Den Druckbereich können wir aus
         * dem PageFormat auslesen mit getImageableX() und getImageableY(), das bewirkt,
         * dass der 0,0-Punkt in den durch den ausgewählten Drucker druckbaren Bereich
         * verschoben wird.
         */
        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());

        // Setzen der Schriftart
        g2d.setFont(font);

        // Start- und Endzeile finden für die aktuell zu druckende Seite
        int start = (page == 0) ? 0 : (pageBreaks[page - 1]);	// Ergebnis ist der Index im text-Array
        int ende = (page == pageBreaks.length) ? (text.length) : pageBreaks[page]; // Ergebnis ist die zu druckende Zeile (Index wäre um eins niedriger!)

        // Setzen jeder einzelnen Zeile
        // Damit der Text unterhalb der Überschrift mit einer Leerzeile dazwischen anfängt
        int y = 2*zeilenhoehe;

        // In einer Schleife die auszugebenden Texte durchgehen
        for (int zeile = start; zeile < ende; zeile++) {
            /*
             * Da bei allen Grafikelementen die linke obere Ecke als Bezugspunkt genommen wird,
             * ist es offensichtlich nachvollziehbar, dass es bei den Texten die linke UNTERE Ecke ist :)
             *
             * Aus diesem Grund muss zunächst VOR der Ausgabe der Zeile die neue Position des
             * Textes berechnet werden:
             */
            y += zeilenhoehe;

            // Setzen des Textes auf das Graphics-Objekt
            g2d.drawString(text[zeile], LEFT_MARGIN, y);
        }

        // Fußzeile
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        g2d.drawString("Seite " + (page + 1) + " von " + (maxSeitenIndex + 1) + " - Gedruckt am: " + LocalDate.now().format(dtf), LEFT_MARGIN, (int) pf.getImageableHeight());

        /*
         * Als Rückgabe wird nun die Information zurückgegeben, dass hier eine Seite
         * existiert, die gerade geschrieben wird.
         */
        return Printable.PAGE_EXISTS;
    }

}
