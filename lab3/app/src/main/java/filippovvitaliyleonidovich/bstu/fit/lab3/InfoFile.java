package filippovvitaliyleonidovich.bstu.fit.lab3;

import android.os.Environment;

import java.io.File;

public class InfoFile {

    private String absolute,name,path,readwrite,external;


    public String getAbsolute(){
        return absolute;
    }
    public String getName(){
        return name;
    }
    public String getPath(){
        return path;
    }
    public String getReadwrite(){
        return readwrite;
    }
    public String getExternal(){
        return external;
    }

    public void getInfoAboutFile(File dir){
        Boolean wr,rd;
        absolute = dir.getAbsolutePath();
        name = dir.getName();
        path = dir.getPath();
        rd=dir.canRead();
        wr=dir.canWrite();
        if(rd==true || wr==true){
            if(rd==true && wr==true){
                readwrite = "yes/yes";
            }
            else if(rd==true && wr==false){
                readwrite = "yes/no";
            }
            else{
                readwrite = "no/yes";
            }
        }
        else
        {
            readwrite = "no/no";
        }
        external = Environment.getExternalStorageState();
    }

}
