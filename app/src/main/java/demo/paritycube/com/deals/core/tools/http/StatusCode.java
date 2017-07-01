package demo.paritycube.com.deals.core.tools.http;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static demo.paritycube.com.deals.core.tools.http.StatusCode.CODE_CREATED;
import static demo.paritycube.com.deals.core.tools.http.StatusCode.CODE_ERROR;
import static demo.paritycube.com.deals.core.tools.http.StatusCode.CODE_NETWORK_UNAVAILABLE;
import static demo.paritycube.com.deals.core.tools.http.StatusCode.CODE_OK;
import static demo.paritycube.com.deals.core.tools.http.StatusCode.CODE_SERVER_ERROR;
import static demo.paritycube.com.deals.core.tools.http.StatusCode.CODE_TIMEOUT;
import static demo.paritycube.com.deals.core.tools.http.StatusCode.CODE_UNAUTHORIZED;
import static demo.paritycube.com.deals.core.tools.http.StatusCode.CODE_UNKNOWN_HOST;


@Retention(RetentionPolicy.SOURCE)
@IntDef({CODE_NETWORK_UNAVAILABLE, CODE_OK, CODE_CREATED, CODE_ERROR, CODE_TIMEOUT,
    CODE_UNAUTHORIZED, CODE_SERVER_ERROR, CODE_UNKNOWN_HOST})
public @interface StatusCode
{
  int CODE_NETWORK_UNAVAILABLE = 0;
  int CODE_OK = 200;
  int CODE_CREATED = 201;
  int CODE_ERROR = 400;
  int CODE_UNAUTHORIZED = 401;
  int CODE_TIMEOUT = 408;
  int CODE_SERVER_ERROR = 500;
  int CODE_UNKNOWN_HOST = 599;
}
