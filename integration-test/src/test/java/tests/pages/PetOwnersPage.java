package tests.pages;

import net.thucydides.core.annotations.DefaultUrl;
import net.thucydides.core.matchers.BeanMatcher;
import net.thucydides.core.pages.PageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static net.thucydides.core.pages.components.HtmlTable.inTable;

/**
 * @author Alimenkou Mikalai
 * @version 1.0
 */
@DefaultUrl("/owners")
public class PetOwnersPage extends PageObject {
    @FindBy(tagName = "table")
    private WebElement ownersTable;

    public PetOwnersPage(final WebDriver driver) {
        super(driver);
    }

    public boolean isPetOwnerFound(BeanMatcher... matchers) {
        return inTable(ownersTable).containsRowElementsWhere(matchers);
    }
}
