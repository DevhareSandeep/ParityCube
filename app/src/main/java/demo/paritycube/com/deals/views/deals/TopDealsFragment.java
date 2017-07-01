package demo.paritycube.com.deals.views.deals;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.List;

import demo.paritycube.com.deals.ParityApplication;
import demo.paritycube.com.deals.R;
import demo.paritycube.com.deals.core.tools.http.ConnectionHandler;
import demo.paritycube.com.deals.core.tools.http.CustomRequest;
import demo.paritycube.com.deals.core.tools.http.JsonSerializer;
import demo.paritycube.com.deals.core.tools.http.StatusCode;
import demo.paritycube.com.deals.databinding.TopDealsFragmentBinding;
import demo.paritycube.com.deals.db.DBAdapter;
import demo.paritycube.com.deals.db.DbConstants;
import demo.paritycube.com.deals.misc.widgets.MainNavigationItemFragment;
import demo.paritycube.com.deals.pojo.Datum;
import demo.paritycube.com.deals.pojo.Deals;
import demo.paritycube.com.deals.pojo.SeoSetting;
import demo.paritycube.com.deals.pojo.TopDeals;
import demo.paritycube.com.deals.util.AlertDialogFactory;
import demo.paritycube.com.deals.util.HttpUrlsandKeys;
import demo.paritycube.com.deals.util.Validation;
import demo.paritycube.com.deals.views.deals.adapter.DealsRecyclerAdapter;
import demo.paritycube.com.deals.views.menu.MenuViewController;

public class TopDealsFragment extends MainNavigationItemFragment implements Response.ErrorListener, Response.Listener<JSONObject>, ConnectionHandler {

    /*Properties*/
    public static final String BUNDLE_TYPE = "type";
    TopDealsFragmentBinding m_binding;
    private DealsRecyclerAdapter m_adapter;
    public static final String TAG = TopDealsFragment.class.getSimpleName();
    Gson gson = new Gson();
    private String serverRespStr;
    private ParityApplication appController;
    Context context;
    private List<Datum> mDataset;
    private DBAdapter dbAdapter;
    private int m_type;
    public TopDealsFragment() {
        // Required empty public constructor
    }
    /* Creational */
    public static TopDealsFragment newInstance(int type) {
        TopDealsFragment fragment = new TopDealsFragment();
        Bundle args = new Bundle();
        args.putInt(BUNDLE_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        m_type = getArguments() != null ? getArguments().getInt("type") : 1;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        context = getContext();
        dbAdapter = new DBAdapter(context);
        appController = (ParityApplication) getActivity().getApplicationContext();
    /* Setup view. */
        TopDealsFragmentBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.top_deals_fragment, container, false);
        m_binding = binding;
        binding.recyclerView.setAdapter(m_adapter = new DealsRecyclerAdapter(context, this::onDealClicked));
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        if (MenuViewController.TOPDEAL)
        {
            Toolbar toolbar = getToolbar();
            toolbar.setTitle(R.string.topdeal);
            MenuViewController.TOPDEAL=false;
        }
        topDealSync();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    private void topDealSync() {
        if (Validation.isNetworkAvailable(getActivity())) {
            //String url = HttpUrlsandKeys.buildServiceUrl(HttpUrlsandKeys.topdeals);
            CustomRequest customRequest = new CustomRequest(Request.Method.GET, HttpUrlsandKeys.topdeals, null, this, this);
            ParityApplication.getInstance().addToRequestQueue(customRequest, TAG);

        } else {
            Runnable runnable = () -> {
                try {
                    /*check offline data available or not*/
                    dbAdapter.open();
                   if (dbAdapter.isDataExistInDB(DbConstants.Tables.TOP_DEALS)){
                       /*read available data from db*/
                       mDataset = appController.getListOfTopDeals();
                       if (mDataset!=null)
                       {
                           m_binding.progressBar.setVisibility(View.GONE);
                           switch (200) {
                               case StatusCode.CODE_OK:
                                   setData(mDataset);
                                   break;
                               default:
                                   showErrorMessage(StatusCode.CODE_ERROR, "Data Not found.");
                                   break;
                           }
                       }
                   }else {
                       /*get data from assets*/
                       TopDealsFragment.this.getOfflineData();
                       setData(mDataset);
                   }

                    dbAdapter.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            };
            runInUI(runnable);

        }
    }

    private void setData(List<Datum> list) {
        mDataset = list;
        m_adapter.updateDataSet(mDataset);

    }
    /* this is optional, just to show data if its not loaded by both channel 1. online 2. offline*/
    private int getOfflineData() {
        String json = ParityApplication.loadJsonData(context, "topdealresponse.json");
        // Type collectType = new TypeToken<List<TopDeals>>(){}.getType();
        final TopDeals topDealsAre = JsonSerializer.serialize(json, TopDeals.class);
        final Deals dealsiS = topDealsAre.getDeals();
        final List<Datum> dataIs = dealsiS.getData();
        if (Validation.isNotNull(dealsiS.getData())) {
            int sizeIs = dealsiS.getData().size();
            for (int i = 0; i < sizeIs; i++) {
                mDataset = dealsiS.getData();
            }

        }
        return StatusCode.CODE_OK;
    }

    private void onDealClicked(int position, Datum dealIs) {
        //open  deal details fragment.
        //pushFragmentToNavigation(CallDealDetailFragment.newInstance(dealIs));
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Runnable runnable = () ->
        {
            m_binding.progressBar.setVisibility(View.GONE);
            showErrorMessage(StatusCode.CODE_SERVER_ERROR, "Data not updated, Please try again later." + error.getMessage());
        };
        runInUI(runnable);
    }

    @Override
    public void onResponse(JSONObject response) {
        Runnable runnable = () ->
        {
            m_binding.progressBar.setVisibility(View.GONE);
            if (response != null && response.length() != 0) {
                if (response.length() >= 0) {
                    serverRespStr = String.valueOf(response);
                    final TopDeals respTopDeals = gson.fromJson(serverRespStr, TopDeals.class);
                    final SeoSetting seosetRsp = respTopDeals.getSeoSetting();
                    final Deals dealsRsp = respTopDeals.getDeals();
                    final List<Datum> listOfData = dealsRsp.getData();
                    if (Validation.isNotNull(dealsRsp.getData())) {
                        int sizeOfDeals = listOfData.size();
                        for (int i = 0; i < sizeOfDeals; i++) {
                            mDataset = dealsRsp.getData();
                        }
                        /*Insert data into local db*/
                        insertTopDeals(mDataset);
                        setData(mDataset);

                    }
                }
            } else {
                showErrorMessage(StatusCode.CODE_ERROR, "Data Not found.");
            }
        };
        runInUI(runnable);
    }

    public void insertTopDeals(List<Datum> mDataset) {
        if (mDataset != null) {
            dbAdapter.open();
            for (Datum details : mDataset) {
                dbAdapter.insertTopDeals(details);
            }
            dbAdapter.close();
        }
    }

    @Override
    public void onConnectionFalied() {
        m_binding.progressBar.setVisibility(View.GONE);
        AlertDialogFactory.buildAlertDialog(context, R.string.app_title, R.string.error_no_connection);
    }

    @Override
    public void onServerErrorOccurred() {
        m_binding.progressBar.setVisibility(View.GONE);
        AlertDialogFactory.buildAlertDialog(context, R.string.app_title, R.string.error_server);
    }

}
