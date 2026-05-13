package com.example.foodfight;

//Skye and Ava work together to make StorageActivity for memory and future access to past accounts

public class StorageActivity {
    private static int currentUserId = -1;

    public static void setCurrentUserId(int uid){
        currentUserId = uid;
    }

    public static int getCurrentUserId(){
        return currentUserId;
    }
}
