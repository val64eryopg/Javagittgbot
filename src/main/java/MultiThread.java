import lombok.SneakyThrows;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class MultiThread extends Thread {
    private What bot;

    MultiThread(What bot) {
        this.bot = bot;
    }

    @SneakyThrows
    public void run() {
        while (true) {
            sleep(60000);
            for (String s : Logic.database.returnTasks()) {
                if (Logic.equalityDates(Logic.getData(), s)) {
                    System.out.println(1);
                    String chatId = s.split(" ")[0];
                    String task = s.split(" ")[3];
                    bot.execute(
                            SendMessage.builder()
                                    .text(task + "Прошел срок выполнения вашей задачи")
                                    .chatId(chatId)
                                    .build());

                }
            }
        }
    }
}
