package com.geojournal.entry;


import com.geojournal.journey.JourneyService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntryService {

    private final EntryRepository entryRepository;
    private final JourneyService journeyService;

    public EntryService(EntryRepository entryRepository, JourneyService journeyService){
        this.entryRepository = entryRepository;
        this.journeyService = journeyService;
    }

    public List<Entry> getAllEntries(){
        return entryRepository.findAll();
    }

    public List<Entry> getEntriesByJourneyId(Long journeyId){
        journeyService.getJourneyById(journeyId);
        return entryRepository.findByJourneyId(journeyId);

    }

    public Entry createEntry(Long journeyId, CreateEntryRequest request){

        Entry entry = new Entry(
                request.getTitle(),
                request.getDescription(),
                request.getLatitude(),
                request.getLongitude()
        );
        entry.setJourney(journeyService.getJourneyById(journeyId));
        return entryRepository.save(entry);
    }

}
