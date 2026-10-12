package com.geojournal.journey;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import jakarta.validation.Valid;

@RestController
public class JourneyController {

    private final JourneyService journeyService;
    public JourneyController(JourneyService journeyService) {
        this.journeyService = journeyService;
    }

    @GetMapping("/api/journeys")
    public List<Journey> getJourneys(){
        return journeyService.getAllJourneys();
    }

    @GetMapping("/api/journeys/{id}")
    public Journey getJourneyById(@PathVariable Long id){
        return journeyService.getJourneyById(id);
    }

    @PostMapping("/api/journeys")
    public Journey createJourney(@Valid @RequestBody CreateJourneyRequest request) {
        return journeyService.createJourney(request);
    }

    @DeleteMapping("/api/journeys/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteJourney(@PathVariable Long id){
        journeyService.deleteJourney(id);
    }

    @PatchMapping("/api/journeys/{id}")
    public Journey updateJourney(
            @PathVariable Long id,
            @RequestBody UpdateJourneyRequest updates
    ) {
        return journeyService.updateJourney(id, updates);
    }


}


