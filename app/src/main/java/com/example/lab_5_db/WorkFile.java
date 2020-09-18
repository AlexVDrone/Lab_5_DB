package com.example.lab_5_db;

import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class WorkFile {
    private RandomAccessFile file;

    public WorkFile(File file) {
        try {
            this.file = new RandomAccessFile(file, "rw");
        } catch (FileNotFoundException e) {
            Log.d("WorkWithFile", "Файл не существует");
        }
    }

    public String getValue(int hash){
        int index = 0;
        if((index = containsKey(hash)) != -1){
            try{
                file.seek(index);
                return file.readLine().substring(6);
            } catch (IOException e) {Log.d("error ", "Error:" + e.getMessage());}
        }
        return "";
    }

    public boolean put(int hash, String value){
        try{
            int index = 0;
            // если существуует запись получаем индекс символа начала строки и заменяем ее на новую
            if((index = containsKey(hash)) != -1)
            {
                StringBuilder builder = new StringBuilder();
                builder.append(hash);
                builder.append(';');
                builder.append(value);
                builder.append("\n\r");


                file.write(builder.toString().getBytes(), index + 1, 18);
            } else{
                // пишем в конец файла
                file.seek(file.length());
                file.write(hash);
                file.write(';');
                file.write(value.getBytes());
            }
        } catch (IOException e) {Log.d("error ", "Error:" + e.getMessage());}

        return false;
    }

    // возвращает индекс символа с которого начинается строка если ключ есть в файле
    // если ключ отсуствует => -1
    public int containsKey(int hash) {
        int count = 0;

        try{
            file.seek(0);

            String line = null;

            while ((line = file.readLine()) != null) {
                if(line.contains(String.valueOf(hash)))
                    return count;
                count += line.length();
            }
        } catch (IOException e) {
            Log.d("error ", "Error:" + e.getMessage());
        }
        return -1;
    }
}
