package ro.fasttrackit.curs5homework;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

    private Optional<Country> getCountryById(Long id) {
        return countries.stream()
                .filter(country -> country.id() == id)
                .findFirst();
    }

    public Optional<String> findCapitalByCountryId(Long id) {
        Optional<Country> country = getCountryById(id);
        return country.map(Country::capital);
    }

    public Optional<Long> getPopulationByCountryId(Long id) {
        Optional<Country> country = getCountryById(id);
        return country.map(Country::population);
    }

    public Optional<List<String>> getNeighboursByCountryId(Long id) {
        Optional<Country> country = getCountryById(id);
        return country.map(Country::neighbours);
    }

    public List<Country> getCountriesInContinentWithMinPopulation(String continentName, Long minPopulation) {
        if (minPopulation != null) {
            return getCountriesByContinentNameAndMinPopulation(continentName, minPopulation);
        } else {
            return getCountriesByContinent(continentName);
        }
    }

    private List<Country> getCountriesByContinentNameAndMinPopulation(String continentName, Long minPopulation) {
        return countries.stream()
                .filter(country -> country.continent().equalsIgnoreCase(continentName))
                .filter(country -> country.population() <= minPopulation)
                .collect(Collectors.toList());
    }

    public List<Country> getCountriesByContinent(String continent) {
        return countries.stream()
                .filter(country -> country.continent().equalsIgnoreCase(continent))
                .collect(Collectors.toList());
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
                .filter(countryNeighboursContains(includeNeighbour))
                .filter(countryNeighboursContains(excludeNeighbour).negate())
                .collect(Collectors.toList());
    }

    private Predicate<Country> countryNeighboursContains(String neighbour) {
        return country -> country.neighbours().stream()
                .anyMatch(s -> s.equalsIgnoreCase(neighbour));
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
