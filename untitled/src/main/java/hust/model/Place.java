package hust.model;

import hust.Crawler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;

public class Place extends Model{
    private String national;
    private String location;
    private String coordinates;
    private String area;

    public Place(String name, String href) {
        this.setName(name);
        this.setHref(href);
    }

    public String getNational() {
        return national;
    }

    public void setNational(String national) {
        this.national = national;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    @Override
    public void setInfo() throws IOException {
        Document document = Jsoup.connect(Crawler.URI + this.getHref()).timeout(0).get();
        HashMap<String, String> infoKV = new HashMap<>();
        try {
            Element infoElement = document.getElementsByClass("infobox").get(1);
            Elements trElements = infoElement.getElementsByTag("tr");
            for (Element tr : trElements) {
                infoKV.put(tr.getElementsByTag("th").text().trim(),
                        tr.getElementsByTag("td").text().trim());
            }
        } catch (Exception e) {
            System.out.println("Không có thông tin địa danh "+ this.getName() + ". " + e);
        }
        this.setNational(infoKV.get("Quốc gia"));
//        if (!infoKV.get("Vị trí").isEmpty()) this.setLocation(infoKV.get("Vị trí"));
//        if (!infoKV.get("Địa điểm").isEmpty()) this.setLocation(infoKV.get("Địa điểm"));
//        if (!infoKV.get("Khu vực").isEmpty()) this.setLocation(infoKV.get("Khu vực"));
//        if (!infoKV.get("Địa chỉ").isEmpty()) this.setLocation(infoKV.get("Địa chỉ"));
        this.setCoordinates(infoKV.get("Tọa độ"));
        this.setArea(infoKV.get("Diện tích"));
    }
}