package buzzies.commands.timing;

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
        LiteralArgumentBuilder<ServerCommandSource> baseCommand = literal("timing")
                .executes( (c) -> new TimingCommandExecution(c).defaultMessage() )
                .then(literal("start")
                        .then(argument("message", greedyString())
                                .executes(command( (execution) -> execution.start("message") ))))
                .then(literal("log")
                        .then(argument("message", greedyString())
                                .executes(command( (execution) -> execution.log("message") ))))
                .then(literal("restart")
                        .then(argument("message", greedyString())
                                .executes(command( (execution) -> execution.restart("message") ))))
                .then(literal("expects")
                        .then(argument("interval", integer(1))
                                .executes(command( (execution) -> execution.setExpectInterval("interval") ))))
                        .then(literal("none")
                                .executes(command( (execution) -> execution.disableExpecting() )));

        dispatcher.register(baseCommand);
    }

    private static Command<ServerCommandSource> command(CommandExecutionRunner<TimingCommandExecution> runner) {
        return (c) -> runner.run(new TimingCommandExecution(c));
    }
}
