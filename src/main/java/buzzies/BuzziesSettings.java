package buzzies;

import carpet.api.settings.Rule;

import static carpet.api.settings.RuleCategory.*;

public class BuzziesSettings
{
    public enum BeenestGenerationOptions
    {
        VANILLA(false, false),
        OFF(true, false),
        ALWAYS(true, true),
        ALWAYS_ALL(true, true);

        public final boolean cancelsDefaultDecorator;
        public final boolean usesModifiedDecorator;

        BeenestGenerationOptions(boolean cancel, boolean modified) {
            this.cancelsDefaultDecorator = cancel;
            this.usesModifiedDecorator = modified;
        }
    }

    @Rule(categories = {CREATIVE})
    public static BeenestGenerationOptions beenestGeneration = BeenestGenerationOptions.VANILLA;
}
