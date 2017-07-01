package demo.paritycube.com.deals.misc.widgets;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.annotation.CallSuper;
import android.support.v7.widget.RecyclerView;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

public abstract class AnimationAdapter extends RecyclerView.Adapter
{
  private boolean m_animate;
  private Interpolator m_interpolator = new LinearInterpolator();

  public void setAnimate (boolean animate)
  {
    m_animate = animate;
  }

  protected boolean shouldAnimate (int position)
  {
    return m_animate;
  }

  @Override
  @CallSuper
  public void onBindViewHolder (RecyclerView.ViewHolder holder, int position)
  {
    if (shouldAnimate(position))
    {
      animate(holder, position);
    }
  }

  /* Internal methods */

  private void animate (RecyclerView.ViewHolder holder, int position)
  {
    ObjectAnimator alpha = ObjectAnimator.ofFloat(holder.itemView, "alpha", 0.0f, 1f);
    ObjectAnimator scaleX = ObjectAnimator.ofFloat(holder.itemView, "scaleX", 0.7f, 1f);

    AnimatorSet set = new AnimatorSet();
    set.setInterpolator(m_interpolator);
    set.setDuration(300);
    set.playTogether(alpha, scaleX);
    set.start();
  }
}
