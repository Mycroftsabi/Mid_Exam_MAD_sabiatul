package com.example.midexam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText editTextName, editTextStudentId;
    CheckBox checkEnrolled;
    Button btnAdd, btnView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editTextName = findViewById(R.id.editTextName);
        editTextStudentId = findViewById(R.id.editTextStudentId);
        checkEnrolled = findViewById(R.id.checkEnrolled);
        btnAdd = findViewById(R.id.btnAdd);
        btnView = findViewById(R.id.btnView);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString();
                String studentId = editTextStudentId.getText().toString();
                boolean enrolled = checkEnrolled.isChecked();

                if (name.isEmpty() || studentId.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Enter data", Toast.LENGTH_SHORT).show();
                } else {
                    DBHelper dbHelper = new DBHelper(MainActivity.this);
                    dbHelper.addStudent(new StudentModel(name, studentId, enrolled));
                    Toast.makeText(MainActivity.this, "Add Successful", Toast.LENGTH_SHORT).show();

                    editTextName.setText("");
                    editTextStudentId.setText("");
                    checkEnrolled.setChecked(false);
                }
            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ViewStudent.class);
                startActivity(intent);
            }
        });
    }
}