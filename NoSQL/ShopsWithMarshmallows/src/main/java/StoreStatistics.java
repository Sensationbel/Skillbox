import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.Arrays;

public class StoreStatistics {

    public static void getStatistics(MongoCollection<Document> collectionShops) {
        Bson lookup = Aggregates.lookup("products"
                , "product"
                , "_id"
                , "product_list");
        Bson unwind = Aggregates.unwind("$product");
        Bson math = Aggregates.match(Filters.lt("product.price", 100));
        System.out.println("---------------------------");
        countProductsInShop(lookup, unwind, collectionShops);
        System.out.println("---------------------------");
        countAveragePrice(lookup, unwind, collectionShops);
        System.out.println("---------------------------");
        outputMaxMinPrice(lookup, unwind, collectionShops);
        System.out.println("---------------------------");
        countProductsCheaperThanPrice(lookup, unwind, math, collectionShops);
    }

    private static void countProductsCheaperThanPrice(Bson lookup, Bson unwind, Bson math, MongoCollection<Document> collectionShops) {
        Bson group = Aggregates.group("$name"
                , Accumulators.sum("countFilter", 1));
        collectionShops
                .aggregate(Arrays.asList(lookup, unwind, math, group))
                .forEach(doc ->
                        System.out.println("Количество товаров в магазине "
                                + doc.get("_id")
                                + ", дешевле 100 - "
                                + doc.get("countFilter")
                                + " шт."));
    }

    private static void outputMaxMinPrice(Bson lookup, Bson unwind, MongoCollection<Document> collectionShops) {
        Bson group = Aggregates.group("$name"
                , Accumulators.max("maxPrice", "$product.price"),
                Accumulators.min("minPrice", "$product.price"));
        collectionShops
                .aggregate(Arrays.asList(lookup, unwind, group))
                .forEach(doc -> System.out.println("Магазин " + doc.get("_id")
                                + ": \n\tмаксимальная цена - "
                                + doc.get("maxPrice")
                                + " руб." + "\n\t"
                                + "минимальная цена - "
                                + doc.get("minPrice")
                                + " руб."));
    }

    private static void countAveragePrice(Bson lookup, Bson unwind, MongoCollection<Document> collectionShops) {
        Bson group = Aggregates.group("$name",
                Accumulators.avg("avgPrice", "$product.price"));
        collectionShops
                .aggregate(Arrays.asList(lookup, unwind, group))
                .forEach(doc -> System.out.println("Магазин " + doc.get("_id")
                        + ", средняя цена - "
                        + doc.get("avgPrice")
                        + " руб."));
    }

    private static void countProductsInShop(Bson lookup, Bson unwind, MongoCollection<Document> collectionShops) {
        Bson group = Aggregates.group("$name"
                , Accumulators.sum("countAll", 1));
        collectionShops.aggregate(Arrays.asList(lookup, unwind, group))
                .forEach(doc -> System.out.println("Количество товаров в магазине "
                        + doc.get("_id")
                        + " - "
                        + doc.get("countAll")
                        + " шт."));
    }
}
