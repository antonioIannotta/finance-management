package finance_management.utils;

import java.util.List;

public class FinanceManagementUtils {

    public static boolean isCategoryValid(String category, List<String> categories) {
        for (String c : categories) {
            if (c.equals(category.toUpperCase())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isAmountValid(float amount) {
        float MINIMUM_THRESHOLD = 0.0F;
        return amount > MINIMUM_THRESHOLD;
    }

    public static boolean isDescriptionValid(String description) {
        return !description.isEmpty();
    }
}
