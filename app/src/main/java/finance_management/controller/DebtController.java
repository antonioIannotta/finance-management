package finance_management.controller;

import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import finance_management.exception.WriteDebtMongoException;
import finance_management.model.Debt;
import finance_management.model.category.DebtCategory;
import finance_management.mongo.Mongo;
import org.bson.Document;
import org.bson.types.ObjectId;

import javax.print.Doc;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class DebtController {

    public static final String COLLECTION_NAME = "Debt";

    public static final MongoCollection<Document> debtCollection = Mongo.getCollectionByName(COLLECTION_NAME);

    public static void insertDebt(Debt debt) throws WriteDebtMongoException {
        try {
            debtCollection.insertOne(new Document()
                    .append("_id", new ObjectId())
                    .append("original_amount", Float.toString(debt.getOriginalAmount()))
                    .append("remaining_amount", Float.toString(debt.getRemainingAmount()))
                    .append("remaining_instalments", Integer.toString(debt.getRemainingInstalments()))
                    .append("instalments_amount", Float.toString(debt.getInstalmentAmount()))
                    .append("description", debt.getDescription().toLowerCase())
                    .append("debt_category", debt.getDebtCategory().toString().toLowerCase())
                    .append("payments_date", debt.getPaymentsDate())
            );
        } catch (MongoException mongoException) {
            throw new WriteDebtMongoException("Errore nel salvataggio del debito su Mongo!");
        }
    }

    public static Debt retrieveDebtByCategory(DebtCategory debtCategory) {
        Document searchQuery = new Document();
        searchQuery.put("debt_category", debtCategory.toString().toLowerCase());

        return returnDebtFromQuery(searchQuery);
    }

    public static void payInstalments(int numberInstalments, DebtCategory debtCategory, LocalDateTime date) {
        var debt = retrieveDebtByCategory(debtCategory);
        float paymentAmount = debt.getInstalmentAmount() * numberInstalments;
        debt.setRemainingAmount(debt.getRemainingAmount() - paymentAmount);
        debt.setRemainingInstalments(debt.getRemainingInstalments() - numberInstalments);
        debt.setInstalmentAmount(debt.getRemainingAmount() / debt.getRemainingInstalments());
        var updatedMap = debt.getPaymentsDate();
        updatedMap.put(date, paymentAmount);
        debt.setPaymentsDate(updatedMap);

        updateDebt(debtCategory, debt);
    }

    public static void updateDebt(DebtCategory debtCategory, Debt debt) {
        var updatedDebtDocument = getDocumentDebt(debt);
        var searchQuery = new Document().append("debt_category", debtCategory.toString().toLowerCase());

        Document updateObject = new Document();
        updateObject.put("$set", updatedDebtDocument);

        debtCollection.updateOne(searchQuery, updateObject);
    }

    private static Document getDocumentDebt(Debt debt) {
        var searchQuery = new Document();
        var updatedDocument = new Document();
        searchQuery.put("debt_category", debt.getDebtCategory().toString().toLowerCase());
        FindIterable<Document> cursor = debtCollection.find(searchQuery);
        try (final MongoCursor<Document> cursorIterator = cursor.cursor()) {
            if (cursorIterator.hasNext()) {
                var debtDocument = cursorIterator.next();
                updatedDocument.put("_id", debtDocument.get("_id"));
            }
        }
        updatedDocument.put("debt_category", debt.getDebtCategory().toString().toLowerCase());
        updatedDocument.put("description", debt.getDescription().toLowerCase());
        updatedDocument.put("original_amount", Float.toString(debt.getOriginalAmount()));
        updatedDocument.put("remaining_amount", Float.toString(debt.getRemainingAmount()));
        updatedDocument.put("instalments_amount", Float.toString(debt.getInstalmentAmount()));
        updatedDocument.put("remaining_instalments", Integer.toString(debt.getRemainingInstalments()));
        updatedDocument.put("payments_date", getDocumentFromMap(debt.getPaymentsDate()));

        return updatedDocument;
    }

    private static Debt returnDebtFromQuery(Document query) {
        Debt debtFromQuery = new Debt();
        FindIterable<Document> cursor = debtCollection.find(query);
        try (final MongoCursor<Document> cursorIterator = cursor.cursor()) {
            if (cursorIterator.hasNext()) {
                var debtDocument = cursorIterator.next();
                debtFromQuery.setDebtCategory(DebtCategory.valueOf(debtDocument.get("debt_category").toString()));
                debtFromQuery.setDescription(debtDocument.get("description").toString());
                debtFromQuery.setOriginalAmount(Float.parseFloat(debtDocument.get("original_amount").toString()));
                debtFromQuery.setRemainingAmount(Float.parseFloat(debtDocument.get("remaining_amount").toString()));
                debtFromQuery.setInstalmentAmount(Integer.parseInt(debtDocument.get("instalmentAmount").toString()));
                debtFromQuery.setPaymentsDate(returnMapFromDocument((Document) debtDocument.get("payments_date")));
            }
        }

        return debtFromQuery;
    }

    private static Map<LocalDateTime, Float> returnMapFromDocument(Document paymentsSubDocument) {
        var paymentsMap = new HashMap<LocalDateTime, Float>();
        for (String date : paymentsSubDocument.keySet()) {
            paymentsMap.put(LocalDateTime.parse(date), Float.parseFloat(paymentsSubDocument.get(date).toString()));
        }
        return paymentsMap;
    }

    private static Document getDocumentFromMap(Map<LocalDateTime, Float> paymentsMap) {
        var paymentsDocument = new Document();
        for (LocalDateTime date : paymentsMap.keySet()) {
            paymentsDocument.put(date.toString(), paymentsMap.get(date));
        }
        return paymentsDocument;
    }

}
