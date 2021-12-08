import lombok.SneakyThrows;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Multithreading extends Thread {
    private static Database database = new Database(GetDatabase.getUserName(),
            GetDatabase.getPassword(), GetDatabase.getUrl());
    private What bot;

    Multithreading(What bot) {
        this.bot = bot;
    }

    @SneakyThrows
    public void run() {
//        Calendar calendar = new GregorianCalendar();
//        calendar.set(Calendar.YEAR, 2017);
//        calendar.set(Calendar.MONTH, 0);
//        calendar.set(Calendar.DAY_OF_MONTH, 25);
//        calendar.set(Calendar.HOUR_OF_DAY, 19);
//        calendar.set(Calendar.MINUTE, 42);
//        calendar.set(Calendar.SECOND, 12);
//        System.out.println(calendar.getTime());
        while (true) {sleep(100000);
            for (String s : database.returnTasks()) {
                if (Logic.equalityDates(Logic.getData(), s)) {
                    String chatId = s.split(" ")[0];
                    String task = s.split(" ")[3];
                    bot.execute(
                            SendMessage.builder()
                                    .text(task + "Прошел срок выполнения вашей задачи")
                                    .chatId(chatId)
                                    .build());

                }

//        bot.execute(
//        SendMessage.builder()
//                .text()
//                .chatId()
//                .build());
            }
        }


//    @SneakyThrows
//    public void DateComparison(String chat){
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
