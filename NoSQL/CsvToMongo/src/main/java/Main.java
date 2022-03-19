import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.conversions.Bson;

import static java.lang.System.out;

public class Main {

    public static final String PATH_TO_CSV = "src/main/resources/mongo.csv";

    public static void main(String[] args) {

        MongoDbStudents mongoDbStudents = new MongoDbStudents();
        mongoDbStudents.addMongoDocument();
        MongoCollection<Document> collection = mongoDbStudents.getCollection();

        out.println("==============================");
        out.printf("Number of students in table:  %d \n", collection.countDocuments());

        BsonDocument query = BsonDocument.parse("{age: {$gt: 40}}");
        out.println("==============================");

        long countStudents = collection.countDocuments(query);
        out.printf("Number of students over 40 years old: %d \n", countStudents);
        out.println("==============================");

        printNameOfOld(collection);
        out.println("==============================");
        printCourses(collection);
        out.println("==============================");
    }

    private static void printCourses(MongoCollection<Document> collection) {
        Bson project = new Document("courses", 1L);
        Bson sort = new Document("age", -1L);
        FindIterable<Document> request = collection
                .find()
                .projection(project)
                .sort(sort)
                .limit((int) 1L);
        Document obj = request.cursor().next();
        var courses = obj.get("courses");
        out.printf("List of courses of the oldest student: %s\n", courses.toString());
    }

    private static void printNameOfOld(MongoCollection<Document> collection) {
        Bson project = new Document("name", 1L);
        Bson sort = new Document("age", 1L);
        FindIterable<Document> request = collection
                .find()
                .projection(project)
                .sort(sort)
                .limit((int) 1L);
        Document obj = request.cursor().next();
        String name = obj.getString("name");
        out.printf("The name of the youngest student: %s \n", name);
    }
}

