package com.example.foodfight;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {User.class}, version = 6)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;
    public static final String DB_NAME = "foodFight";

    abstract UserDAO getUserDAO();

    public static synchronized AppDatabase getInstance(Context ctx) {
        if(instance == null){
            instance = Room.databaseBuilder(ctx.getApplicationContext(), AppDatabase.class, DB_NAME).fallbackToDestructiveMigration().build();
        }
        return instance;
    }
}
