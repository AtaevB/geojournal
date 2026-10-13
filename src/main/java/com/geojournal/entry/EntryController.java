package com.geojournal.entry;

import com.geojournal.journey.Journey;
import jakarta.validation.Valid;
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

}
