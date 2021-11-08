import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class logicTest {

    @org.junit.jupiter.api.Test
    void getTime() {
        Date date = new Date(68400000L);
        String[] a = logic.getTime(date).split(":");
        assertTrue(Integer.parseInt(a[0]) < Math.abs(24));
        assertTrue(Integer.parseInt(a[1]) < Math.abs(60));
        String[] b = logic.getTime(logic.getData()).split(":");
        assertTrue(Integer.parseInt(b[0]) < Math.abs(24));
        assertTrue(Integer.parseInt(b[1]) < Math.abs(60));
        assertNotNull(logic.getTime(logic.getData()));
    }

    @org.junit.jupiter.api.Test
    void getData() {
        assertNotNull(logic.getData());
    }

    @Test
    void getToken() {
        assertNotNull(gettoken.getbottoken());
    }
}