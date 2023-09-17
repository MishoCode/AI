import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Solver {
    private static final int MAX_COORDINATE = 10_000;
    private static final int LAST_GENERATION = 20_000;
    private static final int ELITISM = 20;
    private static final int POPULATION_SIZE = 200;

    private final int numberOfCities;
    private List<Individual> population = new ArrayList<>();

    private final Random random = new Random();

    public Solver(int numberOfCities) {
        this.numberOfCities = numberOfCities;

        List<City> cities = testWithRealCities();

        for (int i = 0; i < POPULATION_SIZE; i++) {
            Individual individual = new Individual(cities.toArray(new City[numberOfCities]));
            population.add(individual);
            Collections.shuffle(cities);
        }
    }

    public void solve() {
        int generation = 0;
        while (generation++ < LAST_GENERATION) {
            sortPopulation();

            if (generation == 1 || generation == 10 || generation == 50
                || generation == 100 || generation == 250 || generation == 500 ||
                generation == 1000 || generation == 5000 || generation == LAST_GENERATION) {
                System.out.println("Generations: " + generation);
                System.out.printf("Distance: %.3f\n", population.get(0).calculateDistance());
                System.out.println(population.get(0));
                System.out.println("=============================");
            }

            List<Individual> newPopulation = new ArrayList<>(population
                .stream()
                .limit(POPULATION_SIZE / ELITISM)
                .toList());

            for (int i = POPULATION_SIZE / ELITISM; i < POPULATION_SIZE; i++) {
                newPopulation.add(crossover(population.get(i - 1), population.get(i)));
            }

            for (int i = POPULATION_SIZE / ELITISM; i < POPULATION_SIZE; i++) {
                swapMutation(newPopulation.get(i));
            }

            population = newPopulation;
        }
    }

    public Individual crossover(Individual parent1, Individual parent2) {
        Individual child = new Individual(numberOfCities);
        int endIndex = (int) (Math.random() * numberOfCities);

        for (int i = 0; i < endIndex; i++) {
            child.set(i, parent1.get(i));
        }

        for (int i = 0; i < numberOfCities; i++) {
            if (!child.contains(parent2.get(i))) {
                child.set(endIndex, parent2.get(i));
                endIndex++;
            }
        }
        return child;
    }

    public void swapMutation(Individual individual) {
        int first = random.nextInt(numberOfCities);
        int second = random.nextInt(numberOfCities);
        City temp = individual.get(first);
        individual.set(first, individual.get(second));
        individual.set(second, temp);
    }

    private void sortPopulation() {
        population.sort((c1, c2) -> {
            double firstDistance = c1.calculateDistance();
            double secondDistance = c2.calculateDistance();
            return Double.compare(firstDistance, secondDistance);
        });
    }

    private List<City> testWithRealCities() {
        List<City> cities = new ArrayList<>();

        String[] names = {
            "Aberystwyth",
            "Brighton",
            "Edinburgh",
            "Exeter",
            "Glasgow",
            "Inverness",
            "Liverpool",
            "London",
            "Newcastle",
            "Nottingham",
            "Oxford",
            "Stratford"};
        try {
            BufferedReader inputReader = new BufferedReader(
                new FileReader("resources" + File.separator + "uk12_xy.csv"));
            String line;
            int k = 0;
            while ((line = inputReader.readLine()) != null) {
                String[] coords = line.split(",");
                double x = Double.parseDouble(coords[0]);
                double y = Double.parseDouble(coords[1]);
                cities.add(new City(x, y, names[k]));
                k++;
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        return cities;
    }

}
