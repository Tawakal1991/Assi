package com.example.assignment1;

import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;

public class DatabaseManager {
    public static final String DB_NAME = "Friends";
    public static final String DB_TABLE = "Friends";
    public static final int DB_VERSION = 1;
    private static final String CREATE_TABLE = "CREATE TABLE " + DB_TABLE + " (userID INTEGER PRIMARY KEY, FirstName TEXT, LastName TEXT, gender TEXT, age INTEGER, address TEXT, img INTEGER DEFAULT 1);";
    private SQLHelper helper;
    private SQLiteDatabase db;
    private Context context;

    public DatabaseManager(Context c) {
        this.context = c;
        helper = new SQLHelper(c);
        this.db = helper.getWritableDatabase();
    }

    public DatabaseManager openReadable() throws android.database.SQLException {
        helper = new SQLHelper(context);
        db = helper.getReadableDatabase();
        return this;
    }

    public void close() {
        helper.close();
    }

    public boolean addRow( String fn, String ln, String gender, Integer age, String address, String img) {
        synchronized(this.db) {

            ContentValues newProduct = new ContentValues();
            //newProduct.put("id ", id);
            newProduct.put("FirstName", fn);
            newProduct.put("LastName", ln);
            newProduct.put("gender", gender);
            newProduct.put("age", age);
            newProduct.put("address", address);
            newProduct.put("img", img);
            try {
                db.insert(DB_TABLE, null, newProduct);
            } catch (Exception e) {
                Log.e("Error in inserting rows", e.toString());
                e.printStackTrace();
                return false;
            }
            //db.close();
            return true;
        }
    }

    public ArrayList<String> retrieveRows() {
        ArrayList<String> productRows = new ArrayList<String>();
        String[] columns = new String[] {"userID", "FirstName", "LastName", "gender", "age", "address", "img"};
        Cursor cursor = db.query(DB_TABLE, columns, null, null, null, null, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            productRows.add(Integer.toString(cursor.getInt(0)) + ", " + cursor.getString(1) + ", " + cursor.getString(2) + ", " + cursor.getString(3) + ", " + Integer.toString(cursor.getInt(4)) + ", " + cursor.getString(5)+ ", " + cursor.getString(6)  );
            cursor.moveToNext();
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return productRows;
    }

    public void clearRecords()
    {
        db = helper.getWritableDatabase();
        db.delete(DB_TABLE, null, null);
    }

    public class SQLHelper extends SQLiteOpenHelper {
        public SQLHelper (Context c) {
            super(c, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w("Products table", "Upgrading database i.e. dropping table and re-creating it");
            db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
            onCreate(db);
        }
    }
    public void deleteEntries(ArrayList<String> list){

        while(!list.isEmpty()){
            String id = list.get(0);
            id = id.substring(0, id.length() - 1);
            list.remove(0);
            db.execSQL("DELETE FROM Friends WHERE userID='"+id+"'");
        }

    }
    public void updateRecord(Integer id, String fn, String ln, String gender, Integer age, String address, String img){
        ContentValues values = new ContentValues();
        values.put("FirstName", fn);
        values.put("LastName", ln);
        values.put("gender", gender);
        values.put("age", age);
        values.put("address", address);
        values.put("img", img);
        //values.put("image", Integer.parseInt(newInfo[5]));
        db.update(DB_TABLE, values, "userID ="+id+"", null);

    }
    public void deleteFriend(int id){


            db.execSQL("DELETE FROM Friends WHERE userID='"+id+"'");


    }

}

