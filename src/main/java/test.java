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

    public static String GetPage(String city) {
//        добываем время по запросу если нет города честно это говорим
        try {
            Document doc = Jsoup.connect("https://dateandtime.info/ru/index.php#")
                    .get();
            Elements timeCity = doc.select("a[href^=\"city\"],time[datetime]");
            for (int i = 0; i <= timeCity.size() - 1; i++) {
                if (timeCity.get(i).text().equals(city)) {
                    return timeCity.get(i + 1).text();
                }
            }
        } catch (IOException e) {
            return "не подлючились к странице";
        }
        return "нет такого города";
    }
}
