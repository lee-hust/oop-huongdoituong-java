package hust;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class crawlEvent {
    public static void main(String[] args) throws IOException {
        String url = "https://vi.wikipedia.org/wiki/Ni%C3%AAn_bi%E1%BB%83u_l%E1%BB%8Bch_s%E1%BB%AD_Vi%E1%BB%87t_Nam";
        try {
            Document doc = Jsoup.connect(url).get();
            Elements elements = doc.select(".mw-parser-output>*");
            int length = elements.size();
            for(int i = 0; i < length; i++){
                if(i > 4 && i < length - 2) {
                    Element e = elements.get(i);
                    Boolean pb = e.select("p>b").isEmpty();
                    Boolean dldd = e.select("dl>dd").isEmpty();
                    if ((pb || dldd)
                            && (e.select("h2").isEmpty()
                            && e.select("h3").isEmpty()
                            && e.select("h1").isEmpty())
                            && (e.select("table").isEmpty())) {
                        if (dldd) {
                            String year = e.getElementsByTag("b").text();
                            System.out.println("Year:" + year);
                        }
                        if (pb) {
                            Elements dds = e.select("dl>dd");
                            for (Element dd:dds) {
                                String event = dd.text();
                                String timeOfYear = dd.getElementsByTag("b").text();
                                String eventOfYear = event.substring(timeOfYear.length());
                                System.out.println("Time:" + timeOfYear );
                                System.out.println("Event:" + eventOfYear + "\n");
                            }
                        }
                        if(dldd) {
                            String event = e.getElementsByTag("a").text();
                            if(event != "")
                                System.out.println("Event:" + event);
                        }
                    }
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}