package requests;

import aquality.selenium.core.logging.Logger;
import utils.DBUtils;

public class DBApplicationRequest {

    public static void getMinimalTimeOfWorkForEachTest(String query, Logger logger, int amountOfColumns) {
        String[][] tableWithMinTimeOfTestWork = DBUtils.performRequestForResponseInThreeColumnTable(query, logger);
        logResponseTable(tableWithMinTimeOfTestWork, logger, amountOfColumns);
    }

    public static void getProjectsWithAmountOfUniqueTests(String query, Logger logger, int amountOfColumns) {
        String[][] tableOfProjectsWithAmountOfUniqueTest = DBUtils.performRequestForResponseInTwoColumnTable(query, logger);
        logResponseTable(tableOfProjectsWithAmountOfUniqueTest, logger, amountOfColumns);
    }

    public static void getTestsAppliedBeforeCertainDate(String query, Logger logger, int amountOfColumns) {
        String[][] tableOfTestsAppliedBeforeCertainDate = DBUtils.performRequestForResponseInThreeColumnTable(query, logger);
        logResponseTable(tableOfTestsAppliedBeforeCertainDate, logger, amountOfColumns);
    }

    public static void getAmountOfTestsAppliedOnCertainBrowsers(String query, Logger logger) {
        String[] tableOfTestsAppliedOnCertainBrowsers = DBUtils.performRequestForResponseInOneColumnTable(query, logger);
        logResponseTable(tableOfTestsAppliedOnCertainBrowsers, logger);
    }

    private static void logResponseTable(String[][] tableToLog, Logger logger, int amountOfColumns) {

        for (String[] strings : tableToLog) {
            StringBuilder lineOfTableToLog = new StringBuilder("| ");

            for (int j = 0; j < amountOfColumns; j++) {
                StringBuilder whiteSpaces = new StringBuilder();

                if (strings[j] != null) {

                    for (int k = 0; k < getMaxLineLengthInColumn(tableToLog, j) - strings[j].length(); k++) {
                        whiteSpaces.append(" ");
                    }

                } else {

                    for (int k = 0; k < getMaxLineLengthInColumn(tableToLog, j); k++) {
                        whiteSpaces.append(" ");
                    }

                }
                lineOfTableToLog.append(strings[j]).append(whiteSpaces).append("|");
            }
            logger.info(lineOfTableToLog.toString());
        }
    }

    private static void logResponseTable(String[] tableToLog, Logger logger){
        for (String s : tableToLog) {
            StringBuilder lineOfTableToLog = new StringBuilder("| ");
            StringBuilder whiteSpaces = new StringBuilder();
            for (int j = 0; j < getMaxLineLengthInColumn(tableToLog) - s.length(); j++) {
                whiteSpaces.append(" ");
            }
            lineOfTableToLog.append(s).append(whiteSpaces).append("|");
            logger.info(lineOfTableToLog.toString());
        }
    }

    private static int getMaxLineLengthInColumn(String[][] table, int columnNumber) {
        int longestLineInColumn = 0;
        for (String[] strings : table) {
            if (strings[columnNumber] != null && strings[columnNumber].length() > longestLineInColumn) {
                longestLineInColumn = strings[columnNumber].length();
            }
        }
        return longestLineInColumn;
    }

    private static int getMaxLineLengthInColumn(String[] table) {
        int longestLineInColumn = 0;
        for (String s : table) {
            if (s != null && s.length() > longestLineInColumn) {
                longestLineInColumn = s.length();
            }
        }
        return longestLineInColumn;
    }
}
