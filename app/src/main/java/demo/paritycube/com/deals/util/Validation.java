package demo.paritycube.com.deals.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Patterns;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Validation
{
  /* Utility methods */

  /**
   * Whether the device is connected to a network.
   *
   * @return true if connected to a network, otherwise, false
   */
  public static boolean isNetworkAvailable(Context m_context)
  {
    ConnectivityManager cm = (ConnectivityManager)
            m_context.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo = cm.getActiveNetworkInfo();
    return   networkInfo != null
            && networkInfo.isConnected();
  }
  public static boolean isEmpty (String value)
  {
    return value == null || value.trim().isEmpty();
  }
  public static boolean isEmail (String email)
  {
    return   email != null
            && Patterns.EMAIL_ADDRESS.matcher(email).matches();
  }
  public static boolean isEqual (String x, String y)
  {
    return   x != null
          && y != null
          && x.equalsIgnoreCase(y);
  }

  public static boolean contains (String base, String y)
  {
    return   base != null
          && y != null
          && base.toLowerCase().contains(y.toLowerCase());
  }
  public static boolean isNull(Object obj) {
    if (obj instanceof Long) {
      return isNull((Long) obj);
    } else if (obj instanceof String) {
      return isNull((String) obj);
    } else if (obj == null) {
      return true;
    } else {
      return false;
    }
  }

  public static boolean isJSONValid(String test) {
    try {
      new JSONObject(test);
    } catch (JSONException ex) {
      // edited, to include @Arthur's comment
      // e.g. in case JSONArray is valid as well...
      try {
        new JSONArray(test);
      } catch (JSONException ex1) {
        return false;
      }
    }
    return true;
  }
  public static boolean isNull(Long l) {
    if ((l == null) || l.longValue() == 0) {
      return true;
    } else {
      return false;
    }
  }

  public static boolean isNull(String s) {
    if (s == null) {
      return true;
    }

    s = s.trim();

    //noinspection ObjectEqualsNull
    if ((s.equals(null)) || (s.equals(""))) {
      return true;
    }

    return false;
  }

  public static boolean isNull(Object[] array) {
    if ((array == null) || (array.length == 0)) {
      return true;
    } else {
      return false;
    }
  }
  public static boolean isNotNull(Object obj) {
    return !isNull(obj);
  }

  public static boolean isNotNull(Long l) {
    return !isNull(l);
  }

  public static boolean isNotNull(String s) {
    return !isNull(s);
  }

  public static boolean isNotNull(Object[] array) {
    return !isNull(array);
  }

  public static boolean equals(String s1, String s2) {
    if ((s1 == null) && (s2 == null)) {
      return true;
    } else if ((s1 == null) || (s2 == null)) {
      return false;
    } else {
      return s1.equals(s2);
    }
  }

  public static boolean isDigit(String s) {
    if (isNull(s)) {
      return false;
    }

    char[] c = s.toCharArray();

    for (int i = 0; i < c.length; i++) {
      if (!isDigit(c[i])) {
        return false;
      }
    }

    return true;
  }


  public static boolean isDigit(char c) {
    int x = c;

    if ((x >= 48) && (x <= 57)) {
      return true;
    }

    return false;
  }
  public static String buildMYArrayString(ArrayList<String> list) {
    String listString = "";

    for (String s : list)
    {
      listString += s + "\n";
    }

    return  listString;

  }

}
