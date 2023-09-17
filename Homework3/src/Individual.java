import java.util.Arrays;

public class Individual {
    private final City[] cities;

    public Individual(int numberOfCities) {
        cities = new City[numberOfCities];
    }

    public Individual(City[] cities) {
        this.cities = cities;
    }

    public City get(int index) {
        return cities[index];
    }

    public void set(int index, City value) {
        cities[index] = value;
    }

    public boolean contains(City city) {
        for (City currentCity : cities) {
            if (city.equals(currentCity)) {
                return true;
            }
        }

        return false;
    }

    public double calculateDistance() {
        double distance = 0;
        for (int i = 1; i < cities.length; i++) {
            distance += cities[i].getDistance(cities[i - 1]);
        }

        return distance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Individual that = (Individual) o;
        return Arrays.deepEquals(cities, that.cities);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(cities);
    }

    @Override
    public String toString() {
        return "Individual{" +
               "cities=" + Arrays.toString(cities) +
               '}';
    }
}
