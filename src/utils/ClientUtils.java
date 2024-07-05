package utils;

import data.Client;
import exceptions.ClientDataParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ClientUtils {

    public static void saveClientsToFile(Client[] clients, String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Client client : clients) {
                writer.write(client.toString());
                writer.write(" ");
            }
        }
    }

    public static Client[] loadClientsFromFile(String filename) throws IOException {
        List<Client> clients = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line = reader.readLine();
            if (line != null) {
                String[] clientStrings = line.split(" ");
                for (String clientString : clientStrings) {
                    clients.add(Client.fromString(clientString));
                }
            }
        } catch (ClientDataParseException e) {
            System.out.println(e.getMessage());
        }
        return clients.toArray(new Client[0]);
    }

}
