import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class ConnectingToMongoDB {

    private static final String HOST ="mongodb://localhost:27017";
    private static final String DATABASE_NAME = "ShopsWithMarshmallows" ;

    private  MongoCollection<Document> collectionShops;
    private MongoCollection<Document> collectionProducts;

    public ConnectingToMongoDB() {
        MongoClient mongoClient = MongoClients.create(HOST);
        MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
        collectionShops = database.getCollection("shops");
        collectionProducts = database.getCollection("products");
        collectionShops.drop();
        collectionProducts.drop();
    }

    public MongoCollection<Document> getCollectionShops() {
        return collectionShops;
    }

    public MongoCollection<Document> getCollectionProducts() {
        return collectionProducts;
    }
}
