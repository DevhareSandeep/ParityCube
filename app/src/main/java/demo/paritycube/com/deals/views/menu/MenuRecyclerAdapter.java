package demo.paritycube.com.deals.views.menu;

import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import demo.paritycube.com.deals.R;
import demo.paritycube.com.deals.databinding.MenuItemBinding;


class MenuRecyclerAdapter extends RecyclerView.Adapter
{
  /* Properties */

  private String[] m_menus;
  private TypedArray m_menuIcons;
  private MenuClickListener m_clickListener;

  /* Initializations */

  MenuRecyclerAdapter(String[] menus, TypedArray menuIcons, MenuClickListener clickListener)
  {
    m_clickListener = clickListener;
    m_menus = menus;
    m_menuIcons = menuIcons;
  }

  @Override
  public int getItemCount ()
  {
    return m_menus.length;
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder (ViewGroup parent, int viewType)
  {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    MenuItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.menu_item, parent, false);
    return new MenuItemViewHolder(binding, m_clickListener);
  }

  @Override
  public void onBindViewHolder (RecyclerView.ViewHolder holder, int position)
  {
    String title = m_menus[position];
    Drawable icon = m_menuIcons.getDrawable(position);

    ((MenuItemViewHolder) holder).onBind(icon, title);
  }
}
