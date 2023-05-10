package buzzies.commands.notebop;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.NoteBlock;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import static net.minecraft.block.NoteBlock.POWERED;

public class NoteChannel {
    public final String name;
    public BlockPos position;

    public NoteChannel(String name, BlockPos position) {
        this.name = name;
        this.position = position.toImmutable();
    }

    public NoteChannelExecution newExecution(CommandContext<ServerCommandSource> context) {
        return new NoteChannelExecution(this, context);
    }

    // optimize the below later on

    public void play(ServerWorld world) {
        NoteBlock block = getNoteBlock(world);
        if (block == null)
            return;
        world.setBlockState(position, getBlockState(world).with(POWERED, true), Block.NOTIFY_ALL);
        world.setBlockState(position, getBlockState(world).with(POWERED, false), 0);
        block.playNote(null, getBlockState(world), world, position);
    }

    private NoteBlock getNoteBlock(ServerWorld world) {
        Block block = getBlockState(world).getBlock();
        if (block instanceof NoteBlock)
            return (NoteBlock) block;
        return null;
    }

    private BlockState getBlockState(ServerWorld world) {
        return world.getBlockState(position);
    }
}
