public class Transport {
    public static String getTransport(String str){
        String string ="Введите итересующие вас время и задачу в формате\n 66:66-раскрасить открытку";
        if (str.matches("\\d{2}\\D\\d{2}\\D\\D+" )){

        return "вы записали свою задачу на "+str;
        }
        switch (str){
            case "/get_time":
                return logic.gettime();
            case"Monday":
                return string;
            case"Tuesday":
                return string+"";
            case"Friday":
                return "да ладно это тоже не очень день для дел "+string;
            case"Saturday":
                return string+"суббота";
            case"/help":
                return "В нашем боте есть комманды:\n" +
                        "/get_time-возвращает точное время\n" +
                        " и\n" +
                        " /get_data-используетьсяс для записи ваших планов";
            default:
                return "пока наш бот не умеет говорить \n"+
                        "для озкаомления с возможностью бота введите /help";




        }
    }

}
