package filippov.bstu.fit.poibms.lab5;

import android.annotation.SuppressLint;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import static filippov.bstu.fit.poibms.lab5.Constants.EMPTY_LINE;
import static filippov.bstu.fit.poibms.lab5.Constants.FILE_NAME;
import static filippov.bstu.fit.poibms.lab5.Constants.TAG;
import static java.lang.String.format;

public class WorkWithFile implements IFile{
    @Override
    public boolean exists(final File dir, final String name) {
        final String filePath = format("%s/%s", dir.getPath(), FILE_NAME);
        return new File(filePath).exists();
    }

    @Override
    public File createFile(final File dir, final String name) throws IOException {
        final File file = new File(dir.getPath(), FILE_NAME);
        file.createNewFile();
        for (int i = 0; i < 10; ++i) {
            appendToFile(file, EMPTY_LINE);
        }
        return file;
    }

    private boolean appendToFile(final File file, final String text) {
        try {
            final BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
            bw.append(text);
            bw.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public String readFile(final File file, final int pos){
        String value = "";
        try {
            final RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
            final FileChannel channel = randomAccessFile.getChannel();
            final ByteBuffer byteBuffer = ByteBuffer.allocate(35);
            channel.read(byteBuffer, pos);
            value = new String(byteBuffer.array());
            channel.close();
            randomAccessFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return value;
    }

    @Override
    public boolean writeToFile(final File file, final String text, final int pos) {
        try {
            final RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
            randomAccessFile.seek(pos);
            randomAccessFile.write(text.getBytes());
            randomAccessFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean addValue(final File file, final String text, final int hash) {
        Log.d(Constants.TAG,"2");

        int pos = findPos(file, hash,true);
        Log.d(Constants.TAG,"2-pos-"+pos);

        if(pos % 30 == 0){
            writeToFile(file, String.valueOf(hash)+":"+ text + ";", pos);
            Log.d(TAG,String.valueOf(hash)+":"+ text + ";");
        }
        else{
            writeToFile(file, text+";", pos);
            Log.d(TAG,"else-"+text + ";");
        }
        Log.d(Constants.TAG,"2_2");

        return false;
    }

    @Override
    public List<String> getValue(final File file, final int hash) {
        final List<String> list = new ArrayList<>();
        int pos = findPos(file, hash,false);
        String str = readFile(file,pos);
        String values = str.substring(10,str.lastIndexOf(";"));
        Log.d(TAG,"-value: "+values);

        String[] arrayValues = values.split(";");
        for (String value: arrayValues) {
            list.add(value);
            Log.d(TAG,"-value: "+value);
        }
        return list;
    }


    @SuppressLint("DefaultLocale")
    private int findPos(final File file, final int hash,final boolean write) {
        boolean next;
        int pos = 0;
        try {
            final RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
            final FileChannel channel =  randomAccessFile.getChannel();
            do {
                next = false;
                final ByteBuffer byteBuffer = ByteBuffer.allocate(9);
                channel.read(byteBuffer, pos);
                String value = new String(byteBuffer.array());
                Log.d(Constants.TAG,hash+"--"+value);

                if(!value.equals("         ")){
                    if(value.equals(String.valueOf(hash))){
                        ByteBuffer buffer = ByteBuffer.allocate(24);
                        channel.read(buffer, pos);
                        String str = new String(buffer.array());
                        Log.d(TAG, "str: " + str);

                        if(write){
                            Log.d(TAG, "in if w: " + pos);
                            pos += str.lastIndexOf(";")+1;
                        }
                        else{
                            Log.d(TAG, "in else w: " + pos);

                            //pos += str.lastIndexOf(":")+1;
                        }
                    }
                    else{
                        pos += 30;
                        next = true;
                    }
                }
            } while (next);
            channel.close();
            randomAccessFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e){
            pos = 0;
        }
        Log.d(TAG, "pos: " + pos);
        return pos;
    }
}
