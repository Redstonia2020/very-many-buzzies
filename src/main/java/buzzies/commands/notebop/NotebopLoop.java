package buzzies.commands.notebop;

import java.util.ArrayList;
import java.util.List;

public class NotebopLoop {
    public int cycleTime;
    public List<NotebopLoopEntry> entries = new ArrayList<>();

    public NotebopLoop(int cycleTime) {
        this.cycleTime = cycleTime;
    }
}
