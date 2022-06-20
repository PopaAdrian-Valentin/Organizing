package DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import Models.CalendarModel;
import Models.CategoryModel;
import Models.CurrencyModel;
import Models.TranzactionModel;

public class DataBaseHelper extends SQLiteOpenHelper{

    public static final String COLUMN_ID = "ID";
    public static final String CATEGORY_TABLE= "CATEGORY_TABLE";
    public static final String COLUMN_CATEGORY_NAME = "CATEGORY_NAME";
    public static final String CURRENCY_TABLE= "CURRENCY_TABLE";
    public static final String CALENDAR_TABLE= "CALENDAR_TABLE";
    public static final String COLUMN_CURRENCY_NAME = "CURRENCY_NAME";
    public static final String COLUMN_VALUE = "VALUE";
    public static final String TRANZACTION_TABLE="TRANZACTION_TABLE";
    public static final String COLUMN_DESCRIPTION="COLUMN_DESCRIPTION";
    public static final String COLUMN_CATEGORYID="COLUMN_CATEGORYID";
    public static final String COLUMN_CURRENCYID="COLUMN_CURRENCYID";
    public static final String COLUMN_TYPE="COLUMN_TYPE";
    public static final String COLUMN_DATE="COLUMN_DATE";
    public static final String COLUMN_EVENT="COLUMN_EVENT";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "organizerdb.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableCategory= "CREATE TABLE " + CATEGORY_TABLE + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_CATEGORY_NAME + " TEXT)";
        String createTableCurrency= "CREATE TABLE " + CURRENCY_TABLE + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_CURRENCY_NAME + " TEXT, " +
                COLUMN_VALUE + " REAL)";
        String createTableTranzaction= "CREATE TABLE " + TRANZACTION_TABLE + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_VALUE + " REAL, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_CATEGORYID + " TEXT, " +
                COLUMN_CURRENCYID + " TEXT, " +
                COLUMN_TYPE + " TEXT, " +
                COLUMN_DATE + " TEXT, " +
                " FOREIGN KEY (" + COLUMN_CATEGORYID + ") REFERENCES " + CATEGORY_TABLE + " (" + COLUMN_CATEGORY_NAME + "), " +
                " FOREIGN KEY (" + COLUMN_CURRENCYID + ") REFERENCES " + CURRENCY_TABLE + " (" + COLUMN_CURRENCY_NAME + "));";
        String createTableCalendar="CREATE TABLE " + CALENDAR_TABLE + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_EVENT + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT)";
        db.execSQL(createTableCategory);
        db.execSQL(createTableCurrency);
        db.execSQL(createTableTranzaction);
        db.execSQL(createTableCalendar);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addCat(CategoryModel categoryModel){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();

        cv.put(COLUMN_CATEGORY_NAME,categoryModel.getName());

        long insert = db.insert(CATEGORY_TABLE, null, cv);

        if(insert==-1)
        {
            return false;
        }
        else{
            return true;
        }
    }

    public List<CategoryModel> getCategories(){

        List<CategoryModel> returnList=new ArrayList<>();

        String query="SELECT * FROM "+CATEGORY_TABLE;

        SQLiteDatabase db=this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                int categoryID=cursor.getInt(0);
                String categoryName=cursor.getString(1);

                CategoryModel newCategory=new CategoryModel(categoryID,categoryName);
                returnList.add(newCategory);
            } while (cursor.moveToNext());
        }
        else{

        }

        cursor.close();
        db.close();
        return returnList;
    }

    public List<CategoryModel> getCategoriesFrom4(){

        List<CategoryModel> returnList=new ArrayList<>();

        String query="SELECT * FROM "+CATEGORY_TABLE;

        SQLiteDatabase db=this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query,null);
        if(cursor.move(4)){
            do{
                int categoryID=cursor.getInt(0);
                String categoryName=cursor.getString(1);

                CategoryModel newCategory=new CategoryModel(categoryID,categoryName);
                returnList.add(newCategory);
            } while (cursor.moveToNext());
        }
        else{

        }

        cursor.close();
        db.close();
        return returnList;
    }

    public boolean DeleteCat(CategoryModel categoryModel){
        SQLiteDatabase db = this.getWritableDatabase();
        String query="DELETE FROM "+CATEGORY_TABLE+" WHERE "+COLUMN_ID+" = "+categoryModel.getId();

        Cursor cursor=db.rawQuery(query,null);
        if(cursor.moveToFirst())
        {
            cursor.close();
            db.close();
            return true;
        }
        else{
            cursor.close();
            db.close();
            return false;
        }
    }

    public int GetCategoryID(String name)
    {
        int id;

        SQLiteDatabase db=this.getReadableDatabase();
        String query="SELECT * FROM " + CATEGORY_TABLE + " WHERE " + COLUMN_CATEGORY_NAME + " = " + name;
        Cursor cursor=db.rawQuery(query,null);

        if(cursor.moveToFirst())
        {
            id=cursor.getInt(1);
            cursor.close();
            db.close();
            return id;

        }
        else{
            cursor.close();
            db.close();
            return -1;
        }
    }

    public String GetCategoryName(String ID)
    {
        String name;

        SQLiteDatabase db=this.getReadableDatabase();
        String query="SELECT * FROM " + CATEGORY_TABLE + " WHERE " + COLUMN_ID + " = '" + ID + "'";
        Cursor cursor=db.rawQuery(query,null);

        if(cursor.moveToFirst())
        {
            int categoryID=cursor.getInt(0);
            name=cursor.getString(1);
            cursor.close();
            db.close();
            return name;

        }
        else{
            cursor.close();
            db.close();
            return "error";
        }
    }

    public boolean addCurr(CurrencyModel currencyModel){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();

        cv.put(COLUMN_CURRENCY_NAME,currencyModel.getCurrencyname());
        cv.put(COLUMN_VALUE,currencyModel.getValue());
        long insert = db.insert(CURRENCY_TABLE, null, cv);

        if(insert==-1)
        {
            return false;
        }
        else{
            return true;
        }
    }


    public List<CurrencyModel> getCurrencies(){

        List<CurrencyModel> returnList=new ArrayList<>();

        String query="SELECT * FROM "+ CURRENCY_TABLE;

        SQLiteDatabase db=this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                int currencyID=cursor.getInt(0);
                String currencyName=cursor.getString(1);

                CurrencyModel newCategory=new CurrencyModel(currencyID,currencyName);
                returnList.add(newCategory);
            } while (cursor.moveToNext());
        }
        else{

        }

        cursor.close();
        db.close();
        return returnList;
    }

    public boolean DeleteCurr(CategoryModel categoryModel){
        SQLiteDatabase db = this.getWritableDatabase();
        String query="DELETE FROM "+CURRENCY_TABLE+" WHERE "+COLUMN_ID+" = "+categoryModel.getId();

        Cursor cursor=db.rawQuery(query,null);
        if(cursor.moveToFirst())
        {
            cursor.close();
            db.close();
            return true;
        }
        else{
            cursor.close();
            db.close();
            return false;
        }
    }

    public int GetCurrencyID(String name)
    {
        int id;

        SQLiteDatabase db=this.getReadableDatabase();
        String query="SELECT * FROM " + CATEGORY_TABLE + " WHERE " + COLUMN_CATEGORY_NAME + " = " + name;
        Cursor cursor=db.rawQuery(query,null);

        if(cursor.moveToFirst())
        {
            id=cursor.getInt(1);
            cursor.close();
            db.close();
            return id;

        }
        else{
            cursor.close();
            db.close();
            return -1;
        }
    }

    public String GetCurrencyName(int ID)
    {
        String name;

        SQLiteDatabase db=this.getReadableDatabase();
        String query="SELECT * FROM " + CURRENCY_TABLE + " WHERE " + COLUMN_ID + " = " + ID;
        Cursor cursor=db.rawQuery(query,null);

        if(cursor.moveToFirst())
        {
            int currencyID=cursor.getInt(0);
            name=cursor.getString(1);
            cursor.close();
            db.close();
            return name;

        }
        else{
            cursor.close();
            db.close();
            return "error";
        }
    }

    public boolean addTranzaction(TranzactionModel tranzactionModel){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();

        cv.put(COLUMN_VALUE,tranzactionModel.getValue());
        cv.put(COLUMN_DESCRIPTION,tranzactionModel.getDescription());
        cv.put(COLUMN_CATEGORYID,tranzactionModel.getCategory());
        cv.put(COLUMN_CURRENCYID,tranzactionModel.getCurrency());
        cv.put(COLUMN_TYPE,tranzactionModel.getType());
        java.util.Date datenow = new java.util.Date();
        java.sql.Date sqlDate = new Date(datenow.getTime());
        //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        cv.put(COLUMN_DATE, String.valueOf(new Date(sqlDate.getTime())));
        long insert = db.insert(TRANZACTION_TABLE, null, cv);

        if(insert==-1)
        {
            return false;
        }
        else{
            return true;
        }
    }

    public List<TranzactionModel> getTranzactions(){

        List<TranzactionModel> returnList=new ArrayList<>();

        String query="SELECT * FROM "+TRANZACTION_TABLE;

        SQLiteDatabase db=this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToLast()){
            do{
                int tranzactionID=cursor.getInt(0);
                Float value=cursor.getFloat(1);
                String description=cursor.getString(2);
                String category=cursor.getString(3);
                String currency=cursor.getString(4);
                String type=cursor.getString(5);
                String date= cursor.getString(6);

                TranzactionModel tranzactionModel=new TranzactionModel(tranzactionID,value,description,category,currency,type,date);
                returnList.add(tranzactionModel);
            } while (cursor.moveToPrevious());
        }
        else{

        }

        cursor.close();
        db.close();
        return returnList;
    }
    public List<TranzactionModel> getTranzactionsBetweenDates(String date1,String date2){

        List<TranzactionModel> returnList=new ArrayList<>();

        String query="SELECT * FROM " + TRANZACTION_TABLE + " WHERE " + COLUMN_DATE + " BETWEEN  '" + date1 + " 00:00:00' " + "  AND  '" + date2 + " 23:59:59'";
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToLast()){
            do{
                int tranzactionID=cursor.getInt(0);
                Float value=cursor.getFloat(1);
                String description=cursor.getString(2);
                String category=cursor.getString(3);
                String currency=cursor.getString(4);
                String type=cursor.getString(5);
                String date= cursor.getString(6);

                TranzactionModel tranzactionModel=new TranzactionModel(tranzactionID,value,description,category,currency,type,date);
                returnList.add(tranzactionModel);
            } while (cursor.moveToPrevious());
        }
        else{

        }

        cursor.close();
        db.close();
        return returnList;
    }

    public float getValue(){

        List<TranzactionModel> returnList=new ArrayList<>();

        String query="SELECT * FROM "+TRANZACTION_TABLE;

        SQLiteDatabase db=this.getReadableDatabase();
        float totalvalue=0;

        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                int tranzactionID=cursor.getInt(0);
                Float value=cursor.getFloat(1);
                String description=cursor.getString(2);
                String category=cursor.getString(3);
                String currency=cursor.getString(4);
                String type=cursor.getString(5);
                String date= cursor.getString(6);
                totalvalue=totalvalue+value;
            } while (cursor.moveToNext());
        }
        else{

        }

        cursor.close();
        db.close();
        return totalvalue;
    }
    public float getValueCard(){

        List<TranzactionModel> returnList=new ArrayList<>();
        String query="SELECT * FROM "+ TRANZACTION_TABLE + " WHERE " + COLUMN_TYPE + " = 'card' ";

        SQLiteDatabase db=this.getReadableDatabase();
        float totalvalue=0;
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToLast()){
            do{
                int tranzactionID=cursor.getInt(0);
                Float value=cursor.getFloat(1);
                String description=cursor.getString(2);
                String category=cursor.getString(3);
                String currency=cursor.getString(4);
                String type=cursor.getString(5);
                String date= cursor.getString(6);

                totalvalue = totalvalue + value;
            } while (cursor.moveToPrevious());
        }
        else{

        }

        cursor.close();
        db.close();
        return totalvalue;
    }

    public float getValueCash(){

        List<TranzactionModel> returnList=new ArrayList<>();

        String query="SELECT * FROM "+TRANZACTION_TABLE + " WHERE " + COLUMN_TYPE + " = 'cash' ";

        SQLiteDatabase db=this.getReadableDatabase();
        float totalvalue=0;
        String cash="cash";
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                int tranzactionID=cursor.getInt(0);
                Float value=cursor.getFloat(1);
                String description=cursor.getString(2);
                String category=cursor.getString(3);
                String currency=cursor.getString(4);
                String type=cursor.getString(5);
                String date= cursor.getString(6);
                totalvalue = totalvalue + value;
            } while (cursor.moveToNext());
        }
        else{
        }
        cursor.close();
        db.close();
        return totalvalue;
    }

    public boolean addEvent(CalendarModel calendarModel){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();

        cv.put(COLUMN_DATE,calendarModel.getDate());
        cv.put(COLUMN_EVENT,calendarModel.getEvent());
        cv.put(COLUMN_DESCRIPTION,calendarModel.getDescription());
        long insert = db.insert(CALENDAR_TABLE, null, cv);

        if(insert==-1)
        {
            return false;
        }
        else{
            return true;
        }
    }

    public List<CalendarModel> getEvents(String Selecteddate){

        List<CalendarModel> returnList=new ArrayList<>();

        String query="SELECT * FROM "+CALENDAR_TABLE + " WHERE " + COLUMN_DATE + " = '" + Selecteddate + "'";

        SQLiteDatabase db=this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToLast()){
            do{
                int ID=cursor.getInt(0);
                String date= cursor.getString(1);
                String event=cursor.getString(2);
                String description=cursor.getString(3);

                CalendarModel calendarModel=new CalendarModel(date,event,description);
                returnList.add(calendarModel);
            } while (cursor.moveToPrevious());
        }
        else{

        }

        cursor.close();
        db.close();
        return returnList;
    }

    public List<String> getEventsName(String Selecteddate){

        List<String> returnList=new ArrayList<>();

        String query="SELECT * FROM "+CALENDAR_TABLE + " WHERE " + COLUMN_DATE + " = '" + Selecteddate + "'";

        SQLiteDatabase db=this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToLast()){
            do{
                String event=cursor.getString(2);
                returnList.add(event);
            } while (cursor.moveToPrevious());
        }
        else{

        }

        cursor.close();
        db.close();
        return returnList;
    }

    public boolean DeleteEvent(String event){
        SQLiteDatabase db = this.getWritableDatabase();
        String query="DELETE FROM "+ CALENDAR_TABLE + " WHERE " + COLUMN_EVENT + " = '" + event + "'";

        Cursor cursor=db.rawQuery(query,null);
        if(cursor.moveToFirst())
        {
            cursor.close();
            db.close();
            return true;
        }
        else{
            cursor.close();
            db.close();
            return false;
        }
    }

}
