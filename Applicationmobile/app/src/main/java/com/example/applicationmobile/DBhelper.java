package com.example.applicationmobile;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBhelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "All_Trip_Date";
    public  static final int DATABASE_VERSION = 1 ;

    public DBhelper(Context context) {
        super( context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table IF NOT EXISTS TripDetails(_id INTEGER primary key autoincrement, trip_name TEXT , trip_destination TEXT , trip_date TEXT , trip_risk TEXT , trip_Description TEXT )");

        DB.execSQL("create Table IF NOT EXISTS TripEX(EX_id INTEGER primary key autoincrement, EX_Type TEXT , EX_Expense  TEXT , EX_Time TEXT , EXTRIP_id INTEGER , FOREIGN KEY (EXTRIP_id) REFERENCES TripDetails(_id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop Table if exists TripDetails");

        DB.execSQL("drop Table if exists TripEX");
        onCreate(DB);
    }

    public boolean insertstripdata(String trip_name , String trip_destination ,String trip_date ,String trip_risk ,String trip_Description)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("trip_name" , trip_name);
        contentValues.put("trip_destination" , trip_destination);
        contentValues.put("trip_date" , trip_date);
        contentValues.put("trip_risk" , trip_risk);
        contentValues.put("trip_Description" , trip_Description);
        long result=DB.insert("TripDetails" , null ,contentValues);
        if (result==-1){
            return false;
        }else {
            return true;
        }
    }

    public boolean insertEXdata(String EX_Type , String EX_Expense ,String EX_Time ,String EXTRIP_id )
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("EX_Type" , EX_Type);
        contentValues.put("EX_Expense" , EX_Expense);
        contentValues.put("EX_Time" , EX_Time);
        contentValues.put("EXTRIP_id" , EXTRIP_id);
        long result=DB.insert("TripEX" , null ,contentValues);
        if (result==-1){
            return false;
        }else {
            return true;
        }
    }

    public boolean updatestripdata(String _id, String trip_name , String trip_destination ,String trip_date ,String trip_risk ,String trip_Description)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("trip_name" , trip_name);
        contentValues.put("trip_destination" , trip_destination);
        contentValues.put("trip_date" , trip_date);
        contentValues.put("trip_risk" , trip_risk);
        contentValues.put("trip_Description" , trip_Description);
        Cursor cursor = DB.rawQuery("Select * from TripDetails where _id = ?", new String[] {_id});
        if (cursor.getCount()>0) {
            long result = DB.update("TripDetails",
                    contentValues,
                    "_id=?",
                    new String[]{_id});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }else {
            return false;
        }
    }

    public boolean deletestripdata(String _id)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from TripDetails where _id = ?", new String[] {_id});
        if (cursor.getCount()>0) {
            long result = DB.delete("TripDetails",
                    "_id=?",
                    new String[]{_id});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }else {
            return false;
        }
    }

    public boolean deletesEXtripdata(String EX_id)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from TripEX where EX_id = ?", new String[] {EX_id});
        if (cursor.getCount()>0) {
            long result = DB.delete("TripEX",
                    "EX_id=?",
                    new String[]{EX_id});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }else {
            return false;
        }
    }

    public void deleteAllData(){
        SQLiteDatabase DB = this.getWritableDatabase();
        DB.execSQL("DELETE FROM TripDetails");
        DB.execSQL("DELETE FROM TripEX");
    }

    public Cursor getdata(){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from TripDetails", null);
        return cursor;
    }

    public Cursor getEXdata(String EXTRIP_id){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from TripEX where EXTRIP_id=?", new String[] {EXTRIP_id});
        return cursor;
    }

    public Cursor getlistEXdata(){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from TripEX", null);
        return cursor;
    }

    public trip getTripbyID(String tripid) {
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("Select * from TripDetails where _id=" +"'"+tripid+"'",null);
        if (cursor.moveToFirst()){
            return new trip(
              cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5)
                    );
        }
        return null;
    }

}
