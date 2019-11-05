package mywebserver.dao.temperature;

import java.util.Date;

public class Temperature {

    private long id;
    private double value;
    private Date date;

    public Temperature(double value, Date date) {
        this.value = value;
        this.date = date;
    }

    public Temperature(long id, double value, Date date) {
        this.id = id;
        this.value = value;
        this.date = date;
    }

    public long getID() {
        return this.id;
    }

    public void setID(long id) {
        this.id = id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
