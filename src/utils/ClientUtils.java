package utils;

import data.Client;
import exceptions.ClientDataException;
import io.IOService;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientUtils {

    private static final String FILE_NAME = "src/resources/clients.txt";

    public static void saveClientsToFile(Client[] clients) throws ClientDataException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Client client : clients) {
                writer.write(client.toString());
                writer.write(" ");
            }
        } catch (Exception e) {
            throw new ClientDataException("Ошибка сохранения данных клиентов в файл", e);
        }
    }

    public static Optional<Client[]> loadClientsFromFile() {
        List<Client> clients = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line = reader.readLine();
            if (line != null) {
                String[] clientStrings = line.split(" ");
                for (String clientString : clientStrings) {
                    clients.add(Client.fromString(clientString).orElseThrow(() ->
                            new ClientDataException("Ошибка парсинга данных клиента")));
                }
            }
            return Optional.of(clients.toArray(new Client[0]));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

}
