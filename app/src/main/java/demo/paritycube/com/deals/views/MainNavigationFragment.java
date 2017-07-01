package demo.paritycube.com.deals.views;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

import demo.paritycube.com.deals.R;
import demo.paritycube.com.deals.core.navigation.NavigationFragment;
import demo.paritycube.com.deals.databinding.MainNavigationFragmentBinding;
import demo.paritycube.com.deals.views.home.HomeFragment;


public class MainNavigationFragment extends NavigationFragment
{
  /* Properties */

  private MainNavigationFragmentBinding m_binding;

  private MasterDetailHandler m_masterDetailHandler;

  /* Life-cycle methods */

  @Override
  public void onAttach (Context context)
  {
    super.onAttach(context);

    Fragment parent = getParentFragment();
    if (   parent != null
        && parent instanceof MasterDetailHandler)
    {
      m_masterDetailHandler = (MasterDetailHandler) parent;
    }
  }

  @Nullable
  @Override
  public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState)
  {
    /* Setup view. */
    MainNavigationFragmentBinding binding =
        DataBindingUtil.inflate(inflater, R.layout.main_navigation_fragment, container, false);
    m_binding = binding;

    Toolbar toolbar = binding.toolbar;
    toolbar.setNavigationIcon(R.drawable.ic_hamburger);
    toolbar.setNavigationOnClickListener(v ->
    {
      final int stackSize = getBackStackSize();
      if (stackSize == 0)
      {
        if (m_masterDetailHandler != null)
        {
          m_masterDetailHandler.showMaster();
        }
      }
      else
      {
        popFragmentFromNavigation(true);
      }
    });

    return binding.getRoot();
  }

  @Override
  public void onViewCreated (View view, Bundle savedInstanceState)
  {
    super.onViewCreated(view, savedInstanceState);
    setRootFragment(new HomeFragment());
  }

  @Override
  public Toolbar getToolbar ()
  {
    return m_binding.toolbar;
  }

  @Override
  protected void onNavigationChanged (Fragment oldFragment, Fragment newFragment)
  {
    super.onNavigationChanged(oldFragment, newFragment);
    setupToolbar();
    showHideToolbarBG(!(newFragment instanceof HomeFragment));
  }

  /* Internal methods */

  private void setupToolbar ()
  {
    int stackSize = getBackStackSize();

    Toolbar toolbar = getToolbar();
    toolbar.setNavigationIcon(stackSize == 0 ? R.drawable.ic_hamburger : R.drawable.ic_back);
  }

  private void showHideToolbarBG (boolean show)
  {
    final View toolbarBG = m_binding.toolbarBg;
    ObjectAnimator animator = (ObjectAnimator) toolbarBG.getTag();
    if (animator != null)
    {
      animator.cancel();
    }
    int visibility = toolbarBG.getVisibility();

    final int toolbarHeight = toolbarBG.getHeight();

    final float fromY = show && visibility == View.GONE ? -toolbarHeight : toolbarBG.getY();
    final float toY = show ? 0 : -toolbarBG.getHeight();

    animator = ObjectAnimator.ofFloat(toolbarBG, "y", fromY, toY);
    animator.setDuration(350);
    animator.setInterpolator(new AccelerateDecelerateInterpolator());
    animator.addListener(new Animator.AnimatorListener()
    {
      @Override
      public void onAnimationStart (Animator animation)
      {
        toolbarBG.setVisibility(View.VISIBLE);
      }

      @Override
      public void onAnimationEnd (Animator animation)
      {
        if (!show)
        {
          toolbarBG.setVisibility(View.GONE);
        }
      }

      @Override
      public void onAnimationCancel (Animator animation) { }

      @Override
      public void onAnimationRepeat (Animator animation) { }
    });
    animator.start();
    toolbarBG.setTag(animator);
  }
}
