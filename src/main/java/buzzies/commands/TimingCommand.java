package buzzies.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import static com.mojang.brigadier.arguments.StringArgumentType.*;
import static com.mojang.brigadier.arguments.IntegerArgumentType.*;
import static net.minecraft.server.command.CommandManager.*;
import static net.minecraft.util.Formatting.*;
import static buzzies.utils.CommandUtils.*;

public class TimingCommand {
    private static long gameTimeStart;

    private static boolean isExpecting;
    private static int expectedInterval;

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralArgumentBuilder<ServerCommandSource> baseCommand = literal("timing")
                .executes(context -> defaultMessage(context.getSource())
                )
                .then(literal("start")
                        .then(argument("message", greedyString())
                                .executes(c -> start(s(c), getString(c, "message"))
                                )))
                .then(literal("log")
                        .then(argument("message", greedyString())
                                .executes(c -> log(s(c), getString(c, "message"))
                                )))
                .then(literal("restart")
                        .then(argument("message", greedyString())
                                .executes(c -> restart(s(c), getString(c, "message"))
                                )))
                .then(literal("expects")
                        .then(argument("interval", integer(1))
                                .executes(c -> setExpectInterval(s(c), getInteger(c, "interval"))
                                ))
                        .then(literal("none")
                                .executes(c -> disableExpecting(s(c)))
                        ));

        dispatcher.register(baseCommand);
    }

    private static int defaultMessage(ServerCommandSource s) {
        send(s, "* You seem kinda lost");
        return 1;
    }

    private static int start(ServerCommandSource s, String message) {
        String print = "[%s] 0 - %s".formatted(getGameTime(s), message);
        broadcast(s, print, GREEN);

        gameTimeStart = getGameTime(s);

        return 1;
    }

    private static int log(ServerCommandSource s, String message) {
        String print = "[%s] %s - %s".formatted(getGameTime(s), getTimeSinceStart(s), message);
        broadcast(s, print);

        return 1;
    }

    private static int restart(ServerCommandSource s, String message) {
        long timeSinceStart = getTimeSinceStart(s);
        if (!isExpecting) {
            String print = "[%s] %s - %s".formatted(getGameTime(s), timeSinceStart, message);
            broadcast(s, print, GREEN);
        } else if (timeSinceStart != expectedInterval) {
            String print = "[%s] %s (does not match expected interval %s) - %s".formatted(getGameTime(s), timeSinceStart, expectedInterval, message);
            broadcast(s, print, RED);
        }

        gameTimeStart = getGameTime(s);
        return 1;
    }

    private static int setExpectInterval(ServerCommandSource s, int interval) {
        expectedInterval = interval;
        isExpecting = true;
        send(s, "You are expected to yell at me every %s ticks.".formatted(interval));
        return 1;
    }

    private static int disableExpecting(ServerCommandSource s) {
        isExpecting = false;
        send(s, "Finally free of all the expectations.");
        return 1;
    }

    private static long getGameTime(ServerCommandSource s) {
        return s.getWorld().getTime();
    }

    private static long getTimeSinceStart(ServerCommandSource s) {
        return getGameTime(s) - gameTimeStart;
    }
}
