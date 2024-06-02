package finance_management.mongo;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;


public class Mongo {

    private static final String MONGO_URI = "mongodb://localhost:27017";
    private static final String DB_NAME = "financeManagement";

    public static MongoDatabase getDatabase() {
        return MongoClients.create(MONGO_URI).getDatabase(DB_NAME);
    }

    public static MongoCollection<Document> getCollectionByName(String collectionName) {
        return getDatabase().getCollection(collectionName);
    }

    
}
