package finance_management.controller;


import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.InsertOneResult;
import finance_management.exception.WriteIncomeMongoException;
import finance_management.model.Income;
import finance_management.mongo.Mongo;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

public class IncomeController {

    public static final String COLLECTION_NAME = "Income";

    public static void insertIncome(Income income) throws WriteIncomeMongoException {
        MongoCollection<Document> incomeCollection = Mongo.getCollectionByName(COLLECTION_NAME);
        try {
             InsertOneResult result = incomeCollection.insertOne(new Document()
                .append("_id", new ObjectId())
                .append("category", income.getCategory())
                .append("amount", Float.toString(income.getAmount()))
                .append("description", income.getDescription())
                .append("date", income.getDate().toString())
            );
        } catch (MongoException mongoException) {
            throw new WriteIncomeMongoException("Errore nell'Inserimento del documento di Income in Mongo");
        }
    }
}
