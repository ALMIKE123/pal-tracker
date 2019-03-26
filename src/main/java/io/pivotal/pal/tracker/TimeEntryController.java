package io.pivotal.pal.tracker;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {

    private final TimeEntryRepository repository;
    private final ResponseEntity<TimeEntry> NOT_FOUND = new ResponseEntity<>(HttpStatus.NOT_FOUND);

    public TimeEntryController(TimeEntryRepository timeEntryRepository) {
        this.repository = timeEntryRepository;
    }

    @PostMapping
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntryToCreate) {
        TimeEntry timeEntry = repository.create(timeEntryToCreate);
        if (timeEntry != null) {
            return new ResponseEntity<>(timeEntry, HttpStatus.CREATED);
        }
        return NOT_FOUND;
    }

    @GetMapping("/{timeEntryId}")
    public ResponseEntity<TimeEntry> read(@PathVariable Long timeEntryId) {
        if (timeEntryId == null) {
            return NOT_FOUND;
        }

        TimeEntry timeEntry = repository.find(timeEntryId);
        if (timeEntry != null) {
            return new ResponseEntity<>(timeEntry, HttpStatus.OK);
        }

        return NOT_FOUND;
    }

    @RequestMapping
    public ResponseEntity<List<TimeEntry>> list() {
        return new ResponseEntity<>(repository.list(), HttpStatus.OK);
    }

    @PutMapping("/{timeEntryId}")
    public ResponseEntity update(@PathVariable Long timeEntryId, @RequestBody TimeEntry entry) {
        TimeEntry timeEntry = repository.update(timeEntryId, entry);
        if (timeEntry != null) {
            return new ResponseEntity<>(timeEntry, HttpStatus.OK);
        }

        return NOT_FOUND;
    }

    @DeleteMapping("/{timeEntryId}")
    public ResponseEntity<TimeEntry> delete(@PathVariable Long timeEntryId) {
        repository.delete(timeEntryId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
