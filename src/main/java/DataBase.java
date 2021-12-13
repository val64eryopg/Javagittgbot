import java.util.ArrayList;
import java.sql.*;

class Database {
  public static String USER_NAME = GetDatabase.getUserName();
  public static String PASSWORD = GetDatabase.getPassword();
  public static String URL = GetDatabase.getUrl();
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
              "Delete from tasks where chat_id = '"+chat_id+"' AND date_task = '"+date_task+"' AND time_task = '"+time_task+"'");
    } catch (Exception e){
      return false;
    }
    return true;
  }

  boolean addTask(String chat_id, String date_task, String time_task, String task) throws SQLException, ClassNotFoundException {
    // Method created for add task to database
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      statement.executeUpdate("INSERT INTO tasks(chat_id, date_task, time_task, task) value ('"+chat_id+"', '"+date_task+"','"+time_task+"', '"+task+"')");
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

  ArrayList<String> returnTasks(){
    ArrayList<String> result = new ArrayList<String>(){};
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");

      ResultSet resultSet = statement.executeQuery(
          "SELECT * from tasks"); // return table tasks

      while (resultSet.next()) {

        result.add(resultSet.getString(2) + " " +
            resultSet.getString(3) + " " +
            resultSet.getString(4) + " " +
            resultSet.getString(5));
      }

    } catch (Exception e){
      return new ArrayList<String>();
    }
    return result;
  }


  boolean addUserCondition(String username, String condtion){
    try{
      Class.forName("com.mysql.cj.jdbc.Driver");
      statement.executeUpdate("INSERT INTO users(username, cond) value ('"+username+"', '"+condtion+"')");
    } catch (Exception e){
      System.out.println(e);
      return false;
    }
    return true;
  }





  ArrayList<String> parseUserCondition(String username){
    ArrayList<String> result = new ArrayList<String>(){};
    try{
      Class.forName("com.mysql.cj.jdbc.Driver");
      ResultSet resultSet = statement.executeQuery("SELECT * from users where username = '"+username+"'");
      while (resultSet.next()) {
        result.add(resultSet.getString(2) + " "
                + resultSet.getString(3));
      }
    } catch (Exception e){
      return new ArrayList<String>();
    }
    return result;
  }

  boolean checkUserCondition(String username){
    try{

      Class.forName("com.mysql.cj.jdbc.Driver");
      ResultSet resultSet = statement.executeQuery("SELECT * from users where username = '"+ username +"'");
      if (resultSet.next()){
        return true;
      }
      else{
        return false;
      }

    } catch (Exception e){
      return false;
    }
  }

  boolean delUserCondition(String username){
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      statement.executeUpdate("Delete from users where username = '"+username+"'");
    } catch (Exception e) {
      return false;
    }
    return true;
  }

  boolean addUserCity(String chat_id, String city){
    try{
      Class.forName("com.mysql.cj.jdbc.Driver");
      statement.executeUpdate("INSERT INTO user_city(chat_id, city) value ('"+chat_id+"', '"+city+"')");
    } catch (Exception e){
      System.out.println(e);
      return false;
    }
    return true;
  }

  ArrayList<String> parseUserCity(String chat_id){
    ArrayList<String> result = new ArrayList<String>(){};
    try{
      Class.forName("com.mysql.cj.jdbc.Driver");
      ResultSet resultSet = statement.executeQuery("SELECT * from user_city where chat_id = '"+chat_id+"'");
      while (resultSet.next()) {
        result.add(resultSet.getString(2) + " "
            + resultSet.getString(3));
      }
    } catch (Exception e){
      return new ArrayList<String>();
    }
    return result;
  }

  boolean checkUserCity(String chat_id){
    try{

      Class.forName("com.mysql.cj.jdbc.Driver");
      ResultSet resultSet = statement.executeQuery("SELECT * from user_city where chat_id = '"+ chat_id +"'");
      if (resultSet.next()){
        return true;
      }
      else{
        return false;
      }

    } catch (Exception e){
      return false;
    }
  }

  boolean delUserCity(String chat_id){
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      statement.executeUpdate("Delete from user_city where chat_id = '"+chat_id+"'");
    } catch (Exception e) {
      return false;
    }
    return true;
  }
}