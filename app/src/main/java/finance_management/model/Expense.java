package finance_management.model;

import finance_management.controller.CategoryController;
import finance_management.utils.FinanceManagementUtils;

import java.time.LocalDateTime;

public class Expense {

    private float amount;
    private String category;
    private String description;
    private LocalDateTime date;

    public Expense(String category, String description, float amount, LocalDateTime date) {
        if (FinanceManagementUtils.isCategoryValid(category, CategoryController.getCategories())
                && FinanceManagementUtils.isAmountValid(amount)
                && FinanceManagementUtils.isDescriptionValid(description)) {
            this.category = category;
            this.description = description;
            this.amount = amount;
            this.date = date;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
