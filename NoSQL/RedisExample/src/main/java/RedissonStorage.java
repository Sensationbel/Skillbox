
import redis.clients.jedis.Jedis;
import redis.clients.jedis.resps.Tuple;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static java.lang.System.out;

public class RedissonStorage {

    private final String KEY = "Users";
    private final String QUEUE = "Queue";
    AtomicReference<String> userQueue = new AtomicReference<>("");

    private Jedis jedis;

    public RedissonStorage() {
        jedis = new Jedis();
        clearData();
        createUsers();
    }

    private String getTi() {
        return String.valueOf(new Date());
    }

    private void createUsers() {
        for (int i = 1; i <= 20; i++) {
            jedis.zadd(KEY, i, getTi());
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void printUsers() {
        int counter = 1;
        createQueue();
        String user;
        for (; ; ) {
            if (counter % 10 == 0) {
                createUserWithSubscription();
                user = jedis.lpop(QUEUE);
                out.println("Paid subscription:  " + user);
                jedis.lrem(QUEUE, 0, user);
            } else {
                user = jedis.lpop(QUEUE);
                out.println(user);
            }
            jedis.rpush(QUEUE, user);
            counter++;
        }
    }

    private void createUserWithSubscription() {
        int score = ThreadLocalRandom.current().nextInt(1, 20);

        List<Tuple> listQueue = jedis.zrangeByScoreWithScores(KEY, score, score);
        listQueue.forEach(tuple -> {
            userQueue.set("User " + tuple.getScore() + ", date registration " + tuple.getElement());
            jedis.lpush(QUEUE, String.valueOf(userQueue));
        });
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void createQueue() {
        List<Tuple> listQueue = jedis.zrangeByScoreWithScores(KEY, 1, 20);
        listQueue.forEach(tuple -> {
            userQueue.set("User " + tuple.getScore() + ", date registration " + tuple.getElement());
            jedis.rpush(QUEUE, String.valueOf(userQueue));
        });
    }

    public void clearData() {
        jedis.flushAll();
    }

    public void shutdown() {
        jedis.shutdown();
    }
}
