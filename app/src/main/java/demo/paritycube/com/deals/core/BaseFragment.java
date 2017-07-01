package demo.paritycube.com.deals.core;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class BaseFragment extends BaseControllerFragment
{
  /* Properties */

  private Handler m_internalHandler;

  /* Fragment life-cycle methods */

  @Override
  public void onCreate (Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    m_internalHandler = new Handler();
  }

  @Override
  public void onDestroyView ()
  {
    hideKeyboard();
    super.onDestroyView();
  }

  @Override
  public void onDestroy ()
  {
    if (m_internalHandler != null)
    {
      m_internalHandler.removeCallbacksAndMessages(null);
      m_internalHandler = null;
    }
    super.onDestroy();
  }

  /* Custom callback methods */

  protected void hideKeyboard ()
  {
    View curFocus;
    Activity activity = getActivity();
    if (   activity != null
        && (curFocus = activity.getCurrentFocus()) != null)
    {
      InputMethodManager imm = (InputMethodManager)
          activity.getSystemService(Context.INPUT_METHOD_SERVICE);
      imm.hideSoftInputFromWindow(curFocus.getWindowToken(), 0);
    }
  }

  /**
   * Called when a key was pressed down and not handled by any of the views.
   */
  public boolean onKeyDown (int keyCode, KeyEvent event)
  {
    return false;
  }

  /* Property methods */

  /**
   * Retrieve attached fragment with the specified string tag.
   *
   * @param tag the String fragment tag used when fragment is attached
   * @return the Fragment instance
   */
  protected Fragment retrieveFragmentByTag (String tag)
  {
    return getChildFragmentManager().findFragmentByTag(tag);
  }

  /**
   * Retrieve attached fragment with the specified id.
   *
   * @param id the Integer fragment id used when fragment is attached
   * @return the Fragment instance
   */
  protected Fragment retrieveFragmentByid (int id)
  {
    return getChildFragmentManager().findFragmentById(id);
  }

  /* Thread confinement methods */

  /**
   * Schedules the {@link Runnable} instance to be executed on the main
   * thread.<br/>NOTE: Any Pending or to be scheduled runnable instances will be
   * disregard when activity is destroyed.
   */
  protected void runInUI (Runnable runnable)
  {
    if (m_internalHandler != null)
    {
      m_internalHandler.post(runnable);
    }
  }

  /**
   * Schedules the {@link Runnable} instance that will be executed at the
   * given time delay in milliseconds on the main thread.<br/>NOTE: Any
   * Pending or to  be scheduled runnable instances will be disregard
   * when activity is destroyed.
   */
  protected void runInUIWithDelay (Runnable runnable, long delayInMillis)
  {
    if (m_internalHandler != null)
    {
      m_internalHandler.postDelayed(runnable, delayInMillis);
    }
  }
}
