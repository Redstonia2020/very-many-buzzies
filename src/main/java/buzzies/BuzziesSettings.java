package buzzies;

import carpet.api.settings.Rule;

import static carpet.api.settings.RuleCategory.*;

public class BuzziesSettings
{
    public enum BeenestGenerationOptions
    {vanilla, off, always, alwaysAll}

    @Rule(categories = {CREATIVE})
    public static BeenestGenerationOptions beenestGeneration = BeenestGenerationOptions.vanilla;
}
