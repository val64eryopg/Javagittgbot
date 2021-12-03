import java.io.FileInputStream;
import java.nio.file.Paths;
import java.util.Properties;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;


public class getdatabase {

    static String getUserName() {
        Properties props = new Properties();
        try{
            FileInputStream fis = new FileInputStream("src/main/resources/properties.properties");
            props.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props.getProperty("username");
    }

    static String getPassword() {
        Properties props = new Properties();
        try{
            FileInputStream fis = new FileInputStream("src/main/resources/properties.properties");
            props.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props.getProperty("password");
    }

    static String getUrl(){
        return "jdbc:mysql://sql11.freemysqlhosting.net:3306/" + getUserName() + "?user=" + getUserName() + "&password=" + getPassword() + "&useUnicode=true&characterEncoding=UTF-8";
    }
}