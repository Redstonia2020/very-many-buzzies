package buzzies;

import buzzies.settings.BuzziesSettings;
import carpet.CarpetExtension;
import carpet.CarpetServer;

import java.util.Map;

public class BuzziesExtension implements CarpetExtension {
    @Override
    public String version() {
        return BuzziesMod.MOD_ID;
    }

    @Override
    public void onGameStarted() {
        CarpetServer.settingsManager.parseSettingsClass(BuzziesSettings.class);
    }

    @Override
    public Map<String, String> canHasTranslations(String lang) {
        return BuzziesTranslations.getTranslation(lang);
    }
}
