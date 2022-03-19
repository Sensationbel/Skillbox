import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class Main {
    private static final String source = "https://lenta.ru";
    private static final String dest = "Data\\Downloads\\" ;

    public static void main(String[] args) throws IOException {
        parsingUrl(source);
    }


    private static void parsingUrl(String source) throws IOException {
        Document doc = Jsoup.connect(source).get();
        Elements elements =doc.select("img[src~=(?i)\\.(png|jpe?g)]");
        elements.forEach(element -> {
            try {
                String url = element.attr("abs:src");
                Main.downloadFileFromUrl(dest, url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


    private static void downloadFileFromUrl(String dest, String url) throws IOException {
        String fileName = url.substring(url.lastIndexOf("/") + 1);
        String temp = dest + fileName;
        if (!Files.exists(Path.of(dest))) {
            Files.createDirectories(Path.of(dest));
        }
        try(InputStream in = URI.create(url).toURL().openStream()) {
            Files.copy(in, Paths.get(temp), REPLACE_EXISTING);
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }
}
