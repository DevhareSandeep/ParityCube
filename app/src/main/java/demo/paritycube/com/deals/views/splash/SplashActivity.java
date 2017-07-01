package demo.paritycube.com.deals.views.splash;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import demo.paritycube.com.deals.R;
import demo.paritycube.com.deals.core.BaseActivity;
import demo.paritycube.com.deals.databinding.SplashActivityBinding;
import demo.paritycube.com.deals.views.login.LoginFragment;


public class SplashActivity extends BaseActivity
{
  private static final String TAG_LOGIN_FRAGMENT = "loginFragment";

  /* Properties */

  private SplashActivityBinding m_binding;

  /* Life-cycle callback */

  @Override
  protected void onCreate (Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    m_binding = DataBindingUtil.setContentView(this, R.layout.splash_activity);
    runInUIWithDelay(() ->
    {

      showLoginFragment(false);

    }, 2000L);

    }



  private void showLoginFragment (boolean animate)
  {
    m_binding.progressBar.setVisibility(View.GONE);
    if (retrieveFragmentByTag(TAG_LOGIN_FRAGMENT) != null)
    {
      return;
    }

    FragmentManager manager = getSupportFragmentManager();
    FragmentTransaction trx = manager.beginTransaction();
    if (animate)
    {
      trx.setCustomAnimations(R.anim.login_show, 0);
    }
    trx.replace(R.id.content, new LoginFragment(), TAG_LOGIN_FRAGMENT);
    trx.commit();
  }

}
