import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class ParsingHTML {

    private Document document;

    public ParsingHTML(String url) throws IOException {
        document = Jsoup.connect(url).get();
    }

    public String parsLine() throws IOException {
        StringBuilder lineBuilder = new StringBuilder();
        Elements elements = document.select("span[data-line]");
        elements.forEach(element -> lineBuilder.append(element.attr("data-line") + " ")
                .append(element.text())
                .append("\n"));
        return lineBuilder.toString();
    }

    public String parsStation() {
        StringBuilder stationBuilder = new StringBuilder();
        Elements elements = document.select("div[data-line]");
        elements.forEach(element -> stationBuilder
                .append(element.attr("data-line")
                        + "\n" + element.select("span[class=\"name\"]").html())
                .append("\n"));
        return stationBuilder.toString();
    }
}
