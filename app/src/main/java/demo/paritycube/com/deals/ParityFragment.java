package demo.paritycube.com.deals;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import demo.paritycube.com.deals.core.BaseFragment;
import demo.paritycube.com.deals.core.navigation.NavigationFragment;
import demo.paritycube.com.deals.databinding.ParityFragmentBinding;
import demo.paritycube.com.deals.util.ViewHelper;
import demo.paritycube.com.deals.views.MainNavigationFragment;
import demo.paritycube.com.deals.views.MasterDetailHandler;
import demo.paritycube.com.deals.views.home.HomeFragment;

/**
 * Created by Sandeep Devhare @APAR on 6/21/2017.
 */

public class ParityFragment extends BaseFragment
        implements MasterDetailHandler
{
  /* Properties */

    private ParityFragmentBinding m_binding;

  /* Life-cycle methods */

    @Nullable
    @Override
    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container,
                              @Nullable Bundle savedInstanceState)
    {
        final Context context = getContext();

    /* Setup view. */
        ParityFragmentBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.parity_fragment, container, false);
        m_binding = binding;

        binding.drawerLayout.setDrawerShadow(
                ViewHelper.getDrawable(context, R.drawable.sliding_pane_shadow),
                Gravity.START);

        return binding.getRoot();
    }

    @Override
    public boolean onKeyDown (int keyCode, KeyEvent event)
    {
        boolean handled = false;

        if (isMasterShowing())
        {
            handled = true;
            hideMaster();
        }

        if (!handled)
        {
            MainNavigationFragment mainNavigationFragment = (MainNavigationFragment)
                    retrieveFragmentByid(R.id.main_navigation_fragment);
            handled = mainNavigationFragment.onKeyDown(keyCode, event);
        }
        return   handled
                || super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean isMasterShowing ()
    {
        return m_binding.drawerLayout.isDrawerOpen(Gravity.START);
    }

    @Override
    public void showMaster ()
    {
        hideKeyboard();
        m_binding.drawerLayout.openDrawer(Gravity.START);
    }

    @Override
    public void hideMaster ()
    {
        m_binding.drawerLayout.closeDrawer(Gravity.START);
    }

    @Override
    public void showFragment (Fragment fragment)
    {
        NavigationFragment navFragment = (NavigationFragment)
                retrieveFragmentByid(R.id.main_navigation_fragment);

        if (fragment instanceof HomeFragment)
        {
            navFragment.setRootFragment(fragment);
        }
        else
        {
            navFragment.setRootFragment(new HomeFragment());
            navFragment.pushFragmentToNavigation(fragment, null, false);
        }
    }
}
