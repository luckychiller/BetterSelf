package com.example.betterself;

import javafx.scene.control.Button;

public class ItemDTO {
    String status;
    String task;
    String due;
    Button edit;

    public ItemDTO(String status, String task, String due, Button edit) {
        this.status = status;
        this.task = task;
        this.due = due;
        this.edit = edit;
    }
    public Button getEdit() {return edit;}
    public void setEdit(Button edit) {this.edit = edit;}
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getTask() {
        return task;
    }
    public void setTask(String task) {
        this.task = task;
    }
    public String getDue() {
        return due;
    }
    public void setDue(String due) {
        this.due = due;
    }
}
