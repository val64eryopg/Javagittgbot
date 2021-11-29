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
        } else
            if (update.hasMessage()) {
            handleMessage(update.getMessage());
        }
    }

    @SneakyThrows
    private void handleCallback(CallbackQuery callbackQuery) {
        Message message = callbackQuery.getMessage();
        System.out.println(message.getText());
        Logic.getCommand(callbackQuery.getData(), message.getText(),message.getChatId().toString());
        execute(SendMessage.builder().chatId(message.getChatId().toString()).text("\"Введите итересующие вас время и задачу в формате\\n 66:66-раскрасить открытку\"").build());
    }



    @SneakyThrows
    public void handleMessage(Message message){
        //метод анализирует сообщение ли это или комана после чего отправляет ползователю соответсвующию команду

        if(message.hasText() && message.hasEntities()){
            Optional<MessageEntity> commandEntity =
                    message.getEntities().stream().filter(e -> "bot_command".equals(e.getType())).findFirst();
            if(commandEntity.isPresent()){
                String command =
                        message
                                .getText()
                                .substring(commandEntity.get().getOffset(), commandEntity.get().getLength());
                SendToUser(Logic.SwitchMessage(command,message.getChatId().toString()),message.getChatId().toString());
                System.out.println("случилось непредвиденное");

                // прошлые нароботки не удалять не коментировать
//                switch (command){
//                        case"/get_data":
//                        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
//                        for (Season date :Season.values()){
//                            buttons.add(
//                                    Arrays.asList(InlineKeyboardButton.builder().text(date.name()).callbackData(date.toString()).build()));
//                        }
//                        execute(SendMessage.builder()
//                                .text("выберете дату этой неделиЖ")
//                                .chatId(message.getChatId().toString())
//                                .replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build())
//                                .build());
//                        break;
//
//
//
//                    case"/get_month":
//                        String str = new String("");
//                        List<List<InlineKeyboardButton>> button = new ArrayList<>();
//                        Integer DaysAtMonth = new Integer(0);
//                        for (date element : date.values()) {
//                            if (Calendar.getInstance().get(Calendar.MONTH) == element.ordinal()){
//                                DaysAtMonth = element.getI();
//                                System.out.println(DaysAtMonth);
//                            }
//
//                        }
//                        for (int i = Calendar.getInstance().get(Calendar.DAY_OF_MONTH); i <= DaysAtMonth+1; i++){
//                            System.out.println(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
//                            if (i <= DaysAtMonth) {str = Integer.toString(i);}
//                            else {str = "следущий месяц";}
//                            button.add(
//                                    Arrays.asList(InlineKeyboardButton.builder().text(str).callbackData(str).build()));
//                        }
//                        execute(SendMessage.builder()
//                                .text("выберете число этого месяца")
//                                .chatId(message.getChatId().toString())
//                                .replyMarkup(InlineKeyboardMarkup.builder().keyboard(button).build())
//                                .build());
//                        break;
//                    case "/f":
//                        break;
//                    case "/get_time":
//                        execute(SendMessage.builder().chatId(message.getChatId().toString()).text("hendlemessege").build());
//                        break;
//
//
//                    case "/fast":
//                        break;
//
//
//                    case "/spisok":
//
//
//                    case "/help":
//                        execute(SendMessage.builder().chatId(message.getChatId().toString()).text("hendlemesseg").build());
//                        break;
//                }
//
            }
        }
        else {
            ConditionOfTheObject mc = ConditionOfTheObject.COMMAND;
            if (message.hasText() && (mc.getS() != "ТипКоманды")&&(mc.getS() != "Значение")) {
                Date date = new Date();

                Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
                cal.setTime(date);
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                String resolt = year +"-" +month +"-"+mc.getS();
                String messages = message.getText();
                execute(SendMessage.builder()
                        .text("Задача записана: " + messages + " " + mc.getS().toString())
                        .chatId(message.getChatId().toString())
                        .build());
                String[] TimeAndDo = messages.split("-");
//                Logic.Writing(message.getChatId().toString(),resolt,TimeAndDo[1],TimeAndDo[2]);
                mc.setS("ТипКоманды");
                mc.setI("Значение");
            } else
                execute(SendMessage.builder()
                        .text("данный бот не умеет говорить воспользуйтесь командой /HELP")
                        .chatId(message.getChatId().toString())
                        .build());
        }
    }



    @SneakyThrows
    public void SendToUser(ResultsCommand TextAndList, String chatId){
        //метод нужен для отправки сообщения пользователю в если у нас сообщение без кнопок у нас массив с первым элементом "не то"
        String str = "";
        for (int i = 0 ;i < TextAndList.getMyArray().length;i++){
            str+=TextAndList.getMyArray()[i];
        }
        System.out.println("теперь я в методе SendToUser вот его ввод:"+str);
        if (!(str.equals("не то"))){
            System.out.println("ахуеть попал");
            execute(SendMessage.builder()
                    .text(TextAndList.getFirst())
                    .chatId(chatId)
                    .replyMarkup(InlineKeyboardMarkup.builder().keyboard(GetLIst(TextAndList.getMyArray())).build())
                    .build());

        }
        else{
            System.out.println("else");
            execute(SendMessage.builder()
                    .text(TextAndList.getFirst())
                    .chatId(chatId)
                    .build());
        }

    }



    public List<List<InlineKeyboardButton>> GetLIst(String[] TextOrButtons){
        //получение определенного массива кнопок который засивисит от класса библеотеки телеграм бот
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        for (int i = 0;i<TextOrButtons.length;i++){
            buttons.add(
                    Arrays.asList(InlineKeyboardButton.builder().text(TextOrButtons[i]).callbackData(TextOrButtons[i]).build()));

        }
        return buttons;
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
