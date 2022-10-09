package utils;

import aquality.selenium.core.logging.Logger;
import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBUtils {

    private static final ISettingsFile jsonSettings = new JsonSettingsFile("config.json");

    private static final String url = jsonSettings.getValue("/urlToDB").toString();
    private static final String user = jsonSettings.getValue("/DBUser").toString();
    private static final String password = jsonSettings.getValue("/DBPassword").toString();

    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;

    public static String[][] performRequest(String query, Logger logger) {

        PreparedStatement preparedStatement;

        String[][] responseInTableType = null;

        try {
            connection = DriverManager.getConnection(url, user, password);
            preparedStatement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            resultSet = preparedStatement.executeQuery();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

            resultSet.last();
            int rows = resultSet.getRow();
            int columns = resultSetMetaData.getColumnCount();
            responseInTableType = new String[rows][columns];
            resultSet.first();

            for (int i = 0; i < responseInTableType.length; i++) {
                for (int j = 0; j < resultSetMetaData.getColumnCount(); j++) {
                    responseInTableType[i][j] = resultSet.getString(j + 1);
                }
                resultSet.next();
            }

        } catch (SQLException sqlException) {
            logger.info("Request to DB isn't performed because of " + sqlException);
        } finally {
            try {
                connection.close();
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
