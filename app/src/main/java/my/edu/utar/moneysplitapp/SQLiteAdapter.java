package my.edu.utar.moneysplitapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class SQLiteAdapter {

    private static final String MYDATABASE_NAME = "MY_DATABASE";
    private static final String DATABASE_TABLE = "HISTORY_RECORD"; //table
    private static final int MYDATABASE_VERSION = 1;  //version

    public static final String NAME = "Name";
    public static final String INPUT_VALUE = "input";
    public static final String OUTPUT_VALUE = "output";
    public static final String TOTAL_AMOUNT = "totalAmount";
    public static final String NUM_PEOPLE = "numberPeople";



    //sql command to create the table with column
    //ID, Name, input, output
    private static final String SCRIPT_CREATE_DATABASE =
            "create table " + DATABASE_TABLE + " (id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NAME + " text not null, " + INPUT_VALUE + " text, "
            + OUTPUT_VALUE + " text, " + TOTAL_AMOUNT + " text, "
            + NUM_PEOPLE + " text);" ;

    private Context context;
    private SQLiteHelper sqLiteHelper;
    private SQLiteDatabase sqLiteDatabase;

    //constructor
    public SQLiteAdapter(Context c){
        context = c;
    }

    //open database to insert data/to write data
    public SQLiteAdapter openToWrite() throws android.database.SQLException{

        //create a table with MYDATABASE_NAME and the version of MYDATABASE_VERSION
        sqLiteHelper = new SQLiteHelper(context, MYDATABASE_NAME,
                null, MYDATABASE_VERSION);

        //open to write
        sqLiteDatabase = sqLiteHelper.getWritableDatabase();
        return this;
    }

    //open the database to read
    public SQLiteAdapter openToRead() throws android.database.SQLException{

        sqLiteHelper = new SQLiteHelper(context, MYDATABASE_NAME, null, MYDATABASE_VERSION);

        //open to read
        sqLiteDatabase= sqLiteHelper.getReadableDatabase();

        return this;
    }

    //insert data into the Column
    public long insert_2(String content, String content_2, String content_3, String content_4, String content_5){

        ContentValues contentValues = new ContentValues();

        contentValues.put(NAME, content);
        contentValues.put(INPUT_VALUE, content_2);
        contentValues.put(OUTPUT_VALUE, content_3);
        contentValues.put(TOTAL_AMOUNT, content_4);
        contentValues.put(NUM_PEOPLE, content_5);

        //update the table with these values
        return sqLiteDatabase.insert(DATABASE_TABLE, null, contentValues);
    }

    //retrive data from table
    public String queueAll(){

        String[] columns = new String[] {NAME, INPUT_VALUE, OUTPUT_VALUE, TOTAL_AMOUNT,NUM_PEOPLE};
        Cursor cursor = sqLiteDatabase.query(DATABASE_TABLE, columns, null, null,
                null, null, null);

        String result = "";
        int index_CONTENT = cursor.getColumnIndex(NAME);
        int index_CONTENT_2 = cursor.getColumnIndex(INPUT_VALUE);
        int index_CONTENT_3 = cursor.getColumnIndex(OUTPUT_VALUE);
        int index_CONTENT_4 = cursor.getColumnIndex(TOTAL_AMOUNT);
        int index_CONTENT_5 = cursor.getColumnIndex(NUM_PEOPLE);

        for (cursor.moveToFirst(); !(cursor.isAfterLast()); cursor.moveToNext())
        {
            result = result + cursor.getString(index_CONTENT) + ";"
                    + cursor.getString(index_CONTENT_2) + "; "
                    + cursor.getString(index_CONTENT_3) + "; "
                    + cursor.getString(index_CONTENT_4) + "; "
                    + cursor.getString(index_CONTENT_5) + "\n";
        }

        return result;
    }

    public int findRows(){
        //sqLiteDatabase.execSQL("SELECT COUNT(*) FROM " + DATABASE_TABLE + ";");
        String countQuery = "SELECT COUNT(*) FROM " + DATABASE_TABLE + ";" ;
        String[] columns = new String[] {NAME, INPUT_VALUE, OUTPUT_VALUE, TOTAL_AMOUNT,NUM_PEOPLE};
        Cursor cursor = sqLiteDatabase.query(DATABASE_TABLE, columns, null, null,
                null, null, null);
        int count = cursor.getCount();
        return count;

    }

    //retrieve the number of people from user's input from database
    public String[] findNumPeople(){
        String findQuery = "SELECT " + NUM_PEOPLE + " FROM " + DATABASE_TABLE + ";" ;
        String[] columns = new String[] {NAME, INPUT_VALUE, OUTPUT_VALUE, TOTAL_AMOUNT,NUM_PEOPLE};
        Cursor cursor = sqLiteDatabase.query(DATABASE_TABLE, columns, null, null,
                null, null, null);

        String[] result = new String[findRows()];
        int index_CONTENT = cursor.getColumnIndex(NUM_PEOPLE);

        int num=0;
        for (cursor.moveToFirst(); !(cursor.isAfterLast()); cursor.moveToNext())
        {
            result[num] = cursor.getString(index_CONTENT);;
            //result = result + cursor.getString(index_CONTENT) + "\n";
            num++;;
        }

        return result;
    }

    //retrieve the names from database
    public String[] findName(){
        String findQuery = "SELECT " + NAME + " FROM " + DATABASE_TABLE + ";" ;
        String[] columns = new String[] {NAME, INPUT_VALUE, OUTPUT_VALUE, TOTAL_AMOUNT,NUM_PEOPLE};
        Cursor cursor = sqLiteDatabase.query(DATABASE_TABLE, columns, null, null,
                null, null, null);

        String[] result = new String[findRows()];
        int index_CONTENT = cursor.getColumnIndex(NAME);

        int num=0;
        for (cursor.moveToFirst(); !(cursor.isAfterLast()); cursor.moveToNext())
        {
            result[num] = cursor.getString(index_CONTENT);;
            num++;;
        }

        return result;
    }

    //find the output result from database
    public String[] findoutput(){
        String findQuery = "SELECT " + OUTPUT_VALUE + " FROM " + DATABASE_TABLE + ";" ;
        String[] columns = new String[] {NAME, INPUT_VALUE, OUTPUT_VALUE, TOTAL_AMOUNT,NUM_PEOPLE};
        Cursor cursor = sqLiteDatabase.query(DATABASE_TABLE, columns, null, null,
                null, null, null);

        String[] result = new String[findRows()];
        int index_CONTENT = cursor.getColumnIndex(OUTPUT_VALUE);

        int num=0;
        for (cursor.moveToFirst(); !(cursor.isAfterLast()); cursor.moveToNext())
        {
            result[num] = cursor.getString(index_CONTENT);;
            num++;;
        }

        return result;
    }
    //find the user's input value from database
    public String[] findinput(){
        String findQuery = "SELECT " + INPUT_VALUE + " FROM " + DATABASE_TABLE + ";" ;
        String[] columns = new String[] {NAME, INPUT_VALUE, OUTPUT_VALUE, TOTAL_AMOUNT,NUM_PEOPLE};
        Cursor cursor = sqLiteDatabase.query(DATABASE_TABLE, columns, null, null,
                null, null, null);

        String[] result = new String[findRows()];
        int index_CONTENT = cursor.getColumnIndex(INPUT_VALUE);

        int num=0;
        for (cursor.moveToFirst(); !(cursor.isAfterLast()); cursor.moveToNext())
        {
            result[num] = cursor.getString(index_CONTENT);;
            num++;;
        }

        return result;
    }

    //close the database
    public void close(){
        sqLiteHelper.close();
    }

    //delete all the content in the table / delete the table -->so that data will not b repeated
    public int deleteAll(){

        return sqLiteDatabase.delete(DATABASE_TABLE, null, null);
    }


    public class SQLiteHelper extends SQLiteOpenHelper{

        //constructor with 4 parameters
        public SQLiteHelper (@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        //to create database
        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(SCRIPT_CREATE_DATABASE);
        }

        //version control
        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

            //to upgrade the database version, to inform that I have a new version
            sqLiteDatabase.execSQL(SCRIPT_CREATE_DATABASE);

        }
    }
}
