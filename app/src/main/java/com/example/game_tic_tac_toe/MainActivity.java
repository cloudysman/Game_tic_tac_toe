package com.example.game_tic_tac_toe;

import android.content.ContentValues;
import android.icu.lang.UProperty;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private StudentModel std = null;
    private EditText idname;
    private EditText idemail;
    private EditText idpassword;
    private Button btnAdd;
    private Button btnView;
    private Button btnUpdate;
    private SQLiteHelper sqLiteHelper;
    private RecyclerView recyclerView;
    private StudentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initRecyclerView();
        sqLiteHelper = new SQLiteHelper(this);

        btnAdd.setOnClickListener(view -> addStudent());
        btnView.setOnClickListener(view -> getStudents());
        btnUpdate.setOnClickListener(view -> updateStudent());

        adapter.setOnClickItem(item -> {
            Toast.makeText(this, item.getName(), Toast.LENGTH_SHORT).show();
            idname.setText(item.getName());
            idemail.setText(item.getEmail());
            idpassword.setText(item.getPassword());
            std = item;
        });

        adapter.setOnClickDeleteItem(item -> deleteStudent(item.getEmail()));
        getStudents();
    }

    private void updateStudent() {
        String name = idname.getText().toString();
        String email = idemail.getText().toString();
        String password=idpassword.getText().toString();
        if (std == null) {
            return;
        }
        if (name.equals(std.getName()) && email.equals(std.getEmail())&&password.equals(std.getPassword())) {
            Toast.makeText(this, "Record not changed....", Toast.LENGTH_SHORT).show();
            return;
        }



        StudentModel std = new StudentModel( name, email,password);
        int status = sqLiteHelper.updateStudent(std);

        if (status > -1) {
            clearEditText();
            getStudents();
        } else {
            Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void getStudents() {
        List<StudentModel> stdList = sqLiteHelper.getAllStudent();
        Log.e("Số thành viên", String.valueOf(stdList.size()));
        adapter.addItem((ArrayList<StudentModel>) stdList);
        adapter.notifyDataSetChanged();
    }


    private void deleteStudent(String email) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete item?");
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", (dialog, which) -> {
            sqLiteHelper.deleteStudentByEmail(email);
            getStudents();
            dialog.dismiss();
        });
        builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void addStudent() {
        String name = idname.getText().toString();
        String email = idemail.getText().toString();
        String password =idpassword.getText().toString();

        if (name.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Please enter required field", Toast.LENGTH_SHORT).show();
            return;
        }

        StudentModel std = new StudentModel(name, email, password);
        int status = (int) sqLiteHelper.insertStudent(std);

        if (status > -1) {
            Toast.makeText(this, "Student Added...", Toast.LENGTH_SHORT).show();
            clearEditText();
            getStudents();
        } else {
            Toast.makeText(this, "Record not saved", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearEditText() {
        idname.setText("");
        idemail.setText("");
        idpassword.setText("");
        idname.requestFocus();
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new StudentAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void initView() {
        idname = findViewById(R.id.idname);
        idemail = findViewById(R.id.idemail);
        idpassword=findViewById(R.id.idpassword);
        btnAdd = findViewById(R.id.btnAdd);
        btnView = findViewById(R.id.btnView);
        btnUpdate = findViewById(R.id.btnUpdate);
        recyclerView = findViewById(R.id.recyclerView);
    }
}
