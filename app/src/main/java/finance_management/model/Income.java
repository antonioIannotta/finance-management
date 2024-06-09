package finance_management.model;

import finance_management.controller.CategoryController;

import java.time.LocalDateTime;

import static finance_management.utils.FinanceManagementUtils.isCategoryValid;
import static finance_management.utils.FinanceManagementUtils.isAmountValid;
import static finance_management.utils.FinanceManagementUtils.isDescriptionValid;

public class Income {

    private String category;
    private int id;
    private float amount;
    private String description;
    private LocalDateTime date;

    public Income(int id, String category, float amount, String description, LocalDateTime date) {
        if (!(isCategoryValid(category, CategoryController.getCategories()) || isAmountValid(amount) || isDescriptionValid(description))) {
            throw new IllegalArgumentException();
        } else {
            this.id = id;
            this.category = category;
            this.amount = amount;
            this.description = description;
            this.date = date;
        }
    }

    public String getCategory() {
        return category;
    }

    public int setId;

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
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
