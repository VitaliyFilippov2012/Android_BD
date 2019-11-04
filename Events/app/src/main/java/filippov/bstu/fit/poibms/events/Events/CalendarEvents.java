package filippov.bstu.fit.poibms.events.Events;

import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import com.google.gson.reflect.TypeToken;

import filippov.bstu.fit.poibms.events.Constants.Constants;
import filippov.bstu.fit.poibms.events.File.WorkWithFile;
import filippov.bstu.fit.poibms.events.File.WorkWithFileJSON;

public class CalendarEvents {
    private static ArrayList<Event> events;
    static {
        events = new ArrayList<Event>();
        loadFromFile();
        Log.d("MyEvent","Static block");

    }

    public static ArrayList<Event> getEvents(){
        return events;
    }

    public static Event getEventByDate(String dateWithFormatYYYYMMDD){
        for(Event e:events){
            if(e.getDate().equals(dateWithFormatYYYYMMDD)){
                return e;
            }
        }
        return null;
    }

    public static boolean addEvent(Event event){
        Event oldEvent = checkEventWithThisDate(event);
        if(oldEvent!=null){
            return changeEvent(oldEvent,event);
        }
        events.add(event);
        Log.d("MyEvent","Add new event");
        task.run();
        return true;
    }

    private static Event checkEventWithThisDate(Event e){
        for(Event e1:events){
            if(e.compareEvent(e1)){
                return e1;
            }
        }
        return null;
    }

    public static boolean removeEvent(Event event){
        Event existsEvent = checkEventWithThisDate(event);
        if(existsEvent!=null){
            events.remove(existsEvent);
            task.run();
            Log.d("MyEvent","Remove event");
            return true;
        }
        return false;
    }

    public static boolean changeEvent(Event oldEvent,Event newEvent){
        if(events.contains(oldEvent)){
            events.remove(oldEvent);
            events.add(newEvent);
            task.run();
            Log.d("MyEvent","Change event");
            return true;
        }
        return false;
    }

    private static Runnable task = new Runnable() {
        @Override
        public void run() {
            synchronizedEventsWithFile();
        }
    };


    private static void synchronizedEventsWithFile(){
        WorkWithFile wfS = new WorkWithFile(Constants.EVENTS);
        wfS.createFile(true);
        WorkWithFileJSON<Event> workWithFileJSONS = new WorkWithFileJSON<Event>(wfS);
        for(Event e: events){
            workWithFileJSONS.saveAsJson(e);
        }
        Log.d("MyEvent","synchronized");

    }

    private static void loadFromFile(){
        WorkWithFile wfS = new WorkWithFile(Constants.EVENTS);
        WorkWithFileJSON<Event> workWithFileJSONS = new WorkWithFileJSON<Event>(wfS);
        events = workWithFileJSONS.deserialize(new TypeToken<Event>(){}.getType());
        Log.d("MyEvent","loadFromFile");
    }

    @Override
    public String toString(){
        return "\nCount events: " + events.size();
    }
}

