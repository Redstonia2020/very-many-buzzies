package buzzies.commands.notebop;

import buzzies.commands.ExecutionFunction;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import net.minecraft.server.command.ServerCommandSource;

import java.util.*;

import static com.mojang.brigadier.arguments.StringArgumentType.*;
import static com.mojang.brigadier.arguments.IntegerArgumentType.*;
import static net.minecraft.command.argument.BlockPosArgumentType.*;
import static net.minecraft.server.command.CommandManager.*;

public class NotebopCommand {
    public static List<NoteChannel> noteChannels = new ArrayList<>();
    public static Map<String, NoteChannel> nameToChannel = new HashMap<>();
    //eventually allow for creation of more loops
    public static NotebopLoop loop = new NotebopLoop(4);


    public static final String NB_CHANNEL_NAME = "note-block-channel";
    public static final String REGISTRY_COORDINATES = "registry-coordinates";
    public static final String CYCLE_TIME = "cycle-time";
    public static final String EXECUTION_TICK = "execute-at";

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("notebop")
                .then(literal("register")
                        .then(noteChannelNameArgument()
                                .then(argument(REGISTRY_COORDINATES, blockPos())
                                        .executes(command(NotebopExecution::registerChannel)))))
                .then(literal("play")
                        .then(noteChannelNameArgument()
                                .executes(command(NotebopExecution::manualPlay))))
                .then(literal("loop")
                        .then(literal("show")
                                .executes(command(NotebopExecution::showLoop)))
                        .then(literal("setCycle")
                                .then(argument(CYCLE_TIME, integer(0))
                                        .executes(command(NotebopExecution::setCycleTime))))
                        .then(literal("add")
                                .then(argument(NB_CHANNEL_NAME, word())
                                        .then(argument(EXECUTION_TICK, integer(0))
                                                .executes(command(NotebopExecution::addEntry)))))));
    }

    private static Command<ServerCommandSource> command(ExecutionFunction<NotebopExecution> function) {
        return (c) -> function.run(new NotebopExecution(c));
    }

    private static RequiredArgumentBuilder<ServerCommandSource, String> noteChannelNameArgument() {
        return argument(NB_CHANNEL_NAME, word())
                .suggests((c,b) -> {
                    for (NoteChannel channel : noteChannels)
                        b.suggest(channel.name);
                    return b.buildFuture();
                });
    }
}
