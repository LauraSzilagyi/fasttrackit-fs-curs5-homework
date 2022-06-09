package ro.fasttrackit.curs5homework;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.regex.Pattern.quote;

@Component
public class CountryReader {

    public List<Country> getCountriesFromFile() {

        try {
            List<String> lines = Files.readAllLines(Path.of("src/main/resources/files/countries.txt"));
            return IntStream.range(0, lines.size())
                    .filter(index -> lines.get(index).split(quote("|")).length >= 5)
                    .mapToObj(index -> getCountryFormat(lines.get(index), index))
                    .toList();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }

    private Country getCountryFormat(String line, int id) {
        String[] tokens = line.split(quote("|"));
        String[] neighbours = getNeighbours(tokens);

        return new Country(++id, tokens[0], tokens[1], Long.parseLong(tokens[2]),
                Integer.parseInt(tokens[3]), tokens[4], List.of(neighbours));
    }

    private String[] getNeighbours(String[] tokens) {
        if (tokens.length == 6) {
            return tokens[5].split(quote("~"));
        } else {
            return new String[0];
        }
    }
}
