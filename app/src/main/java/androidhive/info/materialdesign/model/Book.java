package androidhive.info.materialdesign.model;

import java.io.Serializable;

/**
 * Created by baratheraja on 22/7/15.
 */
public class Book implements Serializable {
    String id;
    String title;
    String about;
    String author;
    String blocked;
    String stock;
    String given;
    boolean checked =false;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }



    public Book() {

    }

    public Book( String title, String about, String author, String stock, String given) {

        this.title = title;
        this.about = about;
        this.author = author;
        this.stock = stock;
        this.given = given;
    }

    public Book(String id, String title, String about, String author, String blocked, String stock, String given) {
        this.id = id;
        this.title = title;
        this.about = about;
        this.author = author;
        this.blocked = blocked;
        this.stock = stock;
        this.given = given;

    }
    public Book(String id, String title, String about, String author, String blocked, String stock, String given, boolean checked) {
        this.id = id;
        this.title = title;
        this.about = about;
        this.author = author;
        this.blocked = blocked;
        this.stock = stock;
        this.given = given;
        this.checked = checked;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBlocked() {
        return blocked;
    }

    public void setBlocked(String blocked) {
        this.blocked = blocked;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getGiven() {
        return given;
    }

    public void setGiven(String given) {
        this.given = given;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
