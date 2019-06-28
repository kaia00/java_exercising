package jdbc.repository;

import jdbc.model.Country;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CountryRepository {

    private static String dbUrl = "jdbc:mysql://localhost:3306/world?serverTimezone=UTC";
    private static String dbUser = "root";
    private static String dbPassword = "e2b9t9mc";

    public static List<Country> getCountries() throws SQLException {

        Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        Statement stm = conn.createStatement();

        ResultSet rs = stm.executeQuery("SELECT * FROM country LIMIT 20");

        List<Country> countries = parseResultSetToCountries(rs);

        rs.close();
        stm.close();
        conn.close();

        return countries;
    }

    private static List<Country> parseResultSetToCountries(ResultSet rs) throws SQLException{

        List<Country> countries = new ArrayList<>();

        while(rs.next()){

            Country country = new Country();

            country.setCode(rs.getString("Code"));
            country.setName(rs.getString("Name"));
            country.setContinent(rs.getString("Continent"));
            country.setRegion(rs.getString("Region"));

            countries.add(country);

        }
        return countries;
    }

    public static List<Country> getCountriesByName(String countryName)throws SQLException {

        Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        PreparedStatement prepStmt = conn.prepareStatement("SELECT * FROM country WHERE name = ?");
        prepStmt.setString(1, countryName);


        ResultSet rs = prepStmt.executeQuery();

        List<Country> countries = parseResultSetToCountries(rs);

        rs.close();
        prepStmt.close();
        conn.close();

        return countries;
    }


}
