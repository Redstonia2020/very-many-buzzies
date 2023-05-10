package buzzies.commands.notebop;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class NotebopLoop {
    public int cycleTime;
    public List<NotebopLoopEntry> entries = new ArrayList<>();

    public NotebopLoop(int cycleTime) {
        this.cycleTime = cycleTime;
    }

    public List<NotebopLoopEntry> entriesByTick() {
        List<NotebopLoopEntry> sorted = new ArrayList<>(entries);
        sorted.sort(Comparator.comparing(entry -> entry.tick));
        return sorted;
    }

    public List<NotebopLoopEntry> getEntriesForTick(int tick) {
        return entries.stream()
                .filter(entry -> entry.tick == tick)
                .toList();
    }
}
