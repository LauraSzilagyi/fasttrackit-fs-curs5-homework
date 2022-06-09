package ro.fasttrackit.curs5homework;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class CountryController {
    private final CountryService service;

    public CountryController(CountryService countryService) {
        this.service = countryService;
    }

    @GetMapping(value = "countries")
    List<Country> getAllCountriesOrGetAllCountriesByNeighbour(@RequestParam(required = false) String includeNeighbour,
                                                              @RequestParam(required = false) String excludeNeighbour){
        return service.findAllCountriesOrGetAllCountriesByNeighbour(includeNeighbour, excludeNeighbour);
    }

    @GetMapping("countries/names")
    List<String> getAllCountryName() {
        return service.getAllNames();
    }

    @GetMapping("countries/{countryId}/capital")
    String getCapitalOfACountryById(@PathVariable Long countryId) {
        return service.findCapitalById(countryId);
    }

    @GetMapping("countries/{countryId}/population")
    Long getPopulationOfACountry(@PathVariable Long countryId) {
        return service.getPopulationById(countryId);
    }

    @GetMapping(value = "continents/{continentName}/countries")
    List<Country> getCountriesInContinent(@PathVariable String continentName,
                                          @RequestParam(required = false) Long minPopulation) {
        return service.getCountriesInContinentWithMinPopulation(continentName, minPopulation);
    }

    @GetMapping("countries/{countryId}/neighbours")
    List<String> getCountryNeighbours(@PathVariable Long countryId) {
        return service.getNeighboursByCountryId(countryId);
    }

    @GetMapping("countries/population")
    Map<String, Long> getMapFromCountryToPopulation(){
        return service.getMapFromCountryNameToPopulation();
    }

    @GetMapping("continents/countries")
    Map<String, List<Country>> getMapFromContinentToCountries(){
        return service.getMapFromContinentToListOfCountries();
    }
}
