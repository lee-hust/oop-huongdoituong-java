package hust;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import hust.model.Period;
import hust.model.Person;
import hust.model.Place;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Crawler {
    public static final ObjectMapper mapper = new ObjectMapper();
    public static final ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
    public static final ObjectReader reader = mapper.reader();
    public static final String URI = "https://nguoikesu.com";
    public static final String TIMELINE_HREF = "/dong-lich-su";

    public static void main(String[] args) throws IOException {
        writer.writeValue(new File("src/main/resources/json/people.json"), new ArrayList<Person>());
        writer.writeValue(new File("src/main/resources/json/places.json"), new ArrayList<Place>());
        writer.writeValue(new File("src/main/resources/json/periods.json"), new ArrayList<Period>());
        // Tạo document từ url dòng lịch sử
        Document document = Jsoup.connect(URI + TIMELINE_HREF).get();
        Element mainContext = document.getElementById("Mod88");
        mainContext = mainContext.getElementsByClass("module-ct").first();

        // Crawler các thời kỳ lịch sử
        List<Period> periods = new ArrayList<>();
        Elements periodEs = mainContext.getElementsByTag("li");
        for (Element periodE:periodEs) {
            String href = periodE.getElementsByTag("a").get(0).attr("href");
            String name = periodE.getElementsByTag("a").get(0).text();
            Period period = new Period(name,href);
            period.setInfo();

            // Print
            System.out.println("=>" + period.getName());
            for (Person person:period.getPeople()) {
                System.out.println(person.getHref());
                System.out.println(person.getName());
                person.setInfo();
                System.out.println("\tSinh: " + person.getBirth() + "\n"
                        + "\tMất: " + person.getDeath() + "\n"
                        + "\tNiên hiệu: " + person.getAliases() + "\n"
                        + "\tTiền nhiệm: " + person.getPredecessor() + "\n"
                        + "\tKế nhiệm: " + person.getSuccessor() + "\n"
                        + "\tTrị vì: " + person.getReignTime() + "\n"
                        + "\tTên thật: " + person.getRealName() + "\n");
            }
            List<Person> listPerson = Arrays.asList(reader.readValue(new File("src/main/resources/json/people.json"), Person[].class));
            List<Person> people = new ArrayList<>(listPerson);
            people.addAll(period.getPeople());
            writer.writeValue(new File("src/main/resources/json/people.json"), people);
            for (Place place:period.getPlaces()) {
                System.out.println(place.getHref());
                System.out.println(place.getName());
                place.setInfo();
                System.out.println("\tQuốc gia: " + place.getNational() + "\n"
                        + "\tVị trí: " + place.getLocation() + "\n"
                        + "\tTọa độ: " + place.getCoordinates() + "\n"
                        + "\tDiện tích: " + place.getArea() + "\n");
            }
            List<Place> listPlace = Arrays.asList(reader.readValue(new File("src/main/resources/json/places.json"), Place[].class));
            List<Place> places = new ArrayList<>(listPlace);
            places.addAll(period.getPlaces());
            writer.writeValue(new File("src/main/resources/json/places.json"), places);
            System.out.println("");
            periods.add(period);
        }
        writer.writeValue(new File("src/main/resources/json/periods.json"), periods);
    }
}