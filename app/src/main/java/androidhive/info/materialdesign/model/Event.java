package androidhive.info.materialdesign.model;

import java.io.Serializable;

/**
 * Created by baratheraja on 22/7/15.
 */
public class Event  implements Serializable {
    String id;
    String title;
    String about;
    String venue;
    String date;
    String time=null;
    String created_at;
    String club=null;
    boolean checked =false;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }


    public Event() {

    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Event(String title, String about, String venue, String date) {
        this.title = title;
        this.about = about;
        this.venue = venue;
        this.date = date;
        time=null;
        club=null;

    }


    public Event(String title, String about, String venue, String date,  String club, String time) {

        this.title = title;
        this.about = about;
        this.venue = venue;
        this.date = date;
        this.club = club;
        this.time=time;
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

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
