package com.example.lab_5_db;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private final String FILE_NAME="Lab.txt";
    EditText setKey,setValue,getKey,getValue;
    CheckAccessFile checkAccessFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setKey= findViewById(R.id.editTextTextMultiLine);
        setValue= findViewById(R.id.editTextTextMultiLine2);
        getKey=findViewById(R.id.editTextTextMultiLine3);
        getValue=findViewById(R.id.editTextTextMultiLine4);
        try {
            checkFile(FILE_NAME);

        } catch (IOException e) {
            e.printStackTrace();
        }

        checkAccessFile = new CheckAccessFile(new File(super.getFilesDir(),FILE_NAME));
    }

    // запись пары в файл
    public void SaveRecord(View view){

        int key = setKey.getText().toString().hashCode();
        String value = setValue.getText().toString();
        if (checkAccessFile.put(key, value))
            Log.d("MainActivity", "Записано");
        onCreateDialog("Record saved");

    }

    // получяем значения по ключу
    public void GetRecord(View view){
        String str = getKey.getText().toString();
        int key;
        if(!str.isEmpty()){
            key = getKey.getText().toString().hashCode();
            str = checkAccessFile.getValue(key);
            getValue.setText(str);
        }

    }

    public void FileInfo(View view){
        String message = checkAccessFile.readFile();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Содержимое файла").
                setPositiveButton("OK", null).setMessage(message);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    protected void onCreateDialog(String s) {
        androidx.appcompat.app.AlertDialog.Builder b = new androidx.appcompat.app.AlertDialog.Builder(this);
        b.setTitle(s);
        androidx.appcompat.app.AlertDialog ad = b.create();
        ad.show();
    }
    public void checkFile(String fileName) throws IOException{
        File file=new File(super.getFilesDir(), fileName);
        if(!file.exists()){
            file.createNewFile();
            Toast.makeText(this, "Файл создан", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Файл существует", Toast.LENGTH_SHORT).show();
        }
    }
    public void checkEditText(){

    }
}