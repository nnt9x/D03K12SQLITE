package com.example.d03k12sqlite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText edtInput;
    private Button btnAdd;
    private ListView lvTodo;

    private List<String> dataSource;
    private ArrayAdapter<String> myAdapter;

    // Config DB
    private AppDatabase appDatabase;
    private TodoDAO todoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtInput = findViewById(R.id.edtInput);
        btnAdd = findViewById(R.id.btnAdd);
        lvTodo = findViewById(R.id.lvTodo);

        // Config DB
        appDatabase = Room.databaseBuilder(this, AppDatabase.class, "myDB")
                .allowMainThreadQueries()
                .build();

        todoDAO = appDatabase.getTodoDAO();

        dataSource = new ArrayList<>();
        // Lay du lieu tu SQLite
        List<Todo> todoList = todoDAO.getAll();
        for (int i = 0; i < todoList.size(); i++) {
            dataSource.add(todoList.get(i).toString());
        }

        myAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dataSource);

        lvTodo.setAdapter(myAdapter);

        lvTodo.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int index, long l) {

                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Canh bao")
                        .setMessage("Ban co muon xoa?")
                        .setPositiveButton("Dong y", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // Lay phan tu o vi tri i
                                Todo item = todoDAO.getAll().get(index);
                                // Xoa trong db
                                todoDAO.delete(item);
                                // Xoa o dataSource
                                dataSource.remove(index);
                                // Thong bao cho Adapter
                                myAdapter.notifyDataSetChanged();
                            }
                        }).show();
                return false;
            }
        });
    }

    public void addNewTodo(View view) {
        String input = edtInput.getText().toString().trim();
        if (input.isEmpty()) {
            edtInput.setError("Hay nhap du lieu");
            return;
        }
        // Them du lieu vao SQLite
        Todo todo = new Todo(input);
        todoDAO.save(todo);
        // Them du lieu vao datasource
        dataSource.add(input);
        myAdapter.notifyDataSetChanged();
        edtInput.setText("");

    }
}