package demo.paritycube.com.deals;

import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import demo.paritycube.com.deals.db.DBAdapter;
import demo.paritycube.com.deals.db.DbConstants;
import demo.paritycube.com.deals.pojo.Datum;
import demo.paritycube.com.deals.pojo.UserInfo;
import demo.paritycube.com.deals.util.HttpUrlsandKeys;
import demo.paritycube.com.deals.util.Logger;

/**
 * Created by Sandeep Devhare @APAR on 6/21/2017.
 */

public class ParityApplication extends Application{

    /*Properties*/
    public static final String TAG = ParityApplication.class.getSimpleName();
    public static Context baseContext;
    private RequestQueue mRequestQueue;
    public static ParityApplication sInstance;
    UserInfo userDetails;
     /* Life-cycle methods */
    @Override
    public void onCreate ()
    {
        super.onCreate();
        sInstance = this;


    }

    @Override
    public void onLowMemory ()
    {
        super.onLowMemory();

    }

    public static synchronized ParityApplication getInstance() {
        return sInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    /*Inside addToReqQueue method we are adding calling add() method upon RequestObject and passing request as paramter to it .*/
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        req.setRetryPolicy(new DefaultRetryPolicy(
                HttpUrlsandKeys.MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES));
        Logger.info("getRequest queue inside my application called" + req.toString() + "tag" + tag);
        getRequestQueue().add(req);

    }

    public <T> void addToRequestQueue(Request<T> req) {
        addToRequestQueue(req, TAG);
    }
    /*The cancelPendingReq is used for cancelling the request.*/
    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }


    public UserInfo getUserDetails() {
        return userDetails = getUserDetailsDataDetails();
    }

    public void setUserDetails(UserInfo userDetails) {
        this.userDetails = userDetails;
    }
    public UserInfo getUserDetailsDataDetails() {
        UserInfo userInfo = null;
        DBAdapter dbAdapter = new DBAdapter(this);
        dbAdapter.open();
        Cursor cursor = dbAdapter.getUserDetailsData();
        try {
            while (cursor != null && cursor.moveToNext()) {
                userInfo = new UserInfo();
                userInfo.setId(cursor.getString((cursor.getColumnIndex(DbConstants.UserAccountCollumn._ID))));
                userInfo.setName(cursor.getString((cursor.getColumnIndex(DbConstants.UserAccountCollumn.USERNAME))));
                userInfo.setEmail(cursor.getString((cursor.getColumnIndex(DbConstants.UserAccountCollumn.USEREMAIL))));
                userInfo.setLink(cursor.getString((cursor.getColumnIndex(DbConstants.UserAccountCollumn.PROFILELINK))));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            cursor.close();
            dbAdapter.close();
        }
        return userInfo;
    }
    public List<Datum> getListOfTopDeals() {
        List<Datum> list = new ArrayList<Datum>();
        DBAdapter dbAdapter = new DBAdapter(this);
        dbAdapter.open();
        Cursor cursor = dbAdapter.getAllTopDeals();
        try {
            while (cursor != null && cursor.moveToNext()) {
                String dTitle = cursor.getString((cursor.getColumnIndex(DbConstants.TopDealCollumn.TITLE)));
                String dDesc = cursor.getString((cursor.getColumnIndex(DbConstants.TopDealCollumn.DESCRIPTION)));
                String dImage = cursor.getString((cursor.getColumnIndex(DbConstants.TopDealCollumn.IMAGEURL)));
                Datum info = new Datum();
                info.setTitle(dTitle);
                info.setDescription(dDesc);
                info.setImage(dImage);
                list.add(info);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            cursor.close();
            dbAdapter.close();
        }
        return list;
    }
    public List<Datum> getListOfPopularDeals() {
        List<Datum> list = new ArrayList<Datum>();
        DBAdapter dbAdapter = new DBAdapter(this);
        dbAdapter.open();
        Cursor cursor = dbAdapter.getAllPopularDeals();
        try {
            while (cursor != null && cursor.moveToNext()) {
                String dTitle = cursor.getString((cursor.getColumnIndex(DbConstants.TopDealCollumn.TITLE)));
                String dDesc = cursor.getString((cursor.getColumnIndex(DbConstants.TopDealCollumn.DESCRIPTION)));
                String dImage = cursor.getString((cursor.getColumnIndex(DbConstants.TopDealCollumn.IMAGEURL)));
                Datum info = new Datum();
                info.setTitle(dTitle);
                info.setDescription(dDesc);
                info.setImage(dImage);
                list.add(info);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            cursor.close();
            dbAdapter.close();
        }
        return list;
    }
    public static String loadJsonData(Context m_context,String fileName){
        InputStream is = null;
        BufferedReader br = null;
        StringBuilder builder =null;
        try
        {
            is = m_context.getAssets().open(fileName);
            br = new BufferedReader(new InputStreamReader(is));

            builder = new StringBuilder();

            String line;
            while ((line = br.readLine()) != null)
            {
                builder.append(line);
            }


        } catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (br != null)
            {
                try
                {
                    br.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }

            if (is != null)
            {
                try
                {
                    is.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return builder.toString();
    }
}
