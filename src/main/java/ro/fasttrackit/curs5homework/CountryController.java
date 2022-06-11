package ro.fasttrackit.curs5homework;

import org.springframework.web.bind.annotation.*;
import ro.fasttrackit.curs5homework.exceptions.CountryNotFoundException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("countries")
public class CountryController {
    private final CountryService service;

    public CountryController(CountryService countryService) {
        this.service = countryService;
    }

    @GetMapping
    List<Country> getAllCountriesOrGetAllCountriesByNeighbour(@RequestParam(required = false) String includeNeighbour,
                                                              @RequestParam(required = false) String excludeNeighbour){
        return service.findAllCountriesOrGetAllCountriesByNeighbour(includeNeighbour, excludeNeighbour);
    }

    @GetMapping("names")
    List<String> getAllCountryName() {
        return service.getAllNames();
    }

    @GetMapping("{countryId}/capital")
    String getCapitalOfACountryById(@PathVariable Long countryId) {
        return service.findCapitalByCountryId(countryId)
                .orElseThrow(() -> new CountryNotFoundException(countryId));
    }

    @GetMapping("{countryId}/population")
    Long getPopulationOfACountry(@PathVariable Long countryId) {
        return service.getPopulationByCountryId(countryId)
                .orElseThrow(() -> new CountryNotFoundException(countryId));
    }

    @GetMapping("{countryId}/neighbours")
    List<String> getCountryNeighbours(@PathVariable Long countryId) {
        return service.getNeighboursByCountryId(countryId)
                .orElseThrow(() -> new CountryNotFoundException(countryId));
    }

    @GetMapping("population")
    Map<String, Long> getMapFromCountryToPopulation(){
        return service.getMapFromCountryNameToPopulation();
    }
}
