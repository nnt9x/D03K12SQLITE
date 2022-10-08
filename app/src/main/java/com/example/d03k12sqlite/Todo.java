package com.example.d03k12sqlite;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "todos")
public class Todo {
    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo
    private String content;

    public Todo() {
    }

    public Todo(String content) {
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
       return content;
    }
}
