package buzzies;

import carpet.api.settings.Rule;

import static carpet.api.settings.RuleCategory.*;

public class BuzziesSettings
{
    public enum BeenestGenerationOptions
    {
        VANILLA, OFF, ALWAYS, ALWAYS_ALL;

        public boolean cancelsDefaultDecorator() {
            return this != VANILLA;
        }

        public boolean usesModifiedDecorator() {
            return this == ALWAYS || this == ALWAYS_ALL;
        }
    }

    @Rule(categories = {CREATIVE})
    public static BeenestGenerationOptions beenestGeneration = BeenestGenerationOptions.VANILLA;
}
