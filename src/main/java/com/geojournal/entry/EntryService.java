package com.geojournal.entry;


import com.geojournal.journey.Journey;
import com.geojournal.journey.JourneyService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    public Entry getEntryById(Long journeyId, Long entryId) {

        journeyService.getJourneyById(journeyId);
        Entry entry = entryRepository.findById(entryId)
                .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Journey not found"
        ));

        if(journeyId.equals(entry.getJourney().getId())){
            return entry;
        } else { throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Entry not found");
        }

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

    public void deleteEntry(Long journeyId, Long entryId){
        Entry entry = getEntryById(journeyId, entryId);
        entryRepository.delete(entry);
    }

    public Entry updateEntry(Long journeyId,
            Long entryId,
            UpdateEntryRequest request
    ) {
        Entry existingEntry = getEntryById(journeyId, entryId);

        if (request.getTitle() != null && request.getTitle().isBlank()){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Title must not be blank"
            );
        }

        if (request.getTitle() != null) {
            existingEntry.setTitle(request.getTitle());
        }

        if(request.getDescription() != null){
            existingEntry.setDescription(request.getDescription());
        }

        if(request.getLatitude() != null){
            existingEntry.setLatitude(request.getLatitude());
        }

        if(request.getLongitude() != null){
            existingEntry.setLongitude(request.getLongitude());
        }
        return entryRepository.save(existingEntry);
    }

}

