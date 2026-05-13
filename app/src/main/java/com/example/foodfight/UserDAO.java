package com.example.foodfight;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

//Skye and Ava collaborated to implement database!!

@Dao
interface UserDAO {
    @Insert
    long insertUser(User user);

    @Update
    void updateUser(User user);

    @Query("SELECT * FROM user WHERE uid = :id LIMIT 1")
    User getUserById(int id);

    @Query("SELECT * FROM user WHERE email = :email LIMIT 1")
    User getUserByEmail(String email);
}
