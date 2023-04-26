package buzzies.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import static buzzies.utils.CommandUtils.*;
import static com.mojang.brigadier.arguments.StringArgumentType.*;
import static net.minecraft.server.command.CommandManager.*;
import static net.minecraft.util.Formatting.*;

public class TimingCommand {
    private static long gameTimeStart;

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralArgumentBuilder<ServerCommandSource> builder = literal("timing")
                .executes(context -> defaultMessage(context.getSource()))
                .then(literal("start")
                        .then(argument("message", greedyString())
                                .executes(context -> start(getString(context, "message"), context.getSource()))))
                .then(literal("log")
                        .then(argument("message", greedyString())
                                .executes(context -> log(getString(context, "message"), context.getSource()))))
                .then(literal("restart")
                        .then(argument("message", greedyString())
                                .executes(context -> restart(getString(context, "message"), context.getSource()))));

        dispatcher.register(builder);
    }

    private static int defaultMessage(ServerCommandSource source) {
        source.sendMessage(Text.literal("* You are lost in the timing command"));
        return 1;
    }

    private static int start(String message, ServerCommandSource source) {
        String print = "[%s] 0 - %s".formatted(getGameTime(source), message);
        broadcast(source, print, GREEN);

        gameTimeStart = getGameTime(source);

        return 1;
    }

    private static int log(String message, ServerCommandSource source) {
        String print = "[%s] %s - %s".formatted(getGameTime(source), getTimeSinceStart(source), message);
        broadcast(source, print);

        return 1;
    }

    private static int restart(String message, ServerCommandSource source) {
        String print = "[%s] %s - %s".formatted(getGameTime(source), getTimeSinceStart(source), message);
        broadcast(source, print, GREEN);

        gameTimeStart = getGameTime(source);

        return 1;
    }

    private static long getGameTime(ServerCommandSource source) {
        return source.getWorld().getTime();
    }

    private static long getTimeSinceStart(ServerCommandSource source) {
        return getGameTime(source) - gameTimeStart;
    }
}
