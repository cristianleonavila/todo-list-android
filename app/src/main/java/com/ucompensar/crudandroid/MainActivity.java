package com.ucompensar.crudandroid;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText editTextTask;
    private Button btnAdd, btnUpdate, btnDelete;
    private ListView listViewTasks;
    private ArrayAdapter<String> arrayAdapter;
    private List<String> tasksList;
    private TodoListDbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextTask = findViewById(R.id.editTextTask);
        btnAdd = findViewById(R.id.btnAdd);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        listViewTasks = findViewById(R.id.listViewTasks);
        db = new TodoListDbHelper(this);

        loadTasks();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String task = editTextTask.getText().toString().trim();
                if (!task.isEmpty()) {
                    db.addTask(task);
                    editTextTask.setText("");
                    loadTasks();
                }
            }
        });

        listViewTasks.setOnItemClickListener((parent, view, position, id) -> {
            String task = tasksList.get(position);
            editTextTask.setText(task);
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String task = editTextTask.getText().toString().trim();
                if (!task.isEmpty()) {
                    db.updateTask(task, task); // position + 1 because SQLite IDs start from 1
                    editTextTask.setText("");
                    loadTasks();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String task = editTextTask.getText().toString().trim();
                if (!task.isEmpty()) {
                    db.deleteTask(task); // position + 1 because SQLite IDs start from 1
                    editTextTask.setText("");
                    loadTasks();
                }
            }
        });
    }

    private void loadTasks() {
        tasksList = db.getAllTasks();
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tasksList);
        listViewTasks.setAdapter(arrayAdapter);
    }
}