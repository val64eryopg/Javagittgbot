import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class test {
    public static void main(String[] args) {
        String a = GetPage("Абиджан");
        System.out.println(a);
    }

    @SneakyThrows
    public static String GetPage(String city) {
//        String content = null;
//        URLConnection connection = null;
//        try {
//            connection =  new URL("https://dateandtime.info/ru/index.php#").openConnection();
//            Scanner scanner = new Scanner(connection.getInputStream());
//            scanner.useDelimiter("\\Z");
//            content = scanner.next();
//            scanner.close();
//        }catch ( Exception ex ) {
//            ex.printStackTrace();
//        }
//        URL oracle = new URL("https://dateandtime.info/ru/index.php#" );
//        BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));
        try {
            Document doc = Jsoup.connect("https://dateandtime.info/ru/index.php#")
                    .get();
            Elements listNews = doc.select("a[href^=\"city\"],time[datetime]");

            Map<String, String> map = new HashMap<String, String>();
            for (int i = 0; i <= listNews.size() - 1; i++) {
                if (listNews.get(i).text().equals(city)) {
                    return listNews.get(i + 1).text();
                }
//                System.out.println(l.text());
            }

        } catch (IOException e) {
            return ""
            System.out.println("не подлючились к странице");
        }
        return "нет такого города";
    }
}
