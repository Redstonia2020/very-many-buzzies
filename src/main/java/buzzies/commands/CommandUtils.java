package buzzies.commands;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.text.Text;

public class CommandUtils {
    public static CommandSyntaxException simpleException(String message) {
        return new SimpleCommandExceptionType(Text.literal(message)).create();
    }
}
