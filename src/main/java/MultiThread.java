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
            sleep(60000);
            for (String s : logic.database.returnTasks()) {

                String chatId = s.split(" ")[0];
                String task = s.split(" ")[3];
                if (logic.equalityDate(
                                        logic.database.parseUserCity(chatId).get(0).split( " ")[1], // не укаждого пользователя указано timezone
                                        logic.getData(),
                                        s)) {
                    System.out.println(1);
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
