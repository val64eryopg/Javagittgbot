import java.io.FileInputStream;
import java.nio.file.Paths;
import java.util.Properties;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class getToken {
    public static String getBotToken()  {
        Properties props = new Properties();
        try{
            FileInputStream fis = new FileInputStream("src/main/resources/properties.properties");
            props.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props.getProperty("token");
    }
}
