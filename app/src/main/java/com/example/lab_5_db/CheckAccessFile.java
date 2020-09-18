package com.example.lab_5_db;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;

public class CheckAccessFile {
    private File file;
    public CheckAccessFile(File file)
    {
        this.file = file;
    }

    public String getValue(int key){
        String str = "";
        int index = constainsKey(key);

        if(index != -1)
            try{
                RandomAccessFile randomAccessFile = new RandomAccessFile(file,"r");
                randomAccessFile.seek(index);

                str = randomAccessFile.readLine();
                randomAccessFile.close();

                return str.substring(str.lastIndexOf(';') + 1);
            } catch (FileNotFoundException e) {
                Log.d("AccessFile", "Ошибка в getValue" + e.getMessage());
            } catch (IOException e) {
                Log.d("AccessFile", "Ошибка в getValue" + e.getMessage());
            }

        return "";
    }

    public boolean put(int key, String value){

        String str = String.valueOf(key) + ";" + value + "\n";
        int index = constainsKey(key);
        try {
            // если запись отсутствует, добавляем в конец файла
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
            randomAccessFile.seek(randomAccessFile.length());
            randomAccessFile.write(str.getBytes());
            randomAccessFile.close();
            return true;
        } catch (FileNotFoundException e) {
            Log.d("CheckAccessFile", "Ошибка в put" + e.getMessage());
        } catch (IOException e) {
            Log.d("CheckAccessFile", "Ошибка в put" + e.getMessage());
        }

        return false;
    }

    // вернет индекс начала строки
    public int constainsKey(int key){
        int index = 0;
        String str = "";

        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
            randomAccessFile.seek(0);

            while ((str = randomAccessFile.readLine()) != null){
                if(str.contains(String.valueOf(key))){
                    randomAccessFile.close();
                    return index;
                }

                index += randomAccessFile.getFilePointer();
            }

        } catch (FileNotFoundException e) {
            Log.d("AccessFile", "Ошибка в сontainsKey" + e.getMessage());
        } catch (IOException e) {
            Log.d("AccessFile", "Ошибка в сontainsKey" + e.getMessage());
        }

        return -1;
    }

    public String readFile() {
        StringBuilder text = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }

            return String.valueOf(text);

        } catch (FileNotFoundException e) {
            Log.d("CheckAccessFile", "Ошибка в readFile" + e.getMessage());
        } catch (IOException e) {
            Log.d("CheckAccessFile", "Ошибка в readFile" + e.getMessage());
        }
        return "";
    }


}

