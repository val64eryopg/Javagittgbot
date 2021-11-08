import org.checkerframework.checker.nullness.qual.AssertNonNullIfNonNull;
import org.junit.jupiter.api.Test;

import javax.validation.constraints.AssertTrue;

import static org.junit.jupiter.api.Assertions.*;

class whatTest {

    @Test
    void getCommandTest() {
        String string = "Введите итересующие вас время и задачу в формате\n 66:66-раскрасить открытку";
        String command = what.getCommand(" ");
        //
        assertEquals(command, "пока наш бот не умеет говорить \n" +
                "для озкаомления с возможностью бота введите /help");
        assertNotNull(command);
        //
        String command1 = what.getCommand("Monday");
        assertEquals(command1, string);
        assertNotNull(command1);
        //
        String command2 = what.getCommand("Tuesday");
        assertEquals(command2, string + "");
        assertNotNull(command2);
        //
        String command3 = what.getCommand("Friday");
        assertEquals(command3, "да ладно это тоже не очень день для дел " + string);
        assertNotNull(command3);
        //
        String command4 = what.getCommand("Saturday");
        assertEquals(command4, string + "суббота");
        assertNotNull(command4);

    }

}