import java.util.ArrayList;
import java.sql.*;

class Database {
  public static String USER_NAME = null;
  public static String PASSWORD = null;
  public static String URL = null;
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

  void registration(String fname, String lname)
      throws SQLException, ClassNotFoundException {
    // Registers new user
    // input firstname and lastname
    // makes request in database
    Class.forName("com.mysql.cj.jdbc.Driver");
    statement.executeUpdate("INSERT INTO users(firstname, lastname) value ('"+fname+"','"+lname+"')");
  }

//  void checkDatabase() throws ClassNotFoundException, SQLException {
//    Class.forName("com.mysql.cj.jdbc.Driver");
//    ResultSet resultSet = statement.executeQuery("SELECT * from users");
//
//    while(resultSet.next()){
//      System.out.println(resultSet.getString(1)+ " " +
//          resultSet.getString(2) + " " +
//          resultSet.getString(3));
//    }
//
//    resultSet = statement.executeQuery("SELECT * from tasks");
//
//    while(resultSet.next()){
//      System.out.println(resultSet.getString(1)+ " " +
//          resultSet.getString(2) + " " +
//          resultSet.getString(3) + " " +
//          resultSet.getString(4) + " " +
//          resultSet.getString(5));
//    }
//  }

  void addTask(String lname, String date_task, String time_task, String task) throws SQLException, ClassNotFoundException {
    // Method created for add task to database
    Class.forName("com.mysql.cj.jdbc.Driver");
    ResultSet resultSet = statement.executeQuery("SELECT * from users where lastname = '"+lname+"'");
    String fname_id = "0";
    if (resultSet.next()){
      fname_id = resultSet.getString(1);
    }
    System.out.println(fname_id);
    statement.executeUpdate("INSERT INTO tasks(name_id, date_task, time_task, task) value ('"+fname_id+"','"+date_task+"', '"+time_task+"', '"+task+"')");
  }

  ArrayList<String> checkTasks(String lname) throws ClassNotFoundException, SQLException {
    // Method use lastname only
    //
    Class.forName("com.mysql.cj.jdbc.Driver");
    ResultSet resultSet = statement.executeQuery("SELECT * from users where lastname = '"+lname+"'");
    String fname_id = "0";
    ArrayList<String> result = new ArrayList<String>(){};
    if (resultSet.next()){
      fname_id = resultSet.getString(1); // return id user
    }
    resultSet = statement.executeQuery("SELECT * from tasks where name_id = '"+fname_id+"'"); // return table tasks
    // where name_id = fname_id
    while(resultSet.next()){
      result.add(resultSet.getString(1)+ " " +
          resultSet.getString(2) + " " +
          resultSet.getString(3) + " " +
          resultSet.getString(4) + " " +
          resultSet.getString(5));
    }
    return result;
  }
}
