package buzzies.commands.notebop;

import buzzies.commands.Execution;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.math.BlockPos;

import static buzzies.commands.notebop.NotebopCommand.*;
import static buzzies.commands.CommandUtils.*;

public class NotebopExecution extends Execution {
    public NotebopExecution(CommandContext<ServerCommandSource> context) {
        super(context);
    }

    public int registerChannel() throws CommandSyntaxException {
        String channelName = getStringArgument(NB_CHANNEL_NAME);
        BlockPos pos = getBlockPosArgument(REGISTRY_COORDINATES);

        NoteChannel channel = getChannel(channelName);
        if (channel != null) {
            // channel already exists
            channel.position = pos;
        } else {
            NoteChannel newChannel = new NoteChannel(channelName, pos);
            noteChannels.add(newChannel);
            nameToChannel.put(channelName, newChannel);
        }

        return 1;
    }

    public int manualPlay() throws CommandSyntaxException {
        String channelName = getStringArgument(NB_CHANNEL_NAME);

        NoteChannel channel = getChannelIfExists(channelName);
        channel.newExecution(context).manualPlay();

        return 1;
    }

    private NoteChannel getChannel(String name) {
        return nameToChannel.get(name);
    }

    private NoteChannel getChannelIfExists(String name) throws CommandSyntaxException {
        NoteChannel channel = getChannel(name);
        if (channel != null)
            return channel;
        throw simpleException("Note block channel %s does not exist! Please register it first!".formatted(name));
    }
}
