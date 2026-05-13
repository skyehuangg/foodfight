package com.example.foodfight;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

//Skye and Ava collaborated to implement database!!

@Entity(tableName = "user")
public class User {
    @PrimaryKey(autoGenerate = true)
    public int uid;
    public String lastName;
    public String firstName;
    public String fullName;
    public String email;
    public String password;
    public Integer profileIcon;
    public boolean dairyAllergy;
    public boolean peanutAllergy;
    public boolean glutenAllergy;
    public boolean vegan;
    public boolean vegetarian;
    public boolean soyAllergy;
    public boolean shellfishAllergy;
    public boolean noAllergy;
    public boolean otherAllergy;
    public String accountType;
    public double balance = 0.00;
    public String stringOther;


    public User(String iLastName, String iFirstName, String iFullName, String iEmail, String iPassword, Integer iProfileIcon){
        lastName = iLastName;
        firstName = iFirstName;
        fullName = iFullName;
        email = iEmail;
        password = iPassword;
        profileIcon = iProfileIcon;
    }

    public User() {}

    //Skye update database to include more allergies

    public String getFirstName(){
        return firstName;
    }

    public String getFullName(){
        return fullName;
    }

    public String getEmail(){
        return email;
    }

    public String getPassword(){
        return password;
    }

    public int getProfileIcon(){
        return profileIcon;
    }

    public boolean getDairyAllergy(){
        return dairyAllergy;
    }

    public boolean getPeanutAllergy(){
        return peanutAllergy;
    }

    public boolean getGlutenAllergy(){
        return glutenAllergy;
    }
    public boolean getVegan (){ return vegan; }
    public boolean getVegetarian (){ return vegetarian; }
    public boolean getSoyAllergy(){ return soyAllergy; }
    public boolean getShellfishAllergy(){ return shellfishAllergy; }
    public boolean getNoAllergy(){
        return noAllergy;
    }

    public boolean getOtherAllergy(){
        return otherAllergy;
    }

    public String getAccountType(){
        return accountType;
    }

    public double getBalance(){
        return balance;
    }

    public String getOtherStringAllergy(){
        return stringOther;
    }
}
