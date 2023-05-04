package buzzies.settings;

import carpet.api.settings.Rule;

import static carpet.api.settings.RuleCategory.*;
import static carpet.api.settings.Validators.*;
import static buzzies.settings.Validators.*;

public class BuzziesSettings {
    public static final String BUZZIES = "buzzies";
    public static final String WHY = "dontevenknowanymore";

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

    @Rule(categories = {BUZZIES, CREATIVE},
          options = {"5"},
          strict = false,
          validators = NonNegativeNumber.class)
    public static int waterFlowRate = 5;

    @Rule(categories = {BUZZIES, CREATIVE},
          options = {"30"},
          strict = false,
          validators = NonNegativeNumber.class)
    public static int lavaFlowRate = 30;

    @Rule(categories = {BUZZIES, CREATIVE},
          options = {"10"},
          strict = false,
          validators = NonNegativeNumber.class)
    public static int lavaFlowRateUltrawarm = 10;

    @Rule(categories = {BUZZIES, CREATIVE},
          options = {"-1"},
          strict = false,
          validators = NonNegativeOrFalse.class)
    public static int xpBottleDropValue = -1;

    @Rule(categories = {BUZZIES, WHY})
    public static boolean zombiesResistDrowning = false;
}
