package Uebung36.client.rmi;

import Uebung36.model.VerbindungsSystem;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {

    /* statische Referenz auf sich selbst */
    private static Client client;

    /*
     * Hier wird die Referenz zum stub des Servers abgelegt, muss natürlich vorher
     * vom Server an der Registry angemeldet worden sein
     */
    private VerbindungsSystem server;

    /**
     * Fabrikmethode
     * @return das aktuelle RmiClient-Objekt
     */
    public static Client getInstance() {
        if (client == null) {
            client = new Client();
        }

        return client;
    }

    /**
     * privater Konstruktor, damit keine Instanz dieser Klasse von aussen erzeugt werden kann
     * Stellt eine Verbindung zum ChatServer her
     */
    private Client() {
        try {
            Registry registry = LocateRegistry.getRegistry("127.0.0.1", VerbindungsSystem.SERVER_PORT);
            server = (VerbindungsSystem) registry.lookup(VerbindungsSystem.SERVER_NAME);
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Schließt die Verbindung zum Server
     */
    public void disconnect() {
        client = null;
    }

    /**
     * Schickt die übergebene Nachricht an den ChatServer
     *
     * @param message zu sendende Nachricht
     * @return true, wenn die Nachricht erfolgreich verschickt wurde, false andernfalls
     */
    public boolean nachrichtSenden(String message) {
        try {
            /*
             * Ruft mittels des Stubs die Methode sendMessage auf dem Server auf. Wenn der
             * Server keine Exception wirft, ist alles in Ordnung. Die Funktion nachrichtSenden
             * gibt dann true zurück.
             */
            server.sendMessage(message);
            return true;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }
}
