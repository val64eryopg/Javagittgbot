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


public class Bot extends TelegramLongPollingBot{

    private static Logic logic = new Logic();

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
        System.out.println("тут"+message.getText());

        logic.getCommand(callbackQuery.getData(), message.getText(),message.getChatId().toString());
        if ((message.getText().equals("Выберете число этого месяца:"))){
            execute(SendMessage.builder().chatId(message.getChatId().toString()).text("Введите время и задачу в формате:" +
                    "\n 99:99-ваша задача").build());
        }
        if ((message.getText().equals("Выберете число этого месяца и укажите повтор:"))){
            execute(SendMessage.builder().chatId(message.getChatId().toString()).text("Введите итересующие вас время и задачу в формате" +
                    "\n 99:99-ваша задача-перриуд").build());
            sendToUser(logic.switchMessage(message.getText(),message.getChatId().toString()),message.getChatId().toString());
        }

        if(message.getText().equals("Нажмите на то что хотите удалить:")){
            execute(SendMessage.builder().chatId(message.getChatId().toString()).text(
                "задача успешно удалена").build());}
    }



    @SneakyThrows
    public void handleMessage(Message message){
        //метод анализирует сообщение ли это или комана после чего отправляет ползователю соответсвующию команду

        if(message.hasText() && message.hasEntities()){
            Optional<MessageEntity> commandEntity =
                    message.getEntities().stream().filter(e -> "bot_command".equals(e.getType())).findFirst();
            if(commandEntity.isPresent()){
                String command =
                        message.getText().substring(commandEntity.get().getOffset(), commandEntity.get().getLength());
                sendToUser(logic.switchMessage(command,message.getChatId().toString()),message.getChatId().toString());
                System.out.println("случилось непредвиденное");
            }
        }

        else {


            String ID = message.getChatId().toString();
            System.out.println(logic.database.checkUserCondition(ID));


            if (logic.database.checkUserCondition(ID)) {

                String[] zz = logic.database.parseUserCondition(message.getChatId().toString()).get(0).split("%");
                System.out.println(zz[0]);
                System.out.println(logic.database.parseUserCondition(ID).get(0).split(" ")[1]);

                if(zz[0].split(" ").length == 7) {
                   //вот вить основа для тебя
                   if (zz[0].split(" ")[7].equals("повтор:")) {
                       String regex = "\\d{2}:\\d{2}-\\D+-\\d{1,2}";
                       Pattern pattern = Pattern.compile(regex);
                       Matcher matcher = pattern.matcher(message.getText());
                       if (matcher.matches()) {
                           System.out.println("все сделано правильно");
                           String[] data = message.getText().split("-");

                           System.out.println(data[0] + " " + data[1] + " " + data[2]);
                           //время задача и число повторений
                       }
                       else {
                           execute(SendMessage.builder()
                                   .text("упс не вышло")
                                   .chatId(message.getChatId().toString())
                                   .build());
                           logic.database.delUserCondition(message.getChatId().toString());
                       }

                   }

               }

               else {

                   if (zz[0].split(" ").length==4){

                   if (zz[0].split(" ")[4].equals("месяца:")) {

                       String regex = "\\d{1,2}:\\d{2}-.+";
                       Pattern pattern = Pattern.compile(regex);
                       Matcher matcher = pattern.matcher(message.getText());
                       if (matcher.matches()) {
                           try {
                               String[] z = logic.database.parseUserCondition(message.getChatId().toString()).get(0).split("%");
                               System.out.println(z[0] + " " + z[1]);
                               Date date = new Date();
                               Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
                               cal.setTime(date);
                               int year = cal.get(Calendar.YEAR);
                               int month = cal.get(Calendar.MONTH) + 1;
                               System.out.println(month);
                               String result = year + "-" + month + "-" + z[1];
                               System.out.println(result);
                               String messages = message.getText();
                               execute(SendMessage.builder()
                                       .text("Задача записана: " + messages)
                                       .chatId(message.getChatId().toString())
                                       .build());
                               String[] TimeAndDo = messages.split("-");
                               logic.database.delUserCondition(message.getChatId().toString());
                               logic.writing(message.getChatId().toString(), result, TimeAndDo[0], TimeAndDo[1]);
                           } catch (Throwable e) {
                               System.out.println("видать ошибка в том что массив пустой"
                               );
                           }
                       }
                       } else {
                           execute(SendMessage.builder()
                                   .text("усп что то не то ввели ведите заново или отмените \n/otmena")
                                   .chatId(message.getChatId().toString())
                                   .build());


                       }
                   }
               }

                if(logic.database.parseUserCondition(ID).get(0).split(" ")[1].equals("RegistrationOnCity%")){
                    System.out.println(1);
                    String s = logic.getTimeFromPage(message.getText());
                    if (s.equals("Город не найден")){
                        execute(SendMessage.builder()
                                .text("к сожалению ваш город не найден попробуйте указать другой город такого же часового пояса или напишите его правильно")
                                .chatId(message.getChatId().toString())
                                .build());
                        logic.database.delUserCondition(message.getChatId().toString());
                    }
                    else {
                        String regex = "\\S{2} \\d{1,2}:\\d{1,2}";
                        Pattern pattern = Pattern.compile(regex);
                        Matcher matcher = pattern.matcher(s);

                        if(matcher.matches()) {
                            if (logic.database.checkUserCity(ID)){
                                logic.database.delUserCity(message.getChatId().toString());

                                logic.database.addUserCity(message.getChatId().toString(), logic.gettingTimeZone(s));
                                execute(SendMessage.builder()
                                        .text("ваше текущее время: " + s)
                                        .chatId(ID)
                                        .build());
                                logic.database.delUserCondition(ID);
                            }else {
                                logic.database.addUserCity(ID, logic.gettingTimeZone(s));
                                execute(SendMessage.builder()
                                        .text("ваше текущее время: " + s)
                                        .chatId(message.getChatId().toString())
                                        .build());
                                logic.database.delUserCondition(message.getChatId().toString());
                            }
                        }
                        else {
                            execute(SendMessage.builder()
                                    .text("Ваш город пока не подключен" +
                                            "\n попробуйте указать город с вашим часовым поясом" )
                                    .chatId(message.getChatId().toString())
                                    .build());
                            logic.database.delUserCondition(message.getChatId().toString());
                        }
                    }
                    logic.database.delUserCondition(message.getChatId().toString());
                }



                if (zz[1].equals("выбираем город") && message.hasText()) {
                    execute(SendMessage.builder()
                            .text(logic.getTimeFromPage(message.getText()))
                            .chatId(message.getChatId().toString())
                            .build());
                    logic.database.delUserCondition(message.getChatId().toString());
                }

//                    String regex = "\\d{1,2}:\\d{2}-.+";
//                    Pattern pattern = Pattern.compile(regex);
//                    Matcher matcher = pattern.matcher(message.getText());
//                    if (matcher.matches()) {
//                        try {
//                            String[] z = logic.database.parseUserCondition(message.getChatId().toString()).get(0).split("%");
//                            System.out.println(z[0] + " " + z[1]);
//                            Date date = new Date();
//                            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
//                            cal.setTime(date);
//                            int year = cal.get(Calendar.YEAR);
//                            int month = cal.get(Calendar.MONTH)+1;
//                            System.out.println(month);
//                            String result = year + "-" + month + "-" + z[1];
//                            System.out.println(result);
//                            String messages = message.getText();
//                            execute(SendMessage.builder()
//                                    .text("Задача записана: " + messages)
//                                    .chatId(message.getChatId().toString())
//                                    .build());
//                            String[] TimeAndDo = messages.split("-");
//                            logic.database.delUserCondition(message.getChatId().toString());
//                            logic.writing(message.getChatId().toString(), result, TimeAndDo[0], TimeAndDo[1]);
//                        } catch (Throwable e) {
//                            System.out.println("видать ошибка в том что массив пустой"
//                            );
//                        }
//                    } else {
//                        execute(SendMessage.builder()
//                                .text("усп что то не то ввели ведите заново или отмените \n/otmena")
//                                .chatId(message.getChatId().toString())
//                                .build());
//
//
//                    }

                }

            else {
                execute(SendMessage.builder()
                        .text("данный бот не умеет говорить воспользуйтесь командой /HELP")
                        .chatId(message.getChatId().toString())
                        .build());
            }
        }
}



    @SneakyThrows
    public void sendToUser(ResultsCommand TextAndList, String chatId){
        //метод нужен для отправки сообщения пользователю в если у нас сообщение без кнопок у нас массив с первым элементом "не то"
        String str = "";
        for (int i = 0 ;i < TextAndList.getMyArray().length;i++){
            str+=TextAndList.getMyArray()[i];
        }

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
        return GetToken.getBotToken();
    }


    @SneakyThrows
    public static void main(String[] args) {
        Bot bot = new Bot();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(bot);
        logic.DateInizialition();
        MultiThread newThread = new MultiThread(bot,logic);
        newThread.start();
    }

}