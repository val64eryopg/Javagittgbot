import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

class LogicTest {

    private static final Database database = new Database(GetDatabase.getUserName(), GetDatabase.getPassword(), GetDatabase.getUrl());

//    @org.junit.jupiter.api.Test
//    void getTime() {
//        Date date = new Date(68400000L);
//        String[] a = Logic.getTime(date).split(":");
//        assertTrue(Integer.parseInt(a[0]) < Math.abs(24));
//        assertTrue(Integer.parseInt(a[1]) < Math.abs(60));
//        String[] b = Logic.getTime(Logic.getData()).split(":");
//        assertTrue(Integer.parseInt(b[0]) < Math.abs(24));
//        assertTrue(Integer.parseInt(b[1]) < Math.abs(60));
//        assertNotNull(Logic.getTime(Logic.getData()));
//    }

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

    @Test
    void testDatabaseAddTask() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String chat_id = "474174177";
            database.addTask(chat_id, "2021-10-24", "22:50:00", "popit pivo");
            String date_task ="";
            String time_task ="";
            String task ="";
            try (Connection conn = DriverManager.getConnection(GetDatabase.getUrl(), GetDatabase.getUserName(), GetDatabase.getPassword())) {
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(
                        "SELECT * from tasks where chat_id = '" + chat_id + "'");
                assertNotNull(resultSet);
                if (resultSet.next()){
                    date_task = resultSet.getString(3);
                    time_task = resultSet.getString(4);
                    task = resultSet.getString(5);
                    boolean i = database.delTask(chat_id,"2021-10-24","22:50:00");
                }
            }
            assertEquals(date_task, "2021-10-24");
            assertEquals(time_task, "22:50:00");
            assertEquals(task, "popit pivo");
        } catch (ClassNotFoundException ex) {
            assertTrue(1 == 2);
        }
    }

    @Test
    void testDatabaseCheckTasks() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String chat_id = "99";
            database.addTask(chat_id, "2024-10-20", "22:50:00", "popit pivo");
            ArrayList<String> check = database.checkTasks(chat_id);
            assertNotNull(check);
            String checksStr = check.get(0);
            assertEquals(checksStr, "2024-10-20" + " " + "22:50:00" + " " + "popit pivo");
            database.delTask(chat_id,"2024-10-20","22:50:00");
        } catch (ClassNotFoundException | SQLException ex) {
            assertTrue(1 == 2);
        }
    }

    @Test
    void testDatabaseDelTask(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String chat_id = "54";
            String date_task = "2024-10-20";
            String time_task = "22:50:00";
            database.addTask(chat_id, date_task, time_task, "popit pivo");
            ArrayList<String> check = database.checkTasks(chat_id);
            assertNotNull(check);
            assertTrue(database.delTask(chat_id, date_task, time_task));
        } catch (ClassNotFoundException | SQLException ex) {
            assertTrue(1 == 2);
        }
    }

    String chatId = "100";
    String command = "/RegistrationForA_Week";
    ResultsCommand result;

    @Test
    void testSwitchMessageRegistrationForA_Week() {
        ResultsCommand result = Logic.switchMessage(command, chatId);
        assertNotNull(result);
        assertNotNull(result.getFirst());
        assertNotNull(result.getMyArray());
        assertEquals(result.getFirst(), "выберете день недели");
        assertEquals(result.getMyArray().length, 7);
    }
    @Test
    void testSwitchMessageRegistrationForA_Month() {
        command = "/RegistrationForA_Month";
        result = Logic.switchMessage(command, chatId);
        assertNotNull(result);
        assertNotNull(result.getFirst());
        assertNotNull(result.getMyArray());
        assertEquals(result.getFirst(), "Выберете число этого месяца:");
        //
    }
    @Test
    void testSwitchMessageLookAtWatch() {
        command = "/LookAtWatch";
        result = Logic.switchMessage(command, chatId);
        assertNotNull(result);
        assertNotNull(result.getFirst());
        assertNotNull(result.getMyArray());
        assertEquals(result.getFirst(), Logic.getData().toString());
        assertEquals(result.getMyArray().length, 1);
        //
    }
    @Test
    void testSwitchMessageDeleteTask() {
        command = "/DeleteTask";
        result = Logic.switchMessage(command, chatId);
        assertNotNull(result);
        assertNotNull(result.getFirst());
        assertNotNull(result.getMyArray());
        assertEquals(result.getFirst(), "Нажмите на то что хотите удалить:");
        //
    }
    @Test
    void testSwitchMyTask() {
        command = "/MyTask";
        result = Logic.switchMessage(command, chatId);
        assertNotNull(result);
        assertNotNull(result.getFirst());
        assertNotNull(result.getMyArray());
        assertEquals(result.getFirst(), "Вот список ваших задач:");
        //
    }
    @Test
    void testSwitchHelp() {
        command = "/HELP";
        result = Logic.switchMessage(command,chatId);
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
    void testEqualDatesNowAndFromDatabase() throws SQLException, ClassNotFoundException {
        String chat_id = "55";
        String date_task = "2021-12-07";
        String time_task = "02:50:00";
        database.addTask(chat_id, date_task, time_task, "popit pivo");
        ArrayList<String> check = database.checkTasks(chat_id);
        String checksStr = check.get(0);
        //assertTrue(Logic.equalityDate(Logic.getData(),checksStr));
        //assertEquals(checksStr, "2021-12-07" + " " + "02:40:00" + " " + "popit pivo");
        database.delTask(chat_id,date_task,time_task);
    }

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
