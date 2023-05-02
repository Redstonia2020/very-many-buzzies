package buzzies.commands;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;

import static com.mojang.brigadier.arguments.StringArgumentType.*;
import static com.mojang.brigadier.arguments.IntegerArgumentType.*;
import static net.minecraft.command.argument.BlockPosArgumentType.*;

public class Execution {
    protected final CommandContext<ServerCommandSource> context;
    protected final ServerCommandSource source;

    public Execution(CommandContext<ServerCommandSource> context) {
        this.context = context;
        this.source = context.getSource();
    }

    protected String getStringArgument(String id) {
        return getString(context, id);
    }

    protected int getIntArgument(String id) {
        return getInteger(context, id);
    }

    protected BlockPos getBlockPosArgument(String id) throws CommandSyntaxException {
        return getBlockPos(context, id);
    }

    protected void send(String rawMessage, Formatting... format) {
        Text message = Text.literal(rawMessage).formatted(format);
        source.sendMessage(message);
    }

    protected void broadcast(String rawMessage, Formatting... format) {
        Text message = Text.literal(rawMessage).formatted(format);
        PlayerManager playerManager = source.getServer().getPlayerManager();
        playerManager.broadcast(message, false);
    }
}
