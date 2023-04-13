package buzzies;

import buzzies.utils.BuzziesTranslations;
import carpet.CarpetExtension;
import carpet.CarpetServer;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class BuzziesMod implements ModInitializer, CarpetExtension
{
    public static final String MODID = "buzzies";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    @Override
    public void onInitialize()
    {
        LOGGER.info("beenest gaming");
        BuzziesMod.loadExtension();
    }

    public static void loadExtension()
    {
        CarpetServer.manageExtension(new BuzziesMod());
    }

    @Override
    public String version()
    {
        return MODID;
    }

    @Override
    public void onGameStarted()
    {
        CarpetServer.settingsManager.parseSettingsClass(BuzziesSettings.class);
    }

    @Override
    public Map<String, String> canHasTranslations(String lang)
    {
        return BuzziesTranslations.getTranslation(lang);
    }
}
