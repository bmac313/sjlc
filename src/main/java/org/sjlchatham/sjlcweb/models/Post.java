package org.sjlchatham.sjlcweb.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Post {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min = 1, max = 100, message = "Post title must be between 1 and 100 characters.")
    private String title;

    @NotNull
    @Size(min = 1, max = 2000000000, message = "Please enter a post body of a valid length.")
    private String body;

    @NotNull
    @Size(min = 1, max = 100, message = "Author name must be between 1 and 100 characters.")
    private String author;

    @NotNull
    @Size(min = 1, max = 255, message = "Please provide a link to your image (255 characters or less).")
    @Pattern(regexp = "(http)?s?:?(\\/\\/[^\"']*\\.(?:png|jpg|jpeg|gif|png|svg))", message = "Please enter a valid image URL. Your URL must start with 'http://' or 'https://' end with .jpg, .png, .svg, or .gif.")
    private String imgUrl;

    private final String timeStamp;


    public Post() {
        this.timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
    }

    public Post(String title, String author, String body, String imgUrl) {
        this();
        this.title = title;
        this.author = author;
        this.body = body;
        this.imgUrl = imgUrl;
    }

    public int getId() {
        return id;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
