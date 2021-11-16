import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class LogicTest {

    @org.junit.jupiter.api.Test
    void getTime() {
        Date date = new Date(68400000L);
        String[] a = Logic.getTime(date).split(":");
        assertTrue(Integer.parseInt(a[0]) < Math.abs(24));
        assertTrue(Integer.parseInt(a[1]) < Math.abs(60));
        String[] b = Logic.getTime(Logic.getData()).split(":");
        assertTrue(Integer.parseInt(b[0]) < Math.abs(24));
        assertTrue(Integer.parseInt(b[1]) < Math.abs(60));
        assertNotNull(Logic.getTime(Logic.getData()));
    }

    @org.junit.jupiter.api.Test
    void getData() {
        assertNotNull(Logic.getData());
    }

    @Test
    void getToken() {
        assertNotNull(gettoken.getbottoken());
    }
}