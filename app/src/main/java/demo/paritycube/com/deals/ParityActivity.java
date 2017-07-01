package demo.paritycube.com.deals;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.KeyEvent;
import android.widget.Toast;

import demo.paritycube.com.deals.core.BaseActivity;
import demo.paritycube.com.deals.util.Logger;
import demo.paritycube.com.deals.util.PermissionUtil;

public class ParityActivity extends BaseActivity
{
  /* Properties */

    private boolean m_dismissOnBack;
    private static final int REQUEST_NETWORK = 1;
    private static String[] PERMISSIONS_NETWORK = {Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE};
  /* Life-cycle methods */

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        checkNetworkPermissions();
        setContentView(R.layout.parity_main);
    }

    @Override
    public void onLowMemory ()
    {
        super.onLowMemory();
    }

    @Override
    protected void onDestroy ()
    {
        super.onDestroy();

    }

    @Override
    public boolean onKeyDown (int keyCode, KeyEvent event)
    {
        ParityFragment eliteFragment = (ParityFragment) retrieveFragmentByID(R.id.parity_fragment);
        boolean handled = eliteFragment.onKeyDown(keyCode, event);
        if (   !handled
                && !m_dismissOnBack)
        {
            handled = true;
            m_dismissOnBack = true;

            Toast.makeText(this, R.string.terminate_notification, Toast.LENGTH_SHORT).show();

            runInUIWithDelay(() -> m_dismissOnBack = false, 2000L);
        }
        return handled || super.onKeyDown(keyCode, event);
    }
    private void checkNetworkPermissions() {
        // Verify that all required permissions have been granted.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            Logger.info("INTERNET permissions has NOT been granted. Requesting permissions.");
            requestInternetPermissions();

        } else {

            // permissions have been granted. Call service request.
            Logger.info(" permissions have already been granted,Call service.");

        }
    }
    private void requestInternetPermissions() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.INTERNET)
                || ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_NETWORK_STATE)) {
            Logger.info("Displaying Internet permission rationale to provide additional context.");

            ActivityCompat
                    .requestPermissions(this, PERMISSIONS_NETWORK,
                            REQUEST_NETWORK);
        } else {
            // permissions have not been granted yet. Request them directly.
            ActivityCompat.requestPermissions(this, PERMISSIONS_NETWORK, REQUEST_NETWORK);
        }
    }
    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (requestCode == REQUEST_NETWORK) {

            // Check if the only required permission has been granted
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //  permission has been granted, preview can be displayed
                Logger.info(" permission has now been granted. Showing preview.");
            } else {
                Logger.info(" permission was NOT granted.");

            }
            // END_INCLUDE(permission_result)

        } else if (requestCode == REQUEST_NETWORK) {
            // We have requested multiple permissions for Internet, so all of them need to be
            // checked.
            if (PermissionUtil.verifyPermissions(grantResults)) {
                // All required permissions have been granted, call network services.

            } else {
                Logger.info(" permission was NOT granted.");
            }

        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
