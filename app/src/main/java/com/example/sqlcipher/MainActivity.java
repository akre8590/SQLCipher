package com.example.sqlcipher;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import net.sqlcipher.SQLException;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SqliteWrapper;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private Button encriptarBtn;
    public static String DB_NAME = "original.db3";
    public static String DB_PATH = "/storage/emulated/0/Test/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SQLiteDatabase.loadLibs(getApplicationContext());

        encriptarBtn = (Button)findViewById(R.id.encripter);
        encriptarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkDataBase()){
                    ProgressDialog progress = new ProgressDialog(MainActivity.this);
                    progress.setMessage("Encriptando...");
                    progress.setCancelable(false);
                    new MyTaskEncrypt(progress, MainActivity.this).execute();
                } else {
                    Toast.makeText(MainActivity.this, "Base de datos no se encuentra", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private boolean checkDataBase() {
        File databasePath = getApplicationContext().getDatabasePath(DB_PATH + DB_NAME);
        return databasePath.exists();
    }
}