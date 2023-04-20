package buzzies;

import carpet.CarpetExtension;
import carpet.CarpetServer;

import java.util.Map;

public class CarpetBuzziesExtension implements CarpetExtension {
    @Override
    public String version() {
        return CarpetBuzziesMod.MOD_ID;
    }

    @Override
    public void onGameStarted() {
        CarpetServer.settingsManager.parseSettingsClass(CarpetBuzziesSettings.class);
    }

    @Override
    public Map<String, String> canHasTranslations(String lang) {
        return CarpetBuzziesTranslations.getTranslation(lang);
    }
}
