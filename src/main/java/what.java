import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;
import lombok.SneakyThrows;
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
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
        if (!(message.getText().equals("Вот список ваших задач:"))&&!(message.getText().equals("Нажмите на то что хотите удалить:"))) {
            execute(SendMessage.builder().chatId(message.getChatId().toString()).text("Введите итересующие вас время и задачу в формате\n 66:66-раскрасить открытку").build());
        }
        if(message.getText().equals("Нажмите на то что хотите удалить:")){execute(SendMessage.builder().chatId(message.getChatId().toString()).text(
                "задача успешно удалена"
        ).build());}
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


            }
        }
        else {
            if (Logic.database.checkUserCodtion(message.getChatId().toString())) {
                String[] zz = Logic.database.checkUserCodtionf(message.getChatId().toString()).get(1).split("%");
                if (zz[1].equals("выбираем город") && message.hasText()) {
                    execute(SendMessage.builder()
                            .text(test.GetPage(message.getText()))
                            .chatId(message.getChatId().toString())
                            .build());
                    Logic.database.delUserCondtion(message.getChatId().toString());
                } else {
                    String regex = "\\d{1,2}:\\d{2}-.+";
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(message.getText());
                    if (matcher.matches()) {
                        System.out.println("суда нахуй попал");
                        try {
                            System.out.println(Logic.database.checkUserCodtionf(message.getChatId().toString()).get(1));
                            String[] z = Logic.database.checkUserCodtionf(message.getChatId().toString()).get(1).split("%");
                            System.out.println(z[0] + " " + z[1]);
                            Date date = new Date();
                            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
                            cal.setTime(date);
                            int year = cal.get(Calendar.YEAR);
                            int month = cal.get(Calendar.MONTH);
                            String resolt = year + "-" + month + "-" + z[1];
                            System.out.println(resolt);
                            String messages = message.getText();
                            execute(SendMessage.builder()
                                    .text("Задача записана: " + messages)
                                    .chatId(message.getChatId().toString())
                                    .build());
                            String[] TimeAndDo = messages.split("-");
                            Logic.database.delUserCondtion(message.getChatId().toString());
                            Logic.Writing(message.getChatId().toString(), resolt, TimeAndDo[0], TimeAndDo[1]);
                        } catch (Throwable e) {
                            System.out.println("видать ошибка в том что массив пустой"
                            );
                        }
                    } else {
                        execute(SendMessage.builder()
                                .text("усп что то не то ввели ведите заново или отмените \n/otmena")
                                .chatId(message.getChatId().toString())
                                .build());


                    }

                }

            }else
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
        return getToken.getBotToken();
    }


    @SneakyThrows
    public static void main(String[] args) {
        what bot = new what();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(bot);
        Multithreading newThread = new Multithreading(bot);
        newThread.start();
    }

}
