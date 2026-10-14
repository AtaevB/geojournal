package com.geojournal.entry;

import com.geojournal.journey.Journey;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EntryController {

    private final EntryService entryService;
    public EntryController(EntryService entryService){ this.entryService = entryService; }

    @PostMapping("/api/journeys/{journeyId}/entries")
    public Entry createEntry(@Valid @RequestBody CreateEntryRequest request, @PathVariable Long journeyId) {
        return entryService.createEntry(journeyId, request);
    }

    @GetMapping("/api/journeys/{journeyId}/entries")
    public List<Entry> getEntriesByJourneyId(@PathVariable Long journeyId){
        return entryService.getEntriesByJourneyId(journeyId);
    }

    @GetMapping("/api/journeys/{journeyId}/entries/{entryId}")
    public Entry getEntryById(@PathVariable Long journeyId, @PathVariable Long entryId){
        return entryService.getEntryById(journeyId, entryId);
    }

    @DeleteMapping("/api/journeys/{journeyId}/entries/{entryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEntry(@PathVariable Long journeyId, @PathVariable Long entryId){

        entryService.deleteEntry(journeyId, entryId);

    }

    @PatchMapping("/api/journeys/{journeyId}/entries/{entryId}")
    public Entry updateEntry(@PathVariable Long journeyId,
                             @PathVariable Long entryId,
                             @RequestBody UpdateEntryRequest request){
        return entryService.updateEntry(journeyId, entryId, request);
    }

}
