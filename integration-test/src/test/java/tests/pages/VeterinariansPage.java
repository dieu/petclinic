package tests.pages;

import net.thucydides.core.annotations.DefaultUrl;
import net.thucydides.core.pages.PageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.Map;

import static net.thucydides.core.pages.components.HtmlTable.rowsFrom;

/**
 * @author Alimenkou Mikalai
 * @version 1.0
 */
@DefaultUrl("/vets")
public class VeterinariansPage extends PageObject {
    @FindBy(tagName = "table")
    private WebElement veterinariesTable;

    public VeterinariansPage(final WebDriver driver) {
        super(driver);
    }

    public List<Map<Object, String>> getAllExistingVeterinarians() {
        return rowsFrom(veterinariesTable);
    }
}
