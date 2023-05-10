package buzzies.commands.notebop;

import net.minecraft.server.world.ServerWorld;

public class NoteLoopEntry {
    public NoteChannel channel;
    public int tick;

    public NoteLoopEntry(NoteChannel channel, int tick) {
        this.channel = channel;
        this.tick = tick;
    }

    public void play(ServerWorld world) {
        channel.play(world);
    }
}
