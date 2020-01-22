package Uebung36.model;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;

public interface VerbindungsSystem extends Remote {

    /*
     * Hinweis: Die Konstanten brauchen kein public static final, weil sie beim
     * Interface standardmäßig public static final sind
     */
    String BEENDEN = ".bye";

    /* Das ist der Name, der in der Registry gebunden werden soll */
    String SERVER_NAME = "Chatserver";

    /* Das ist der Port, an dem die Registry implementiert werden soll */
    int SERVER_PORT = Registry.REGISTRY_PORT;

    /**
     * Methode um eine Nachricht entgegenzunehmen
     *
     * @param message gesendete Nachricht
     * @throws RemoteException
     */
    void sendMessage(String message) throws RemoteException;
}
