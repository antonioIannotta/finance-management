package finance_management.controller;


import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.result.InsertOneResult;
import finance_management.exception.WriteIncomeMongoException;
import finance_management.model.Income;
import finance_management.mongo.Mongo;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.types.ObjectId;

import javax.print.Doc;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class IncomeController {

    public static final String COLLECTION_NAME = "Income";

    public static final MongoCollection<Document> incomeCollection = Mongo.getCollectionByName(COLLECTION_NAME);

    public static void insertIncome(Income income) throws WriteIncomeMongoException {
        try {
             InsertOneResult result = incomeCollection.insertOne(new Document()
                     .append("_id", new ObjectId())
                     .append("category", income.getCategory())
                     .append("amount", Float.toString(income.getAmount()))
                     .append("description", income.getDescription())
                     .append("day", Integer.toString(income.getDate().getDayOfMonth()))
                     .append("month", Integer.toString(income.getDate().getMonthValue()))
                     .append("year", Integer.toString(income.getDate().getYear()))
            );
        } catch (MongoException mongoException) {
            throw new WriteIncomeMongoException("Errore nell'Inserimento del documento di Income in Mongo");
        }
    }

    public static List<Document> getIncomeByYear(int year) {

        Document searchQuery = new Document();
        searchQuery.put("year", Integer.toString(year));

        return documentListFromQuery(searchQuery);
    }

    public static List<Document> getIncomeByYearAndMonth(int year, int month) {
        Document searchQuery = new Document();
        searchQuery.put("month", Integer.toString(month));
        searchQuery.put("year", Integer.toString(year));

        return documentListFromQuery(searchQuery);
    }

    public static Document updateDocument(int id, Document newDocument) {
        Document query = new Document();
        query.put("_id", Integer.toString(id));

        Document updateObject = new Document();
        updateObject.put("$set", newDocument);

        incomeCollection.updateOne(query, updateObject);

        return updateObject;

    }

    public static int getNewId() {
        return getLastDocumentId() + 1;
    }

    private static int getLastDocumentId() {
        return Integer.parseInt(Objects.requireNonNull(incomeCollection.find()
                .sort(Sorts.descending("_id")).first()).get("_id").toString());
    }

    private static List<Document> documentListFromQuery(Document query) {
        List<Document> documentList = new ArrayList<>();
        FindIterable<Document> cursor = incomeCollection.find(query);
        try (final MongoCursor<Document> cursorIterator = cursor.cursor()) {
            while (cursorIterator.hasNext()) {
                documentList.add(cursorIterator.next());
            }
        }

        return documentList;
    }
}


