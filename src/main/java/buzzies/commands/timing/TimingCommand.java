package buzzies.commands.timing;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.server.command.ServerCommandSource;

import java.util.function.Function;

import static com.mojang.brigadier.arguments.StringArgumentType.*;
import static com.mojang.brigadier.arguments.IntegerArgumentType.*;
import static net.minecraft.server.command.CommandManager.*;

public class TimingCommand {
    public static long timeStart;
    public static boolean isExpectingCycle;
    public static int expectedInterval;

    public static final String MESSAGE = "message";
    public static final String INTERVAL = "interval";

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("timing")
                .executes(command(TimingExecution::defaultMessage))
                .then(branch("start", TimingExecution::start))
                .then(branch("log", TimingExecution::log))
                .then(branch("restart", TimingExecution::restart))
                .then(literal("expects")
                        .then(argument(INTERVAL, integer(1))
                                .executes(command(TimingExecution::setExpectInterval))))
                        .then(literal("none")
                                .executes(command(TimingExecution::disableExpecting))));
    }

    private static LiteralArgumentBuilder<ServerCommandSource> branch(String name, Function<TimingExecution, Integer> function) {
        return literal(name)
                .then(argument(MESSAGE, greedyString())
                        .executes(command(function)));
    }

    private static Command<ServerCommandSource> command(Function<TimingExecution, Integer> function) {
        return c -> function.apply(new TimingExecution(c));
    }
}
