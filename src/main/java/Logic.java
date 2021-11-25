import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class Logic {
    private static Database database = new Database("sql11453146", "lAMgQUpd9q",
            "jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11453146");

    public static String getCommand(String CommandOrText,String ListOfButtons,String ChatId) throws SQLException, ClassNotFoundException {
        String string = "Введите итересующие вас время и задачу в формате\n 66:66-раскрасить открытку";

        if (CommandOrText.matches("\\d{2}\\D\\d{2}\\D\\D+")) {
            return "вы записали свою задачу на " + CommandOrText;
        }


        if (!(CommandOrText.equals(""))&&!(ListOfButtons.equals(""))){
            ConditionOfTheObject mc = ConditionOfTheObject.COMMAND;
            switch (ListOfButtons){
                case "выберете число этого месяца:":
                    switch (CommandOrText) {
                        case "следущий месяц":

                            break;
                        default:
                            return string;
                    }

                case "выберете дату этой неделиЖ":
                    switch (CommandOrText) {
                        case "Monday":
                            mc.setS("неделя");
                            System.out.println(ChatId);
                            database.addTask( ChatId,"2021-11-26","22:53","сделай что-то");
                            System.out.println(database.checkTasks(ChatId));
                            System.out.println(mc.getS());
                            return string;
                        case "Tuesday":
                            return string + "";
                        case "Wednesday":
                            return string;
                        case "Thursday":
                            return string;
                        case "Sunday":
                            return string;
                        case "Friday":
                            return "да ладно это тоже не очень день для дел " + string;
                        case "Saturday":
                            return string + "суббота";

                        default:
                            return "пока наш бот не умеет говорить \n" +
                                    "для озкаомления с возможностью бота введите /help";
                    }

            }

        }
        else
            return "неизвестная науке ошибка";
        return "";
    }


    public Integer hoIAM(){return 1;}




    public static String getTime(Date date) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        return timeFormat.format(date);
    }

    public static Date getData() {
        return new Date();
    }
}
