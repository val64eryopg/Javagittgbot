import java.io.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;


public class Logic {

    public static Map<String, Integer> dateToInt = new HashMap<String, Integer>();
    public static Database database = new Database(GetDatabase.getUserName(),
            GetDatabase.getPassword(), GetDatabase.getUrl());

    public static void DateInizialition(){
        dateToInt.put("Пн", 1);
        dateToInt.put("Вт", 2);
        dateToInt.put("Ср", 3);
        dateToInt.put("Чт", 4);
        dateToInt.put("Пт", 5);
        dateToInt.put("Сб", 6);
        dateToInt.put("Вс", 7);
    }

    public static String getTimeFromPage(String city) {
//        добываем время по запросу если нет города честно это говорим
        String href = "";
        try {
            Document doc = Jsoup.connect("https://dateandtime.info/ru/index.php#")
                    .get();
            Elements timeCity = doc.select("a[href^=\"city\"],time[datetime]");
            for (int i = 0; i <= timeCity.size() - 1; i++) {
                if (timeCity.get(i).text().equals(city)) {
                    href =  timeCity.get(i + 1).text();
                }
            }
            if (!(href.equals(""))){
                return href;
            }
            else{
                return "Город не найден";
            }
        } catch (IOException e) {
            return "не подлючились к странице";
        }
    }


    public static void getCommand(String CommandOrText, String ListOfButtons, String chatId)
            throws SQLException, ClassNotFoundException {
        if(!(database.checkUserCondition(chatId))){
            if (!(CommandOrText.equals("")) && !(ListOfButtons.equals(""))) {
                switchListOfButtons(CommandOrText, ListOfButtons, chatId);
            }
        }else{
            database.delUserCondition(chatId);
            switchListOfButtons(CommandOrText, ListOfButtons, chatId);
        }
    }

    private static void switchListOfButtons(String CommandOrText, String ListOfButtons,
        String chatId)  throws SQLException, ClassNotFoundException {
        switch (ListOfButtons) {
            case "Выберете число этого месяца:":
                if (!(CommandOrText.equals("следущий месяц"))) {
                    database.addUserCondition(chatId, ListOfButtons + "%" + CommandOrText);
                }
                break;
            case "Нажмите на то что хотите удалить:":
                String[] words = CommandOrText.split(" ");
                database.delTask(chatId, words[0], words[1]);
                break;
            default:
                System.out.println("не попал");
        }
    }

    public static int dateSubraction(String otherDate){
        String localDate = getTimeFromPage("Екатеринбург");
        System.out.println(localDate.split(" ")[0]);
        int dayOfWeekIntLocal = dateToInt.get(localDate.split(" ")[0]);
        System.out.println(otherDate);
        int dayOfWeekIntOther = dateToInt.get(otherDate.split(" ")[0]);
        System.out.println(dayOfWeekIntLocal);
        if (dayOfWeekIntOther == 7 || dayOfWeekIntLocal > dayOfWeekIntOther)
            return -1;
        else if (dayOfWeekIntOther > dayOfWeekIntLocal)
            return 1;

        return 0;
    }

    public static String gettingTimeZone(String str){
        String[] localDate = dateFormat(getData()).split(" ");
        String[] otherDate = str.split(" ");
        Calendar timeInBeforeBreak = Calendar.getInstance();
        timeInBeforeBreak.clear();

        Calendar timeOutBeforeBreak = Calendar.getInstance();
        timeOutBeforeBreak.clear();
        int k = dateSubraction(str);
        timeInBeforeBreak.add(Calendar.DAY_OF_MONTH, Integer.parseInt(localDate[0].split("-")[2]));
        timeInBeforeBreak.add(Calendar.HOUR_OF_DAY, Integer.parseInt(localDate[1].split(":")[0]));
        timeInBeforeBreak.add(Calendar.MINUTE, Integer.parseInt(localDate[1].split(":")[1]));
        timeOutBeforeBreak.add(Calendar.DAY_OF_MONTH, Integer.parseInt(localDate[0].split("-")[2]) + k);
        timeOutBeforeBreak.add(Calendar.HOUR_OF_DAY, Integer.parseInt(otherDate[1].split(":")[0]));

        timeOutBeforeBreak.add(Calendar.MINUTE, Integer.parseInt(otherDate[1].split(":")[1]));

        long timeMillis = timeOutBeforeBreak.getTimeInMillis() - timeInBeforeBreak.getTimeInMillis();
        System.out.println(timeMillis/1000/60/60);
        return timeMillis/1000/60/60+"";
    }


    public static ResultsCommand switchMessage(String command, String chatId) {
        //разбор команды
        ResultsCommand result = new ResultsCommand();
        String[] defolt = {"не то"};
        switch (command) {


            case "/RegistrationForA_Month":


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
                result.setResult("Выберете число этого месяца:");
                result.setMyArray(array);

                return result;
            case "/start":

                result.setResult(
                        "Доброго времени, суток наш бот записывает и отображает ваши задачи" +
                                "\n Вот список доступных команд такой: " +
                                "\n /DeleteTask " +
                                "\n /MyTask " +
                                "\n /LookAtWatch " +
                                "\n /RegistrationForA_Month " +
                                "\n /RegistrationForA_Week"+
                                "\n Для того чтобы пользоваться этим ботом необходимо привязаться к городу дефолт Лондон" +
                                "\n команда для привязки " +
                                "\n /RegistrationOnCity");

                result.setMyArray(defolt);
                return result;
            case "/LookAtWatch":

                result.setResult("Напишите город:");
                database.addUserCondition(chatId, "/LookAtWatch%выбираем город");
                result.setMyArray(defolt);
                return result;
            case "/otmena":
                database.delUserCondition(chatId);
                break;
            case "/RegistrationOnCity":
                result.setMyArray(defolt);
                result.setResult("Напишите ваш город");
                database.addUserCondition(chatId,"RegistrationOnCity");
                return result;

            case "/DeleteTask":

                result.setResult("Нажмите на то что хотите удалить:");
                try {
                    ArrayList<String> check = database.checkTasks(chatId);
                    if (check.size() != 0) {
                        String[] array111 = new String[check.size()];
                        for (int i = 0; i < check.size(); i++) array111[i] = check.get(i);
                        result.setMyArray(array111);
                        return result;
                    } else {
                        check.add("нет данных");
                        String[] array111 = new String[check.size()];
                        for (int i = 0; i < check.size(); i++) array111[i] = check.get(i);
                        result.setMyArray(array111);
                        return result;

                    }
                } catch (ClassNotFoundException | SQLException ex) {
                }
                break;
            case "/MyTask":

                result.setResult("Вот список ваших задач:");
                try {
                    ArrayList<String> check = database.checkTasks(chatId);
                    if (check.size() != 0) {
                        String[] array111 = new String[check.size()];
                        for (int i = 0; i < check.size(); i++) array111[i] = check.get(i);
                        result.setMyArray(array111);
                    } else {
                        check.add("нет данных");
                        String[] array111 = new String[check.size()];
                        for (int i = 0; i < check.size(); i++) array111[i] = check.get(i);
                        result.setMyArray(array111);
                        return result;
                    }
                } catch (ClassNotFoundException | SQLException ex) {
                    ex.printStackTrace();
                }
                break;

            case "/HELP":

                result.setResult(
                        "Список доступных команд: " +
                                "\n /DeleteTask" +
                                " \n /MyTask" +
                                " \n /LookAtWatch" +
                                " \n /RegistrationForA_Week");

                result.setMyArray(defolt);
                return result;
            default:
                result.setResult(
                    "Команда не распознана." +
                            "\nСписок доступных команд:" +
                            " \n /HELP \n" +
                            " /DeleteTask " +
                            "\n /MyTask " +
                            "\n /LookAtWatch " +
                            "\n /RegistrationForA_Month ");
                result.setMyArray(defolt);
                return result;
        }
        return result;
    }



    public static void writing(String chatId, String Data, String Time, String Task) throws SQLException, ClassNotFoundException {
        database.addTask(chatId, Data, Time, Task);
        System.out.println(database.checkTasks(chatId));
    }

    public static String dateFormat(Date date) {
        String pattern = "yyyy-MM-dd HH:mm";
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }

    public static boolean equalityDates(Date dateNow, String dateFromDatabase) {
        String[] dateArray = dateFormat(dateNow).split(" ");
        String[] dateFromDatabaseArray = dateFromDatabase.split(" ");

        System.out.println(dateArray[0]);
        System.out.println(dateFromDatabaseArray[1]);

        boolean YearMonthDay = dateArray[0].equals(dateFromDatabaseArray[1]);
        boolean Hour = dateArray[1].split(":")[0].regionMatches(0, dateFromDatabaseArray[2].split(":")[0], 0, 2);
        boolean Min = dateArray[1].split(":")[1].regionMatches(0, dateFromDatabaseArray[2].split(":")[1], 0, 2);

        System.out.println(dateArray[1].split(":")[0]);
        System.out.println(dateArray[1].split(":")[1]);

        return YearMonthDay & Hour & Min;
    }


    public static Date getData() {
        Date date = new Date();
        return date;
    }

}
