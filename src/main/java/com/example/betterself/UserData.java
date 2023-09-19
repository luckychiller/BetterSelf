package com.example.betterself;
import java.io.Serializable;
import java.sql.Date;

public class UserData implements Serializable {
    public int UserId;
    public String Name;
    public String Email;
    public Date DateOfBirth;
    public byte[] Profile_Pic;
    public String Password;
    public int Total_Streaks;
    public int Total_Daily;
    public int Total_DeadLifts;

    public int Points;
}