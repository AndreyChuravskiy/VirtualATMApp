package utils;

import data.ATMData;
import exceptions.ATMDataException;

import java.io.*;
import java.util.Optional;

public class ATMUtils {

    private static final String FILE_NAME = "src/resources/atmData.txt";

    public static void saveATMDataToFile(ATMData atmData) throws ATMDataException{
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            writer.write(atmData.toString());
        } catch (Exception e) {
            throw new ATMDataException("Ошибка сохранения данных банкомата в файл", e);
        }
    }

    public static Optional<ATMData> loadATMDataFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line = reader.readLine();
            return ATMData.fromString(line);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

}
