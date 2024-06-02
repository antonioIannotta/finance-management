package finance_management.controller;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.InsertOneResult;
import finance_management.exception.WriteExpenseMongoException;
import finance_management.model.Expense;
import finance_management.model.Income;
import finance_management.mongo.Mongo;
import org.bson.Document;
import org.bson.types.ObjectId;

public class ExpenseController {

    public static final String COLLECTION_NAME = "Expenses";

    public static void insertExpense(Expense expense) throws WriteExpenseMongoException {
        MongoCollection<Document> expenseCollection = Mongo.getCollectionByName(COLLECTION_NAME);
        try {
            InsertOneResult result = expenseCollection.insertOne(new Document()
                    .append("_id", new ObjectId())
                    .append("category", expense.getCategory())
                    .append("amount", Float.toString(expense.getAmount()))
                    .append("description", expense.getDescription())
                    .append("date", expense.getDate().toString())
            );
        } catch (MongoException mongoException) {
            throw new WriteExpenseMongoException("Errore nella scrittura della spesa su MongoDB!");
        }
    }
}

