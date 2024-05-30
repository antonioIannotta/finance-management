package finance_management.utils;

import finance_management.category.Category;

public class FinanceManagementUtils {

    public static boolean isCategoryValid(String category, Category[] categories) {
        for (Category c : categories) {
            if (c.toString().equals(category.toUpperCase())) {
                return true;
            }
        }
        return false;
    }
}
