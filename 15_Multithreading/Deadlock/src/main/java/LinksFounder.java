import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

public class LinksFounder {

    private final Map<String, Set<String>> visitedLinks = new ConcurrentHashMap<>();
    private static final int MAX_THREADS = Runtime.getRuntime().availableProcessors() * 2;
    private final int MAX_VISITED_LINKS_LENGTH = 200;
    private long startTime = System.currentTimeMillis();

    private final String rootUrl;
    private final ForkJoinPool pool;

    public LinksFounder(String rootUrl) {
        this.rootUrl = fixTail(rootUrl);
        pool = new ForkJoinPool(MAX_THREADS);

    }

    private String fixTail(String link) {
        return link.endsWith("/") ? link : link + "/";
    }

    public void startSearching() {
        LinkFinderAction action = new LinkFinderAction(this.rootUrl, this);
        pool.execute(action);

        do {
            try {
                TimeUnit.MILLISECONDS.sleep(5_000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (!action.isDone());

        if (action.isCompletedNormally()) {
            System.out.println("All tasks completed normally.");
        } else {
            action.getException().printStackTrace();
        }
        printExecutionTime();

        pool.shutdown();
    }

    private void printExecutionTime() {
        long endTime = System.currentTimeMillis();
        System.out.printf("LinksFounder: Start Time: %s\n", new Date(startTime));
        System.out.printf("LinksFounder: Finish Time: %s\n", new Date(endTime));
        System.out.printf("LinksFounder: difference in time: %s\n", (double) (endTime - startTime) / 1000);
        System.out.print("*********************************************************\n");

    }

    public int size() {
        return visitedLinks.size();
    }

    public boolean isVisited(String link) {
        return visitedLinks.containsKey(link);
    }

    public void addVisited(String link, Set<String> children) {
        if (link.contains("\"")) {
            int numPosChar = link.indexOf("\"");
            link = link.substring(0, numPosChar);
        }
        visitedLinks.put(link, children);
    }

    public Map<String, Set<String>> getVisitedLinks() {
        return visitedLinks;
    }

    public int getMAX_VISITED_LINKS_LENGTH() {
        return MAX_VISITED_LINKS_LENGTH;
    }

}
