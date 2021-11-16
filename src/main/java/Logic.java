import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;
import java.text.SimpleDateFormat;
import java.util.*;

public class Logic {


    public static String getTime(Date date) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        return timeFormat.format(date);
    }

    public static Date getData() {
        return new Date();
    }
}
