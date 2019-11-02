package filippov.bstu.fit.poibms.events.Events;

import android.util.Log;

import java.util.Date;

public class Event {
    private String name;
    private String info;
    private Date date;

    public Event(String name,String info,Date date){
        this.name = name;
        this.info = info;
        this.date = date;
        Log.d("Event", "Create object Event");
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
        Log.d("Event", "Set new value of name");
    }

    public String getInfo() {
        return info;
    }
    public void setInfo(String info) {
        this.info = info;
        Log.d("Event", "Set new value of info");
    }

    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
        Log.d("Event", "Set new value of date");
    }

    public String getStringDate(){
        return this.date.toString();
    }

    @Override
    public String toString(){
        return "Event: " + this.getName() +"\n" + this.getInfo()+"\n Date: "+this.getDate()+ this.getStringDate();
    }
}
