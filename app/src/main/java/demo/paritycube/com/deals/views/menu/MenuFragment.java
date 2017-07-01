package demo.paritycube.com.deals.views.menu;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;

import java.util.ArrayList;
import java.util.List;

import demo.paritycube.com.deals.ParityApplication;
import demo.paritycube.com.deals.R;
import demo.paritycube.com.deals.core.BaseFragment;
import demo.paritycube.com.deals.core.ViewController;
import demo.paritycube.com.deals.databinding.MenuFragmentBinding;
import demo.paritycube.com.deals.db.DBAdapter;
import demo.paritycube.com.deals.pojo.UserInfo;
import demo.paritycube.com.deals.util.AlertDialogFactory;
import demo.paritycube.com.deals.views.MasterDetailHandler;

public class MenuFragment extends BaseFragment {
  /* Properties */

    private MasterDetailHandler m_masterDetailHandler;
    private DBAdapter dbAdapter;
    private ParityApplication appController;
    private String displayNameIs;
    private MenuFragmentBinding mBinding;
  /* Life-cycle methods */

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Fragment parent = getParentFragment();
        if (parent != null
                && parent instanceof MasterDetailHandler) {
            m_masterDetailHandler = (MasterDetailHandler) parent;
        }

    }

    @Override
    public List<ViewController> onCreateChildViewControllers(Activity activity) {

        List<ViewController> viewControllers = new ArrayList<>();
        viewControllers.add(new MenuViewController(m_masterDetailHandler));

        return viewControllers;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final Context context = getContext();
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        dbAdapter = new DBAdapter(context);
        appController = (ParityApplication) getActivity().getApplication();
        UserInfo userInfo = appController.getUserDetailsDataDetails();
        /* Setup view. */
        MenuFragmentBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.menu_fragment, container, false);
        mBinding = binding;
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        try{
            if (userInfo.isValid()) {
                displayNameIs = userInfo.getName();
            } else {
                displayNameIs = "Test@parity.com";
            }
        }catch (NullPointerException npe)
        {
            npe.printStackTrace();
        }
        binding.userTextView.setText(displayNameIs);
        binding.logoutButton.setOnClickListener(this::logout);

        return binding.getRoot();
    }
    private void logout(View view) {
        AlertDialogFactory.buildAlertDialog(getActivity(),
                0,
                R.string.login_sign_out_confirmation,
                R.string.yes,
                R.string.no, (dialog, which) ->
                {
                    if (which == DialogInterface.BUTTON_POSITIVE) {
                        if (AccessToken.getCurrentAccessToken() != null) {
                            LoginManager.getInstance().logOut();
                            getActivity().finish();
                            dbAdapter.open();
                            dbAdapter.removeUserAccount();
                            dbAdapter.close();

                        }else {
                            getActivity().finish();
                            dbAdapter.open();
                            dbAdapter.removeUserAccount();
                            dbAdapter.close();
                        }

                    }
                    dialog.dismiss();
                }).show();
    }

}
