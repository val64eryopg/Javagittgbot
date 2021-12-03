import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Multithreading extends Thread {
    public static String chat = "";
    private static Database database = new Database(getdatabase.getUserName(),
            getdatabase.getPassword(), getdatabase.getUrl());

    public void run(){
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.YEAR, 2017);
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 25);
        calendar.set(Calendar.HOUR_OF_DAY, 19);
        calendar.set(Calendar.MINUTE, 42);
        calendar.set(Calendar.SECOND, 12);
        System.out.println(calendar.getTime());
        while(true){
            DateComparison(chat);
        }
    }

    @SneakyThrows
    public void DateComparison(String chat){
        ArrayList<String> check = database.checkTasks(chat);
        if (check.size() != 0) {
            String[] array23 = new String[check.size()];
            for (int i = 0; i < check.size(); i++){
                String[] set = check.get(i).split(" ");
                Calendar calendar = new GregorianCalendar();
                calendar.set(Calendar.YEAR,1);

            }

        }

    }
}
