package damm.it.proyectoud2samuelmanuel.models;

import java.io.Serial;
import java.io.Serializable;

public class Apod implements Serializable {
    @Serial
    private static final long serialVersionUID = 1;

    private String date;
    private String url;
    private String title;
    private String explanation;
    private String copyright;

    public Apod(String date, String url, String title, String explanation, String copyright) {
        this.date = date;
        this.url = url;
        this.title = title;
        this.explanation = explanation;
        this.copyright = copyright;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }
}
