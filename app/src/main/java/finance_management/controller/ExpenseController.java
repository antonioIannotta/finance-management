package finance_management.controller;

import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.result.InsertOneResult;
import finance_management.exception.WriteExpenseMongoException;
import finance_management.model.Expense;
import finance_management.model.Income;
import finance_management.mongo.Mongo;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class ExpenseController {

    public static final String COLLECTION_NAME = "Expenses";

    public static final MongoCollection<Document> expenseCollection = Mongo.getCollectionByName(COLLECTION_NAME);

    public static void insertExpense(Expense expense) throws WriteExpenseMongoException {

        try {
            InsertOneResult result = expenseCollection.insertOne(new Document()
                    .append("_id", new ObjectId())
                    .append("category", expense.getCategory())
                    .append("amount", Float.toString(expense.getAmount()))
                    .append("description", expense.getDescription())
                    .append("day", Integer.toString(expense.getDate().getDayOfMonth()))
                    .append("month", Integer.toString(expense.getDate().getMonthValue()))
                    .append("year", Integer.toString(expense.getDate().getYear()))
            );
        } catch (MongoException mongoException) {
            throw new WriteExpenseMongoException("Errore nella scrittura della spesa su MongoDB!");
        }
    }

    public static List<Document> getExpenseByYear(int year) {
        Document searchQuery = new Document();
        searchQuery.put("year", Integer.toString(year));

        return documentListFromQuery(searchQuery);
    }

    public static List<Document> getExpenseByYearAndMonth(int year, int month) {
        Document searchQuery = new Document();
        searchQuery.put("month", Integer.toString(month));
        searchQuery.put("year", Integer.toString(year));

        return documentListFromQuery(searchQuery);
    }

    public static List<Document> getExpenseByCategory(String category) {
        Document query = new Document();
        query.put("category", category);

        return documentListFromQuery(query);
    }

    private static List<Document> documentListFromQuery(Document query) {
        List<Document> documentList = new ArrayList<>();
        FindIterable<Document> cursor = expenseCollection.find(query);
        try (final MongoCursor<Document> cursorIterator = cursor.cursor()) {
            while (cursorIterator.hasNext()) {
                documentList.add(cursorIterator.next());
            }
        }

        return documentList;
    }
}

