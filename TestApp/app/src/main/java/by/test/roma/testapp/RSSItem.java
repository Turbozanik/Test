package by.test.roma.testapp;

/**
 * Created by Roma on 05.10.2015.
 */
import java.net.MalformedURLException;
import java.net.URL;

public class RSSItem{
    private String title;
    private String link;
    private String description;
    private String content;
    private String date;

    RSSItem(String title,String url,String description){
        this.title = title;
        this.link = url;
        this.description = description;
    }

    public void setContent(String content){
        this.content = content;
    }

    public String getContent(){
        return this.content;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle(){
        return this.title;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getDescription(){
        return this.description;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLink(){
        return this.link;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    // sort by date
    public int compareTo(RSSItem another) {
        if (another == null) return 1;
        return another.date.compareTo(date);
    }

}