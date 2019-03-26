package io.pivotal.pal.tracker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {

    private Map<Long, TimeEntry> database = new ConcurrentHashMap<>();
    private long maxId = 0;

    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        TimeEntry inserted = timeEntry;
        long id = timeEntry.getId();
        if (timeEntry.getId() < 0) {
            maxId++;
            id = maxId;
            inserted = new TimeEntry(id, timeEntry.getProjectId(), timeEntry.getUserId(), timeEntry.getDate(), timeEntry.getHours());
        }
        database.put(id, inserted);
        return inserted;
    }

    @Override
    public TimeEntry find(long id) {
        return database.get(id);
    }

    @Override
    public List<TimeEntry> list() {
        return new ArrayList<>(database.values());
    }

    @Override
    public TimeEntry update(long id, TimeEntry fields) {
        TimeEntry updated = new TimeEntry(id, fields.getProjectId(), fields.getUserId(), fields.getDate(), fields.getHours());
        if (find(id) == null) {
            return null;
        }
        database.put(id, updated);
        return updated;
    }

    @Override
    public void delete(long id) {
        database.remove(id);
    }
}
