package buzzies.commands.notebop;

import buzzies.commands.Execution;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.math.BlockPos;

import static buzzies.commands.notebop.NotebopCommand.*;
import static buzzies.commands.CommandUtils.*;
import static net.minecraft.util.Formatting.*;

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
            send("Set position of channel %s to %s.".formatted(channelName, pos.toString()));
        } else {
            NoteChannel newChannel = new NoteChannel(channelName, pos);
            noteChannels.add(newChannel);
            nameToChannel.put(channelName, newChannel);
            send("Created channel %s at %s.".formatted(channelName, pos.toString()));
        }

        return 1;
    }

    public int manualPlay() throws CommandSyntaxException {
        String channelName = getStringArgument(NB_CHANNEL_NAME);

        NoteChannel channel = getChannelIfExists(channelName);
        channel.newExecution(context).manualPlay();

        return 1;
    }

    public int showLoop() {
        send("--== Notebop Loop ==--", GREEN);

        int currentTick = 0;
        boolean flip = true;
        for (NotebopLoopEntry entry : loop.entriesByTick()) {
            if (entry.tick > currentTick) {
                currentTick = entry.tick;
                flip = !flip;
                send("%s - %s".formatted(currentTick, entry.channel.name), flip ? GRAY : WHITE);
            } else {
                // yes, this breaks with 2-digit numbers. fix later
                send("   - %s".formatted(entry.channel.name), flip ? GRAY : WHITE);
            }
        }

        return 1;
    }

    public int setCycleTime() {
        int cycleTime = getIntArgument(CYCLE_TIME);

        loop.cycleTime = cycleTime;
        send("Set cycle to %s ticks.".formatted(cycleTime));

        return 1;
    }

    public int addEntry() throws CommandSyntaxException {
        String channelName = getStringArgument(NB_CHANNEL_NAME);
        int executionTick = getIntArgument(EXECUTION_TICK);

        loop.entries.add(new NotebopLoopEntry(getChannelIfExists(channelName), executionTick));
        send("Added entry to loop: channel %s at tick %s".formatted(channelName, executionTick));

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
