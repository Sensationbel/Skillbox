import com.google.gson.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class ParsingHTML {
    private Document document;
    private JsonObject jsonObject = new JsonObject();
    private JsonArray jsonArrayLine = new JsonArray();
    private String[] lineNumber = new String[16];

    public ParsingHTML(String url) throws IOException {
        document = Jsoup.connect(url).get();
    }

    public void parseLine() throws IOException {
        Elements elements = document.select("span[data-line]");
        AtomicInteger count = new AtomicInteger(1);
        elements.forEach(element -> {
            lineNumber[count.get() - 1] = element.attr("data-line");
            count.getAndIncrement();
            JsonObject jsonObjectLine = new JsonObject();
            jsonObjectLine.addProperty("number", element.attr("data-line"));
            jsonObjectLine.addProperty("name", (element.text()));
            jsonArrayLine.add(jsonObjectLine);
        });
        jsonObject.add("lines", jsonArrayLine);
    }

    public JsonObject parseStation() {
        Elements elStation = document.select("a[data-metrost]");
        JsonObject objectLineAndStation = new JsonObject();
        JsonArray arrayStation = new JsonArray();
        AtomicInteger count = new AtomicInteger(1);
        AtomicInteger indexLineNumber = new AtomicInteger();
        elStation.forEach(element -> {
            int index = Integer.parseInt(element.selectFirst("span[class=\"num\"]")
                    .text().replace(".", ""));
            if (count.get() == index) {
                count.getAndIncrement();
                arrayStation.add(element.selectFirst("span[class=\"name\"]").text());
                if (indexLineNumber.get() == 15 && count.get() > index){
                    objectLineAndStation.add(lineNumber[indexLineNumber.get()], arrayStation);
                }
            }  else if(count.get() > index || indexLineNumber.get() == 15){
                JsonArray jsonTemp = arrayStation.deepCopy();
                count.set(2);
                objectLineAndStation.add(lineNumber[indexLineNumber.get()], jsonTemp);
                indexLineNumber.incrementAndGet();
                clearJsonArray(arrayStation);
                arrayStation.add(Objects.requireNonNull(element.selectFirst("span[class=\"name\"]")).text());
            }
        });
        jsonObject.add("Station", objectLineAndStation);
        return jsonObject;
    }

    private void clearJsonArray(JsonArray array) {
        while (array.size() > 0) {
            array.remove(0);
        }
    }
}
