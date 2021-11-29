import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;


public class Logic {


    private static Database database = new Database(getdatabase.getUserName(), getdatabase.getPassword(), getdatabase.getUrl());

    public static void getCommand(String CommandOrText, String ListOfButtons, String chatId) throws SQLException, ClassNotFoundException {
        String string = "Введите итересующие вас время и задачу в формате\n HH:mm-раскрасить открытку";


        if (!(CommandOrText.equals("")) && !(ListOfButtons.equals(""))) {
            ConditionOfTheObject mc = ConditionOfTheObject.COMMAND;
            switch (ListOfButtons) {
                case "Выберете число этого месяца:":
                    System.out.println("попал");
                    if (!(CommandOrText.equals("следущий месяц"))) {
                        System.out.println(CommandOrText);
                        mc.setS(ListOfButtons);
                        mc.setI(CommandOrText);
                    }
                    break;
                case "выберете дату этой неделиЖ":

                    break;
                case "Нажмите на то что хотите удалить:":
                    String[] words = CommandOrText.split(" ");
                    database.delTask(words[0], words[1], words[2]);
                    break;
                default:
                    System.out.println("не попал");

//                case "выберете дату этой неделиЖ":
//                    switch (CommandOrText) {
//                        case "Monday":
//                            mc.setS("неделя");
//                            mc.setS("неделя");
//                            mc.setI("time");
//                            System.out.println(mc.getS());
//                            return string;
//                        case "Tuesday":
////                            hoIAM("выберете дату этой неделиЖ","Tuesday");
//                            mc.setS("неделя");
//                            mc.setI("time");
//                            System.out.println(mc.getS());
//                            return string + "";
//                        case "Wednesday":
//                            mc.setS("неделя");
//                            mc.setI("time");
//                            System.out.println(mc.getS());
//                            return string;
//                        case "Thursday":
//                            mc.setS("неделя");
//                            mc.setI("time");
//                            System.out.println(mc.getS());
//                            return string;
//                        case "Sunday":
//                            mc.setS("неделя");
//                            mc.setI("time");
//                            System.out.println(mc.getS());
//                            return string;
//                        case "Friday":
//                            mc.setS("неделя");
//                            mc.setI("time");
//                            System.out.println(mc.getS());
//                            return "да ладно это тоже не очень день для дел " + string;
//                        case "Saturday":
//                            hoIAM(ListOfButtons, Season.Saturday.ordinal());
//                            mc.setS("неделя");
//                            mc.setI("time");
//                            System.out.println(mc.getS());
//                            return string + "суббота";
//
//                        default:
//                            return "пока наш бот не умеет говорить \n" +
//                                    "для озкаомления с возможностью бота введите /help";
//                    }
//
//            }
//
//        } else
//            return "неизвестная науке ошибка";
//        return "";
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
                List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
                for (Season date : Season.values()) {
                    Buttons1.add(date.name());
                }
                result.SetResult("выберете день недели");
                String[] arrayWeek = new String[Buttons1.size()];
                for (int i = 0; i < Buttons1.size(); i++) arrayWeek[i] = Buttons1.get(i);
                result.setMyArray(arrayWeek);
                return result;

            case "/RegistrationForA_Month":

                System.out.println("я попал в нужный кейс");
                ArrayList<String> Buttons = new ArrayList<String>();
                String str = new String("");
                Integer DaysAtMonth = new Integer(0);


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
                String[] arrayMonth = new String[Buttons.size()];
                for (int i = 0; i < Buttons.size(); i++) arrayMonth[i] = Buttons.get(i);
                result.SetResult("Выберете число этого месяца:");
                result.setMyArray(arrayMonth);
                System.out.println(result.getFirst());
                return result;
            case "/f":
                break;
            case "/LookAtWatch":

                ArrayList<String> Buttons3 = new ArrayList<String>();
                result.SetResult(getData().toString());
                Buttons3.add("не то");
                String[] array11 = new String[Buttons3.size()];
                for (int i = 0; i < Buttons3.size(); i++) array11[i] = Buttons3.get(i);
                result.setMyArray(array11);
                return result;
            case "/DeleteTask":
                result.SetResult("Нажмите на то что хотите удалить:");
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Database database = new Database(getdatabase.getUserName(), getdatabase.getPassword(), getdatabase.getUrl());
                    ArrayList<String> check = database.checkTasks(chatId);
                    String[] array111 = new String[check.size()];
                    for (int i = 0; i < check.size(); i++) array111[i] = check.get(i);
                    result.setMyArray(array111);
                    return result;
                } catch (ClassNotFoundException | SQLException ex) {
                }
                break;
            case "/MyTask":

                result.SetResult("Вот список ваших задач:");
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Database database = new Database(getdatabase.getUserName(), getdatabase.getPassword(), getdatabase.getUrl());
                    ArrayList<String> check = database.checkTasks(chatId);
                    String[] arrayTask = new String[check.size()];
                    for (int i = 0; i < check.size(); i++) arrayTask[i] = check.get(i);
                    result.setMyArray(arrayTask);
                    return result;
                } catch (ClassNotFoundException | SQLException ex) {
                }
                break;
            case "/Help":
                ArrayList<String> Buttons4 = new ArrayList<String>();
                String strHelp = "";
                result.SetResult("Список доступных команд: \n/DeleteTask \n/MyTask \n/LookAtWatch \n/RegistrationForA_Month \n/RegistrationForA_Week");
                String[] array5 = new String[Buttons4.size()];
                for (int i = 0; i < Buttons4.size(); i++) array5[i] = Buttons4.get(i);
                result.setMyArray(array5);
                return result;
        }
        return result;
    }


    public static void hoIAM(String init) {
        // метод должен определять на какое число идет запись

    }


    public static String getTime(Date date) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        return timeFormat.format(date);
    }

    public static Date getData() {
        return new Date();
    }

    public static void Writing(String write, String Data, String Time, String Task) throws SQLException, ClassNotFoundException {
        database.addTask(write, Data, Time, Task);
        System.out.println(database.checkTasks(write));
    }
}
