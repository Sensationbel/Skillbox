import com.google.gson.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class ParseJsonFile {
    private final String filePath;
    private StringBuilder builderJsonFile;

    public ParseJsonFile(String filePath) {
        this.filePath = filePath;
    }

    public void verifyInputData() {
        getJsonFile();
        System.out.print("Введите номер линии: ");
        Scanner scanner = new Scanner(System.in);
        String lineNumber = scanner.nextLine();
        parseLineInFile(lineNumber);
    }

    private void parseLineInFile(String lineNumber) {
        JsonParser parser = new JsonParser();
        JsonElement rootNode = parser.parse(getStringBuilder());
        AtomicBoolean resultSearch = new AtomicBoolean(false);
        if (rootNode.isJsonObject()) {
            JsonObject jsonObject = rootNode.getAsJsonObject();
            JsonArray jsonArray = jsonObject.getAsJsonArray("lines");
            jsonArray.forEach(a -> {
                JsonElement number = a.getAsJsonObject().get("number");
                JsonElement name = a.getAsJsonObject().get("name");
                if (lineNumber.equals(number.toString().replaceAll("\"", ""))) {
                    resultSearch.set(true);
                    String nameLine = new Gson().toJson(name).replaceAll("\"", "");
                    int countStation = parseStationInFile(number);
                    printResult(nameLine, countStation);
                }
            });
        }
        if (!resultSearch.get()) {
            throw new IllegalArgumentException("Line number entered incorrectly!");
        }
    }

    private int parseStationInFile(JsonElement number) {
        JsonParser parser = new JsonParser();
        JsonElement rootNode = parser.parse(getStringBuilder());
        int countStation = 0;
        if (rootNode.isJsonObject()) {
            JsonObject jsonObject = rootNode.getAsJsonObject();
            JsonObject station = (JsonObject) jsonObject.get("Station");
            String string = new Gson().toJson(number).replaceAll("\"", "");
            JsonArray jsonArray = station.getAsJsonArray(string);
            countStation = jsonArray.size();
            System.out.println();
        }
        return countStation;
    }

    private void getJsonFile() {
        builderJsonFile = new StringBuilder();
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            lines.forEach(builderJsonFile::append);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getStringBuilder() {
        return builderJsonFile.toString();
    }

    private void printResult(String nameLine, int countStation) {
        System.out.printf("%s насчитывает %d станций", nameLine, countStation);
    }
}
