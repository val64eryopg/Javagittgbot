import lombok.SneakyThrows;

import javax.xml.crypto.Data;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Multithreading extends Thread {

    private what bot;

    public void run(){
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.YEAR, 2017);
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 25);
        calendar.set(Calendar.HOUR_OF_DAY, 19);
        calendar.set(Calendar.MINUTE, 42);
        calendar.set(Calendar.SECOND, 12);
        System.out.println(calendar.getTime());
        String str = "";
        try {
            ArrayList<String> result = Logic.database.returnTasks();
                for (String i : result) {
//                    DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//                    Date d = sdf.parse(i);
//                    System.out.println(d);
                    String[] array = i.split(" |:");
                    for(String b : array){
                        str+=b;
                    System.out.println(b);}


                    System.out.println(i);
                }
        }catch (Throwable e){
            str = "не получилось";
            System.out.println(str);
        }

        while(true){
            DateComparison("f");

        }
    }

    Multithreading(what bot) {
        this.bot = bot;
    }

    @SneakyThrows
    public void DateComparison(String chat){
//        ArrayList<String> check = database.checkTasks(chat);
//        if (check.size() != 0) {
//            String[] array23 = new String[check.size()];
//            for (int i = 0; i < check.size(); i++){
//                String[] set = check.get(i).split(" ");
//                String data =   set[0];
//                String time = set[1];
//                String task = set[2];
//                String[] newarray = data.split(":");
//                String[] Secondarray = time.split(":");
//                Calendar calendar = new GregorianCalendar(Integer.parseInt (newarray[0]),Integer.parseInt (newarray[1]),Integer.parseInt (newarray[2]),1 ,1);
//                System.out.println(calendar);
//    `        }
//
//        }

    }
}
