package ch.fhnw.oop2.hydropowerfx.database.sqlite;

import ch.fhnw.oop2.hydropowerfx.database.Database;
import ch.fhnw.oop2.hydropowerfx.presentationmodel.Canton;
import ch.fhnw.oop2.hydropowerfx.presentationmodel.PowerStation;
import ch.fhnw.oop2.hydropowerfx.presentationmodel.RootPM;
import javafx.collections.ObservableList;

import java.io.File;
import java.sql.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class SQLite extends Database {
    private Connection connection;

    public SQLite(RootPM rootPM, ObservableList<Canton> cantons, ObservableList<PowerStation> stations) {
        super(rootPM, cantons, stations);

        try {
            Class.forName("org.sqlite.JDBC");

            connection = DriverManager.getConnection("jdbc:sqlite:" + rootPM.getFilePath() + File.separator +"sqlite.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS cantons ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "name VARCHAR (255),"
                    + "short_name VARCHAR (2),"
                    + "number VARCHAR (255),"
                    + "civil_vote INT,"
                    + "accession INT,"
                    + "main_village VARCHAR (255),"
                    + "population INT,"
                    + "foreigners DOUBLE,"
                    + "area INT,"
                    + "inhabitant_density INT,"
                    + "villages INT,"
                    + "language VARCHAR (255)"
                    + ");");

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS stations ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "entity_id INT,"
                    + "name VARCHAR (255),"
                    + "type VARCHAR (2),"
                    + "site VARCHAR (255),"
                    + "canton VARCHAR (2),"
                    + "max_water DOUBLE,"
                    + "max_power DOUBLE,"
                    + "start_operation INT,"
                    + "last_operation INT,"
                    + "latitude DOUBLE,"
                    + "longitude DOUBLE,"
                    + "status VARCHAR (255),"
                    + "waterbodies VARCHAR (255),"
                    + "img_url VARCHAR (255)"
                    + ");");
        }
        catch (ClassNotFoundException e) {
            //TODO: handle exception
        }
        catch (SQLException e) {
            //TODO: handle exception
        }



        init();
    }

    @Override
    public void close() {
        try
        {
            if(connection != null)
                connection.close();
        }
        catch(SQLException e)
        {
            System.err.println(e);
        }
    }

    @Override
    protected void addAllCantonsAndStations() {
        boolean first = true;
        String cantonQuery = "INSERT INTO cantons (name, short_name, number, civil_vote, accession, main_village, population, foreigners, area, inhabitant_density, villages, language) VALUES ";
        for (Canton canton : cantonList) {
            if (first) {
                first = false;
            }
            else {
                cantonQuery = cantonQuery + ", ";
            }

            cantonQuery = cantonQuery + "("
                    + "'" + canton.getCantonName().replace("'", "''") + "',"
                    + "'" + canton.getShortName().replace("'", "''") + "',"
                    + "'" + canton.getNumber().replace("'", "''") + "',"
                    + canton.getCivilVote() + ","
                    + canton.getAccession() + ","
                    + "'" + canton.getMainVillage().replace("'", "''") + "',"
                    + canton.getPopulation() + ","
                    + canton.getForeigners() + ","
                    + canton.getArea() + ","
                    + canton.getInhabitantDensity() + ","
                    + canton.getVillages() + ","
                    + "'" + canton.getLanguage().replace("'", "''") + "'"
                    + ")";
        }

        first = true;
        String stationQuery = "INSERT INTO stations (entity_id, name, type, site, canton, max_water, max_power, start_operation, last_operation,latitude, longitude, status, waterbodies, img_url) "
                + "VALUES ";
        for (PowerStation station : stationList) {
            if (first) {
                first = false;
            }
            else {
                stationQuery = stationQuery + ", ";
            }

            stationQuery = stationQuery + "("
                    + station.getEntitiyID() + ","
                    + "'" + station.getName().replace("'", "''") + "',"
                    + "'" + station.getType().replace("'", "''") + "',"
                    + "'" + station.getSite().replace("'", "''") + "',"
                    + "'" + station.getCanton().replace("'", "''") + "',"
                    + station.getMaxWater() + ","
                    + station.getMaxPower() + ","
                    + station.getStartOperation() + ","
                    + station.getLastOperation() + ","
                    + station.getLatitude() + ","
                    + station.getLongitude() + ","
                    + "'" + station.getStatus().replace("'", "''") + "',"
                    + "'" + station.getWaterbodies().replace("'", "''") + "',"
                    + "'" + station.getImgUrl().replace("'", "''") + "'"
                    + ")";
        }

        System.out.println(cantonQuery);
        System.out.println(stationQuery);

        try {
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            statement.executeUpdate(cantonQuery);
            statement.executeUpdate(stationQuery);
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    protected Collection<Canton> getAllCantons() {
        Set<Canton> cantons = new HashSet<>();

        try {
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            ResultSet result = statement.executeQuery("SELECT * FROM cantons");

            while(result.next())
            {
                Canton canton = new Canton(stationList);
                canton.setCantonName(result.getString("name"));
                canton.setShortName(result.getString("short_name"));
                canton.setNumber(result.getString("number"));
                canton.setCivilVote(result.getDouble("civil_vote"));
                canton.setAccession(result.getInt("accession"));
                canton.setMainVillage(result.getString("main_village"));
                canton.setPopulation(result.getInt("population"));
                canton.setForeigners(result.getDouble("foreigners"));
                canton.setArea(result.getInt("area"));
                canton.setInhabitantDensity(result.getInt("inhabitant_density"));
                canton.setVillages(result.getInt("villages"));
                canton.setLanguage(result.getString("language"));

                cantonList.add(canton);
            }
        }
        catch (SQLException e) {
            //TODO: handle exception
        }

        return cantons;
    }

    @Override
    protected void addCanton(Canton canton) {
        try {
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            statement.executeUpdate("INSERT INTO cantons (name, short_name, number, civil_vote, accession, main_village, population, foreigners, area, inhabitant_density, villages, language) "
                    + "VALUES ("
                    + "'" + canton.getCantonName().replace("'", "''") + "',"
                    + "'" + canton.getShortName().replace("'", "''") + "',"
                    + "'" + canton.getNumber().replace("'", "''") + "',"
                    + canton.getCivilVote() + ","
                    + canton.getAccession() + ","
                    + "'" + canton.getMainVillage().replace("'", "''") + "',"
                    + canton.getPopulation() + ","
                    + canton.getForeigners() + ","
                    + canton.getArea() + ","
                    + canton.getInhabitantDensity() + ","
                    + canton.getVillages() + ","
                    + "'" + canton.getLanguage().replace("'", "''") + "'"
                    + ")");
        }
        catch (SQLException e) {
            //TODO: handle exception
        }
    }

    @Override
    protected void updateCanton(Canton canton) {
        try {
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            statement.executeUpdate("UPDATE cantons "
                    + "SET "
                    + "name = '" + canton.getCantonName().replace("'", "''") + "',"
                    + "short_name = '" + canton.getShortName().replace("'", "''") + "',"
                    + "civil_vote = " + canton.getCivilVote() + ","
                    + "accession = " + canton.getAccession() + ","
                    + "main_village = '" + canton.getMainVillage().replace("'", "''") + "',"
                    + "population = " + canton.getPopulation() + ","
                    + "foreigners = " + canton.getForeigners() + ","
                    + "area = " + canton.getArea() + ","
                    + "inhabitant_density = " + canton.getInhabitantDensity() + ","
                    + "villages = " + canton.getVillages() + ","
                    + "language = '" + canton.getLanguage().replace("'", "''") + "'"
                    + "WHERE number = '" + canton.getNumber() + "'");
        }
        catch (SQLException e) {
            //TODO: handle exception
        }
    }

    @Override
    protected void deleteCanton(Canton canton) {
        try {
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            statement.executeUpdate("DELETE FROM cantons WHERE number = '" + canton.getNumber() + "'");
        }
        catch (SQLException e) {
            //TODO: handle exceptions
        }
    }

    @Override
    protected Collection<PowerStation> getAllStations() {
        Set<PowerStation> stations = new HashSet<>();

        try {
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            ResultSet result = statement.executeQuery("SELECT * FROM stations");

            while(result.next())
            {
                PowerStation station = new PowerStation();
                station.setEntitiyID(result.getInt("entity_id"));
                station.setName(result.getString("name"));
                station.setType(result.getString("type"));
                station.setSite(result.getString("site"));
                station.setCanton(result.getString("canton"));
                station.setMaxWater(result.getDouble("max_water"));
                station.setMaxPower(result.getDouble("max_power"));
                station.setStartOperation(result.getInt("start_operation"));
                station.setLastOperation(result.getInt("last_operation"));
                station.setLatitude(result.getDouble("latitude"));
                station.setLongitude(result.getDouble("longitude"));
                station.setStatus(result.getString("status"));
                station.setWaterbodies(result.getString("waterbodies"));
                station.setImgUrl(result.getString("img_url"));

                stationList.add(station);
            }
        }
        catch (SQLException e) {
            //TODO: handle exception
        }

        return stations;
    }

    @Override
    protected void addStation(PowerStation station) {
        try {
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            statement.executeUpdate("INSERT INTO stations (entity_id, name, type, site, canton, max_water, max_power, start_operation, last_operation,latitude, longitude, status, waterbodies, img_url) "
                    + "VALUES ("
                    + station.getEntitiyID() + ","
                    + "'" + station.getName().replace("'", "''") + "',"
                    + "'" + station.getType().replace("'", "''") + "',"
                    + "'" + station.getSite().replace("'", "''") + "',"
                    + "'" + station.getCanton().replace("'", "''") + "',"
                    + station.getMaxWater() + ","
                    + station.getMaxPower() + ","
                    + station.getStartOperation() + ","
                    + station.getLastOperation() + ","
                    + station.getLatitude() + ","
                    + station.getLongitude() + ","
                    + "'" + station.getStatus().replace("'", "''") + "',"
                    + "'" + station.getWaterbodies().replace("'", "''") + "',"
                    + "'" + station.getImgUrl().replace("'", "''") + "'"
                    + ")");
        }
        catch (SQLException e) {
            //TODO: handle exceptions
        }
    }

    @Override
    protected void updateStation(PowerStation station) {
        try {
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            statement.executeUpdate("UPDATE stations "
                    + "SET "
                    + "name = '" + station.getName().replace("'", "''") + "',"
                    + "type = '" + station.getType().replace("'", "''") + "',"
                    + "site = '" + station.getSite().replace("'", "''") + "',"
                    + "canton = '" + station.getCanton().replace("'", "''") + "',"
                    + "max_water = " + station.getMaxWater() + ","
                    + "max_power = " + station.getMaxPower() + ","
                    + "start_operation = " + station.getStartOperation() + ","
                    + "last_operation = " + station.getLastOperation() + ","
                    + "latitude = " + station.getLatitude() + ","
                    + "longitude = " + station.getLongitude() + ","
                    + "status = '" + station.getStatus().replace("'", "''") + "',"
                    + "waterbodies = '" + station.getWaterbodies() + "',"
                    + "img_url = '" + station.getImgUrl().replace("'", "''") + "'"
                    + "WHERE entity_id = " + station.getEntitiyID());
        }
        catch (SQLException e) {
            //TODO: handle exceptions
        }
    }

    @Override
    protected void deleteStation(PowerStation station) {
        try {
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            statement.executeUpdate("DELETE FROM stations WHERE entity_id = " + station.getEntitiyID());
        }
        catch (SQLException e) {
            //TODO: handle exceptions
        }
    }
}
