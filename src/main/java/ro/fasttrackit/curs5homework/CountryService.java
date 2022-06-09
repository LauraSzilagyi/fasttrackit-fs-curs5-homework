package ro.fasttrackit.curs5homework;

import org.springframework.stereotype.Service;
import ro.fasttrackit.curs5homework.exceptions.CountryNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class CountryService {

    private final List<Country> countries = new ArrayList<>();

    public CountryService(CountryReader countryReader) {
        if (countryReader != null) {
            countries.addAll(countryReader.getCountriesFromFile());
        }
    }

    public List<String> getAllNames() {
        return countries.stream()
                .map(Country::name)
                .collect(Collectors.toList());
    }

    private Country getCountryById(Long id) {
        return countries.stream()
                .filter(country -> country.id() == id)
                .findFirst()
                .orElseThrow(() -> new CountryNotFoundException(id));
    }

    public String findCapitalById(Long id) {
        return getCountryById(id).capital();
    }

    public Long getPopulationById(Long id) {
        return getCountryById(id).population();
    }

    public List<Country> getCountriesByContinent(String continent) {
        return countries.stream()
                .filter(country -> country.continent().equalsIgnoreCase(continent))
                .collect(Collectors.toList());
    }

    public List<String> getNeighboursByCountryId(Long id) {
        return getCountryById(id).neighbours();
    }

    public List<Country> getCountriesInContinentWithMinPopulation(String continentName, Long minPopulation) {
        if (minPopulation != null) {
            return getCountriesByContinent(continentName).stream()
                    .filter(country -> country.population() <= minPopulation)
                    .collect(Collectors.toList());
        } else {
            return getCountriesByContinent(continentName);
        }
    }

    public List<Country> findAllCountriesOrGetAllCountriesByNeighbour(String includeNeighbour, String excludeNeighbour) {

        if (includeNeighbour != null && excludeNeighbour != null) {
            return getCountriesByNeighbours(includeNeighbour, excludeNeighbour);
        } else {
            return countries;
        }
    }

    private List<Country> getCountriesByNeighbours(String includeNeighbour, String excludeNeighbour) {
        return countries.stream()
                .filter(CountryNeighboursContains(includeNeighbour))
                .filter(CountryNeighboursContains(excludeNeighbour).negate())
                .collect(Collectors.toList());
    }

    private Predicate<Country> CountryNeighboursContains(String neighbour) {
        return country -> country.neighbours().stream()
                .map(String::toUpperCase)
                .toList()
                .contains(neighbour.toUpperCase());
    }

    public Map<String, Long> getMapFromCountryNameToPopulation() {
        return countries.stream()
                .collect(Collectors.toMap(Country::name, Country::population));
    }


    public Map<String, List<Country>> getMapFromContinentToListOfCountries() {
        return countries.stream()
                .collect(Collectors.groupingBy(Country::continent));
    }
}
