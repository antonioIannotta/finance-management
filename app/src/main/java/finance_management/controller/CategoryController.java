package finance_management.controller;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import finance_management.mongo.Mongo;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class CategoryController {

    public static final String COLLECTION_NAME = "Category";

    public static final MongoCollection<Document> categoryCollection = Mongo.getCollectionByName(COLLECTION_NAME);


    public static List<String> getCategories() {
        List<String> categoryList = new ArrayList<>();
        FindIterable<Document> cursor = categoryCollection.find();
        try (final MongoCursor<Document> cursorIterator = cursor.cursor()) {
            while (cursorIterator.hasNext()) {
                categoryList.add(cursorIterator.next().get("category").toString());
            }
        }
        return categoryList;
    }

    public static void addCategory(String category) {
        Document newCategory = new Document();
        newCategory.put("category", category);

        categoryCollection.insertOne(newCategory);
    }
}
