import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class LogicTest {
    public Logic logic = new Logic();

    private static final Database database = new Database(GetDatabase.getUserName(), GetDatabase.getPassword(), GetDatabase.getUrl());



    @org.junit.jupiter.api.Test
    void getData() {
        assertNotNull(Logic.getData());
    }

    @Test
    void getToken() {
        assertNotNull(GetToken.getBotToken());
    }

    @Test
    void testNamePassURL(){
        assertNotNull(GetDatabase.getUserName());
        assertNotNull(GetDatabase.getUrl());
        assertNotNull(GetDatabase.getPassword());
    }


    @Test
    void testDatabaseIsNotNull() {
        assertNotNull(database);
    }



    String chatId = "100";
    String command = "/RegistrationForA_Week";
    ResultsCommand result;


    @Test
    void testSwitchMessageRegistrationForA_Month() {
        command = "/RegistrationForA_Month";
        result = logic.switchMessage(command, chatId);
        assertNotNull(result);
        assertNotNull(result.getFirst());
        assertNotNull(result.getMyArray());
        assertEquals(result.getFirst(), "Выберете число этого месяца:");
        //
    }

    @Test
    void testSwitchMessageDeleteTask() {
        command = "/DeleteTask";
        result = logic.switchMessage(command, chatId);
        assertNotNull(result);
        assertNotNull(result.getFirst());
        assertNotNull(result.getMyArray());
        assertEquals(result.getFirst(), "Нажмите на то что хотите удалить:");
        //
    }

    @Test
    void getTimeFromPage() {
        String time = logic.getTimeFromPage("Абиджан");
        String haventcity = logic.getTimeFromPage("gnmbkgnfbljnf");
        assertNotNull(haventcity);
        String regex = "\\S{2} \\d{1,2}:\\d{1,2}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(time);
        matcher.matches();
    }

    @Test
    void testSwitchMyTask() {
        command = "/MyTask";
        result = logic.switchMessage(command, chatId);
        assertNotNull(result);
        assertNotNull(result.getFirst());
        assertNotNull(result.getMyArray());
        assertEquals(result.getFirst(), "Вот список ваших задач:");
        //
    }
    @Test
    void testSwitchHelp() {
        command = "/HELP";
        result = logic.switchMessage(command,chatId);
        assertNotNull(result);
        assertNotNull(result.getFirst());
        assertEquals(result.getFirst(), "Список доступных команд: \n /DeleteTask \n /MyTask \n /LookAtWatch \n /RegistrationForA_Month \n /RegistrationForA_Week");
        // assertEquals(result.getMyArray().length,0);
    }

    //   @Test
    //   void testEqualityDates(){
    //       Date dateNow = new Date();
    //       String dateFromDatabase = "2021-12-07 02:48:00 sdfefsef";
    //       String[] dateArray = Logic.dateFormat(dateNow).split(" ");
    //       String[] dateFromDatabaseArray = dateFromDatabase.split(" ");
    //       boolean YearMonthDay = dateArray[0].equals(dateFromDatabaseArray[0]);
    //       String a = dateArray[1];
    //       String b = dateFromDatabaseArray[1];
    //       boolean HourMin = dateArray[1].regionMatches(0,dateFromDatabaseArray[1],0,dateFromDatabaseArray[1].length() -3);
    //   }



    @Test
    void testUserCondition(){
        String username = "pupa";
        String condition = "4444";
        assertTrue(database.addUserCondition(username,condition));
        assertTrue(database.checkUserCondition(username));
        assertNotNull(database.parseUserCondition(username));
        database.delUserCondition(username);
    }

}
