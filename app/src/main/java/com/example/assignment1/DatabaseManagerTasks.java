package com.example.assignment1;

import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;

public class DatabaseManagerTasks {
    public static final String DB_NAME = "Tasks";
    public static final String DB_TABLE = "Tasks";
    public static final int DB_VERSION = 1;
    private static final String CREATE_TABLE = "CREATE TABLE " + DB_TABLE + " (taskID INTEGER PRIMARY KEY, taskName TEXT, location TEXT, status TEXT);";
    private SQLHelper helper;
    private SQLiteDatabase db;
    private Context context;

    public DatabaseManagerTasks(Context c) {
        this.context = c;
        helper = new SQLHelper(c);
        this.db = helper.getWritableDatabase();
    }

    public DatabaseManagerTasks openReadable() throws android.database.SQLException {
        helper = new SQLHelper(context);
        db = helper.getReadableDatabase();
        return this;
    }

    public void close() {
        helper.close();
    }

    public boolean addRow( String tn, String loca, String stat) {
        synchronized(this.db) {

            ContentValues newProduct = new ContentValues();
            //newProduct.put("id ", id);
            newProduct.put("taskName", tn);
            newProduct.put("location", loca);
            newProduct.put("status", stat);
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
        String[] columns = new String[] {"taskID", "taskName", "location", "status"};
        Cursor cursor = db.query(DB_TABLE, columns, null, null, null, null, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            productRows.add(Integer.toString(cursor.getInt(0)) + ", " + cursor.getString(1) + ", " + cursor.getString(2) + ", " + cursor.getString(3)   );
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
            db.execSQL("DELETE FROM Tasks WHERE taskID='"+id+"'");
        }

    }
    public void updateRecord(Integer id, String fn, String ln, String gender){
        ContentValues values = new ContentValues();
        values.put("taskName", fn);
        values.put("location", ln);
        values.put("status", gender);
        //values.put("image", Integer.parseInt(newInfo[5]));
        db.update(DB_TABLE, values, "taskID ="+id+"", null);

    }
    public ArrayList<String> getCompleted() {
        String whereClause = "status='Completed'";
        ArrayList<String> productRows = new ArrayList<String>();
        String[] columns = new String[] {"taskID", "taskName", "location", "status"};
        Cursor cursor = db.query(DB_TABLE, columns, whereClause, null, null, null, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            productRows.add(Integer.toString(cursor.getInt(0)) + ", " + cursor.getString(1) + ", " + cursor.getString(2) + ", " + cursor.getString(3)   );
            cursor.moveToNext();
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return productRows;
    }
    public ArrayList<String> getNonCompleted() {
        String whereClause = "status='Not Completed'";
        ArrayList<String> productRows = new ArrayList<String>();
        String[] columns = new String[] {"taskID", "taskName", "location", "status"};
        Cursor cursor = db.query(DB_TABLE, columns, whereClause, null, null, null, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            productRows.add(Integer.toString(cursor.getInt(0)) + ", " + cursor.getString(1) + ", " + cursor.getString(2) + ", " + cursor.getString(3)   );
            cursor.moveToNext();
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return productRows;
    }
    public void deleteFriend(int id){


        db.execSQL("DELETE FROM Tasks WHERE taskID='"+id+"'");


    }

}

