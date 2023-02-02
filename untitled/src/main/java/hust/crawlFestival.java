package hust;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class crawlFestival {
    public static void main(String[] args) throws IOException {
        String url = "https://vi.wikipedia.org/wiki/L%E1%BB%85_h%E1%BB%99i_Vi%E1%BB%87t_Nam#Danh_s%C3%A1ch_m%E1%BB%99t_s%E1%BB%91_l%E1%BB%85_h%E1%BB%99i";
        try {
            Document doc = Jsoup.connect(url).get();
            Elements elements = doc.getElementsByTag("tbody").get(1).getElementsByTag("tr");
            elements.remove(0);

            for(Element element : elements){

                Elements festival = element.getElementsByTag("td");
                int length = festival.size();
                for(int i = 0; i < length; i++){
                    String str = festival.get(i).text();
                    if(str != "")
                        System.out.println(str);
                    else
                        System.out.println("null");
                }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}