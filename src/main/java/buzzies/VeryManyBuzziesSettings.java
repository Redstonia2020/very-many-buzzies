package buzzies;

import carpet.api.settings.Rule;
import carpet.api.settings.CarpetRule;
import carpet.api.settings.Validator;
import net.minecraft.server.command.ServerCommandSource;

import static carpet.api.settings.RuleCategory.*;

public class VeryManyBuzziesSettings {
    public enum BeenestGenerationOptions { vanilla, off, always, alwaysAll }

    @Rule(categories = {CREATIVE})
    public static BeenestGenerationOptions beenestGeneration = BeenestGenerationOptions.vanilla;
}
