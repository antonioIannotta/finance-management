package finance_management.model;

import finance_management.model.category.DebtCategory;

import java.time.LocalDateTime;
import java.util.Map;

public class Debt {

    private float originalAmount;
    private float remainingAmount;
    private int remainingInstalments;
    private String description;
    private DebtCategory debtCategory;
    private Map<LocalDateTime, Float> paymentsDate;
    private float instalmentAmount;

    public Debt()  {}

    public Debt(float originalAmount, float remainingAmount,
                int remainingInstalments, String description, DebtCategory debtCategory,
                Map<LocalDateTime, Float> paymentsDate, float instalmentAmount) {
        this.originalAmount = originalAmount;
        this.remainingAmount = remainingAmount;
        this.remainingInstalments = remainingInstalments;
        this.description = description;
        this.debtCategory = debtCategory;
        this.paymentsDate = paymentsDate;
        this.instalmentAmount = instalmentAmount;
    }

    public float getInstalmentAmount() {
        return instalmentAmount;
    }

    public void setInstalmentAmount(float instalmentAmount) {
        this.instalmentAmount = instalmentAmount;
    }

    public Map<LocalDateTime, Float> getPaymentsDate() {
        return paymentsDate;
    }

    public void setPaymentsDate(Map<LocalDateTime, Float> paymentsDate) {
        this.paymentsDate = paymentsDate;
    }

    public float getOriginalAmount() {
        return originalAmount;
    }

    public float getRemainingAmount() {
        return remainingAmount;
    }

    public int getRemainingInstalments() {
        return remainingInstalments;
    }

    public DebtCategory getDebtCategory() {
        return debtCategory;
    }

    public String getDescription() {
        return description;
    }

    public void setDebtCategory(DebtCategory debtCategory) {
        this.debtCategory = debtCategory;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setOriginalAmount(float originalAmount) {
        this.originalAmount = originalAmount;
    }


    public void setRemainingAmount(float remainingAmount) {
        this.remainingAmount = remainingAmount;
    }

    public void setRemainingInstalments(int remainingInstalments) {
        this.remainingInstalments = remainingInstalments;
    }
}
