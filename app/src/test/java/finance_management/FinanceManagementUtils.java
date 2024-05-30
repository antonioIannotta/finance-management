package finance_management;

import finance_management.category.Category;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.Arrays;

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