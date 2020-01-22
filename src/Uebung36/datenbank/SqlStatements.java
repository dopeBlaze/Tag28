package Uebung36.datenbank;

public class SqlStatements {

    // Aktuelle Version
    public final static int VERSION = 3;

    // Befehle zum Setzen und Auslesen der Version
    public final static String GET_VERSION = "PRAGMA USER_VERSION;";
    public final static String SET_VERSION = "PRAGMA USER_VERSION = ";

    // +-------------------------------------------------------------------------------------------------------------------------+
    // |                                                  VERSION 1																 |
    // +-------------------------------------------------------------------------------------------------------------------------+

    // Erzeugung der Telefonbuch-Tabelle
    public final static String CREATE_TELEFONBUCH = "CREATE TABLE IF NOT EXISTS telefonbuch(" +
            "  vorname VARCHAR(50) NOT NULL," +
            "  nachname VARCHAR(50) NOT NULL," +
            "  strasse VARCHAR(50) NOT NULL," +
            "  hausnr VARCHAR(10) NOT NULL," +
            "  plz CHAR(5) NOT NULL," +
            "  ort VARCHAR(50) NOT NULL," +
            "  telefonnr VARCHAR(30) NOT NULL UNIQUE" +
            ");";

    // +-------------------------------------------------------------------------------------------------------------------------+
    // |                                                  VERSION 2 															 |
    // +-------------------------------------------------------------------------------------------------------------------------+

    // Erstellen der Tabellen
    public static final String CREATE_TABLE_VORNAME = "CREATE TABLE \"table_vorname\" (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, vorname VARCHAR(50) NOT NULL UNIQUE);";
    public static final String CREATE_TABLE_NACHNAME = "CREATE TABLE \"table_nachname\" (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, nachname VARCHAR(50) NOT NULL UNIQUE);";
    public static final String CREATE_TABLE_STRASSE = "CREATE TABLE \"table_strasse\" (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, strasse VARCHAR(50) NOT NULL UNIQUE);";
    public static final String CREATE_TABLE_HAUSNR = "CREATE TABLE \"table_hausnr\" (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, hausnr VARCHAR(10) NOT NULL UNIQUE);";
    public static final String CREATE_TABLE_PLZ = "CREATE TABLE \"table_plz\" (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, plz CHAR(5) NOT NULL UNIQUE);";
    public static final String CREATE_TABLE_ORT = "CREATE TABLE \"table_ort\" (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, ort VARCHAR(50) NOT NULL UNIQUE);";
    public static final String CREATE_TABLE_TELEFONBUCH = "CREATE TABLE \"table_telefonbuch\" ( "
            + "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + "vorname_id INTEGER NOT NULL, "
            + "nachname_id INTEGER NOT NULL, "
            + "strasse_id INTEGER NOT NULL, "
            + "hausnr_id INTEGER NOT NULL, "
            + "plz_id INTEGER NOT NULL, "
            + "ort_id INTEGER NOT NULL, "
            + "telefonnr VARCHAR(30) NOT NULL UNIQUE,"
            + "FOREIGN KEY (vorname_id) REFERENCES vorname(id), "
            + "FOREIGN KEY (nachname_id) REFERENCES nachname(id), "
            + "FOREIGN KEY (strasse_id) REFERENCES strasse(id), "
            + "FOREIGN KEY (hausnr_id) REFERENCES hausnr(id), "
            + "FOREIGN KEY (plz_id) REFERENCES plz(id), "
            + "FOREIGN KEY (ort_id) REFERENCES ort(id)"
            + ");";
    public static final String ACTIVATE_FOREIGN_KEYS = "PRAGMA foreign_keys = ON;";

    // Umkopieren der in der Tabelle telefonbuch vorhandenen Datensätze in die Basistabellen
    public static final String INSERT_VORNAME = "INSERT INTO table_vorname (vorname) SELECT DISTINCT vorname FROM telefonbuch;";
    public static final String INSERT_NACHNAME = "INSERT INTO table_nachname (nachname) SELECT DISTINCT nachname FROM telefonbuch;";
    public static final String INSERT_STRASSE = "INSERT INTO table_strasse (strasse) SELECT DISTINCT strasse FROM telefonbuch;";
    public static final String INSERT_HAUSNR = "INSERT INTO table_hausnr (hausnr) SELECT DISTINCT hausnr FROM telefonbuch;";
    public static final String INSERT_PLZ = "INSERT INTO table_plz (plz) SELECT DISTINCT plz FROM telefonbuch;";
    public static final String INSERT_ORT = "INSERT INTO table_ort (ort) SELECT DISTINCT ort FROM telefonbuch;";

    // Umkopieren der in der Tabelle telefonbuch vorhandenen Datensätze in die Tabelle table_telefonbuch unter
    // Berücksichtigung der ids aus den Basistabellen
    public static final String INSERT_TELFONBUCH = "INSERT INTO table_telefonbuch (vorname_id, nachname_id, strasse_id, hausnr_id, plz_id, ort_id, telefonnr) " +
            "SELECT v.id, n.id, s.id, h.id, p.id, o.id, t.telefonnr " +
            "FROM telefonbuch t " +
            "INNER JOIN table_vorname v ON t.vorname = v.vorname " +
            "INNER JOIN table_nachname n ON t.nachname = n.nachname " +
            "INNER JOIN table_strasse s ON t.strasse = s.strasse " +
            "INNER JOIN table_hausnr h ON t.hausnr = h.hausnr " +
            "INNER JOIN table_plz p ON t.plz = p.plz " +
            "INNER JOIN table_ort o ON t.ort = o.ort;";

    // Löschen der alten Telefonbuchtabelle
    static final String DROP_TELEFONBUCH = "DROP TABLE telefonbuch;";

    // Erstellen der Sicht
    static final String CREATE_VIEW_TELEFONBUCH = "CREATE VIEW telefonbuch AS "
            + "SELECT v.vorname AS Vorname, "
            + "n.nachname AS Nachname, "
            + "s.strasse AS Straße, "
            + "h.hausnr AS HausNr, "
            + "p.plz AS PLZ, "
            + "o.ort AS Ort, "
            + "t.telefonnr AS TelefonNr "
            + " FROM table_telefonbuch t "
            + "INNER JOIN table_vorname v ON t.vorname_id = v.id "
            + "INNER JOIN table_nachname n ON t.nachname_id = n.id "
            + "INNER JOIN table_strasse s ON t.strasse_id = s.id "
            + "INNER JOIN table_hausnr h ON t.hausnr_id = h.id "
            + "INNER JOIN table_plz p ON t.plz_id = p.id "
            + "INNER JOIN table_ort o ON t.ort_id = o.id;";

    // +-------------------------------------------------------------------------------------------------------------------------+
    // |                                                  VERSION 3 															 |
    // +-------------------------------------------------------------------------------------------------------------------------+

    // Erstellen der Trigger
    public static final String CREATE_TRIGGER_TELEFONBUCH_INS = "CREATE TRIGGER Telefonbuch_INS INSTEAD OF INSERT ON telefonbuch FOR EACH ROW\n" +
            "	BEGIN\n" +
            "	\n" +
            "		INSERT INTO table_vorname (vorname) SELECT NEW.Vorname WHERE NOT EXISTS (SELECT 1 FROM table_vorname WHERE vorname = NEW.Vorname);\n" +
            "		INSERT INTO table_nachname (nachname) SELECT NEW.Nachname WHERE NOT EXISTS (SELECT 1 FROM table_nachname WHERE nachname = NEW.Nachname);\n" +
            "		INSERT INTO table_strasse (strasse) SELECT NEW.Straße WHERE NOT EXISTS (SELECT 1 FROM table_strasse WHERE strasse = NEW.Straße);\n" +
            "		INSERT INTO table_hausnr (hausnr) SELECT NEW.HausNr WHERE NOT EXISTS (SELECT 1 FROM table_hausnr WHERE hausnr = NEW.HausNr);\n" +
            "		INSERT INTO table_plz (plz) SELECT NEW.PLZ WHERE NOT EXISTS (SELECT 1 FROM table_plz WHERE plz = NEW.PLZ);\n" +
            "		INSERT INTO table_ort (ort) SELECT NEW.Ort WHERE NOT EXISTS (SELECT 1 FROM table_ort WHERE ort = NEW.Ort);\n" +
            "		\n" +
            "		INSERT INTO table_telefonbuch (vorname_id, nachname_id, strasse_id, hausnr_id, plz_id, ort_id, telefonnr) SELECT (SELECT id FROM table_vorname WHERE vorname = NEW.Vorname), (SELECT id FROM table_nachname WHERE nachname = NEW.Nachname), (SELECT id FROM table_strasse WHERE strasse = NEW.Straße), (SELECT id FROM table_hausnr WHERE hausnr = NEW.HausNr), (SELECT id FROM table_plz WHERE plz = NEW.PLZ), (SELECT id FROM table_ort WHERE ort = NEW.Ort), NEW.TelefonNr;\n" +
            "	\n" +
            "	END;";

    public static final String CREATE_TRIGGER_TELEFONBUCH_DEL = "CREATE TRIGGER Telefonbuch_DEL INSTEAD OF DELETE ON telefonbuch FOR EACH ROW\n" +
            "	BEGIN\n" +
            "	\n" +
            "		DELETE FROM table_telefonbuch WHERE telefonnr = OLD.TelefonNr;\n" +
            "		\n" +
            "		DELETE FROM table_vorname WHERE id NOT IN (SELECT vorname_id FROM table_telefonbuch);\n" +
            "		DELETE FROM table_nachname WHERE id NOT IN (SELECT nachname_id FROM table_telefonbuch);\n" +
            "		DELETE FROM table_strasse WHERE id NOT IN (SELECT strasse_id FROM table_telefonbuch);\n" +
            "		DELETE FROM table_hausnr WHERE id NOT IN (SELECT hausnr_id FROM table_telefonbuch);\n" +
            "		DELETE FROM table_plz WHERE id NOT IN (SELECT plz_id FROM table_telefonbuch);\n" +
            "		DELETE FROM table_ort WHERE id NOT IN (SELECT ort_id FROM table_telefonbuch);\n" +
            "	\n" +
            "	END;";

    public static final String CREATE_TRIGGER_TELEFONBUCH_UPD = "CREATE TRIGGER Telefonbuch_UPD INSTEAD OF UPDATE ON telefonbuch FOR EACH ROW\n" +
            "	BEGIN\n" +
            "	\n" +
            "		INSERT INTO table_vorname (vorname) SELECT NEW.Vorname WHERE NOT EXISTS (SELECT 1 FROM table_vorname WHERE vorname = NEW.Vorname);\n" +
            "		INSERT INTO table_nachname (nachname) SELECT NEW.Nachname WHERE NOT EXISTS (SELECT 1 FROM table_nachname WHERE nachname = NEW.Nachname);\n" +
            "		INSERT INTO table_strasse (strasse) SELECT NEW.Straße WHERE NOT EXISTS (SELECT 1 FROM table_strasse WHERE strasse = NEW.Straße);\n" +
            "		INSERT INTO table_hausnr (hausnr) SELECT NEW.HausNr WHERE NOT EXISTS (SELECT 1 FROM table_hausnr WHERE hausnr = NEW.HausNr);\n" +
            "		INSERT INTO table_plz (plz) SELECT NEW.PLZ WHERE NOT EXISTS (SELECT 1 FROM table_plz WHERE plz = NEW.PLZ);\n" +
            "		INSERT INTO table_ort (ort) SELECT NEW.Ort WHERE NOT EXISTS (SELECT 1 FROM table_ort WHERE ort = NEW.Ort);\n" +
            "		\n" +
            "		UPDATE table_telefonbuch SET vorname_id = (SELECT id FROM table_vorname WHERE vorname = NEW.Vorname), nachname_id = (SELECT id FROM table_nachname WHERE nachname = NEW.Nachname), strasse_id = (SELECT id FROM table_strasse WHERE strasse = NEW.Straße), hausnr_id = (SELECT id FROM table_hausnr WHERE hausnr = NEW.HausNr), plz_id = (SELECT id FROM table_plz WHERE plz = NEW.PLZ), ort_id = (SELECT id FROM table_ort WHERE ort = NEW.Ort), telefonnr = NEW.TelefonNr WHERE telefonnr = OLD.TelefonNr;\n" +
            "		\n" +
            "		DELETE FROM table_vorname WHERE id NOT IN (SELECT vorname_id FROM table_telefonbuch);\n" +
            "		DELETE FROM table_nachname WHERE id NOT IN (SELECT nachname_id FROM table_telefonbuch);\n" +
            "		DELETE FROM table_strasse WHERE id NOT IN (SELECT strasse_id FROM table_telefonbuch);\n" +
            "		DELETE FROM table_hausnr WHERE id NOT IN (SELECT hausnr_id FROM table_telefonbuch);\n" +
            "		DELETE FROM table_plz WHERE id NOT IN (SELECT plz_id FROM table_telefonbuch);\n" +
            "		DELETE FROM table_ort WHERE id NOT IN (SELECT ort_id FROM table_telefonbuch);\n" +
            "	\n" +
            "	END;";
}
