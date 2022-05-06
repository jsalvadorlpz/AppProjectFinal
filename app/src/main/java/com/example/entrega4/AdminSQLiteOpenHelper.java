package com.example.entrega4;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NOBRE="administracion";
    public static final String DATABASE_TABLE="usuarios";
    public AdminSQLiteOpenHelper(@Nullable Context context) {
        super(context, DATABASE_NOBRE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase BaseDeDatos) {
        BaseDeDatos.execSQL("CREATE TABLE "+ DATABASE_TABLE + "(" +
                "token INT PRIMARY KEY," +
                "nombre STRING NOT NULL," +
                "email STRING NOT NULL," +
                "password STRING NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
