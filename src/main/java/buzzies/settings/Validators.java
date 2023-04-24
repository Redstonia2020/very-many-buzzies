package buzzies.settings;


import carpet.api.settings.CarpetRule;
import carpet.api.settings.Validator;
import net.minecraft.server.command.ServerCommandSource;

public class Validators {
    public static class NonNegativeOrFalse<T extends Number> extends Validator<T> {
        @Override
        public T validate(ServerCommandSource source, CarpetRule<T> currentRule, T newValue, String string) {
            return newValue.doubleValue() >= -1 ? newValue : null;
        }

        @Override
        public String description() {
            return "Must be 0 or higher. -1 will just set this to be disabled.";
        }
    }
}
