package jdbc.repository;

import jdbc.model.City;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CityRepository {

    private static String dbUrl = "jdbc:mysql://localhost:3306/world?serverTimezone=UTC";
    private static String dbUser = "root";
    private static String dbPassword = "e2b9t9mc";


    public static List<City> getCities() throws Exception {


        //Step 1: Connect to database using Driver Manager
        Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);

        //Step 2: Create a statement using connection

        Statement stm = conn.createStatement();

        //Step 3: Execute the statement

        ResultSet rs = stm.executeQuery("SELECT * FROM city LIMIT 20");

        List<City> cities = parseResultSetToCities(rs);

        rs.close();
        stm.close();
        conn.close();

        return cities;
    }

    public static List<City> getCitiesByName(String cityName) throws Exception {

        //Step 1: Connect to database using Driver Manager
        Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);

        //Step 2: Create a statement using connection

        PreparedStatement prepStmt = conn.prepareStatement("SELECT * FROM city WHERE name = ?");
        prepStmt.setString(1, cityName);


        //Step 3: Execute the statement

        ResultSet rs = prepStmt.executeQuery();

        List<City> cities = parseResultSetToCities(rs);

        rs.close();
        prepStmt.close();
        conn.close();

        return cities;

    }
    public static List<City> getCitiesByCountryName(String countryName) throws Exception{

        Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);

        PreparedStatement prepStmt = conn.prepareStatement("SELECT * FROM city INNER JOIN country ON " +
                "city.CountryCode = country.Code WHERE country.Name = ?");
        prepStmt.setString(1, countryName);

        ResultSet rs = prepStmt.executeQuery();

        List<City> cities = parseResultSetToCities(rs);

        rs.close();
        prepStmt.close();
        conn.close();

        return cities;

    }

    public static void addCity(City city) throws SQLException {

        Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        PreparedStatement prepStmt = conn.prepareStatement("INSERT INTO city (Name, CountryCode, District, Population) " +
                "VALUES (?, ?, ?, ?)");

        prepStmt.setString(1, city.getName());
        prepStmt.setString(2, city.getCountryCode());
        prepStmt.setString(3, city.getDistrict());
        prepStmt.setLong(4, city.getPopulation());

        int rowsAffected = prepStmt.executeUpdate();
        System.out.println("Added: " + rowsAffected + " city successfully");

        conn.close();
        prepStmt.close();
    }

    public static void deleteCity(String cityName) throws SQLException {
        Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        PreparedStatement prepStmt = conn.prepareStatement("DELETE FROM city WHERE name = ?");

        prepStmt.setString(1, cityName);

        int rowsAffected = prepStmt.executeUpdate();
        System.out.println("Number of cities deleted: " + rowsAffected);

        conn.close();
        prepStmt.close();

    }

    public static void updateCity(int id, long population) throws SQLException {

        Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        PreparedStatement prepStmt = conn.prepareStatement("UPDATE city SET population = ? where id = ?");

        prepStmt.setLong(1, population);
        prepStmt.setInt(2, id);

        int rowsAffected = prepStmt.executeUpdate();
        System.out.println("Successfully updated " + rowsAffected + " city.");

        conn.close();
        prepStmt.close();


    }

    private static List<City> parseResultSetToCities(ResultSet rs) throws Exception {

        List<City> cities = new ArrayList<>();
        while (rs.next()) {

            City city = new City();

            city.setId(rs.getInt("ID"));
            city.setName(rs.getString("name"));
            city.setCountryCode(rs.getString("countryCode"));
            city.setDistrict(rs.getString("district"));
            city.setPopulation(rs.getLong("population"));

            cities.add(city);

        }
        return cities;
    }
}
