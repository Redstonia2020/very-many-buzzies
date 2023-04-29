package buzzies.utils;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class CommandUtils {
    public static ServerCommandSource s(CommandContext<ServerCommandSource> c) {
        return c.getSource();
    }

    public static void send(ServerCommandSource source, String rawMessage, Formatting... format) {
        Text message = Text.literal(rawMessage).formatted(format);
        source.sendMessage(message);
    }

    public static void broadcast(ServerCommandSource source, String rawMessage, Formatting... format) {
        Text message = Text.literal(rawMessage).formatted(format);
        PlayerManager playerManager = source.getServer().getPlayerManager();
        playerManager.broadcast(message, false);
    }
}
