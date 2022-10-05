package tests;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.logging.Logger;
import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import org.testng.annotations.Test;
import requests.DBApplicationRequest;

public class DBTest{

    private static Logger logger = AqualityServices.getLogger();
    private static ISettingsFile jsonSettings = new JsonSettingsFile("testData.json");

    @Test
    public void getMinWorkTimeForEachTest(){
        DBApplicationRequest.getMinimalTimeOfWorkForEachTest(jsonSettings.getValue("/getMinWorkTimeForEachTest").toString(), logger, 3);
    }

    @Test
    public void getProjectsWithAmountOfUniqueTests(){
        DBApplicationRequest.getProjectsWithAmountOfUniqueTests(jsonSettings.getValue("/getProjectsWithAmountOfUniqueTests").toString(), logger, 2);
    }

    @Test
    public void getTestsAppliedBeforeCertainDate(){
        DBApplicationRequest.getTestsAppliedBeforeCertainDate(jsonSettings.getValue("/getTestsAppliedBeforeCertainDate").toString(), logger, 3);
    }

    @Test
    public void getAmountOfTestsAppliedOnCertainBrowsers(){
        DBApplicationRequest.getAmountOfTestsAppliedOnCertainBrowsers(jsonSettings.getValue("/getAmountOfTestsAppliedOnCertainBrowsers").toString(), logger);
    }
}
