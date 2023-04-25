package buzzies.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import static com.mojang.brigadier.arguments.StringArgumentType.*;
import static net.minecraft.server.command.CommandManager.*;

public class TimingCommand {
    private static long gameTimeStart;

    private static final String START = "start";
    private static final String LOG = "log";
    private static final String RESTART = "restart";

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralArgumentBuilder<ServerCommandSource> builder = literal("timing")
                .executes(context -> defaultMessage(context.getSource()))
                .then(runWithOptionalMessage(START))
                .then(runWithOptionalMessage(LOG))
                .then(runWithOptionalMessage(RESTART));

        dispatcher.register(builder);
    }

    private static LiteralArgumentBuilder<ServerCommandSource> runWithOptionalMessage(String name) {
        return literal(name)
                .executes(context -> runRelevantTimingFunction(name, name, context.getSource()))
                .then(argument("message", greedyString())
                        .executes(context -> runRelevantTimingFunction(name, getString(context, "message"), context.getSource())));
    }

    private static int defaultMessage(ServerCommandSource source) {
        source.sendMessage(Text.literal("* You are lost in the timing command"));
        return 1;
    }

    private static int runRelevantTimingFunction(String name, String message, ServerCommandSource source) {
        if (name == START) {
            startTiming(message, source);
        } else if (name == LOG) {
            logTiming(message, source);
        } else if (name == RESTART) {
            restartTiming(message, source);
        }

        return 1;
    }

    private static void startTiming(String message, ServerCommandSource source) {
        gameTimeStart = getGameTime(source);
        broadcast("[%s] 0 - %s".formatted(getGameTime(source), message), source);
    }

    private static void logTiming(String message, ServerCommandSource source) {
        broadcast("[%s] %s - %s".formatted(getGameTime(source), getGameTime(source) - gameTimeStart, message), source);
    }

    private static void restartTiming(String message, ServerCommandSource source) {
        broadcast("[%s] %s - %s".formatted(getGameTime(source), getGameTime(source) - gameTimeStart, message), source);
        gameTimeStart = getGameTime(source);
    }

    private static void broadcast(String message, ServerCommandSource source) {
        PlayerManager playerManager = source.getServer().getPlayerManager();
        playerManager.broadcast(Text.literal(message), false);
    }

    private static long getGameTime(ServerCommandSource source) {
        return source.getWorld().getTime();
    }
}
