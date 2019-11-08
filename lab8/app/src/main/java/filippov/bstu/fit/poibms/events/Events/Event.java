package filippov.bstu.fit.poibms.events.Events;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

import filippov.bstu.fit.poibms.events.Events.Category.Category;

public class Event {
    private String name;
    private String info;
    private String  date;
    private String category;

    public Event(String name,String info,String date,String category){
        this.name = name;
        this.info = info;
        this.date = date;
        this.category = category;
        Log.d("MyEvent", "Create object Event");
    }

    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
        Log.d("MyEvent", "Set new value of category");
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
        return "MyEvent: " + this.getName() +"\n"+ this.getCategory()+ ": " + this.getInfo()+"\n Date: "+this.getDate();
    }

    public boolean compareEvent(Event e) {
        return this.getDate().equals(e.getDate()) && this.getName().equals(e.getName()) && this.getCategory().equals(e.category);
    }
}
