import java.util.stream.Stream;

public class Main {
    public static final String STARTING_URL = "https://skillbox.ru";

    public static void main(String[] args) {
        LinksFounder founder = new LinksFounder(STARTING_URL);
        founder.startSearching();

        prepareAndPrintResult(founder);
        System.out.printf(
                "LinksFounder: Total Links Found: %s\n", LinkFinderAction.linksFoundCounter.get());
    }

    public static void prepareAndPrintResult(LinksFounder founder) {
        founder.getVisitedLinks().remove(STARTING_URL + "/");
        Stream<String> resultStream = founder.getVisitedLinks().keySet().stream().sorted();
        System.out.print("\nResult:\n\t" + STARTING_URL + "/\n");
        if (founder.getMAX_VISITED_LINKS_LENGTH() == -1) {
            resultStream
                    .skip(1L)
                    .forEach(link -> System.out.printf("%s%s\n", getTabs(link), link));
        } else {
            resultStream
                    .limit(founder.getMAX_VISITED_LINKS_LENGTH())
                    .skip(1L)
                    .forEach(link -> System.out.printf("%s%s\n", getTabs(link), link));
        }
    }

    private static String getTabs(String link) {
        if (link.contains("\"")) {
            int numPosChar = link.indexOf('"');
            link = link.substring(0, numPosChar);
        }
        int count = link.replaceAll("[^/]*", "").length() - 2;
        return "\t".repeat(count);
    }
}
