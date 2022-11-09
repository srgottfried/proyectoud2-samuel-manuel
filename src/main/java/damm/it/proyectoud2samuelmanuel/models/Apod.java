package damm.it.proyectoud2samuelmanuel.models;

import java.io.InputStream;
import java.time.LocalDate;

public class Apod {
    private int id;
    private LocalDate date;
    private InputStream img;
    private String title;
    private String explanation;
    private String copyright;

    public Apod(LocalDate date, InputStream img, String title, String explanation, String copyright) {
        this.date = date;
        this.img = img;
        this.title = title;
        this.explanation = explanation;
        this.copyright = copyright;
    }

    public Apod(int id, LocalDate date, InputStream img, String title, String explanation, String copyright) {
        this.id = id;
        this.date = date;
        this.img = img;
        this.title = title;
        this.explanation = explanation;
        this.copyright = copyright;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public InputStream getImg() {
        return img;
    }

    public void setImg(InputStream img) {
        this.img = img;
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

    @Override
    public String toString() {
        return "Apod{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", url='" + img + '\'' +
                ", title='" + title + '\'' +
                ", explanation='" + explanation + '\'' +
                ", copyright='" + copyright + '\'' +
                '}';
    }
}
