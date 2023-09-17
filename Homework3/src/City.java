import java.util.Objects;

public class City {
    private final double x;
    private final double y;

    private final String name;

    public City(double x, double y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
    }

    public double getDistance(City city) {
        return Math.sqrt(Math.pow(x - city.x, 2) + Math.pow(y - city.y, 2));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return x == city.x && y == city.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "City{" +
               "x=" + x +
               ", y=" + y +
               ", name='" + name + '\'' +
               '}';
    }
}
