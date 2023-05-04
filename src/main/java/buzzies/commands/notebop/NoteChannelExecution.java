package buzzies.commands.notebop;

import buzzies.commands.Execution;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.NoteBlock;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;

import static buzzies.commands.CommandUtils.simpleException;
import static net.minecraft.block.NoteBlock.POWERED;

public class NoteChannelExecution extends Execution {
    private final NoteChannel channel;

    public NoteChannelExecution(NoteChannel channel, CommandContext<ServerCommandSource> context) {
        super(context);
        this.channel = channel;
    }



    public void manualPlay() throws CommandSyntaxException {
        NoteBlock block = getNoteBlock();
        if (block == null)
            throw simpleException("There isn't any note block at %s".formatted(channel.position.toString()));
        world.setBlockState(channel.position, getBlockState().with(POWERED, true), Block.NOTIFY_ALL);
        world.setBlockState(channel.position, getBlockState().with(POWERED, false), 0);
    }

    public void play() {
        NoteBlock block = getNoteBlock();
        world.setBlockState(channel.position, getBlockState().with(POWERED, true), Block.NOTIFY_ALL);
        world.setBlockState(channel.position, getBlockState().with(POWERED, false), 0);
    }

    private NoteBlock getNoteBlock() {
        Block block = getBlockState().getBlock();
        if (block instanceof NoteBlock)
            return (NoteBlock) block;
        return null;
    }

    private BlockState getBlockState() {
        return world.getBlockState(channel.position);
    }
}
