package com.example.d03k12sqlite;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TodoDAO {
    // CRUD

    @Query("SELECT * FROM todos")
    List<Todo> getAll();

    @Query("SELECT * FROM todos WHERE id=(:id)")
    Todo getById(long id);

    @Insert
    long save(Todo todo);

    @Update
    void update(Todo todo);

    @Delete
    void delete(Todo todo);
}
