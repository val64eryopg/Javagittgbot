import lombok.SneakyThrows;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class MultiThread extends Thread {
    private Bot bot;
    private Logic logic;

    MultiThread(Bot bot, Logic logic) {
        this.bot = bot;
        this.logic = logic;
    }

    @SneakyThrows
    public void run() {

        while (true) {
            for (String s : logic.database.returnTasks()) {

                String chatId = s.split("%")[0];
                String date = s.split("%")[1];
                String time = s.split("%")[2];
                String task = s.split("%")[3];
                String timezone = "0";

                if (logic.database.checkUserCity(chatId))
                    timezone = logic.database.parseUserCity(chatId).get(0).split( " ")[1];

                if (logic.equalityDate(
                                        timezone, // не у каждого пользователя указано timezone
                                        logic.getData(),
                                        date + " " + time)) {
                    System.out.println(1);
                    bot.execute(
                            SendMessage.builder()
                                    .text(date + "\t" + time + "\t" + task + "\nПрошел срок выполнения вашей задачи")
                                    .chatId(chatId)
                                    .build());

                    logic.deleteTask(chatId, date, time);
                    System.out.println("все просроченныые задачи удалены");
                }
            }
            sleep(30000);
        }
    }
}
