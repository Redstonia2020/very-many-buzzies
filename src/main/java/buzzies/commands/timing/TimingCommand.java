package buzzies.commands.timing;

import buzzies.commands.CommandExecutionRunner;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.server.command.ServerCommandSource;

import static com.mojang.brigadier.arguments.StringArgumentType.*;
import static com.mojang.brigadier.arguments.IntegerArgumentType.*;
import static net.minecraft.server.command.CommandManager.*;

public class TimingCommand {
    public static long gameTimeStart;

    public static boolean isExpecting;
    public static int expectedInterval;

    public static final String MESSAGE = "message";
    public static final String INTERVAL = "interval";

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("timing")
                .executes(command(TimingCommandExecution::defaultMessage))
                .then(branch("start", TimingCommandExecution::start))
                .then(branch("log", TimingCommandExecution::log))
                .then(branch("restart", TimingCommandExecution::restart))
                .then(literal("expects")
                        .then(argument(INTERVAL, integer(1))
                                .executes(command(TimingCommandExecution::setExpectInterval))))
                        .then(literal("none")
                                .executes(command(TimingCommandExecution::disableExpecting))));
    }

    private static LiteralArgumentBuilder<ServerCommandSource> branch(String name, CommandExecutionRunner<TimingCommandExecution> runner) {
        return literal(name)
                .then(argument(MESSAGE, greedyString())
                        .executes(command(runner)));
    }

    private static Command<ServerCommandSource> command(CommandExecutionRunner<TimingCommandExecution> runner) {
        return (c) -> runner.run(new TimingCommandExecution(c));
    }
}
