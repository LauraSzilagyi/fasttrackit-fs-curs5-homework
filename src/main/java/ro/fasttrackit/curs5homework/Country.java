package ro.fasttrackit.curs5homework;

import java.util.List;

public record Country(int id,
                      String name,
                      String capital,
                      long population,
                      int area,
                      String continent,
                      List<String> neighbours) {
}
