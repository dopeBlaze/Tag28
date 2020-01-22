package Uebung36.server;

import Uebung36.model.VerbindungsSystem;
import Uebung36.server.rmi.Server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class MainServer {
    public static void main(String[] args) throws RemoteException {
        System.out.println("ChatServer\n----------------------------------------\n");

        // Neues ServerObjekt erzeugen
        Server server = new Server();

        // Neues Objekt erstellen, das zur Registry exportiert werden soll
        VerbindungsSystem stub = (VerbindungsSystem) UnicastRemoteObject.exportObject(server, 0);

        // Standard-Port der Registry: 1099
        Registry registry = LocateRegistry.createRegistry(VerbindungsSystem.SERVER_PORT);

        // Das exportierte Objekt an die Registry binden
        registry.rebind(VerbindungsSystem.SERVER_NAME, stub);

        System.out.println("Chatserver ist gestartet und empfangsbereit.");
    }
}
