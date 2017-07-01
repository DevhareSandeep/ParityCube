package demo.paritycube.com.deals.misc.widgets;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.widget.TextView;

import demo.paritycube.com.deals.R;


public class AppToolBar extends Toolbar
{
  /* Properties */

  public CharSequence m_title;

  /* Initializations */

  public AppToolBar(Context context)
  {
    super(context);
    initView(context);
  }

  public AppToolBar(Context context, @Nullable AttributeSet attrs)
  {
    super(context, attrs);
    initView(context);
  }

  public AppToolBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
  {
    super(context, attrs, defStyleAttr);
    initView(context);
  }

  private void initView (Context context)
  {
  }

  @Override
  public void setTitle (CharSequence title)
  {
    m_title = title;
    TextView textView = (TextView) findViewById(R.id.title_text_view);
    textView.setText(title);
  }

  @Override
  public CharSequence getTitle ()
  {
    return m_title;
  }
}
