import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import java.util.Date;


public class Logic {

    public static ConditionOfTheObject mc;


    private static Database database = new Database("sql11453146", "lAMgQUpd9q",
            "jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11453146");

    public static String getCommand(String CommandOrText,String ListOfButtons,String ChatId) throws SQLException, ClassNotFoundException {
        String string = "Введите итересующие вас время и задачу в формате\n 66:66-раскрасить открытку";

        if (CommandOrText.matches("\\d{2}\\D\\d{2}\\D\\D+")) {
            return "вы записали свою задачу на " + CommandOrText;
        }


        if (!(CommandOrText.equals(""))&&!(ListOfButtons.equals(""))){
            ConditionOfTheObject mc = ConditionOfTheObject.COMMAND;
            System.out.println(CommandOrText);
            switch (ListOfButtons){
                case "выберете число этого месяца:":
                    switch (CommandOrText) {
                        case "26":
                            mc.setS("месяц");
                            System.out.println("попал сюда");
                            hoIAM("выберете число этого месяца:","26");
                            break;
                        case "28":
                            break;

                        case "27":
                            break;

                        case "29":
                            break;

                        case "30":
                            break;


                        case "следущий месяц":

                            break;
                        default:
                            System.out.println(CommandOrText);
                            return string;
                    }

                case "выберете дату этой неделиЖ":
                    switch (CommandOrText) {
                        case "Monday":
                            mc.setS("неделя");
                            database.addTask(ChatId,"2021-11-26","22:53","сделай что то");
                            System.out.println(database.checkTasks(ChatId));
                            mc.setS("неделя");
                            mc.setI("time");
                            System.out.println(mc.getS());
                            return string;
                        case "Tuesday":
                            hoIAM("выберете дату этой неделиЖ","Tuesday");
                            mc.setS("неделя");
                            mc.setI("time");
                            System.out.println(mc.getS());
                            return string + "";
                        case "Wednesday":
                            mc.setS("неделя");
                            mc.setI("time");
                            System.out.println(mc.getS());
                            return string;
                        case "Thursday":
                            mc.setS("неделя");
                            mc.setI("time");
                            System.out.println(mc.getS());
                            return string;
                        case "Sunday":
                            mc.setS("неделя");
                            mc.setI("time");
                            System.out.println(mc.getS());
                            return string;
                        case "Friday":
                            mc.setS("неделя");
                            mc.setI("time");
                            System.out.println(mc.getS());
                            return "да ладно это тоже не очень день для дел " + string;
                        case "Saturday":
                            mc.setS("неделя");
                            mc.setI("time");
                            System.out.println(mc.getS());
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


    public static void hoIAM(String init,String TimeOrData){
        ConditionOfTheObject mc = ConditionOfTheObject.COMMAND;
        System.out.println("а теперь сюда");
        if (init == "выберете число этого месяца:"){
            LocalDate today = LocalDate.now();
            int month = today.getMonthValue();
            int year = today.getYear();
            mc.setI( year+"-" + month +  "-"+ TimeOrData  );
            System.out.println(mc.getI());
            System.out.println(init+" "+month);

        }

    }




    public static String getTime(Date date) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        return timeFormat.format(date);
    }

    public static Date getData() {
        return new Date();
    }

    public static void  Writing(String write,String v,String z, String f) throws SQLException, ClassNotFoundException {
        database.addTask(write,v,z,f);
        System.out.println(database.checkTasks(write));
    }
}
