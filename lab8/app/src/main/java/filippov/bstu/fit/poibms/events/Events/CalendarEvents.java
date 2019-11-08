package filippov.bstu.fit.poibms.events.Events;

import android.util.Log;

import java.util.ArrayList;

import filippov.bstu.fit.poibms.events.Constants.Constants;
import filippov.bstu.fit.poibms.events.Events.Category.Category;
import filippov.bstu.fit.poibms.events.File.WorkWithFile;
import filippov.bstu.fit.poibms.events.File.WorkWithXML;

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

    public static ArrayList<Event> getEventByDate(String dateWithFormatYYYYMMDD){
        ArrayList<Event> dateEvents = new ArrayList<>();
        for(Event e:events){
            if(e.getDate().equals(dateWithFormatYYYYMMDD)){
                dateEvents.add(e);
            }
        }
        return dateEvents;
    }

    public static ArrayList<Event> getEventByCategory(String category){
        ArrayList<Event> categoryEvents = new ArrayList<>();
        for(Event e:events){
            if(e.getCategory().equals(category)){
                categoryEvents.add(e);
            }
        }
        return categoryEvents;
    }

    public static boolean addEvent(Event event){
        Event oldEvent = checkSameEventWithThisDate(event);
        if(oldEvent!=null){
            return changeEvent(oldEvent,event);
        }
        events.add(event);
        Log.d("MyEvent","Add new event");
        task.run();
        return true;
    }

    private static Event checkSameEventWithThisDate(Event e){
        for(Event e1:events){
            if(e.compareEvent(e1)){
                return e1;
            }
        }
        return null;
    }

    public static boolean removeEvent(Event event){
        Event existsEvent = checkSameEventWithThisDate(event);
        if(existsEvent!=null){
            events.remove(existsEvent);
            task.run();
            Log.d("MyEvent","Remove event");
            return true;
        }
        return false;
    }

    public static void clear(){
        events.clear();
        task.run();
    }

    private static boolean changeEvent(Event oldEvent, Event newEvent){
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
        WorkWithXML xml = new WorkWithXML(wfS);
        xml.serializeToXMLDocument(Category.getCategoryNames(),CalendarEvents.getEvents());
        Log.d("MyEvent","synchronized");
    }

    private static void loadFromFile(){
        WorkWithFile wfS = new WorkWithFile(Constants.EVENTS);
        WorkWithXML xml = new WorkWithXML(wfS);
        xml.deserialize();
        Log.d("MyEvent","loadFromFile");
    }

    @Override
    public String toString(){
        return "\nCount events: " + events.size();
    }
}

