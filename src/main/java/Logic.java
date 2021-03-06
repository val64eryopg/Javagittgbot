import java.awt.SystemTray;
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


    public  void getCommand(String CommandOrText, String ListOfButtons, String chatId)
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

    private void switchListOfButtons(String CommandOrText, String ListOfButtons,
        String chatId)  throws SQLException, ClassNotFoundException {
        switch (ListOfButtons) {
            case "Выберете число этого месяца:":
                if (!(CommandOrText.equals("следущий месяц"))) {
                    System.out.println("выберете число");
                    if(database.checkUserCondition(chatId)){
                    database.delUserCondition(chatId);
                    database.addUserCondition(chatId, ListOfButtons + "%" + CommandOrText);
                    }
                    else {
                        database.addUserCondition(chatId, ListOfButtons + "%" + CommandOrText);
                    }
                }
                break;
            case "Нажмите на то что хотите удалить:":
                String[] words = CommandOrText.split(" ");
                database.delTask(chatId, words[0], words[1]);
                break;
            case "Выберете число этого месяца и укажите повтор:":
                if (!(CommandOrText.equals("следущий месяц"))) {
                    if(database.checkUserCondition(chatId)){
                        database.delUserCondition(chatId);
                        database.addUserCondition(chatId, ListOfButtons + "%" + CommandOrText);
                    }
                    else {
                        database.addUserCondition(chatId, ListOfButtons + "%" + CommandOrText);
                    }
                }
                break;

            default:
                System.out.println("не попал");
        }
    }

    public  int dateSubraction(String otherDate){
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

    public  String gettingTimeZone(String str){
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


    public ResultsCommand switchMessage(String command, String chatId) {
        //разбор команды
        ResultsCommand result = new ResultsCommand();
        String[] defolt = {"не то"};

            switch (command) {
                case"Выберете число этого месяца и укажите повтор:":
                    System.out.println(22);
                    String[] arraystr = {"1-каждый день ",
                            "7-каждую неделю"};

                    result.setMyArray(arraystr);
                    result.setResult("пример");
                    return result;
                case "/RegistrationForA_Month":
                    ArrayList<String> Buttons = new ArrayList<String>();
                    String str = new String("");
                    int DaysAtMonth = 0;

                    for (CalendarDate element : CalendarDate.values()) {
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
                case "/RegistrationWithRepeat":
                    ArrayList<String> Buttons20 = new ArrayList<String>();
                    String str20 = new String("");
                    int DaysAtMonth20 = 0;

                    for (CalendarDate element : CalendarDate.values()) {
                        if (Calendar.getInstance().get(Calendar.MONTH) == element.ordinal()) {
                            DaysAtMonth20 = element.getI();
                        }

                    }

                    for (int i = Calendar.getInstance().get(Calendar.DAY_OF_MONTH); i <= DaysAtMonth20 + 1; i++) {
                        if (i <= DaysAtMonth20) {
                            str20 = Integer.toString(i);
                        } else {
                            str20 = "следущий месяц";
                        }
                        Buttons20.add(str20);
                    }
                    String[] array20 = new String[Buttons20.size()];
                    for (int i = 0; i < Buttons20.size(); i++) {
                        array20[i] = Buttons20.get(i);
                    }
                    result.setResult("Выберете число этого месяца и укажите повтор:");
                    result.setMyArray(array20);

                    return result;
                case "/start":

                    result.setResult(
                            "Доброго времени, суток наш бот записывает и отображает ваши задачи" +
                                    "\n Вот список доступных команд такой: " +
                                    "\n /DeleteTask " +
                                    "\n /MyTask " +
                                    "\n /LookAtWatch " +
                                    "\n /RegistrationForA_Month " +
                                    "\n Для того чтобы пользоваться этим ботом необходимо привязаться к городу дефолт Лондон" +
                                    "\n команда для привязки " +
                                    "\n /RegistrationOnCity" +
                                    "\n/RegistrationWithRepeat");

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
                    if (database.checkUserCondition(chatId)) {
                        database.delUserCondition(chatId);
                        database.addUserCondition(chatId, "RegistrationOnCity%");
                    }else{
                        database.addUserCondition(chatId, "RegistrationOnCity%");
                    }

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
                                    " \n /RegistrationForA_Month");

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
                                    "\n /RegistrationForA_Month");
                    result.setMyArray(defolt);
                    return result;
            }
        return result;
    }


    public void deleteTask(String chatId, String data, String time)
        throws SQLException, ClassNotFoundException {
        // перезаписывает просроченные задачи и корректирует повторения задач
        ArrayList<String> task = database.parseTask(chatId, data, time);
        database.addOverdueTask(chatId, data, time, task.get(0).split("%")[2], "-1");
        String rep = task.get(0).split("%")[3];
        System.out.println(rep.equals("0"));

        System.out.println(chatId+"%"+data+"%"+time);
        if (rep.equals("0")){
            System.out.println(chatId+"%"+data+"%"+time);
            if (database.delTask(chatId, data, time))
                System.out.println("Удаление прошло успешно");
            else
                System.out.println("Задача не удалена");
        }
        else{
            if (database.delTask(chatId, data, time))
                System.out.println("Удаление прошло успешно");
            else
                System.out.println("Задача не удалена");

            data = changeDate(task.get(0).split("%")[0], rep);
            writing(chatId, data, time, task.get(0).split("%")[2], rep);
            System.out.println(chatId+"%"+data+"%"+time);
        }
        System.out.println("DeleteTask отработал");
    }

    public String changeDate(String data, String repeat){
        // считает новую дату в зависимости от того, какое повторение было выбрано
        Calendar time = Calendar.getInstance();
        time.clear();
        time.set(Calendar.YEAR, Integer.parseInt(data.split("-")[0]));
        time.set(Calendar.MONTH, Integer.parseInt(data.split("-")[1]) - 1);
        time.set(Calendar.DAY_OF_MONTH, Integer.parseInt(data.split("-")[2]));
        System.out.println(time.getTime());

        if (repeat.equals("1"))
            time.setTimeInMillis(time.getTimeInMillis() + 1000*60*60*24);
        else if (repeat.equals("2"))
            time.setTimeInMillis(time.getTimeInMillis() + 1000*60*60*24*7);

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        Date date = time.getTime();
        return dateFormat.format(date);

    }

    public void writing(String chatId, String data, String time, String task, String repeats) throws SQLException, ClassNotFoundException {
        database.addTask(chatId, data, time, task, repeats);
        System.out.println(database.checkTasks(chatId));
    }
    private static String dateFormat(Date date) {
        String pattern = "yyyy-MM-dd HH:mm";
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }

    public  boolean equalityDate(String timeZoneUser, Date dateNow, String dateFromDatabase) {
        String[] dateArray = dateFormat(dateNow).split(" ");
        String[] dateFromDatabaseArray = dateFromDatabase.split(" ");

        return equalityDateTimeZone(dateArray,dateFromDatabaseArray,Integer.parseInt(timeZoneUser));
    }

    private static boolean equalityDateTimeZone(String[] dateArray, String[] dateFromDatabaseArray, int timeZoneUser){

        int localDay = Integer.parseInt(dateArray[0].split("-")[2]);
        int localHour = Integer.parseInt(dateArray[1].split(":")[0]);
        int localMin = Integer.parseInt(dateArray[1].split(":")[1]);

        int userDay = Integer.parseInt(dateFromDatabaseArray[0].split("-")[2]);
        int userHour = Integer.parseInt(dateFromDatabaseArray[1].split(":")[0]);
        int userMin = Integer.parseInt(dateFromDatabaseArray[1].split(":")[1]);


        Calendar localTime = Calendar.getInstance();
        localTime.clear();
        localTime.add(Calendar.DAY_OF_MONTH, localDay-1);
        localTime.add(Calendar.HOUR_OF_DAY, localHour);
        localTime.add(Calendar.MINUTE, localMin);

        Calendar userTime = Calendar.getInstance();
        userTime.clear();
        userTime.add(Calendar.DAY_OF_MONTH, userDay-1);
        userTime.add(Calendar.HOUR_OF_DAY, userHour);
        userTime.add(Calendar.MINUTE, userMin);

        localTime.setTimeInMillis(localTime.getTimeInMillis() + (1000*60*60*timeZoneUser));

        return localTime.getTimeInMillis() == userTime.getTimeInMillis();
    }


    public static Date getData() {
        Date date = new Date();
        return date;
    }

}
