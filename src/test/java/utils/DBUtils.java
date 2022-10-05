package utils;

import aquality.selenium.core.logging.Logger;
import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBUtils {

    private static ISettingsFile jsonSettings = new JsonSettingsFile("testData.json");

    private static final String url = jsonSettings.getValue("/urlToDB").toString();
    private static final String user = jsonSettings.getValue("/DBUser").toString();
    private static final String password = jsonSettings.getValue("/DBPassword").toString();

    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;

    //сделала 3 однотипных метода по созданию заполнению таблиц информацией из БД в зависимости от количества столбцов,
    // т.к. не смогла придумать единый алгоритм по их заполнению
    //в частности как организовать создание ArrayLists firstColumn, secondColumn и т.д., в которые записываются данные из столбцов таблицы в БД
    public static String[][] performRequestForResponseInThreeColumnTable(String query, Logger logger) {

        String[][] responseInTableType = null;

        try {
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            ArrayList<String> firstColumn = new ArrayList<>();
            ArrayList<String> secondColumn = new ArrayList<>();
            ArrayList<String> thirdColumn = new ArrayList<>();

            while (resultSet.next()) {
                firstColumn.add(resultSet.getString(1));
                secondColumn.add(resultSet.getString(2));
                thirdColumn.add(resultSet.getString(3));
            }

            responseInTableType = new String[firstColumn.size()][3];

            for (int i = 0; i < responseInTableType.length; i++) {
                responseInTableType[i][0] = firstColumn.get(i);
                responseInTableType[i][1] = secondColumn.get(i);
                responseInTableType[i][2] = thirdColumn.get(i);
            }
        } catch (SQLException sqlException) {
            logger.info("Request to DB isn't performed because of " + sqlException);
        } finally {
            try {
                connection.close();
                statement.close();
                resultSet.close();
            } catch (SQLException sqlException) {
                logger.info("Connection, Statement or ResultSet connection wasn't closed because of " + sqlException);
            }
        }
        return responseInTableType;
    }

    public static String[][] performRequestForResponseInTwoColumnTable(String query, Logger logger) {

        String[][] responseInTableType = null;

        try {
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            ArrayList<String> firstColumn = new ArrayList<>();
            ArrayList<String> secondColumn = new ArrayList<>();

            while (resultSet.next()) {
                firstColumn.add(resultSet.getString(1));
                secondColumn.add(resultSet.getString(2));
            }

            responseInTableType = new String[firstColumn.size()][2];

            for (int i = 0; i < responseInTableType.length; i++) {
                responseInTableType[i][0] = firstColumn.get(i);
                responseInTableType[i][1] = secondColumn.get(i);
            }
        } catch (SQLException sqlException) {
            logger.info("Request to DB isn't performed because of " + sqlException);
        } finally {
            try {
                connection.close();
                statement.close();
                resultSet.close();
            } catch (SQLException sqlException) {
                logger.info("Connection, Statement or ResultSet connection wasn't closed because of " + sqlException);
            }
        }
        return responseInTableType;
    }

    public static String[] performRequestForResponseInOneColumnTable(String query, Logger logger) {

        String[] responseInTableType = null;

        try {
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            ArrayList<String> firstColumn = new ArrayList<>();

            while (resultSet.next()) {
                firstColumn.add(resultSet.getString(1));
            }

            responseInTableType = new String[firstColumn.size()];

            for (int i = 0; i < responseInTableType.length; i++) {
                responseInTableType[i] = firstColumn.get(i);
            }
        } catch (SQLException sqlException) {
            logger.info("Request to DB isn't performed because of " + sqlException);
        } finally {
            try {
                connection.close();
                statement.close();
                resultSet.close();
            } catch (SQLException sqlException) {
                logger.info("Connection, Statement or ResultSet connection wasn't closed because of " + sqlException);
            }
        }
        return responseInTableType;
    }
}
