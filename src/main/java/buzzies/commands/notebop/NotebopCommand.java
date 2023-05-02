package buzzies.commands.notebop;

import buzzies.commands.ExecutionFunction;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.server.command.ServerCommandSource;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.mojang.brigadier.arguments.StringArgumentType.*;
import static net.minecraft.command.argument.BlockPosArgumentType.*;
import static net.minecraft.server.command.CommandManager.*;

public class NotebopCommand {
    public static List<NoteChannel> noteChannels = new ArrayList<>();

    public static final String NB_CHANNEL_NAME = "note-block-channel";
    public static final String REGISTRY_COORDINATES = "registry-coordinates";

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("notebop")
                .then(argument(NB_CHANNEL_NAME, word())
                        .suggests((c,b) -> noteChannelSuggestions(b))
                        .then(argument(REGISTRY_COORDINATES, blockPos())
                                .executes(command(NotebopExecution::registerChannel)))));
    }

    private static Command<ServerCommandSource> command(ExecutionFunction<NotebopExecution> function) {
        return (c) -> function.run(new NotebopExecution(c));
    }

    public static CompletableFuture<Suggestions> noteChannelSuggestions(SuggestionsBuilder builder) {
        for (NoteChannel channel : noteChannels)
            builder.suggest(channel.name);
        return builder.buildFuture();
    }
}
