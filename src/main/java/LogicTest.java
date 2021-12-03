import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.sql.*;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class LogicTest {

    private static final Database database = new Database(getdatabase.getUserName(), getdatabase.getPassword(), getdatabase.getUrl());

    @org.junit.jupiter.api.Test
    void getTime() {
        Date date = new Date(68400000L);
        String[] a = Logic.getTime(date).split(":");
        assertTrue(Integer.parseInt(a[0]) < Math.abs(24));
        assertTrue(Integer.parseInt(a[1]) < Math.abs(60));
        String[] b = Logic.getTime(Logic.getData()).split(":");
        assertTrue(Integer.parseInt(b[0]) < Math.abs(24));
        assertTrue(Integer.parseInt(b[1]) < Math.abs(60));
        assertNotNull(Logic.getTime(Logic.getData()));
    }

    @org.junit.jupiter.api.Test
    void getData() {
        assertNotNull(Logic.getData());
    }

    @Test
    void getToken() {
        assertNotNull(getToken.getBotToken());
    }

    @Test
    void testNamePassURL(){
        assertNotNull(getdatabase.getUserName());
        assertNotNull(getdatabase.getUrl());
        assertNotNull(getdatabase.getPassword());
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
            try (Connection conn = DriverManager.getConnection(getdatabase.getUrl(), getdatabase.getUserName(), getdatabase.getPassword())) {
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

    @Test
    void testSwitchMessage(){
        String chatId = "100";
        String command = "/RegistrationForA_Week";
        ResultsCommand result = Logic.SwitchMessage(command,chatId);
        assertNotNull(result);
        assertNotNull(result.getFirst());
        assertNotNull(result.getMyArray());
        assertEquals(result.getFirst(), "выберете день недели");
        assertEquals(result.getMyArray().length, 7);
        //
        command = "/RegistrationForA_Month";
        result = Logic.SwitchMessage(command,chatId);
        assertNotNull(result);
        assertNotNull(result.getFirst());
        assertNotNull(result.getMyArray());
        assertEquals(result.getFirst(), "Выберете число этого месяца:");
        //
        command = "/LookAtWatch";
        result = Logic.SwitchMessage(command,chatId);
        assertNotNull(result);
        assertNotNull(result.getFirst());
        assertNotNull(result.getMyArray());
        assertEquals(result.getFirst(), Logic.getData().toString());
        assertEquals(result.getMyArray().length,1);
        //
        command = "/DeleteTask";
        result = Logic.SwitchMessage(command,chatId);
        assertNotNull(result);
        assertNotNull(result.getFirst());
        assertNotNull(result.getMyArray());
        assertEquals(result.getFirst(), "Нажмите на то что хотите удалить:");
        //
        command = "/MyTask";
        result = Logic.SwitchMessage(command,chatId);
        assertNotNull(result);
        assertNotNull(result.getFirst());
        assertNotNull(result.getMyArray());
        assertEquals(result.getFirst(), "Вот список ваших задач:");
        //
        command = "/Help";
        result = Logic.SwitchMessage(command,chatId);
        assertNotNull(result);
        assertNotNull(result.getFirst());
        assertNotNull(result.getMyArray());
        assertEquals(result.getFirst(), "Список доступных команд: \n/DeleteTask \n/MyTask \n/LookAtWatch \n/RegistrationForA_Month \n/RegistrationForA_Week");
        assertEquals(result.getMyArray().length,0);
    }
}
