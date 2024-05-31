package finance_management.model;

import finance_management.category.Category;
import org.checkerframework.checker.units.qual.C;
import java.time.LocalDateTime;

import java.util.Arrays;

import static finance_management.utils.FinanceManagementUtils.isCategoryValid;

public class Income {

    private Category category;
    private float amount;
    private String description;
    private LocalDateTime date;

    private final Category[] categories = new Category[] {
            Category.SALARY,
            Category.GIFT,
            Category.SAVINGS
    };

    public Income(String category, float amount, String description, LocalDateTime date) {
        if (!(isCategoryValid(category, categories) || isAmountValid(amount) || isDescriptionValid(description))) {
            throw new IllegalArgumentException();
        } else {
            this.category = Category.valueOf(category);
            this.amount = amount;
            this.description = description;
            this.date = date;
        }
    }

    public boolean isAmountValid(float amount) {
        float MINIMUM_THRESHOLD = 0.0F;
        return amount > MINIMUM_THRESHOLD;
    }

    public boolean isDescriptionValid(String description) {
        return !description.isEmpty();
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
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
