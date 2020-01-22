package Uebung36.server.rmi;

import Uebung36.model.VerbindungsSystem;

import java.rmi.RemoteException;

public class Server implements VerbindungsSystem {
    @Override
    public void sendMessage(String message) throws RemoteException {

        /*
         * Die Message des Clients soll einfach nur ausgegeben werden.
         * Damit es rot eingefärbt wird wird die Fehlerausgabe benutzt.
         */
        System.err.println(message);
    }
}
