package integration;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.junit.Assert.assertEquals;

public class ByTextTest extends IntegrationTest {
  @Before
  public void openTestPage() {
    openFile("page_with_selects_without_jquery.html");
  }

  @Test
  public void userCanFindElementByText() {
    $(byText("Page without JQuery")).shouldHave(text("Page without JQuery"));
    $(byText("Dropdown list")).shouldHave(text("Dropdown list"));
    $(byText("@livemail.ru")).shouldHave(text("@livemail.ru"));
  }

  @Test
  public void spacesInTextAreIgnored() {
    $(byText("L'a Baskerville")).shouldHave(text("L'a Baskerville"));
    $(withText("L'a Baskerville")).shouldHave(text("L'a Baskerville"));
  }

  @Test
  public void canFindElementByTextInsideParentElement() {
    assertEquals(2, $$($("#multirowTable"), byText("Chack")).size());

    assertEquals(1, $$($("#multirowTable tr"), byText("Chack")).size());
    assertEquals("first_row", $("#multirowTable tr").find(byText("Chack")).getAttribute("class"));
  }

  @Test
  public void canFindElementContainingText() {
    $(withText("without")).shouldHave(text("Page without JQuery"));
    $(withText("Dropdown")).shouldHave(text("Dropdown list"));
    $(withText("@livemail.r")).shouldHave(text("@livemail.ru"));
  }

  @Test
  public void canFindElementContainingTextInsideParentElement() {
    assertEquals(2, $$($("#multirowTable"), withText("Cha")).size());

    assertEquals(1, $$($("#multirowTable tr"), withText("ack")).size());
    assertEquals("second_row", $("#multirowTable tr", 1).find(withText("hac")).getAttribute("class"));
  }

  @Test @Ignore
  public void canFindElementsByI18nText() {
    $(byText("Маргарита")).shouldHave(text("Маргарита"));
    $(withText("Марг")).shouldHave(text("Маргарита"));
    $("#radioButtons").find(byText("Кот \"Бегемот\"")).click();

    assertEquals(1, $$($("#radioButtons"), withText("Theodor Woland")).size());

    assertEquals(3, $$($("#radioButtons"), withText("Я")).size());
    assertEquals(1, $$($("#radioButtons input"), withText("Я ")).size());
  }

  @Test
  public void quotesInText() {
    $(byText("Arnold \"Schwarzenegger\"")).shouldBe(visible);
    $("#hero").find(byText("Arnold \"Schwarzenegger\"")).shouldBe(visible);
    $("#apostrophes-and-quotes").find(By.linkText("Options with 'apostrophes' and \"quotes\"")).click();
  }
}
