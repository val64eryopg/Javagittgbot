import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.sql.*;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class LogicTest {

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
        assertNotNull(gettoken.getbottoken());
    }

    @Test
    void testDatabaseIsNotNull() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Database database = new Database("root", "root", "");
            assertNotNull(database);
        } catch (ClassNotFoundException ex) {
        }
    }

    @Test
    void testDatabaseAddTask() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Database database = new Database("root", "root", "");
            String lname = " ";
            database.addTask(lname, "24.10.2021", "22:50", "popit pivo");
            ResultSet resultSet = Database.statement.executeQuery("SELECT * from users where lastname = '" + lname + "'");
            String fname_id = "0";
            if (resultSet.next()) {
                fname_id = resultSet.getString(1);
            }
            resultSet = Database.statement.executeQuery("SELECT * from tasks where name_id = '" + fname_id + "'");
            String date_task = resultSet.getString(3);
            String time_task = resultSet.getString(4);
            String task = resultSet.getString(5);
            /////
            /////
            assertEquals(date_task, "24.10.2021");
            assertEquals(time_task, "22:50");
            assertEquals(task, "popit pivo");
        } catch (ClassNotFoundException ex) {
        }
    }

    @Test
    void testDatabaseCheckTasks() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Database database = new Database("root", "root", "");
            String lname = "pupa";
            database.addTask(lname, "24.10.2021", "22:50", "popit pivo");
            database.addTask(lname, "25.12.2022", "10:30", "Poigrat v dotu");
            ArrayList<String> check = database.checkTasks(lname);
            assertNotNull(check);
            ArrayList<String> rightCheck = new ArrayList<String>();
            rightCheck.add("1" + " " + "pupa" + " " +
                    "24.10.2021" + " " + "22:50" + " " + "popit pivo");
            rightCheck.add("1" + " " + "pupa" + " " +
                    "25.12.2022" + " " + "10:30" + " " + "Poigrat v dotu");
            assertEquals(check, rightCheck);
        } catch (ClassNotFoundException | SQLException ex) {
        }
    }
    @Test
    void testRegistration(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Database database = new Database("root", "root", "");
            String lname = "pupa";
            String fname = "1";

            ResultSet resultSet = Database.statement.executeQuery("SELECT * from users where firstname = '"+fname+"'");
            String fnamecheck = resultSet.getString(2);
            String lnamecheck = resultSet.getString(3);
            //
            //
            assertEquals(fnamecheck, fname);
            assertEquals(lnamecheck, lname);
        } catch (ClassNotFoundException | SQLException ex) {
        }
    }
}