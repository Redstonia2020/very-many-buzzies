package buzzies.commands.timing;

import buzzies.commands.CommandExecution;
import buzzies.commands.CommandExecutionRunner;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.ServerCommandSource;

import static buzzies.commands.timing.TimingCommand.*;
import static buzzies.utils.CommandUtils.broadcast;
import static buzzies.utils.CommandUtils.send;
import static net.minecraft.util.Formatting.GREEN;
import static net.minecraft.util.Formatting.RED;

public class TimingCommandExecution extends CommandExecution {
    public TimingCommandExecution(CommandContext<ServerCommandSource> context) {
        super(context);
    }

    public int defaultMessage() {
        send("* You seem kinda lost");
        return 1;
    }

    public int start(String messageArg) {
        String message = getStringArgument(messageArg);

        String print = "[%s] 0 - %s".formatted(getGameTime(), message);
        broadcast(print, GREEN);

        gameTimeStart = getGameTime();

        return 1;
    }

    public int log(String messageArg) {
        String message = getStringArgument(messageArg);

        String print = "[%s] %s - %s".formatted(getGameTime(), getTimeSinceStart(), message);
        broadcast(print);

        return 1;
    }

    public int restart(String messageArg) {
        String message = getStringArgument(messageArg);

        long timeSinceStart = getTimeSinceStart();
        if (!isExpecting) {
            String print = "[%s] %s - %s".formatted(getGameTime(), timeSinceStart, message);
            broadcast(print, GREEN);
        } else if (timeSinceStart != expectedInterval) {
            String print = "[%s] %s (does not match expected interval %s) - %s".formatted(getGameTime(), timeSinceStart, expectedInterval, message);
            broadcast(print, RED);
        }

        gameTimeStart = getGameTime();
        return 1;
    }

    public int setExpectInterval(String intervalArg) {
        int interval = getIntArgument(intervalArg);

        expectedInterval = interval;
        isExpecting = true;
        send("You are expected to yell at me every %s ticks.".formatted(interval));
        return 1;
    }

    public int disableExpecting() {
        isExpecting = false;
        send("Finally free of all the expectations.");
        return 1;
    }

    private long getGameTime() {
        return source.getWorld().getTime();
    }

    private long getTimeSinceStart() {
        return getGameTime() - gameTimeStart;
    }
}
