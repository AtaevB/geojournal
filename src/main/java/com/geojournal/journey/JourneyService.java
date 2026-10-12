package com.geojournal.journey;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
public class JourneyService {

    private final JourneyRepository journeyRepository;

    public JourneyService(JourneyRepository journeyRepository) {
        this.journeyRepository = journeyRepository;
    }

    public List<Journey> getAllJourneys(){
        return journeyRepository.findAll();
    }

    public Journey createJourney(CreateJourneyRequest request){

        if((request.getStartDate() != null && request.getEndDate() != null) && request.getEndDate().isBefore(request.getStartDate())){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "End date cannot be before start date"
            );
        }

        Journey journey = new Journey(
                request.getTitle(),
                request.getDescription(),
                request.getStartDate(),
                request.getEndDate()
        );

        return journeyRepository.save(journey);
    }

    public Journey getJourneyById(Long id){
        return journeyRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Journey not found"
                ));
    }

    public void deleteJourney(Long id) {
        Journey journey = getJourneyById(id);
        journeyRepository.delete(journey);
    }

    public Journey updateJourney(Long id, UpdateJourneyRequest updates){

        Journey existingJourney = getJourneyById(id);

        LocalDate newStartDate = updates.getStartDate() != null
                ? updates.getStartDate()
                : existingJourney.getStartDate();

        LocalDate newEndDate = updates.getEndDate() != null
                ? updates.getEndDate()
                : existingJourney.getEndDate();

        if (updates.getTitle() != null && updates.getTitle().isBlank()){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Title must not be blank"
            );
        }

        if (newStartDate != null
                && newEndDate != null
                && newEndDate.isBefore(newStartDate)){

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "End date cannot be before start date");
        }

        if (updates.getTitle() != null) {
            existingJourney.setTitle(updates.getTitle());
        }

        if (updates.getDescription() != null) {
            existingJourney.setDescription(updates.getDescription());
        }

        if (updates.getStartDate() != null) {
            existingJourney.setStartDate(updates.getStartDate());
        }

        if (updates.getEndDate() != null) {
            existingJourney.setEndDate(updates.getEndDate());
        }

        return journeyRepository.save(existingJourney);

    }

}
