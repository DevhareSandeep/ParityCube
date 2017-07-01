package demo.paritycube.com.deals.views.menu;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;

import demo.paritycube.com.deals.R;
import demo.paritycube.com.deals.databinding.MenuItemBinding;


class MenuItemViewHolder extends RecyclerView.ViewHolder
{
  /* Properties */

  private MenuItemBinding m_binding;

  /* Initializations */

  MenuItemViewHolder(MenuItemBinding binding, MenuClickListener clickListener)
  {
    super(binding.getRoot());
    m_binding = binding;

    itemView.setOnClickListener(v -> clickListener.onMenuClicked(getAdapterPosition()));
  }

  void onBind (Drawable icon, String title)
  {
    m_binding.imageView.setColorFilter(
        ContextCompat.getColor(itemView.getContext(), R.color.colorPrimary),
        PorterDuff.Mode.SRC_ATOP);
    m_binding.imageView.setImageDrawable(icon);
    m_binding.titleTextView.setText(title);
  }
}
