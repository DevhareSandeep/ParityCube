package demo.paritycube.com.deals.views.menu;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.view.View;

import demo.paritycube.com.deals.R;
import demo.paritycube.com.deals.core.ViewController;
import demo.paritycube.com.deals.databinding.MenuFragmentBinding;
import demo.paritycube.com.deals.views.MasterDetailHandler;
import demo.paritycube.com.deals.views.deals.PopularDealsFragment;
import demo.paritycube.com.deals.views.deals.TopDealsFragment;
import demo.paritycube.com.deals.views.home.HomeFragment;

/**
 * Created by Sandeep Devhare @APAR on 6/21/2017.
 */

public class MenuViewController extends ViewController {
    /* Properties */
    public static boolean POPULARDEAL = false;
    public static boolean TOPDEAL = false;
    private MasterDetailHandler m_masterDetailHandler;

    MenuViewController(MasterDetailHandler masterDetailHandler) {
        m_masterDetailHandler = masterDetailHandler;
    }

  /* Life-cycle methods */

    @Override
    protected void onCreateView(View view) {
        super.onCreateView(view);
        final MenuFragmentBinding binding = DataBindingUtil.bind(view);
        final Context context = getActivity();

        Resources res = context.getResources();
        String[] menus = res.getStringArray(R.array.menu);
        TypedArray menuIcons = res.obtainTypedArray(R.array.menu_icons);

        binding.recyclerView.setAdapter(new MenuRecyclerAdapter(menus, menuIcons,
                this::onMenuClicked));
    }

    private void onMenuClicked(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                break;
            case 1:
                fragment = TopDealsFragment.newInstance(position);
                TOPDEAL = true;
                break;
            case 2:
                fragment = PopularDealsFragment.newInstance(position);
                POPULARDEAL = true;
                break;


        }

        if (fragment != null) {
            m_masterDetailHandler.showFragment(fragment);
            m_masterDetailHandler.hideMaster();
        }
    }
}