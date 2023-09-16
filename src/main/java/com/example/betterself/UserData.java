package com.example.betterself;
import java.sql.Date;

public class UserData {
    UserData(int _Id, String _Name, String _Email, Date _DateOfBirth, byte[] _Profile_Pic, String _Password, int _Total_Streaks, int _Total_Daily, int _Total_DeadLifts)
    {
        UserId=_Id;
        Name=_Name;
        Email=_Email;
        DateOfBirth=_DateOfBirth;
        Profile_Pic=_Profile_Pic;
        Password=_Password;
        Total_Streaks=_Total_Streaks;
        Total_Daily=_Total_Daily;
        Total_DeadLifts=_Total_DeadLifts;
    }
    public int UserId;
    public String Name;
    public String Email;
    public Date DateOfBirth;
    public byte[] Profile_Pic;
    public String Password;
    public int Total_Streaks;
    public int Total_Daily;
    public int Total_DeadLifts;
}