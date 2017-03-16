package com.example.nutty.mysqlapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Nutty on 10/2/2560.
 */

public class TodoListDAO {
    private SQLiteDatabase database;
    private DbHelper dbHelper;

    public TodoListDAO (Context context){
        dbHelper = new DbHelper(context);
    }

    public void open(){
        database = dbHelper.getWritableDatabase(); //เปิด database แบบแก้ไขได้
    }

    public void close(){
        dbHelper.close();
    }

    public ArrayList<TodoList> getAlltodoList(){
        ArrayList<TodoList> todoList = new ArrayList<TodoList>();
        Cursor cursor = database.rawQuery("SELECT * FROM tbtodo_list;",null);
        cursor.moveToFirst();

        //add TodoList
        TodoList todoList1;

        while (!cursor.isAfterLast()){
            //todoList.add(cursor.getString(1));
            todoList1= new TodoList();
            todoList1.setTaskid(cursor.getInt(0));
            todoList1.setTaskname(cursor.getString(1));

            todoList.add(todoList1);
            cursor.moveToNext();
        }
        cursor.close();
        return todoList;
    }

    public void add(TodoList todoList){
        //object was threw and recieve it
        TodoList newTodoList = new TodoList();
        newTodoList = todoList;

        ContentValues values = new ContentValues();
        values.put("taskname",newTodoList.getTaskname());
        //put ชื่อฟิลด์ใน database put table name and value to put it

        //to show result message
        this.database.insert("tbtodo_list",null,values);
        Log.d("Todo List Demo :::","Add OK");
    }

    public void update(TodoList todoList){
        TodoList updateTodoList = todoList;
        ContentValues values = new ContentValues();
        values.put("taskname",updateTodoList.getTaskname());
        values.put("taskid",updateTodoList.getTaskid());
        String where = "taskid=" + updateTodoList.getTaskid(); //primarykey

        this.database.update("tbtodo_list",values,where,null);
        Log.d("Todo List Demo:::","UPDATE OK!!!");
    }

    public void delete(TodoList todoList){
        TodoList delTodoList = todoList;
        String sqlText = "DELETE FROM tbtodo_list WHERE taskid=" + delTodoList.getTaskid();
        this.database.execSQL(sqlText);
    }
}
