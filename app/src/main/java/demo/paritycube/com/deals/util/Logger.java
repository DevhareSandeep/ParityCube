package demo.paritycube.com.deals.util;

import android.util.Log;

import demo.paritycube.com.deals.BuildConfig;


public class Logger
{
  private static final String TAG = "Parity";
  private static final boolean EnableDebug = BuildConfig.DEBUG;

  public static void debug (String message)
  {
    if (EnableDebug)
    {
      Log.d(TAG, message);
    }
  }

  public static void info (String message)
  {
    if (EnableDebug)
    {
      Log.i(TAG, message);
    }
  }
}
