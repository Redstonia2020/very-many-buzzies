package buzzies.utils;

import net.minecraft.server.PlayerManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class CommandUtils {
    public static void broadcast(ServerCommandSource source, String message, Formatting... format) {
        PlayerManager playerManager = source.getServer().getPlayerManager();
        playerManager.broadcast(Text.literal(message).formatted(format), false);
    }
}
