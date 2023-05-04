package buzzies.commands.notebop;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.NoteBlock;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import static buzzies.commands.CommandUtils.*;
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

    public NoteBlock getBlock(ServerWorld world) {
        Block block = getBlockState(world).getBlock();
        if (block instanceof NoteBlock)
            return (NoteBlock) block;
        return null;
    }

    public BlockState getBlockState(ServerWorld world) {
        return world.getBlockState(position);
    }
}
