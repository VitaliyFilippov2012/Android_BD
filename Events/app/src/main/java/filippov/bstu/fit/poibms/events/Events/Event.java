package filippov.bstu.fit.poibms.events.Events;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Event {
    private String name;
    private String info;
    private String  date;

    public Event(String name,String info,String date){
        this.name = name;
        this.info = info;
        this.date = date;
        Log.d("MyEvent", "Create object Event");
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
        Log.d("MyEvent", "Set new value of name");
    }

    public String getInfo() {
        return info;
    }
    public void setInfo(String info) {
        this.info = info;
        Log.d("MyEvent", "Set new value of info");
    }

    public String getDate() {
        return date;
    }
    public void setDate(String  date) {
        this.date = date;
        Log.d("MyEvent", "Set new value of date");
    }


    @Override
    public String toString(){
        return "MyEvent: " + this.getName() +"\n" + this.getInfo()+"\n Date: "+this.getDate();
    }

    public boolean compareEvent(Event e) {
        return this.getDate().equals(e.getDate());
    }
}
