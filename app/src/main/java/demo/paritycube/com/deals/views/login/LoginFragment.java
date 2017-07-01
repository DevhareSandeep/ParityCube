package demo.paritycube.com.deals.views.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import demo.paritycube.com.deals.BuildConfig;
import demo.paritycube.com.deals.ParityActivity;
import demo.paritycube.com.deals.ParityApplication;
import demo.paritycube.com.deals.R;
import demo.paritycube.com.deals.core.BaseActivity;
import demo.paritycube.com.deals.databinding.LoginFragmentBinding;
import demo.paritycube.com.deals.db.DBAdapter;
import demo.paritycube.com.deals.misc.widgets.MainNavigationItemFragment;
import demo.paritycube.com.deals.pojo.UserInfo;
import demo.paritycube.com.deals.security.KeyStoreEncryptor;
import demo.paritycube.com.deals.util.AlertDialogFactory;
import demo.paritycube.com.deals.util.DrawableUtil;
import demo.paritycube.com.deals.util.Logger;
import demo.paritycube.com.deals.util.PreferenceUtil;
import demo.paritycube.com.deals.util.Validation;
import demo.paritycube.com.deals.util.ViewHelper;


public class LoginFragment extends MainNavigationItemFragment {
  /* Properties */

    public static final String PREF_USERNAME = "username";
    public static final String PREF_PASSWORD = "password";
    public static final String TAG = LoginFragment.class.getSimpleName();
    Gson gson = new Gson();
    private LoginFragmentBinding m_binding;
    private SharedPreferences m_prefs;
    private String facebook_id, profile_image, full_name, email_id;
    private CallbackManager callbackManager;
    private String userFBDetails;
    private String authToken;
    private DBAdapter dbAdapter;
    private ParityApplication appController;

  /* Life-cycle callbacks */

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        m_prefs = context.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final Context context = getContext();
        dbAdapter = new DBAdapter(context);
        appController = (ParityApplication) getActivity().getApplication();
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        getKeyHash();
    /* Setup view. */
        LoginFragmentBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.login_fragment, container, false);
        m_binding = binding;
        binding.emailEditText.setText(PreferenceUtil.getStringProperty(m_prefs, PREF_USERNAME, ""));

        String password = KeyStoreEncryptor.decrypt(
                PreferenceUtil.getStringProperty(m_prefs, PREF_PASSWORD, ""));
        binding.passwordEditText.setText(password);

        ViewHelper.setBackgroundDrawable(binding.loginButton,
                DrawableUtil.createDrawable(context, R.drawable.field_single_bg));
        if (BuildConfig.DEBUG) {
            binding.emailEditText.setText("test@parity.com");
            binding.passwordEditText.setText("parity");
        }
        binding.loginButton.setOnClickListener(v -> login());
        if (AccessToken.getCurrentAccessToken() != null) {
            RequestData();
        }
        binding.fbloginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logInWithReadPermissions(getActivity(), (Arrays.asList("public_profile", "user_friends", "user_birthday", "user_about_me", "email")));
            }
        });
        binding.fbloginButton.setReadPermissions("email");
        // If using in a fragment
        binding.fbloginButton.setFragment(this);
        binding.fbloginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                facebook_id = profile_image = full_name = email_id = "";

                if (AccessToken.getCurrentAccessToken() != null) {
                    RequestData();
                    Profile profile = Profile.getCurrentProfile();
                    if (profile != null) {
                        authToken = loginResult.getAccessToken().getToken();
                        facebook_id = profile.getId();
                        full_name = profile.getName();
                        profile_image = profile.getProfilePictureUri(400, 400).toString();
                    }


                }

            }

            @Override
            public void onCancel() {
                Logger.info("cancel fb login");
            }

            @Override
            public void onError(FacebookException error) {
                Logger.info("error" + error.getMessage());
            }
        });
        return binding.getRoot();
    }

    private void RequestData() {
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                JSONObject json = response.getJSONObject();
                if (json != null) {
                    Logger.info("JSON DATA" + json.toString());
                    final UserInfo logDetails = gson.fromJson(json.toString(), UserInfo.class);
                    if (logDetails.isValid()) {
                        full_name = logDetails.getName();
                        profile_image = logDetails.getLink();
                        email_id = logDetails.getEmail();
                        facebook_id = String.valueOf(logDetails.getId());
                        appController.setUserDetails(logDetails);
                        dbAdapter.open();
                        dbAdapter.insertUserDetails(facebook_id, logDetails.getName(), logDetails.getEmail(), logDetails.getLink());
                        dbAdapter.close();
                        /*call main activity after succesful login*/
                        callMainActivity();
                    }


                }

            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,email,picture");
        request.setParameters(parameters);
        request.executeAsync();
    }


    private void getKeyHash() {
        PackageInfo info;
        try {
            info = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                //String something = new String(Base64.encodeBytes(md.digest()));
                Logger.info(TAG + "hash key" + something);

            }
        } catch (PackageManager.NameNotFoundException e1) {
            Logger.info("name not found" + e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Logger.info("no such an algorithm" + e.toString());
        } catch (Exception e) {
            Logger.info("exception" + e.toString());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }

    /**
     * Method to login.
     */
    private void login() {
        hideKeyboard();

        final String username = m_binding.emailEditText.getText().toString();
        final String password = m_binding.passwordEditText.getText().toString();

        final boolean rememberPass = m_binding.rememberPasswordCheckBox.isChecked();
        boolean valid = validate(username, password);
        if (!valid) {
            return;
        }
        if (!Validation.isEqual(username, "test@parity.com")
                || !Validation.isEqual(password, "parity")) {
            AlertDialogFactory.buildAlertDialog(getActivity(), 0, R.string.login_failed).show();
            return;
        }
        ProgressDialog progressDialog =
                AlertDialogFactory.buildProgressDialog(getContext(), R.string.please_wait);
        progressDialog.show();
        runInUIWithDelay(() ->
        {
            progressDialog.dismiss();
      /* Store last email address if able. */
            PreferenceUtil.setStringProperty(m_prefs, PREF_USERNAME, username);

            String encryptedPassword = null;
            if (rememberPass) {
                encryptedPassword = KeyStoreEncryptor.encrypt(getContext(), password);
            }
            PreferenceUtil.setStringProperty(m_prefs, PREF_PASSWORD, encryptedPassword);
            UserInfo userStaticInfo = new UserInfo();
            userStaticInfo.setId(String.valueOf(0));
            userStaticInfo.setName("Test");
            userStaticInfo.setEmail(username);
            userStaticInfo.setLink("");
            appController.setUserDetails(userStaticInfo);
            dbAdapter.open();
            dbAdapter.insertUserDetails(userStaticInfo.getId(), userStaticInfo.getName(), userStaticInfo.getEmail(), userStaticInfo.getLink());
            dbAdapter.close();
            callMainActivity();

        }, 2000L);
    }

    private void callMainActivity() {
        BaseActivity activity = (BaseActivity) getActivity();
        Intent intent = new Intent(activity, ParityActivity.class);
        activity.showActivity(intent);
        activity.finish();
    }

    /**
     * Method to validate user name and password.
     */
    private boolean validate(String username, String password) {
        int errorMsgResID = 0;

        if (Validation.isEmpty(username)) {
            errorMsgResID = R.string.login_username_required;
        } else if (Validation.isEmpty(password)) {
            errorMsgResID = R.string.login_password_required;
        } else if (!Validation.isEmail(username)) {
            errorMsgResID = R.string.login_invalid_email;
        }

        if (errorMsgResID != 0) {
            AlertDialogFactory.buildAlertDialog(getContext(), 0, errorMsgResID).show();
        }
        return errorMsgResID == 0;
    }
}