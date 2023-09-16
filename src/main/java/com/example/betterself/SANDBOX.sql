CREATE TABLE TEAM(
    Id int primary key ,
    Name varchar2(20)
)

CREATE TABLE BETTERUSER(
    Id int primary key ,
    Name varchar2(20),
    Email varchar2(25),
    DateOfBirth date,
    Profile_Pic LONGBLOB,
    Password varchar2(20),
    Total_Streaks int,
    Total_Daily int,
    Total_DeadLifts int
)

CREATE TABLE TEAM_USER(
    User_Id int,
    Team_Id int,
    FOREIGN KEY (User_Id) references BETTERUSER(Id),
    FOREIGN KEY (Team_Id) references TEAM(Id)
)

CREATE TABLE HISTORY(
    User_Id int,
    Task_Id int,
    Task_Name varchar2(20),
    Task_Date DATE,
    Task_Type varchar2(20),
    FOREIGN KEY (User_Id) references BETTERUSER(Id)
)

CREATE TABLE TASK(
      User_Id int,
      Task_Id int,
      Task_Name varchar2(20),
      Task_Date DATE,
      Task_Type varchar2(20),
      FOREIGN KEY (User_Id) references BETTERUSER(Id)
)

CREATE TABLE PROGRESS(
    User_Id int,
    Points int,
    FOREIGN KEY (User_Id) references BETTERUSER(Id)
)

