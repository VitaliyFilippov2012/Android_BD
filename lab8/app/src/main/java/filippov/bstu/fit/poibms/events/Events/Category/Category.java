package filippov.bstu.fit.poibms.events.Events.Category;

import android.util.Log;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import filippov.bstu.fit.poibms.events.Constants.Constants;
import filippov.bstu.fit.poibms.events.File.WorkWithFile;
import filippov.bstu.fit.poibms.events.File.WorkWithFileJSON;

public class Category {
    private static ArrayList<String> categoryNames;
    static {
        categoryNames = new ArrayList<String>();
        loadFromFile();
    }

    public static ArrayList<String> getCategoryNames() {
        return categoryNames;
    }

    public static void setCategoryNames(ArrayList<String> categoryNames) {
        Category.categoryNames.addAll(categoryNames);
    }

    public static boolean addCategoryNames(String categoryName) {
        if(categoryNames.contains(categoryName)){
            return true;
        }
        else{
            categoryNames.add(categoryName);
        }

        task.run();
        return true;
    }

    private static Runnable task = new Runnable() {
        @Override
        public void run() {
            synchronizedEventsWithFile();
        }
    };


    private static void synchronizedEventsWithFile(){
        WorkWithFile wfC = new WorkWithFile(Constants.Category_JSON);
        wfC.createFile(true);
        WorkWithFileJSON<String> workWithFileJSONS = new WorkWithFileJSON<String>(wfC);
        for(String s: categoryNames){
            workWithFileJSONS.serialize(s);
        }
    }

    public static void clear(){
        categoryNames.clear();
        task.run();
    }

    private static void loadFromFile(){
        WorkWithFile wfC = new WorkWithFile(Constants.Category_JSON);
        WorkWithFileJSON<String> workWithFileJSONS = new WorkWithFileJSON<String>(wfC);
        categoryNames = workWithFileJSONS.deserialize(new TypeToken<String>(){}.getType());
        Log.d("MyEvent","loadFromFile");
    }

    public static boolean removeCategory(String category){
        if(categoryNames.contains(category)){
            categoryNames.remove(category);
            task.run();
            Log.d("MyEvent","Remove category");
            return true;
        }
        return false;
    }

    public int getCountCategory(){
        return categoryNames.size();
    }
}
