package personalprojects.seakyluo.everyday;

import java.util.ArrayList;

public class Diary {

    private Long date;
    private String weather;
    private String content;
    private ArrayList<String> images;

    public Diary(Long date, String weather, String content){
        this.date = date;
        this.weather = weather;
        this.content = content;
    }

    public Long getDate() { return date; }
    public String getWeather() { return weather; }
    public void setWeather(String weather) { this.weather = weather; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public ArrayList<String> getImages() { return images; }
    public void setImages(ArrayList<String> images) { this.images = images; }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Diary)) return false;
        return date.equals(((Diary) obj).date);
    }
}
