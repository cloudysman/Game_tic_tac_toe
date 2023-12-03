package com.example.game_tic_tac_toe;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "student.db";
    private static final String TBL_STUDENT = "tbl_student";
    private static final String NAME = "name";
    private static final String EMAIL = "email";
    private static final String PASSWORD ="password";
    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTblStudent = "CREATE TABLE " + TBL_STUDENT + "(" +
                NAME + " TEXT," +
                PASSWORD + " TEXT," +
                EMAIL + " TEXT PRIMARY KEY" + ")";
        db.execSQL(createTblStudent);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TBL_STUDENT);
        onCreate(db);
    }
    public Boolean checkEmail(String email){
        SQLiteDatabase MyDatabase=this.getReadableDatabase();
        Cursor cursor =MyDatabase.rawQuery("Select * from "+TBL_STUDENT+" where email =?", new String []{email});
        if(cursor.getCount()>0){
            return true;
        }
        else{
            return false;
        }
    }
    public Boolean checkEmailPassword(String email, String password){
        SQLiteDatabase MyDatabase =this.getReadableDatabase();
        Cursor cursor =MyDatabase.rawQuery("Select * from "+TBL_STUDENT+" where email = ? and password = ?", new String[]{email,password});
        if(cursor.getCount()>0){
            return true;
        }
        else{
            return false;
        }
    }

    public long insertStudent(StudentModel std) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, std.getName());
        contentValues.put(EMAIL, std.getEmail());
        contentValues.put(PASSWORD,std.getPassword());
        long success = db.insert(TBL_STUDENT, null, contentValues);
        db.close();
        return success;
    }

    @SuppressLint("Range")
    public ArrayList<StudentModel> getAllStudent() {
        ArrayList<StudentModel> stdList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TBL_STUDENT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        try {
            cursor = db.rawQuery(selectQuery, null);
        } catch (Exception e) {
            e.printStackTrace();
            db.execSQL(selectQuery);
            return new ArrayList<>();
        }
        String name;
        String email;
        String password;
        if (cursor.moveToFirst()) {
            do {
                name = cursor.getString(cursor.getColumnIndex("name"));
                email = cursor.getString(cursor.getColumnIndex("email"));
                password= cursor.getString(cursor.getColumnIndex("password"));
                StudentModel std = new StudentModel( name, email,password);
                stdList.add(std);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return stdList;
    }

    public int updateStudent(StudentModel std) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, std.getName());
        contentValues.put(EMAIL, std.getEmail());
        contentValues.put(PASSWORD, std.getPassword());
        int success = db.update(TBL_STUDENT, contentValues, "email=?", new String[]{String.valueOf(std.getEmail())});
        db.close();
        return success;
    }

    public int deleteStudentByEmail(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        int success = db.delete(TBL_STUDENT, "email=?", new String[]{String.valueOf(email)});
        db.close();
        return success;
    }

    public Boolean insertData(String name, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        StudentModel std =new StudentModel(name,email,password);
        contentValues.put(NAME, std.getName());
        contentValues.put(EMAIL, std.getEmail());
        contentValues.put(PASSWORD,std.getPassword());
        long success = db.insert(TBL_STUDENT, null, contentValues);
        db.close();
        if(success == -1){
            return false;
        }
        else{
            return true;
        }

    }
}
