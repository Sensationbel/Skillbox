import com.google.gson.*;
import java.io.*;

public class Main {
    public static final String URL = "https://www.moscowmap.ru/metro.html#lines ";
    private static final String FILE_PATH = "C:\\Users\\Sensationbel\\IdeaProjects\\13_FilesAndNetwork\\files\\map.json";

    public static void main(String[] args) {
        startProgram();
    }

    private static void startProgram() {
        writJsonToFile();
        ParseJsonFile parsFile = new ParseJsonFile(FILE_PATH);
        try {
            parsFile.verifyInputData();
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void writJsonToFile() {
        try {
            ParsingHTML parsingHTML = new ParsingHTML(URL);
            Gson gson = new Gson();
            parsingHTML.parseLine();
            JsonObject str = parsingHTML.parseStation();
            String string = gson.toJson(str);
            try (FileWriter fileWriter = new FileWriter(FILE_PATH)) {
                fileWriter.write(string);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
