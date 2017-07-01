package demo.paritycube.com.deals.views.deals;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;

import com.squareup.picasso.Picasso;

import demo.paritycube.com.deals.R;
import demo.paritycube.com.deals.databinding.DealItemBinding;
import demo.paritycube.com.deals.pojo.Datum;
import demo.paritycube.com.deals.util.RoundedTransformation;
import demo.paritycube.com.deals.util.Validation;

/**
 * Created by Sandeep Devhare @APAR on 6/22/2017.
 */

public class DealItemsViewHolder extends RecyclerView.ViewHolder {
    /* Properties */
    private Datum m_data;
    private DealItemBinding m_binding;
    private String descriptioToText;

    /* Initializations */
    public DealItemsViewHolder(DealItemBinding binding, DealsClickListener clickListener) {
        super(binding.getRoot());
        m_binding = binding;

        itemView.setOnClickListener(v -> clickListener.onDealClicked(getAdapterPosition(), m_data));
    }

    public void onBind(Context mContext, Datum deal) {
        m_data = deal;
        Resources res = itemView.getResources();
        m_binding.dealTitle.setText(deal.getTitle());
       // descriptioToText = Html.fromHtml(deal.getDescription()).toString();
        m_binding.dealDescription.setText(deal.getDescription());
        if ( m_binding.imageView != null)
        {

            if (Validation.isEmpty(deal.getImage()) || Validation.isNotNull(deal.getImage()))
            {
                m_binding.imageView.setImageResource(R.drawable.placeholder);
            }else {
                Context context =  m_binding.imageView.getContext();
                DisplayMetrics metrics = context.getResources().getDisplayMetrics();
                int size = (int) (metrics.density * 55);
                Picasso.with( m_binding.imageView.getContext())
                        .load(deal.getImage())
                        .resize(size, size)
                        .transform(new RoundedTransformation(200, 0))
                        .fit()
                        .onlyScaleDown()
                        .centerInside()
                        .placeholder(R.drawable.placeholder)
                        .into(m_binding.imageView);
            }

        }
      /*  new DealImageLoader(mContext, m_binding.imageView, deal.getImage())
                .execute();*/


    }
}
