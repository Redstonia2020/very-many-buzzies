package buzzies;

import buzzies.commands.notebop.NotebopCommand;
import buzzies.commands.timing.TimingCommand;
import buzzies.settings.BuzziesSettings;
import carpet.CarpetExtension;
import carpet.CarpetServer;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;

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
    public void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandBuildContext) {
        TimingCommand.register(dispatcher);
        NotebopCommand.register(dispatcher);
    }

    @Override
    public Map<String, String> canHasTranslations(String lang) {
        return BuzziesTranslations.getTranslation(lang);
    }

    @Override
    public void onTick(MinecraftServer server) {
        NotebopCommand.onTick(server);
    }
}
