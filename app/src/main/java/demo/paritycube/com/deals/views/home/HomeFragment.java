package demo.paritycube.com.deals.views.home;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import demo.paritycube.com.deals.R;
import demo.paritycube.com.deals.databinding.HomeFragmentBinding;
import demo.paritycube.com.deals.db.DBAdapter;
import demo.paritycube.com.deals.misc.widgets.MainNavigationItemFragment;
import demo.paritycube.com.deals.util.CustomViewPager;
import demo.paritycube.com.deals.views.deals.PopularDealsFragment;
import demo.paritycube.com.deals.views.deals.TopDealsFragment;
import demo.paritycube.com.deals.views.deals.adapter.TabsFragmentAdapter;


public class HomeFragment extends MainNavigationItemFragment implements TabLayout.OnTabSelectedListener {
  /* Properties */

    public String Tag = "Tabbed Fragment";
    CustomViewPager viewPager;
    TabsFragmentAdapter fragmentAdapter;
    HomeFragmentBinding m_binding;
    DBAdapter dbAdapter;

    //Mandatory Constructor
    public HomeFragment() {
    }

    /* Life-cycle methods */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final Context mCotext = getContext();

    /* Setup view. */
        HomeFragmentBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false);
        m_binding = binding;
        /*Inititalisation*/

        Toolbar toolbar = getToolbar();
        toolbar.setTitle("Deals");

        fragmentAdapter = new TabsFragmentAdapter(getFragmentManager());
        fragmentAdapter.addFragment(new TopDealsFragment(), "Top Deal");
        fragmentAdapter.addFragment(new PopularDealsFragment(), "Popular Deal");
        binding.viewpager.setAdapter(fragmentAdapter);
        binding.viewpager.animate();
        binding.viewpager.setOffscreenPageLimit(2);
        binding.tabs.setupWithViewPager(binding.viewpager);
        binding.tabs.setTabGravity(TabLayout.GRAVITY_FILL);
        binding.tabs.setTabTextColors(
                ContextCompat.getColor(getActivity(), R.color.white),
                ContextCompat.getColor(getActivity(), R.color.white)
        );
        binding.tabs.setOnTabSelectedListener(this);
        setupTabIcons();
        binding.viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                binding.viewpager.setCurrentItem(position,true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });




        return binding.getRoot();

    }

    private void setupTabIcons() {
        TextView topDealTab = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        topDealTab.setCompoundDrawablesWithIntrinsicBounds(R.drawable.menu_allocate, 0, 0, 0);
        m_binding.tabs.getTabAt(0).setCustomView(topDealTab);
        topDealTab.setText("Top Deals");
        TextView popularDealTab = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        popularDealTab.setCompoundDrawablesWithIntrinsicBounds(R.drawable.menu_dispatch_empty, 0, 0, 0);
        m_binding.tabs.getTabAt(1).setCustomView(popularDealTab);
        popularDealTab.setText("Popular Deals");
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        m_binding.viewpager.setCurrentItem(tab.getPosition(),true);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
