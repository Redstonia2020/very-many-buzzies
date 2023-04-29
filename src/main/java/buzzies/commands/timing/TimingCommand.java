package buzzies.commands.timing;

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
                                .executes( (c) -> new TimingCommandExecution(c).start("message") )))
                .then(literal("log")
                        .then(argument("message", greedyString())
                                .executes( (c) -> new TimingCommandExecution(c).log("message") )))
                .then(literal("restart")
                        .then(argument("message", greedyString())
                                .executes( (c) -> new TimingCommandExecution(c).restart("message") )))
                .then(literal("expects")
                        .then(argument("interval", integer(1))
                                .executes( (c) -> new TimingCommandExecution(c).setExpectInterval("interval") ))
                        .then(literal("none")
                                .executes( (c) -> new TimingCommandExecution(c).disableExpecting() )));

        dispatcher.register(baseCommand);
    }
}
