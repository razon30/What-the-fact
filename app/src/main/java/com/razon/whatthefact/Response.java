package com.razon.whatthefact;

/**
 * Created by HP on 24-Oct-17.
 */

public class Response {

    String text;
    boolean found;
    String number;
    String type;
    String date;
    String year;

    public Response(String text, boolean found, String number, String type, String date, String year) {
        this.text = text;
        this.found = found;
        this.number = number;
        this.type = type;
        this.date = date;
        this.year = year;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isFound() {
        return found;
    }

    public void setFound(boolean found) {
        this.found = found;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
