import java.io.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.sql.SQLException;
import java.util.*;


public class Logic {

  public static Database database = new Database(getdatabase.getUserName(),
      getdatabase.getPassword(), getdatabase.getUrl());




    public static String getTimeFromPage(String city){
//        добываем время по запросу если нет города честно это говорим
        try {
            Document doc = Jsoup.connect("https://dateandtime.info/ru/index.php#")
                    .get();
            Elements timeCity = doc.select("a[href^=\"city\"],time[datetime]");
            for (int i = 0; i <= timeCity.size() - 1; i++) {
                if (timeCity.get(i).text().equals(city)) {
                    return timeCity.get(i + 1).text();
                }
            }
        } catch (IOException e) {
            return "не подлючились к странице";
        }
        return "нет такого города";
    }



  public static void getCommand(String CommandOrText, String ListOfButtons, String chatId)
      throws SQLException, ClassNotFoundException {
      System.out.println("здесь");

    if (!(CommandOrText.equals("")) && !(ListOfButtons.equals(""))) {
      ConditionOfTheObject mc = ConditionOfTheObject.COMMAND;
      switch (ListOfButtons) {
        case "Выберете число этого месяца:":
          if (!(CommandOrText.equals("следущий месяц"))) {
//              if((database.checkUserCodtion(chatId))) {
                  database.addUserCondtion(chatId, ListOfButtons + "%" + CommandOrText);
                  System.out.println("я добавился");
//            mc.setS(ListOfButtons);
//            mc.setI(CommandOrText);
//              }
          }
          break;
        case "выберете дату этой неделиЖ":

          break;
        case "Нажмите на то что хотите удалить:":
            String[] words = CommandOrText.split(" ");
            System.out.println(words[0]+words[1]+words[2]);
            database.delTask(chatId,words[0],words[1]);
            break;
        default:
          System.out.println("не попал");
      }
    }
  }


  public static ResultsCommand SwitchMessage(String command, String chatId) {
    //разбор команды
    ResultsCommand result = new ResultsCommand();
    System.out.println("я попал в метод switchMessege");
    switch (command) {


      case "/RegistrationForA_Week":
        ArrayList<String> Buttons1 = new ArrayList<String>();
        for (Season date : Season.values()) {
          Buttons1.add(date.name());
        }
        result.SetResult("выберете день недели");
        String[] array1 = new String[Buttons1.size()];
        for (int i = 0; i < Buttons1.size(); i++) {
          array1[i] = Buttons1.get(i);
        }
        result.setMyArray(array1);
        result.setMyArray(array1);
        return result;



      case "/RegistrationForA_Month":

        System.out.println("я попал в нужный кейс");
        ArrayList<String> Buttons = new ArrayList<String>();
        String str = new String("");
        int DaysAtMonth = 0;

        for (date element : date.values()) {
          if (Calendar.getInstance().get(Calendar.MONTH) == element.ordinal()) {
            DaysAtMonth = element.getI();
          }

        }

        for (int i = Calendar.getInstance().get(Calendar.DAY_OF_MONTH); i <= DaysAtMonth + 1; i++) {
          if (i <= DaysAtMonth) {
            str = Integer.toString(i);
          } else {
            str = "следущий месяц";
          }
          Buttons.add(str);
        }
        String[] array = new String[Buttons.size()];
        for (int i = 0; i < Buttons.size(); i++) {
          array[i] = Buttons.get(i);
        }
        result.SetResult("Выберете число этого месяца:");
        result.setMyArray(array);
        System.out.println(result.getFirst());
        return result;
      case "/start":
          ArrayList<String> Buttons44 = new ArrayList<String>();
          result.SetResult(
                  "Доброго времени, суток наш бот записывает и отображает ваши задачи" +
                          "\n Вот список доступных команд такой: " +
                          "\n /DeleteTask " +
                          "\n /MyTask " +
                          "\n /LookAtWatch " +
                          "\n /RegistrationForA_Month " +
                          "\n /RegistrationForA_Week");
          String[] array55= new String[Buttons44.size()];
          for (int i = 0; i < Buttons44.size(); i++) {
              array55[i] = Buttons44.get(i);
          }
          result.setMyArray(array55);
          return result;
      case "/LookAtWatch":

        ArrayList<String> Buttons3 = new ArrayList<String>();
        result.SetResult("Напишите город:");
        Buttons3.add("не то");
        String[] array11 = new String[Buttons3.size()];
        for (int i = 0; i < Buttons3.size(); i++) {
          array11[i] = Buttons3.get(i);
        }
        ConditionOfTheObject mc1 = ConditionOfTheObject.COMMAND;
        mc1.setI("выбираем город");
        result.setMyArray(array11);
        return result;
        case "/otmena":
            ConditionOfTheObject mc = ConditionOfTheObject.COMMAND;
            mc.setS("ТипКоманды");
            mc.setI("Значение");
            database.delUserCondtion(chatId);
            System.out.println(database.checkUserCodtionf(chatId));
            break;
        case "/DeleteTask":
            result.SetResult("Нажмите на то что хотите удалить:");
            try {
                ArrayList<String> check = database.checkTasks(chatId);
                if (check.size() != 0){
                    String[] array111 = new String[check.size()];
                    for(int i = 0; i < check.size(); i++) array111[i] = check.get(i);
                    result.setMyArray(array111);
                    return result;
                } else
                {
                    check.add("нет данных");
                    String[] array111 = new String[check.size()];
                    for(int i = 0; i < check.size(); i++) array111[i] = check.get(i);
                    result.setMyArray(array111);
                    return result;

                }
            } catch (ClassNotFoundException | SQLException ex) {
            }
            break;
        case "/MyTask":
            result.SetResult("Вот список ваших задач:");
            try {
                ArrayList<String> check = database.checkTasks(chatId);
                if (check.size() != 0){ String[] array111 = new String[check.size()];
                    for(int i = 0; i < check.size(); i++) array111[i] = check.get(i);
                    result.setMyArray(array111);}
                else{
                    check.add("нет данных");
                    String[] array111 = new String[check.size()];
                    for(int i = 0; i < check.size(); i++) array111[i] = check.get(i);
                    result.setMyArray(array111);
                    return result;
                }
            } catch (ClassNotFoundException | SQLException ex) {
                ex.printStackTrace();
            }
            break;
      case "/HELP":
        ArrayList<String> Buttons4 = new ArrayList<String>();
        String strHelp = "";
        result.SetResult(
            "Вот список доступных команд такой: \n /DeleteTask \n /MyTask \n /LookAtWatch \n /RegistrationForA_Month \n /RegistrationForA_Week");
        String[] array5 = new String[Buttons4.size()];
        for (int i = 0; i < Buttons4.size(); i++) {
          array5[i] = Buttons4.get(i);
        }
        result.setMyArray(array5);
        return result;
    }
    return result;
  }


  public static void hoIAM(String init) {
    // метод должен определять на какое число идет запись

  }


  public static void Writing(String chatId, String Data, String Time, String Task) throws SQLException, ClassNotFoundException {
    database.addTask(chatId, Data, Time, Task);
    System.out.println(database.checkTasks(chatId));
  }
}
