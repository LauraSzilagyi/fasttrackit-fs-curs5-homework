package ro.fasttrackit.curs5homework;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("continents")
public class ContinentController {
    private final CountryService service;

    public ContinentController(CountryService countryService) {
        this.service = countryService;
    }

    @GetMapping(value = "{continentName}/countries")
    List<Country> getCountriesInContinent(@PathVariable String continentName,
                                          @RequestParam(required = false) Long minPopulation) {
        return service.getCountriesInContinentWithMinPopulation(continentName, minPopulation);
    }

    @GetMapping("countries")
    Map<String, List<Country>> getMapFromContinentToCountries() {
        return service.getMapFromContinentToListOfCountries();
    }
}
