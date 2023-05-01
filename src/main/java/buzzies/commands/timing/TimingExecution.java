package buzzies.commands.timing;

import buzzies.commands.Execution;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.ServerCommandSource;

import static buzzies.commands.timing.TimingCommand.*;
import static net.minecraft.util.Formatting.GREEN;
import static net.minecraft.util.Formatting.RED;

public class TimingExecution extends Execution {
    public TimingExecution(CommandContext<ServerCommandSource> context) {
        super(context);
    }

    public int defaultMessage() {
        send("* You seem kinda lost");
        return 1;
    }

    public int start() {
        String message = getStringArgument(MESSAGE);

        String print = "[%s] 0 - %s".formatted(getGameTime(), message);
        broadcast(print, GREEN);

        timeStart = getGameTime();

        return 1;
    }

    public int log() {
        String message = getStringArgument(MESSAGE);

        String print = "[%s] %s - %s".formatted(getGameTime(), getTimeSinceStart(), message);
        broadcast(print);

        return 1;
    }

    public int restart() {
        String message = getStringArgument(MESSAGE);

        long timeSinceStart = getTimeSinceStart();
        if (!isExpectingCycle) {
            String print = "[%s] %s - %s".formatted(getGameTime(), timeSinceStart, message);
            broadcast(print, GREEN);
        } else if (timeSinceStart != expectedInterval) {
            String print = "[%s] %s (does not match expected interval %s) - %s".formatted(getGameTime(), timeSinceStart, expectedInterval, message);
            broadcast(print, RED);
        }

        timeStart = getGameTime();
        return 1;
    }

    public int setExpectInterval() {
        int interval = getIntArgument(INTERVAL);

        expectedInterval = interval;
        isExpectingCycle = true;
        send("You are expected to yell at me every %s ticks.".formatted(interval));
        return 1;
    }

    public int disableExpecting() {
        isExpectingCycle = false;
        send("Finally free of all the expectations.");
        return 1;
    }

    private long getGameTime() {
        return source.getWorld().getTime();
    }

    private long getTimeSinceStart() {
        return getGameTime() - timeStart;
    }
}
