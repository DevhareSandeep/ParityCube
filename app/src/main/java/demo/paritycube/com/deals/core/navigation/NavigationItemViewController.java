package demo.paritycube.com.deals.core.navigation;

import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;

import demo.paritycube.com.deals.core.ViewController;


public class NavigationItemViewController extends ViewController
{
  /* Properties */

  private NavigationItemDelegate m_navigatioDelegate = new NavigationItemDelegate();

  /* Contract methods */

  void setNavigationHandler (NavigationHandler navigationHandler)
  {
    m_navigatioDelegate.setNavigationHandler(navigationHandler);
  }

  void setNavigationBarHandler (NavigationBarHandler navigationBarHandler)
  {
    m_navigatioDelegate.setNavigationBarHandler(navigationBarHandler);
  }

  /* Navigation methods */

  /**
   * Contract method between {@link NavigationFragment} that defines whether
   * this is a root fragment.
   */
  void setIsRoot (boolean isRoot)
  {
    m_navigatioDelegate.setIsRoot(isRoot);
  }

  /**
   * Whether this is a root fragment from the navigation stack.
   */
  protected boolean isRoot ()
  {
    return m_navigatioDelegate.isRoot();
  }

  /**
   * Retrieves Toolbar instance.
   */
  public Toolbar getToolbar ()
  {
    return m_navigatioDelegate.getToolbar();
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
    m_navigatioDelegate.setRootFragment(fragment, tag);
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
    m_navigatioDelegate.pushFragmentToNavigation(fragment, tag, animated);
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
    m_navigatioDelegate.popFragmentFromNavigation(animated);
  }
}
