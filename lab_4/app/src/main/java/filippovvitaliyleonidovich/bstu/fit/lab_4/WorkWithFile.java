package filippovvitaliyleonidovich.bstu.fit.lab_4;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class WorkWithFile {

    File file;
    public WorkWithFile(String fileName) {
        this.file = new File(fileName);
    }

    public boolean createFile() {
        try {
            file.createNewFile();
        }
        catch (IOException e) {
            return false;
        }
        return true;
    }

    public boolean checkFile(){
        if(file.exists()) {
            return true;
        }
        else{
            return false;
        }
    }

    public boolean writeFile(String text) {
        try {
            FileWriter fileWriter = new FileWriter(file, true);
            fileWriter.write(text);
            fileWriter.close();
        }
        catch(IOException e){
            return false;
        }
        return true;
    }

    public String readFile() {
        StringBuilder builder;
        try{
            FileReader fileReader = new FileReader(file);
            char[] nw =new char[10000];
            fileReader.read(nw);
            builder = new StringBuilder();
            for (int i = 0; i < nw.length; ++i) {
                builder.append(nw[i]);
            }

            fileReader.close();
        }
        catch (Exception e) {
            return "Error";
        }
        return builder.toString();
    }


}


