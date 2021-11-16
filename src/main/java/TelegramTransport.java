import java.util.*;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import static org.junit.jupiter.api.Assertions.*;

import java.util.GregorianCalendar;

public class TelegramTransport extends TelegramLongPollingBot {


    @Override
    @SneakyThrows
    public void onUpdateReceived(Update update) {
        if (update.hasCallbackQuery()) {
            handleCallback(update.getCallbackQuery());
        } else if (update.hasMessage()) {
            handleMessage(update.getMessage());
        }
    }


    @SneakyThrows
    private void handleCallback(CallbackQuery callbackQuery) {
        Message message = callbackQuery.getMessage();
        execute(SendMessage.builder()
                .chatId(message.getChatId().toString()).
                text(getCommand(callbackQuery.getData())).
                build());
    }


    public static String getCommand(String command) {

        String string = "Введите итересующие вас время и задачу в формате\n 66:66-раскрасить открытку";

        if (command.matches("\\d{2}\\D\\d{2}\\D\\D+")) {
            return "вы записали свою задачу на " + command;
        }

        switch (command) {
            case "Monday":
                return string;
            case "Tuesday":
                return string + "";
            case "Friday":
                return "да ладно это тоже не очень день для дел " + string;
            case "Saturday":
                return string + "суббота";

            default:
                return "пока наш бот не умеет говорить \n" +
                        "для озкаомления с возможностью бота введите /help";
        }
    }


    @SneakyThrows
    private void handleMessage(Message message) {
        if (message.hasText() && message.hasEntities()) {
            Optional<MessageEntity> commandEntity =
                    message.getEntities().stream().filter(e -> "bot_command".equals(e.getType())).findFirst();
            if (commandEntity.isPresent()) {
                String command =
                        message
                                .getText()
                                .substring(commandEntity.get().getOffset(), commandEntity.get().getLength());

                switch (command) {
                    case "/plans_for_Week":
                        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
                        for (Season season : Season.values()) {
                            buttons.add(
                                    Arrays.asList(InlineKeyboardButton.builder().text(season.name()).callbackData(season.toString()).build()));
                        }
                        execute(SendMessage.builder()
                                .text("выберите дату этой недели")
                                .chatId(message.getChatId().toString())
                                .replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
                                .build());
                        break;
                    case "/get_time":
                        execute(SendMessage.builder().chatId(message.getChatId().toString()).text(
                                Logic.getTime(Logic.getData())).build());
                        break;
                    case "/help":
                        execute(SendMessage.builder().
                                chatId(message.getChatId().toString()).
                                text("В нашем боте есть комманды:\n" +
                                        "/get_time-возвращает точное время\n" +
                                        " и\n" +
                                        "/plans_for_Week-составить план на сегодня").
                                build());
                        break;

                    case "/plans_for_today":
                        System.out.println("/plans_for_today");
                        break;
                    case "plans_for_month":
                        System.out.println("/plans_for_month");
                        break;
                }

            }
        } else if (message.hasText()) {
            System.out.println("что ");
            String messages = message.getText();
            execute(SendMessage.builder()
                    .text(getCommand(messages))
                    .chatId(message.getChatId().toString())
                    .build());
        }
    }

    @Override
    public String getBotUsername() {
        return "@Router_time_bot";
    }

    @Test
    public void getBotUsernameTest(){
        assertNotNull(getBotUsername());
        assertEquals(getBotUsername(), "@Router_time_bot");
    }

    @Override
    public String getBotToken() {
        return gettoken.getbottoken();
    }


    @SneakyThrows
    public static void main(String[] args) {
        Calendar c2 = new GregorianCalendar();
        System.out.println(c2.get(Calendar.MONTH));
        TelegramTransport bot = new TelegramTransport();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(bot);
    }

}

