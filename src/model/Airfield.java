package model;

public class Airfield {

    private String icao;
    private String iata;
    private String name;
    private String city;
    private String state;
    private String country;
    private String critical;
    private double latitude;
    private double longitude;

    public Airfield(String icao, String iata, String name,
            String city, String state, String country,
            String critical, double latitude, double longitude) {
        this.icao = icao;
        this.iata = iata;
        this.name = name;
        this.city = city;
        this.state = state;
        this.country = country;
        this.critical = critical;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getIcao() {
        return this.icao;
    }

    public String getIata() {
        return this.iata;
    }

    public String getName() {
        return this.name;
    }

    public String getCity() {
        return this.city;
    }

    public String getState() {
        return this.state;
    }

    public String getCountry() {
        return this.country;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Airfield))
            return false;
        return this.icao.equals(((Airfield) o).icao);
    }

    @Override
    public int hashCode() {
        return icao.hashCode();
    }

    @Override
    public String toString() {
        return String.format("%s - %s - %s - %s\n%s - %s - %s\n%f, %f",
                this.icao, this.iata, this.name, this.critical,
                this.city, this.state, this.country,
                this.latitude, this.longitude);
    }
}
