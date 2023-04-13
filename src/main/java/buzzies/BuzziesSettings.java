package buzzies;

import carpet.api.settings.Rule;

import static carpet.api.settings.RuleCategory.*;

public class BuzziesSettings
{
    public enum BeenestGenerationOptions
    {
        vanilla(false),
        off(true),
        always(true, true),
        alwaysAll(true, true);

        public final boolean cancelsDecorator;
        public final boolean usesModifiedDecorator;

        BeenestGenerationOptions(boolean cancel, boolean modified) {
            this.cancelsDecorator = cancel;
            this.usesModifiedDecorator = modified;
        }

        BeenestGenerationOptions(boolean cancel) {
            this(cancel, false);
        }
    }

    @Rule(categories = {CREATIVE})
    public static BeenestGenerationOptions beenestGeneration = BeenestGenerationOptions.vanilla;
}
