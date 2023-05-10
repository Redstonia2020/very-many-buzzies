package buzzies.commands.notebop;

public class NoteLoopEntry {
    public NoteChannel channel;
    public int tick;

    public NoteLoopEntry(NoteChannel channel, int tick) {
        this.channel = channel;
        this.tick = tick;
    }
}
