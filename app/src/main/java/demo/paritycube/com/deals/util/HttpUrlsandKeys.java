package demo.paritycube.com.deals.util;

import android.net.Uri;

import demo.paritycube.com.deals.BuildConfig;

/**
 * Created by Sandeep Devhare @APAR on 6/22/2017.
 */

public class HttpUrlsandKeys {
    public static int  MY_SOCKET_TIMEOUT_MS = 5000;

    public static final String STATUS_CODE = "StatusCode";
    public static final String STATUS_MSG = "StatusMessage";
    public static final String SCHEME = BuildConfig.SCHEME;
    public static final String AUTHORITY = BuildConfig.AUTHORITY;
    public static final String restmobileservice = "/v3/";
    public static final String populardeals = "http://139.162.46.29/v3/deals.json?type=popular&deal_view=true";
    public static final String topdeals = "http://139.162.46.29/v3/deals.json?type=top&deal_view=true";
    public static final String appAuthKey = "0c50c23d1ac0ec18eedee20ea0cdce91ea68a20e9503b2ad77f44dab982034b0";

    public static String buildServiceUrl(String service) {
        Uri.Builder b = Uri.parse(SCHEME +AUTHORITY).buildUpon();
        b.path(restmobileservice + service);
        String url = b.build().toString();
        return url;
    }

}
