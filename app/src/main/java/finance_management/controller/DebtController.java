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

import java.time.LocalDateTime;
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

    public static void payInstalments(int numberInstalments, DebtCategory debtCategory) {
        //TODO
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
        //TODO
    }

}
