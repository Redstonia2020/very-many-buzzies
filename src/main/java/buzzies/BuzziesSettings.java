package buzzies;

import carpet.api.settings.Rule;

import static carpet.api.settings.RuleCategory.*;

public class BuzziesSettings {
    public static final String BUZZIES = "buzzies";

    public enum BeeNestGenerationOptions {
        VANILLA, OFF, ALWAYS, ALWAYS_ALL;
        public boolean alwaysGenerates() { return this == ALWAYS || this == ALWAYS_ALL; }
    }

    @Rule(categories = {BUZZIES, CREATIVE})
    public static BeeNestGenerationOptions beeNestGeneration = BeeNestGenerationOptions.VANILLA;

    @Rule(categories = {BUZZIES, CREATIVE})
    public static boolean beeNestsGenerateBees = true;

    @Rule(categories = {BUZZIES, CREATIVE})
    public static boolean instantLeafDecay = false;

    @Rule(categories = {BUZZIES, CREATIVE})
    public static int waterFlowRate = 5;

    @Rule(categories = {BUZZIES, CREATIVE})
    public static int lavaFlowRate = 30;

    @Rule(categories = {BUZZIES, CREATIVE})
    public static int lavaFlowRateUltrawarm = 10;
}
