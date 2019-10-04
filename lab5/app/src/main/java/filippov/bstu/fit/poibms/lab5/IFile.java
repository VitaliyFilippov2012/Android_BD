package filippov.bstu.fit.poibms.lab5;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface IFile {
    boolean exists(File dir, String name);

    File createFile(File dir, String name) throws IOException;

    String readFile(File file, int pos) throws FileNotFoundException;

    boolean writeToFile(File file, String text, int hash);

    boolean addValue(File file, String text, int hash);

    List<String> getValue(File file, int hash);
}
