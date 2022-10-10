package com.jvanks05025.todolist;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
//entity class
@Entity(tableName = "note_table")
public class Note {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String todo;

    public Note(String todo) {
        this.todo = todo;
    }

    public String getTodo() {
        return todo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
