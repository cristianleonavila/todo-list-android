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
    private String valorAnterior = "";

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

        cargarTareas();

        editTextTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valorAnterior = editTextTask.getText().toString().trim();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String task = editTextTask.getText().toString().trim();
                if (!task.isEmpty()) {
                    db.registrarTarea(task);
                    editTextTask.setText("");
                    cargarTareas();
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
                    db.actualizarTarea(valorAnterior, task); // position + 1 because SQLite IDs start from 1
                    editTextTask.setText("");
                    cargarTareas();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String task = editTextTask.getText().toString().trim();
                if (!task.isEmpty()) {
                    db.eliminarTarea(task); // position + 1 because SQLite IDs start from 1
                    editTextTask.setText("");
                    cargarTareas();
                }
            }
        });
    }

    private void cargarTareas() {
        tasksList = db.listarTareas();
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tasksList);
        listViewTasks.setAdapter(arrayAdapter);
    }
}