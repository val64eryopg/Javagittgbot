import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TelegramTransportTest {

    @Test
    void getCommandTest() {
        String string = "Введите итересующие вас время и задачу в формате\n 66:66-раскрасить открытку";
        String command = TelegramTransport.getCommand(" ");
        //
        assertEquals(command, "пока наш бот не умеет говорить \n" +
                "для озкаомления с возможностью бота введите /help");
        assertNotNull(command);
        //
        String command1 = TelegramTransport.getCommand("Monday");
        assertEquals(command1, string);
        assertNotNull(command1);
        //
        String command2 = TelegramTransport.getCommand("Tuesday");
        assertEquals(command2, string + "");
        assertNotNull(command2);
        //
        String command3 = TelegramTransport.getCommand("Friday");
        assertEquals(command3, "да ладно это тоже не очень день для дел " + string);
        assertNotNull(command3);
        //
        String command4 = TelegramTransport.getCommand("Saturday");
        assertEquals(command4, string + "суббота");
        assertNotNull(command4);

    }

}