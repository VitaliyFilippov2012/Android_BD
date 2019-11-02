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
    }

    public static ArrayList<Event> getEvents(){
        return events;
    }

    public static ArrayList<Event> getEventByDate(Date date){
        ArrayList<Event> eventsWithDate = new ArrayList<>();
        for(Event e:events){
            if(e.getDate().equals(date)){
                eventsWithDate.add(e);
            }
        }
        return eventsWithDate;
    }

    public static boolean addEvent(Event event){
        events.add(event);
        Log.d("Event","Add new event");
        task.run();
        return true;
    }

    public static boolean removeEvent(Event event){
        if(events.contains(event)){
            events.remove(event);
            task.run();
            Log.d("Event","Remove event");
            return true;
        }
        return false;
    }

    public static boolean changeEvent(Event oldEvent,Event newEvent){
        if(events.contains(oldEvent)){
            events.remove(oldEvent);
            events.add(newEvent);
            task.run();
            Log.d("Event","Change event");
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
        wfS.createFile();
        WorkWithFileJSON<Event> workWithFileJSONS = new WorkWithFileJSON<Event>(wfS);
        for(Event e: events){
            workWithFileJSONS.saveAsJson(e);
        }
        Log.d("Event","synchronized");

    }

    private static void loadFromFile(){
        WorkWithFile wfS = new WorkWithFile(Constants.EVENTS);
        WorkWithFileJSON<Event> workWithFileJSONS = new WorkWithFileJSON<Event>(wfS);
        events = workWithFileJSONS.deserialize(new TypeToken<Event>(){}.getType());
        Log.d("Event","loadFromFile");
    }

    @Override
    public String toString(){
        return "\nCount events: " + events.size();
    }
}

