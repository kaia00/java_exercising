package jdbc;

import jdbc.model.City;
import jdbc.model.Country;
import jdbc.repository.CityRepository;
import jdbc.repository.CountryRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class MyJDBCMain {

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter one of the following: ");

        System.out.println("1. View all cities");
        System.out.println("2. View cities by name");
        System.out.println("3. Add a new city");
        System.out.println("4. Delete city by name");
        System.out.println("5. Update city");

        System.out.println("6. View all countries");
        System.out.println("7. View countries by name");



        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                System.out.println("Trying to view all cities");
                List<City> cities = CityRepository.getCities();
                printCities(cities);

                break;
            case 2:
                scanner.nextLine();
                System.out.println("Enter city name");
                String cityName = scanner.nextLine();
                System.out.println("Searching for city with name: " + cityName);
                cities = CityRepository.getCitiesByName(cityName);
                printCities(cities);
                break;
            case 3:
                scanner.nextLine();
                System.out.println("Enter city name");
                City city = new City();
                city.setName(scanner.nextLine());
                System.out.println("Enter disctrict:");
                city.setDistrict(scanner.nextLine());
                System.out.println("Enter country code");
                city.setCountryCode(scanner.nextLine());
                System.out.println("Enter population");
                city.setPopulation(scanner.nextLong());

                System.out.println(city);

                CityRepository.addCity(city);

                break;
            case 4:
                scanner.nextLine();
                System.out.println("Which city do you want to delete?");
                cityName = scanner.nextLine();
                CityRepository.deleteCity(cityName);
                System.out.println("Successfully deleted " + cityName);

                break;
            case 5:
                scanner.nextLine();
                System.out.println("Write the city name you want to update:");
                cityName = scanner.nextLine();
                cities = CityRepository.getCitiesByName(cityName);
                System.out.println("There are following cities with that name:");
                printCities(cities);
                System.out.println("Write the ID of the city you want to update:");
                int id = scanner.nextInt();
                System.out.println("Enter new population for that city:");
                long population = scanner.nextLong();
                CityRepository.updateCity(id, population);

                break;

            case 6:
                scanner.nextLine();
                System.out.println("Trying to view all countries");
                List<Country> countries = CountryRepository.getCountries();
                printCountries(countries);
                break;

            case 7:
                scanner.nextLine();
                System.out.println("Enter country name");
                String countryName = scanner.nextLine();

                System.out.println("Do you want to see:");
                System.out.println("1. Only country details for " + countryName);
                System.out.println("2. Country details + cities in " + countryName);
                choice = scanner.nextInt();

                if (choice == 1) {
                    printCountryDetails(countryName);
                } else if (choice == 2) {
                    scanner.nextLine();
                    printCountryDetails(countryName);
                    System.out.println();
                    cities = CityRepository.getCitiesByCountryName(countryName);
                    System.out.println("Here are the details for all the cities in " + countryName + ":");
                    printCities(cities);

                } else {
                    System.out.println("Invalid choice");
                }


                break;

            default:
                System.out.println("Invalid choice");
        }

    }

    private static void printCountryDetails(String countryName) throws SQLException {
        List<Country> countries;
        System.out.println("Searching for country with name: " + countryName);
        countries = CountryRepository.getCountriesByName(countryName);
        printCountries(countries);
    }

    public static void printCities(List<City> cities) {
        printCityHeader();
        for (City city : cities) {
            printCity(city);
        }
    }

    public static void printCityHeader() {
        System.out.printf("%-10s %-20s %-20s %-20s %-20s \n \n", "ID", "Name", "Country Code", "District", "Population");
    }

    public static void printCity(City city) {
        System.out.printf("%-10s ", city.getId());
        System.out.printf("%-20s ", city.getName());
        System.out.printf("%-20s ", city.getCountryCode());
        System.out.printf("%-20s ", city.getDistrict());
        System.out.println(city.getPopulation());
    }

    public static void printCountries(List<Country> countries) {
        printCountryHeader();
        for (Country country : countries) {
            printCountry(country);
        }
    }

    private static void printCountry(Country country) {
        System.out.printf("%-10s ", country.getCode());
        System.out.printf("%-30s ", country.getName());
        System.out.printf("%-20s ", country.getContinent());
        System.out.println(country.getRegion());
    }

    private static void printCountryHeader() {
        System.out.printf("%-10s %-30s %-20s %-20s \n \n", "Code", "Name", "Continent", "Region");
    }


}
