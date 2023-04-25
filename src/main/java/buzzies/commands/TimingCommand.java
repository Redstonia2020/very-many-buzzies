package buzzies.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import static com.mojang.brigadier.arguments.StringArgumentType.*;
import static net.minecraft.server.command.CommandManager.*;

public class TimingCommand {
    private static long gameTimeStart;

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralArgumentBuilder<ServerCommandSource> builder = literal("timing")
                .executes(context -> defaultMessage(context.getSource()))
                .then(literal("start")
                        .executes(context -> startTiming("start", context.getSource())))
                        .then(argument("message", greedyString())
                                .executes(context -> startTiming(getString(context, "message"), context.getSource())))
                .then(literal("log")
                        .executes(context -> logTiming("log", context.getSource()))
                        .then(argument("message", greedyString())
                                .executes(context -> logTiming(getString(context, "message"), context.getSource()))))
                .then(literal("restart")
                        .executes(context -> restartTiming("end", context.getSource()))
                        .then(argument("message", greedyString())
                                .executes(context -> restartTiming(getString(context, "message"), context.getSource()))));

        dispatcher.register(builder);
    }

    private static int defaultMessage(ServerCommandSource source) {
        source.sendMessage(Text.literal("* You are lost in the timing command"));
        return 1;
    }

    private static int startTiming(String message, ServerCommandSource source) {
        gameTimeStart = getGameTime(source);
        broadcast("[%s] 0 - %s".formatted(getGameTime(source), message), source);
        return 1;
    }

    private static int logTiming(String message, ServerCommandSource source) {
        broadcast("[%s] %s - %s".formatted(getGameTime(source), getGameTime(source) - gameTimeStart, message), source);
        return 1;
    }

    private static int restartTiming(String message, ServerCommandSource source) {
        broadcast("[%s] %s - %s".formatted(getGameTime(source), getGameTime(source) - gameTimeStart, message), source);
        gameTimeStart = getGameTime(source);
        return 1;
    }

    private static void broadcast(String message, ServerCommandSource source) {
        PlayerManager playerManager = source.getServer().getPlayerManager();
        playerManager.broadcast(Text.literal(message), false);
    }

    private static long getGameTime(ServerCommandSource source) {
        return source.getWorld().getTime();
    }
}
