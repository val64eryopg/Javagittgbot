import java.util.ArrayList;
import java.sql.*;

class Database {
  public static String USER_NAME = getdatabase.getUserName();
  public static String PASSWORD = getdatabase.getPassword();
  public static String URL = getdatabase.getUrl();
  public static Statement statement;
  public static Connection connection;

  Database(String username, String pass, String url){
    // constructor class Database
    USER_NAME = username;
    PASSWORD = pass;
    URL = url;
  }


  static{
    try {
      // connecting to database
      // creation variable "connection", "statement"
      connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
    } catch (SQLException throwables){
      throwables.printStackTrace();
      throw new RuntimeException();
    }
  }
  static{
    try {
      statement = connection.createStatement();
    } catch (SQLException throwables){
      throwables.printStackTrace();
      throw new RuntimeException();
    }
  }

  boolean delTask(String chat_id, String date_task, String time_task) throws SQLException, ClassNotFoundException{
    try{
      Class.forName("com.mysql.cj.jdbc.Driver");
      statement.executeUpdate(
              "Delete from tasks where chat_id = '"+chat_id+"', date_task = '"+date_task+"', time_task = '"+time_task+"'");
    } catch (Exception e){
      return false;
    }
    return true;
  }

  boolean addTask(String chat_id, String date_task, String time_task, String task) throws SQLException, ClassNotFoundException {
    // Method created for add task to database
    System.out.println(chat_id + date_task + time_task + task);
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      statement.executeUpdate("INSERT INTO tasks(chat_id, date_task, time_task, task) value ('"+chat_id+"', '"+date_task+"','"+time_task+"', '"+task+"')");
      System.out.println(chat_id + date_task + time_task + task);
    } catch (Exception e){
      System.out.println(e);
      return false;
    }

    return true;
  }

  ArrayList<String> checkTasks(String chat_id) throws ClassNotFoundException, SQLException {
    // Method use lastname only
    ArrayList<String> result = new ArrayList<String>(){};
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");

      ResultSet resultSet = statement.executeQuery(
              "SELECT * from tasks where chat_id = '" + chat_id + "'"); // return table tasks

      while (resultSet.next()) {
        result.add(resultSet.getString(3) + " " +
                resultSet.getString(4) + " " +
                resultSet.getString(5));
      }
    } catch (Exception e){
      return null;
    }
    return result;
  }
}