package buzzies.commands.notebop;

import buzzies.commands.Execution;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.math.BlockPos;

import static buzzies.commands.notebop.NotebopCommand.*;

public class NotebopExecution extends Execution {
    public NotebopExecution(CommandContext<ServerCommandSource> context) {
        super(context);
    }

    public int registerChannel() throws CommandSyntaxException {
        String channel = getStringArgument(NB_CHANNEL_NAME);
        BlockPos pos = getBlockPosArgument(REGISTRY_COORDINATES);

        noteChannels.add(new NoteChannel(channel, pos));

        return 1;
    }
}
