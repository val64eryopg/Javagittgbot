import java.util.*;
import javafx.scene.control.ListCellBuilder;
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
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

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
        Message message = callbackQuery.getMessage();
        execute(SendMessage.builder().chatId(message.getChatId().toString()).text(transport.getTransport(callbackQuery.getData())).build());
    }


    @SneakyThrows
    private void handleMessage(Message message){
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
                        for (season season :season.values() ){
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
                        execute(SendMessage.builder().chatId(message.getChatId().toString()).text(transport.getTransport("/get_time")).build());
                        break;
                    case "/help":
                        execute(SendMessage.builder().chatId(message.getChatId().toString()).text(transport.getTransport("/help")).build());
                        break;
                }

            }
        }
      else
          if (message.hasText()){
            String messages = message.getText().toString();
            execute(SendMessage.builder()
                    .text(transport.getTransport(messages))
                    .chatId(message.getChatId().toString())
                    .build());
        }
    }

    @Override
    public String getBotUsername() {
        return "@testbootexeption_bot";
    }

    @Override
    public String getBotToken() {
        return "2031914374:AAEA0KWp22dLFjHibAyjjDna3UMlJylQ2oA";
    }

    @SneakyThrows
    public static void main(String[] args) {
        what bot = new what();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(bot);
    }

}
enum season{
    Monday,Tuesday,Wednesday,Thursday, Friday,Saturday,Sunday
}
