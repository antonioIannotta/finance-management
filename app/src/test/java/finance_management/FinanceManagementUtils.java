package finance_management;

import finance_management.model.Category;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FinanceManagementUtils {

    @Test
    public void testCategoryValid() {
        String categoryTest = "grocery";
        var isCategoryInCategoryEnum = false;
        for (Category category : Category.values()) {
            if (category.toString().equals(categoryTest.toUpperCase())) {
                isCategoryInCategoryEnum = true;
                break;
            }
        }
        assertTrue(isCategoryInCategoryEnum);
    }

    @Test
    public void testCategoryInvalid() {
        String categoryTest = "shopping";
        var isCategoryInCategoryEnum = false;
        for (Category category : Category.values()) {
            if (category.toString().equals(categoryTest.toUpperCase())) {
                isCategoryInCategoryEnum = true;
                break;
            }
        }
        assertFalse(isCategoryInCategoryEnum);
    }
}