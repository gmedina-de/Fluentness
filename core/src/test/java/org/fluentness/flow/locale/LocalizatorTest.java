package org.fluentness.flow.locale;

import org.fluentness.base.constants.ViewPlaceholders;
import org.junit.Assert;
import org.junit.Test;

public class LocalizatorTest {

    @Test
    public void translate_testStringIsGiven_formattedLocalizationPlaceholderWithTestStringIsGot() {
        Localizator localizator = new Localizator() {
        };

        Assert.assertEquals(
            String.format(ViewPlaceholders.LOCALIZATION_PLACEHOLDER, "test"),
            localizator.translate("test")
        );
    }

    @Test
    public void translate_nullIsGiven_formattedLocalizationPlaceholderWithNullAsStringIsGot() {
        Localizator localizator = new Localizator() {
        };

        Assert.assertEquals(
            String.format(ViewPlaceholders.LOCALIZATION_PLACEHOLDER, "null"),
            localizator.translate(null)
        );
    }

}
