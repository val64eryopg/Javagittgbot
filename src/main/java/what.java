import java.util.*;
import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import java.util.Calendar;



public class what extends TelegramLongPollingBot{

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
        ConditionOfTheObject mc = ConditionOfTheObject.COMMAND;
        Message message = callbackQuery.getMessage();
        execute(SendMessage.builder().chatId(message.getChatId().toString()).text(Logic.getCommand(callbackQuery.getData(), message.getText(),message.getChatId().toString())).build());
    }



    @SneakyThrows
    public void handleMessage(Message message){
        if(message.hasText() && message.hasEntities()){
            Optional<MessageEntity> commandEntity =
                    message.getEntities().stream().filter(e -> "bot_command".equals(e.getType())).findFirst();
            if(commandEntity.isPresent()){
                String command =
                        message
                                .getText()
                                .substring(commandEntity.get().getOffset(), commandEntity.get().getLength());

                switch (command){
                        case"/get_data":
                        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
                        for (Season date :Season.values()){
                            buttons.add(
                                    Arrays.asList(InlineKeyboardButton.builder().text(date.name()).callbackData(date.toString()).build()));
                        }
                        execute(SendMessage.builder()
                                .text("выберете дату этой неделиЖ")
                                .chatId(message.getChatId().toString())
                                .replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
                                .build());
                        break;



                    case"/get_month":
                        String str = new String("");
                        List<List<InlineKeyboardButton>> button = new ArrayList<>();
                        Integer DaysAtMonth = new Integer(0);
                        for (date element : date.values()) {
                            if (Calendar.getInstance().get(Calendar.MONTH) == element.ordinal()){
                                DaysAtMonth = element.getI();
                                System.out.println(DaysAtMonth);
                            }

                        }
                        for (int i = Calendar.getInstance().get(Calendar.DAY_OF_MONTH); i <= DaysAtMonth+1; i++){
                            System.out.println(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                            if (i <= DaysAtMonth) {str = Integer.toString(i);}
                            else {str = "следущий месяц";}
                            button.add(
                                    Arrays.asList(InlineKeyboardButton.builder().text(str).callbackData(str).build()));
                        }
                        execute(SendMessage.builder()
                                .text("выберете число этого месяца")
                                .chatId(message.getChatId().toString())
                                .replyMarkup(InlineKeyboardMarkup.builder().keyboard(button).build())
                                .build());
                        break;
                    case "/f":
                        break;
                    case "/get_time":
                        execute(SendMessage.builder().chatId(message.getChatId().toString()).text("hendlemessege").build());
                        break;


                    case "/fast":
                        break;


                    case "/spisok":


                    case "/help":
                        execute(SendMessage.builder().chatId(message.getChatId().toString()).text("hendlemesseg").build());
                        break;
                }

            }
        }
        else {
            ConditionOfTheObject mc = ConditionOfTheObject.COMMAND;
            if (message.hasText() && (mc.getS() != "ТипКоманды")&&(mc.getS() != "Значение")) {
                String messages = message.getText();
                execute(SendMessage.builder()
                        .text("Задача записанна " + messages + " " + mc.getS().toString())
                        .chatId(message.getChatId().toString())
                        .build());
                String[] TimeAndDo = messages.split("-");
                Logic.Writing(message.getChatId().toString(),mc.getS(),TimeAndDo[1],TimeAndDo[2]);
                mc.setS("ТипКоманды");

                System.out.println(mc.getS());
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "@testbootexeption_bot";
    }

    @Override
    public String getBotToken() {
        return gettoken.getbottoken();
    }

    @SneakyThrows
    public static void main(String[] args) {
        what bot = new what();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(bot);
    }

}
