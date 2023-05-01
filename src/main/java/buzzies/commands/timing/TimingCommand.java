package buzzies.commands.timing;

import buzzies.commands.CommandExecution;
import buzzies.commands.CommandExecutionRunner;
import com.mojang.brigadier.Command;
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
    public static long gameTimeStart;

    public static boolean isExpecting;
    public static int expectedInterval;

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("timing")
                .executes(command(e -> e.defaultMessage()))
                .then(branch("start", e -> e.start("message")))
                .then(branch("log", e -> e.log("message")))
                .then(branch("restart", e -> e.restart("message")))
                .then(literal("expects")
                        .then(argument("interval", integer(1))
                                .executes(command(e -> e.setExpectInterval("interval")))))
                        .then(literal("none")
                                .executes(command(e -> e.disableExpecting()))));
    }

    private static LiteralArgumentBuilder<ServerCommandSource> branch(String name, CommandExecutionRunner<TimingCommandExecution> runner) {
        return literal(name)
                .then(argument("message", greedyString())
                        .executes(command(runner)));
    }

    private static Command<ServerCommandSource> command(CommandExecutionRunner<TimingCommandExecution> runner) {
        return (c) -> runner.run(new TimingCommandExecution(c));
    }
}
