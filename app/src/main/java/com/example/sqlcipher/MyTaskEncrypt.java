package com.example.sqlcipher;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Button;
import android.widget.Toast;

import net.sqlcipher.database.SQLiteDatabase;

import java.io.File;
import java.io.IOException;

public class MyTaskEncrypt extends AsyncTask<Void, Void, Void>  {

    ProgressDialog progress;
    private SQLiteDatabase sqLiteDatabase;
    private Button encriptarBtn;

    public static String DB_NAME = "original.db3";
    public static String DB_PATH = "/storage/emulated/0/Test/";
    Context context;

    public MyTaskEncrypt(ProgressDialog progress, Context context){
        this.progress = progress;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        progress.show();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        createDataBase();
        try {
            encryptDataBase("123456");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        progress.dismiss();
        Toast.makeText(context, "La base de datos se ha encriptado", Toast.LENGTH_SHORT).show();
    }

    public void encryptDataBase(String passphrase) throws IOException {

        File originalFile = context.getDatabasePath(DB_NAME);

        File root = new File("/storage/emulated/0/prueba.db3");

        //File newFile = File.createTempFile("sqlcipherutils", null, root);

        SQLiteDatabase existing_db = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, "", null, SQLiteDatabase.OPEN_READWRITE);

        existing_db.rawExecSQL("ATTACH DATABASE '" + root.getPath() + "' AS encrypted KEY '" + passphrase + "';");
        existing_db.rawExecSQL("SELECT sqlcipher_export('encrypted');");
        existing_db.rawExecSQL("DETACH DATABASE encrypted;");

        existing_db.close();

        originalFile.delete();

        root.renameTo(originalFile);
    }
    public void createDataBase(){
        sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase("/storage/emulated/0/prueba.db3","", null);
        sqLiteDatabase.close();
    }
}