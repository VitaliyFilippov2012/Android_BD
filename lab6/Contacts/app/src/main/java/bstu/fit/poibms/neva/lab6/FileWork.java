package com.andrey.laba5;
import android.content.DialogInterface;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

public class FileWork {

    AlertDialog.Builder dialogBuilder;
    private File file;
    String filename;
    AppCompatActivity appCompatActivity;

    public FileWork(AppCompatActivity appCompatActivity, String filename) {
        this.appCompatActivity = appCompatActivity;
        dialogBuilder = new AlertDialog.Builder(appCompatActivity);
        file = new File(appCompatActivity.getFilesDir(), filename);
        this.filename = filename;
    }

    public void createFile() {
        try {
            file.createNewFile();
            for (int i = 0; i < 10; ++i) {
                initFile("                              ");
            }
            showAlertDialog("File created!");
        }
        catch (Exception e) {
            showAlertDialog("Error");
        }
    }

    public boolean isFileExist() {
        if(!file.exists()) {
            showAlertDialog(String.format("File %s doesn't exist!", filename));
            return false;
        }
        else {
            return true;
        }
    }
    private boolean initFile(final String text) {
        try {
            final BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
            bw.append(text);
            bw.close();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
    private String readFile(final int position) {
        String value = "";
        try {
            final RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
            final FileChannel channel = randomAccessFile.getChannel();
            final ByteBuffer byteBuffer = ByteBuffer.allocate(35);
            channel.read(byteBuffer, position);
            value = new String(byteBuffer.array());
            channel.close();
            randomAccessFile.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return value;
    }
    private boolean writeToFile(final String text, final int pos) {
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
    public void showAlertDialog(String message) {
        dialogBuilder = new AlertDialog.Builder(appCompatActivity);
        dialogBuilder.setTitle(message).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }

    public boolean addValue(final String text, final String key) {
        int hashKey = getHashCode(key);
        int pos = findPos(hashKey,true);
        if(pos % 30 == 0){
            writeToFile(hashKey + ":" + text + ";", pos);
        }
        else{
            writeToFile(text + ";", pos);
        }

        return false;
    }
    public ArrayList<String> getValue(final String key) {
        final ArrayList<String> list = new ArrayList<>();
        int hash = getHashCode(key);
        int pos = findPos(hash,false);
        String str = readFile(pos);
        String values = str.substring(10, str.lastIndexOf(";"));

        String[] arrayValues = values.split(";");
        for (String value: arrayValues) {
            list.add(value);
        }
        return list;
    }
    private int findPos(final int hash,final boolean write) {
        boolean endOfFile;
        int position = 0;
        try {
            final RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
            final FileChannel channel = randomAccessFile.getChannel();
            do {
                endOfFile = false;
                final ByteBuffer byteBuffer = ByteBuffer.allocate(9);
                channel.read(byteBuffer, position);
                String value = new String(byteBuffer.array());

                if(!value.equals("         ")) {
                    if(value.equals(String.valueOf(hash))) {
                        ByteBuffer buffer = ByteBuffer.allocate(24);
                        channel.read(buffer, position);
                        String str = new String(buffer.array());
                        if(write) {
                            position += str.lastIndexOf(";") + 1;
                        }
                    }
                    else {
                        position += 30;
                        endOfFile = true;
                    }
                }
            } while (endOfFile);

            channel.close();
            randomAccessFile.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception e){
            position = 0;
        }
        return position;
    }
    private int getHashCode(String value) {
        String hash = String.valueOf(value.hashCode());
        if(hash.length()<= 9){
            int n = 9 - hash.length();
            for(int i = 0; i < n;i++) {
                hash = hash + "0";
            }
        }
        else {
            hash = hash.substring(0,9);
        }
        return Integer.valueOf(hash);
    }
}
