package buzzies.commands.notebop;

import buzzies.commands.Execution;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.NoteBlock;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.math.BlockPos;

import static buzzies.commands.CommandUtils.*;
import static net.minecraft.block.NoteBlock.POWERED;
import static net.minecraft.util.Formatting.*;

public class NoteChannelExecution extends Execution {
    private final NoteChannel channel;
    private final BlockPos position;

    public NoteChannelExecution(NoteChannel channel, CommandContext<ServerCommandSource> context) {
        super(context);
        this.channel = channel;
        position = channel.position;
    }

    public void play() {
        NoteBlock block = getNoteBlock();
        if (block != null)
            playNote(block);
    }

    public void manualPlay() throws CommandSyntaxException {
        NoteBlock block = getNoteBlock();
        if (block == null)
            throw simpleException("There isn't any note block at %s".formatted(position.toString()));
        playNote(block);
    }

    private void playNote(NoteBlock block) {
        world.setBlockState(position, getBlockState().with(POWERED, true), Block.NOTIFY_ALL);
        world.setBlockState(position, getBlockState().with(POWERED, false), 0);
        block.playNote(null, getBlockState(), world, position);
    }

    private NoteBlock getNoteBlock() {
        Block block = getBlockState().getBlock();
        if (block instanceof NoteBlock)
            return (NoteBlock) block;
        return null;
    }

    private BlockState getBlockState() {
        return world.getBlockState(position);
    }
}
