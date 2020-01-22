package Uebung36.client;

import Uebung36.client.rmi.Client;
import Uebung36.model.VerbindungsSystem;

import java.util.Scanner;

public class MainClient {

    public static void main(String[] args) {
        /* Hiermit stellen wir die Verbindung zu unserem Singleton her */
        Client.getInstance();

        // Benutzer begrüßen
        System.out.println("Willkommen zum RMI-ChatSystem.\nZum Schreiben von Nachrichten: Einfach eine Nachricht eintippen und mit Enter bestätigen.\nZum Beenden des Programms: '" + VerbindungsSystem.BEENDEN + "' als Nachricht schreiben.");

        // Von der Konsole die Nachrichten einlesen
        Scanner scanner = new Scanner(System.in);

        String message;

        // So lange neue Nachrichten abfragen bis der Benutzer die Verbindung beenden will
        do {
            System.out.print("Client: ");
            message = scanner.nextLine();

            Client.getInstance().nachrichtSenden(message);
        } while (!message.equals(VerbindungsSystem.BEENDEN));

        // Verbindung zum Scanner und zum Server trennen
        scanner.close();
        Client.getInstance().disconnect();
    }
}
