package buzzies.commands.notebop;

public class NotebopLoopEntry {
    public NoteChannel channel;
    public int tick;

    public NotebopLoopEntry(NoteChannel channel, int tick) {
        this.channel = channel;
        this.tick = tick;
    }
}
