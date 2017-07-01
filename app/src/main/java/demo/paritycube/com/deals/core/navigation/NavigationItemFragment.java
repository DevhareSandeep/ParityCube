package demo.paritycube.com.deals.core.navigation;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;

import java.util.List;

import demo.paritycube.com.deals.core.BaseFragment;
import demo.paritycube.com.deals.core.ViewController;


public class NavigationItemFragment extends BaseFragment
{
  /* Constants */

  private static final String BundleStateIsRoot = "isRoot";

  /* Properties */

  private NavigationItemDelegate m_navigationDelegate = new NavigationItemDelegate();

  /* Fragment life-cycle methods */

  @Override
  public void onAttach (Context context)
  {
    super.onAttach(context);

    Fragment parentFragment = getParentFragment();
    if (   parentFragment != null
        && parentFragment instanceof NavigationHandler)
    {
      m_navigationDelegate.setNavigationHandler((NavigationHandler) parentFragment);
    }

    if (   parentFragment != null
        && parentFragment instanceof NavigationBarHandler)
    {
      m_navigationDelegate.setNavigationBarHandler((NavigationBarHandler) parentFragment);
    }
  }

  @Override
  public void onCreate (Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);

    List<ViewController> controllers = getChildControllers();
    if (controllers != null)
    {
      for (ViewController viewController : controllers)
      {
        if (viewController instanceof NavigationItemViewController)
        {
          NavigationItemViewController controller = (NavigationItemViewController) viewController;
          controller.setNavigationHandler(m_navigationDelegate.getNavigationHandler());
          controller.setNavigationBarHandler(m_navigationDelegate.getNavigationBarHandler());
        }
      }
    }

    if (savedInstanceState != null)
    {
      setIsRoot(savedInstanceState.getBoolean(BundleStateIsRoot, false));
    }
  }

  @Override
  public void onSaveInstanceState (Bundle outState)
  {
    super.onSaveInstanceState(outState);
    outState.putBoolean(BundleStateIsRoot, m_navigationDelegate.isRoot());
  }

  /* Navigation methods */

  /**
   * Contract method between {@link NavigationFragment} that defines whether
   * this is a root fragment.
   */
  void setIsRoot (boolean isRoot)
  {
    m_navigationDelegate.setIsRoot(isRoot);

    List<ViewController> childControllers = getChildControllers();
    if (childControllers != null)
    {
      for (ViewController viewController : childControllers)
      {
        if (viewController instanceof NavigationItemViewController)
        {
          NavigationItemViewController controller = (NavigationItemViewController) viewController;
          controller.setIsRoot(isRoot);
        }
      }
    }
  }

  /**
   * Whether this is a root fragment from the navigation stack.
   */
  protected boolean isRoot ()
  {
    return m_navigationDelegate.isRoot();
  }

  /**
   * Retrieves Toolbar instance.
   */
  public Toolbar getToolbar ()
  {
    return m_navigationDelegate.getToolbar();
  }

  /**
   * See {@link #setRootFragment(Fragment, String)}.
   */
  protected void setRootFragment (Fragment fragment)
  {
    setRootFragment(fragment, null);
  }

  /**
   * Replaces the current fragment and clears fragment stack.
   *
   * @param fragment the {@link Fragment} to attach
   * @param tag the String fragment tag. Later can be used to retrieve
   * fragment instance
   */
  protected void setRootFragment (Fragment fragment, String tag)
  {
    m_navigationDelegate.setRootFragment(fragment, tag);
  }

  /**
   * See {@link #pushFragmentToNavigation(Fragment, String, boolean)};
   */
  protected void pushFragmentToNavigation (Fragment fragment)
  {
    pushFragmentToNavigation(fragment, null, true);
  }

  /**
   * Pushes a {@link Fragment} to the navigation stack.
   *
   * @param fragment the {@link Fragment} to push into
   * the navigation stack
   * @param tag the String fragment tag (Optional). Later to be used to retrieve
   * fragment instance
   * @param animated true if animated, otherwise, false
   */
  protected void pushFragmentToNavigation (Fragment fragment, String tag, boolean animated)
  {
    m_navigationDelegate.pushFragmentToNavigation(fragment, tag, animated);
  }

  /**
   * See {@link #popFragmentFromNavigation(boolean)}.
   */
  protected void popFragmentFromNavigation ()
  {
    popFragmentFromNavigation(true);
  }

  /**
   * Pop the {@link Fragment} instance from the
   * navigation stack.
   *
   * @param animated true if animated, otherwise, false
   */
  protected void popFragmentFromNavigation (boolean animated)
  {
    m_navigationDelegate.popFragmentFromNavigation(animated);
  }
}