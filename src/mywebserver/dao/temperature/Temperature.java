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

    /**
     * @return Temperature ID
     */
    public long getID() {
        return this.id;
    }

    /**
     * @param id Temperature ID
     */
    public void setID(long id) {
        this.id = id;
    }

    /**
     * @return Temperature value
     */
    public double getValue() {
        return value;
    }

    /**
     * @param value Temperature value
     */
    public void setValue(double value) {
        this.value = value;
    }

    /**
     * @return Temperature Date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date Temperature Date
     */
    public void setDate(Date date) {
        this.date = date;
    }
}
