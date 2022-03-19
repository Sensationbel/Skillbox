import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.List;

public class MongoDbStudents {
    private List<Students> studentsList;
    private MongoCollection<Document> collection;

    public MongoDbStudents() {
        studentsList = new CsvMappedToStudents().parsCsv();
        MongoClient mongoClient = new MongoClient("127.0.0.1", 27017);
        MongoDatabase database = mongoClient.getDatabase("test");
        collection = database.getCollection("Students");
        collection.drop();
    }

    public void addMongoDocument() {
        for (Students student : studentsList) {
            Document studentsInfo = new Document();
            studentsInfo.append("name", student.getName());
            studentsInfo.append("age", student.getAge());
            studentsInfo.append("courses", student.getCourses());
            collection.insertOne(studentsInfo);
        }
    }

    public MongoCollection<Document> getCollection() {
        return collection;
    }
}
