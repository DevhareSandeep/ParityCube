package demo.paritycube.com.deals.views.deals.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import demo.paritycube.com.deals.R;
import demo.paritycube.com.deals.databinding.DealItemBinding;
import demo.paritycube.com.deals.misc.widgets.AnimationAdapter;
import demo.paritycube.com.deals.pojo.Datum;
import demo.paritycube.com.deals.views.deals.DealItemsViewHolder;
import demo.paritycube.com.deals.views.deals.DealsClickListener;

/**
 * Created by Sandeep Devhare @APAR on 6/22/2017.
 */

public class DealsRecyclerAdapter extends AnimationAdapter {
    /* Properties */
    List<Datum> dealsList;
    private DealsClickListener m_clickListener;
    private Context mContext;
     /* Initializations */

    public DealsRecyclerAdapter(Context context, DealsClickListener clickListener)
    {
        mContext = context;
        m_clickListener = clickListener;
    }
    public void updateDataSet(List<Datum> dealsOrder)
    {
        dealsList = dealsOrder;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount ()
    {
        return dealsList == null ? 0 : dealsList.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder (ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        DealItemBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.deal_item, parent, false);

        return new DealItemsViewHolder(binding, m_clickListener);
    }

    @Override
    public void onBindViewHolder (RecyclerView.ViewHolder holder, int position)
    {
        super.onBindViewHolder(holder, position);

        final Datum order = dealsList.get(position);
        ((DealItemsViewHolder) holder).onBind(mContext,order);
    }

    @Override
    protected boolean shouldAnimate (int position)
    {
       return true;
    }
}
