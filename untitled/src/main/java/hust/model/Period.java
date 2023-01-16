package hust.model;

import hust.Crawler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Period extends Model{
    private final List<Person> people = new ArrayList<>();
    private final List<Place> places = new ArrayList<>();
    private final List<Event> events = new ArrayList<>();
    private final List<Festival> festivals = new ArrayList<>();

    public Period() {
    }
    public Period(String name, String href) {
        this.setName(name);
        this.setHref(href);
    }

    public List<Person> getPeople() {
        return people;
    }

    public void addPeople(List<Person> people) {
        this.people.addAll(people);
    }

    public void addPeople(Person person) {
        this.people.add(person);
    }

    public List<Place> getPlaces() {
        return places;
    }

    public void addPlaces(List<Place> places) {
        this.places.addAll(places);
    }

    public void addPlaces(Place places) {
        this.places.add(places);
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events.addAll(events);
    }

    public List<Festival> getFestivals() {
        return festivals;
    }

    public void setFestivals(List<Festival> festivals) {
        this.festivals.addAll(festivals);
    }

    /**
     * Tìm và lưu danh sách nhân vật lịch sử có liên quan đến thời kỳ lịch sử
     * @throws IOException
     */
    @Override
    public void setInfo() throws IOException {
        Document document = Jsoup.connect(Crawler.URI + this.getHref()).timeout(0).get();
        Elements periodElements = document.getElementsByAttributeValue("class", "readmore");
        if (!periodElements.isEmpty()) {
            for (Element periodE : periodElements) {
                try {
                    Element subPeriod = periodE.getElementsByAttributeValue("class", "btn btn-secondary").get(0);
                    Document subDoc = Jsoup.connect(Crawler.URI + subPeriod.attr("href")).timeout(0).get();
                    Element content = subDoc.getElementsByClass("com-content-article__body").get(0);
                    Elements listHref = content.getElementsByTag("a");
                    for (Element hrefNode : listHref) {

                        // Nếu là link thông tin nhân vật
                        if (hrefNode.attr("href").contains("/nhan-vat/")) {
                            Person person = new Person(hrefNode.text(), hrefNode.attr("href"), this.getHref());
                            boolean isExisted = false;
                            for (Person p : this.getPeople()) {
                                if(p.getHref().equals(person.getHref())) {
                                    isExisted = true;
                                    break;
                                }
                            }
                            if (!isExisted) this.addPeople(person);
                        }

                        // Nếu là link thông tin địa danh
                        if (hrefNode.attr("href").contains("/dia-danh/")) {
                            Place place = new Place(hrefNode.text(), hrefNode.attr("href"));
                            boolean isExisted = false;
                            for (Place p : this.getPlaces()) {
                                if(p.getHref().equals(place.getHref())) {
                                    isExisted = true;
                                    break;
                                }
                            }
                            if (!isExisted) this.addPlaces(place);
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Không tìm thấy thông tin nhân vật, địa danh nào. " + e);
                }
//                // Lấy danh sách nhân vật liên quan đến thời kỳ lịch sử
//                try {
//                    Element listRefCharacters = subDoc.getElementById("list-ref-characters");
//                    Elements characters = listRefCharacters.getElementsByTag("a");
//                    for (Element character : characters) {
//                        // Bỏ qua trường hợp là các triều đại
//                        if (character.attr("href").contains("/nha-")) continue;
//                        boolean isExisted = false;
//                        // Tạo đối tượng nhân vật dựa trên tên và đường dẫn đến thông tin nhân vật
//                        Celebrity person = new Person(character.text(), character.attr("href"));
//                        // Nếu nhân vật đã được tạo thì bỏ qua
//                        for (Person p : this.getPeople()) {
//                            if (p.getHref().equals(person.getHref())) {
//                                isExisted = true;
//                                break;
//                            }
//                        }
//                        if (!isExisted) this.addPeople(person);
//                    }
//                } catch (Exception e) {
//                    System.out.println("Không tìm thấy thông tin nhân vật nào. " + e);
////                    throw new RuntimeException(e);
//                }
//
//                // Lấy danh sách địa danh liên quan đến thời kỳ lịch sử
//                try {
//                    Element listRefPlaces = subDoc.getElementById("list-ref-places");
//                    Elements places = listRefPlaces.getElementsByTag("a");
//                    for (Element place : places) {
//                        boolean isExisted = false;
//                        // Tạo đối tượng địa danh dựa trên tên và đường dẫn đến thông tin địa danh
//                        Place place1 = new Place(place.text(), place.attr("href"));
//                        // Nếu địa danh đã được tạo thì bỏ qua
//                        for (Place place2 : this.getPlaces()) {
//                            if (place2.getHref().equals(place1.getHref())) {
//                                isExisted = true;
//                                break;
//                            }
//                        }
//                        if (!isExisted) this.addPlaces(place1);
//                    }
//                } catch (Exception e) {
//                    System.out.println("Không tìm thấy thông tin địa danh nào. " + e);
////                    throw new RuntimeException(e);
//                }
            }
        }
    }

    public String toJson() {

        return null;
    }
}
