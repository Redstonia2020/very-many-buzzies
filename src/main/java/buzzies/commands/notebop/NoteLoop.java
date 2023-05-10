package buzzies.commands.notebop;

import net.minecraft.server.MinecraftServer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class NoteLoop {
    public int cycleTime;
    public List<NoteLoopEntry> entries = new ArrayList<>();
    public boolean active = false;
    public int activeTick = 0;

    public NoteLoop(int cycleTime) {
        this.cycleTime = cycleTime;
    }

    public void onTick(MinecraftServer server) {
        if (!active)
            return;
        List<NoteLoopEntry> entriesForTick = getEntriesForTick(activeTick);
        for (NoteLoopEntry entry : entriesForTick) {
            // later let this get any world
            entry.play(server.getOverworld());
        }

        activeTick++;
        if (activeTick >= cycleTime)
            activeTick = 0;
    }

    public void start() {
        active = true;
        activeTick = 0;
    }

    public void stop() {
        active = false;
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
