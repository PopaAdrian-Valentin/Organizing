package Models;

public class CalendarModel {

    private int ID;
    private String Date;
    private String Event;
    private String Description;

    public CalendarModel(String date, String event,String description) {
        this.ID = ID;
        Date = date;
        Event = event;
        this.Description=description;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getEvent() {
        return Event;
    }

    public void setEvent(String event) {
        Event = event;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    @Override
    public String toString() {
        return "Event name : " + Event + '\n'+
                "Description: " + Description + '\n';
    }
}
