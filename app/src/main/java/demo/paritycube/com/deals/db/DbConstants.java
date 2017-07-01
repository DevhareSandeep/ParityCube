package demo.paritycube.com.deals.db;

/**
 * Created by Sandeep Devhare @APAR on 6/23/2017.
 */

public interface DbConstants {
        interface Tables {
            String USER_ACCOUNT="user_log_table";
            String TOP_DEALS="top_deal_table";
            String POPULAR_DEALS ="popular_deal_table";
        }
        interface UserAccountCollumn{
            String _ID = "_id";
            String USEREMAIL ="user_email";
            String USERNAME ="user_name";
            String PROFILELINK ="profile_image";

        }
        interface TopDealCollumn{
            String _ID ="_id";
            String TITLE = "title";
            String DESCRIPTION = "description";
            String IMAGEURL = "image_url";

        }
        interface PopularDealCollumn{
            String _ID ="_id";
            String TITLE = "title";
            String DESCRIPTION = "description";
            String IMAGEURL = "image_url";

        }
    String SQL_CREATE_USER_ACCOUNT = "CREATE TABLE " + Tables.USER_ACCOUNT + " ("
            + UserAccountCollumn._ID + " TEXT PRIMARY KEY, "
            + UserAccountCollumn.USERNAME + " TEXT DEFAULT NULL, "
            + UserAccountCollumn.USEREMAIL + " TEXT DEFAULT NULL, "
            + UserAccountCollumn.PROFILELINK + " TEXT DEFAULT NULL, "
            + "UNIQUE (" + UserAccountCollumn._ID + ") ON CONFLICT REPLACE)";

    String SQL_CREATE_TOPDEAL = "CREATE TABLE " + Tables.TOP_DEALS + " ("
            + TopDealCollumn._ID + " TEXT PRIMARY KEY, "
            + TopDealCollumn.TITLE + " TEXT DEFAULT NULL, "
            + TopDealCollumn.DESCRIPTION + " TEXT DEFAULT NULL, "
            + TopDealCollumn.IMAGEURL + " TEXT DEFAULT NULL, "
            + "UNIQUE (" + TopDealCollumn._ID + ") ON CONFLICT REPLACE)";

    String SQL_CREATE_POPULARDEAL = "CREATE TABLE " + Tables.POPULAR_DEALS + " ("
            + PopularDealCollumn._ID + " TEXT PRIMARY KEY, "
            + PopularDealCollumn.TITLE + " TEXT DEFAULT NULL, "
            + PopularDealCollumn.DESCRIPTION + " TEXT DEFAULT NULL, "
            + PopularDealCollumn.IMAGEURL + " TEXT DEFAULT NULL, "
            + "UNIQUE (" + PopularDealCollumn._ID + ") ON CONFLICT REPLACE)";
}
