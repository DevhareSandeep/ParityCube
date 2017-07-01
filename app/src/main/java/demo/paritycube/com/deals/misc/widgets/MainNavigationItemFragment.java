package demo.paritycube.com.deals.misc.widgets;


import demo.paritycube.com.deals.R;
import demo.paritycube.com.deals.core.navigation.NavigationItemFragment;
import demo.paritycube.com.deals.core.tools.http.StatusCode;
import demo.paritycube.com.deals.util.AlertDialogFactory;

public class MainNavigationItemFragment extends NavigationItemFragment
{
  /* Common methods */

  /**
   * Common method to handle and show error message.
   */
  protected void showErrorMessage (@StatusCode int statusCode, String message)
  {
    if (statusCode == StatusCode.CODE_OK)
    {
      return;
    }

    if (message == null)
    {
      switch (statusCode)
      {
        case StatusCode.CODE_NETWORK_UNAVAILABLE:
          message = getString(R.string.error_no_connection);
          break;
        case StatusCode.CODE_TIMEOUT:
          message = getString(R.string.error_server_unreachable);
          break;
        case StatusCode.CODE_SERVER_ERROR:
          message = getString(R.string.error_server);
          break;
        default:
          message = getString(R.string.error);
          break;
      }
    }

    if (message != null)
    {
      AlertDialogFactory.buildAlertDialog(getContext(), null, message).show();
    }
  }
}
