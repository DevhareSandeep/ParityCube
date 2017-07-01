package demo.paritycube.com.deals.views;

import android.support.v4.app.Fragment;

public interface MasterDetailHandler
{
  boolean isMasterShowing();

  void showMaster();

  void hideMaster();

  void showFragment(Fragment fragment);
}
