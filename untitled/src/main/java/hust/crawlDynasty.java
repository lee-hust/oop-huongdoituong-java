package hust;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;

public class crawlDynasty {
    public static final ObjectMapper mapper = new ObjectMapper();
    public static final ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
    public static final ObjectReader reader = mapper.reader();
    public static void main(String[] args) throws IOException {


        //Creating a JSONObject object

        String url = "https://vi.wikipedia.org/wiki/L%E1%BB%8Bch_s%E1%BB%AD_Vi%E1%BB%87t_Nam";
        try {

            Document doc = Jsoup.connect(url).get();
            Elements elements = doc.getElementsByClass("mw-headline");
            int len = elements.size();
            for (int i = 0; i < len; i++) {
                Element e = elements.get(i);
                System.out.println(e.text());
            }
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        JSONObject jsonObject = new JSONObject();

        try {
            Document doc = Jsoup.connect(url).get();
            Elements elements = doc.getElementsByClass("mw-headline");
            int len = elements.size();
            for (int i = 0; i < len; i++) {
                Element e = elements.get(i);
                jsonObject.put("Name", e.text());
                FileWriter file = new FileWriter("D:\\code\\java\\oop-huongdoituong-java\\untitled\\src\\main\\resources\\json\\dynasty.json");
                file.write(jsonObject.toJSONString());
            }
        }

        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
