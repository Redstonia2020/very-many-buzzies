package buzzies.commands.notebop;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class NoteLoop {
    public int cycleTime;
    public List<NoteLoopEntry> entries = new ArrayList<>();

    public NoteLoop(int cycleTime) {
        this.cycleTime = cycleTime;
    }

    public List<NoteLoopEntry> entriesByTick() {
        List<NoteLoopEntry> sorted = new ArrayList<>(entries);
        sorted.sort(Comparator.comparing(entry -> entry.tick));
        return sorted;
    }

    public List<NoteLoopEntry> getEntriesForTick(int tick) {
        return entries.stream()
                .filter(entry -> entry.tick == tick)
                .toList();
    }
}
