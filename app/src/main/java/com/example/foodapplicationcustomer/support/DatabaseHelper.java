package com.example.foodapplicationcustomer.support;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {


    public DatabaseHelper(@Nullable Context context) {
        super(context, "FoodDB.db", null, 1);

    }


    public boolean addOrder(OrderModel ordermodel){

        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("productID", ordermodel.getProductID());
        cv.put("productname", ordermodel.getProductname());
        cv.put("price",ordermodel.getPrice());
        cv.put("quantity",ordermodel.getQuantity());
        cv.put("image",ordermodel.getImageLink());

        long insert = db.insert("Ordertable", null, cv);
        if(insert ==-1){
            return false;

        }else{
            return true;
        }

    }
    /*public List<OrderModel> getCarts(){


    }*/

    @Override
    public void onCreate(SQLiteDatabase DB) {

        DB.execSQL("CREATE TABLE Ordertable(orderID INTEGER primary key autoincrement,productID TEXT,productname TEXT,quantity TEXT,price TEXT)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {

        DB.execSQL("drop Table if exists Ordertable");

    }

    public void deleteCart(){
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DELETE FROM Ordertable");

    }


    public void alterCart(){
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("ALTER TABLE Ordertable ADD image VARCHAR");

    }


    public void deleteOrder(String productID){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Ordertable", "productID=?", new String[]{productID});
        db.close();

    }

    public List<OrderModel> getCart(){
        SQLiteDatabase db = this.getReadableDatabase();
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        String[] sqlSelect = {"productID","productname","quantity","price","image"};
        String sqltable = "Ordertable";

        queryBuilder.setTables(sqltable);
        Cursor cursor = queryBuilder.query(db,sqlSelect,null, null,null,null,null);

        final List<OrderModel> selectresult = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                selectresult.add(new OrderModel(cursor.getString(cursor.getColumnIndex("productID")),
                        cursor.getString(cursor.getColumnIndex("productname")),
                        cursor.getString(cursor.getColumnIndex("quantity")),
                        cursor.getString(cursor.getColumnIndex("price")),
                        cursor.getString(cursor.getColumnIndex("image"))
                ));

            }while(cursor.moveToNext());

        }

        return selectresult;
    }



}
