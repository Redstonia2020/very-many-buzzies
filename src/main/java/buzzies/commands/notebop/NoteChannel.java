package buzzies.commands.notebop;

import net.minecraft.util.math.BlockPos;

public class NoteChannel {
    public final String name;
    public BlockPos position;

    public NoteChannel(String name, BlockPos position) {
        this.name = name;
        this.position = position;
    }
}
