package demo.paritycube.com.deals.core.tools.http;

import android.text.TextUtils;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import demo.paritycube.com.deals.util.HttpUrlsandKeys;
import demo.paritycube.com.deals.util.Validation;


public class CustomRequest extends JsonObjectRequest {
    private Response.Listener<JSONObject> listener;
    private Response.ErrorListener errorListener;

    public CustomRequest(int method, String url,
                         JSONObject jsonRequest,
                         Response.Listener<JSONObject> listener,
                         Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
        this.listener = listener;
        this.errorListener = errorListener;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Map<String, String> paramsMap = new HashMap<>();
        return paramsMap;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json; charset=utf-8");
        headers.put("Accept", "text/javascript");
        headers.put("X-Desidime-Client", HttpUrlsandKeys.appAuthKey);
        return headers;
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            if ((response != null) && ((response.statusCode == 200) || (response.statusCode == 204))) {
               Log.d("CustomJsonObjectRequest", "parseNetworkResponse called jsonString " + response.statusCode);
                if (!Validation.isJSONValid(jsonString) || (TextUtils.isEmpty(jsonString))) {
                    JSONObject successjson = new JSONObject();
                    successjson.put("result", "success");
                    return Response.success(successjson,
                            HttpHeaderParser.parseCacheHeaders(response));
                } else if (!Validation.isJSONValid(jsonString) && (!TextUtils.isEmpty(jsonString))) {
                    JSONObject successjson = new JSONObject();
                    successjson.put("result", jsonString);
                    return Response.success(successjson,
                            HttpHeaderParser.parseCacheHeaders(response));
                } else {
                    return Response.success(new JSONObject(jsonString),
                            HttpHeaderParser.parseCacheHeaders(response));
                }
            }
            return Response.success(new JSONObject(jsonString),
                    HttpHeaderParser.parseCacheHeaders(response));

        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }

    @Override
    protected VolleyError parseNetworkError(VolleyError volleyError) {
        errorListener.onErrorResponse(volleyError);
        return volleyError;
    }


    @Override
    protected void deliverResponse(JSONObject response) {
       listener.onResponse(response);
    }
}
