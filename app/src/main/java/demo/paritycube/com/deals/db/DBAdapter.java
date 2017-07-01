package demo.paritycube.com.deals.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import demo.paritycube.com.deals.ParityApplication;
import demo.paritycube.com.deals.pojo.Datum;

/**
 * Created by Sandeep Devhare @APAR on 6/23/2017.
 */

public class DBAdapter {
    private static final String DATABASE_NAME = "Parity_DB";

    private static final String TAG = "DBAdapter";
    private static final int DATABASE_VERSION = 1;
    private final Context context;
    private DatabaseHelper dBHelper;
    private SQLiteDatabase db;
    ParityApplication appController;

    public DBAdapter(Context ctx) {
        this.context = ctx;
        dBHelper = new DatabaseHelper(context);
        appController = (ParityApplication) context.getApplicationContext();

    }



    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DbConstants.SQL_CREATE_USER_ACCOUNT);
            db.execSQL(DbConstants.SQL_CREATE_TOPDEAL);
            db.execSQL(DbConstants.SQL_CREATE_POPULARDEAL);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + DbConstants.Tables.USER_ACCOUNT);
            db.execSQL("DROP TABLE IF EXISTS " + DbConstants.Tables.TOP_DEALS);
            db.execSQL("DROP TABLE IF EXISTS " + DbConstants.Tables.POPULAR_DEALS);
        }
    }

    public DBAdapter open() throws SQLException {
        db = dBHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dBHelper.close();
    }

    public long insertUserDetails(String userId, String username, String useremail, String userprofileimage) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(DbConstants.UserAccountCollumn._ID, userId);
        initialValues.put(DbConstants.UserAccountCollumn.USERNAME, username);
        initialValues.put(DbConstants.UserAccountCollumn.USEREMAIL, useremail);
        initialValues.put(DbConstants.UserAccountCollumn.PROFILELINK, userprofileimage);
        return db.insert(DbConstants.Tables.USER_ACCOUNT, null, initialValues);
    }

    public long insertTopDeals(Datum dealData) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(DbConstants.TopDealCollumn._ID, dealData.getId());
        initialValues.put(DbConstants.TopDealCollumn.TITLE, dealData.getTitle());
        initialValues.put(DbConstants.TopDealCollumn.DESCRIPTION, dealData.getDescription());
        initialValues.put(DbConstants.TopDealCollumn.IMAGEURL, dealData.getImage());
        return db.insert(DbConstants.Tables.TOP_DEALS, null, initialValues);
    }
    public long insertPopularDeals(Datum dealData) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(DbConstants.PopularDealCollumn._ID, dealData.getId());
        initialValues.put(DbConstants.PopularDealCollumn.TITLE, dealData.getTitle());
        initialValues.put(DbConstants.PopularDealCollumn.DESCRIPTION, dealData.getDescription());
        initialValues.put(DbConstants.PopularDealCollumn.IMAGEURL, dealData.getImage());
        return db.insert(DbConstants.Tables.POPULAR_DEALS, null, initialValues);
    }

    public Cursor getAllUserAccountData() {
        return db.query(DbConstants.Tables.USER_ACCOUNT, new String[]{
                DbConstants.UserAccountCollumn._ID, DbConstants.UserAccountCollumn.USERNAME, DbConstants.UserAccountCollumn.USEREMAIL, DbConstants.UserAccountCollumn.PROFILELINK}, null, null, null, null, null);
    }

    public Cursor getUserDetailsData() {
        String selectQuery = " select * from " + DbConstants.Tables.USER_ACCOUNT;
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor;
    }
    public Cursor getAllTopDeals() {
        String selectQuery = " select * from " + DbConstants.Tables.TOP_DEALS;
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor;
    }
    public Cursor getAllPopularDeals() {
        String selectQuery = " select * from " + DbConstants.Tables.POPULAR_DEALS;
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor;
    }

    public boolean updateTopDealsByDealId(int dealId) {
        String[] whereClauseArgument = new String[1];
        whereClauseArgument[0] = "" + dealId;
        ContentValues initialValues = new ContentValues();
        long id = db.update(DbConstants.Tables.TOP_DEALS, initialValues, DbConstants.TopDealCollumn._ID + "= ? ", whereClauseArgument);
        if (id <= 0) {
            return false;
        } else {
            return true;
        }
    }
    public boolean deleteTopDealsByDealId(int dealId) {
        String[] whereClauseArgument = new String[1];
        whereClauseArgument[0] = "" + dealId;
        long id = db.delete(DbConstants.Tables.TOP_DEALS, DbConstants.TopDealCollumn._ID + " = ?", whereClauseArgument);
        if (id <= 0) {
            return false;
        } else {
            return true;
        }
    }
    public boolean checkDataExistInDB(String TableName, String dbfield, String fieldValue) {
        String Query = "Select * from " + TableName + " where " + dbfield + " = '" + fieldValue + "'";
        Cursor cursor = db.rawQuery(Query, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }
    public boolean isDataExistInDB(String TableName) {
        Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + TableName + "'", null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }
    public boolean removeUserAccount() {
        long id = db.delete(DbConstants.Tables.USER_ACCOUNT, null, null);
        if (id <= 0) {
            return false;
        } else {
            return true;
        }
    }
}
