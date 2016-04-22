package com.nomad.cuppcebe.fragments;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.gson.Gson;
import com.nomad.cuppcebe.CuppCebeApplication;
import com.nomad.cuppcebe.MainActivity;
import com.nomad.cuppcebe.R;
import com.nomad.cuppcebe.interfaces.OnMainFragmentListener;
import com.nomad.cuppcebe.model.UserResponse;
import com.nomad.cuppcebe.restful.RestClient;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;

/**
 * Created by kaplanfatt on 19/09/15.
 */
@EFragment(R.layout.fragment_login)
public class LoginFragment extends BaseFragment {

    private OnMainFragmentListener mainFragmentListener;

    CallbackManager callbackManager;

    AccessToken accessToken;

    AccessTokenTracker accessTokenTracker;

    @RestService
    protected RestClient restClient;

    @ViewById(R.id.loginText)
    TextView loginText;

    @ViewById(R.id.loginUserNameEditText)
    EditText loginUserNameEditText;

    @ViewById(R.id.loginPasswordEdittext)
    EditText loginPasswordEdittext;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mainFragmentListener = (OnMainFragmentListener) activity;
        ((MainActivity) getActivity()).showOrHideBottomContainer(0);

        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(getFramentTag(), "Successfull Login");
                accessToken = loginResult.getAccessToken();
                getPreferences().edit().putString(CuppCebeApplication.getInstance().ACCESS_TOKEN_PREF_KEY, accessToken.getToken().toString()).commit();
                getPreferences().edit().putString(CuppCebeApplication.getInstance().FACEBOOK_USER_ID, accessToken.getUserId()).commit();

            }

            @Override
            public void onCancel() {
                Log.d(getFramentTag(), "Cancelled Login");

            }

            @Override
            public void onError(FacebookException e) {
                Log.d(getFramentTag(), "Error with exception: " + e.toString());
            }
        });


        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {
                Log.d(getFramentTag(), "onCurrentAccessTokenChanged");

            }
        };
    }


    @Background
    public void login(String username, String password) {

        showCuppCebeProgress();
        UserResponse userResponse = restClient.login(username, password, 0, 0);
        if (userResponse != null) {

            System.out.println("afafaf");

            getPreferences().edit().putString(CuppCebeApplication.getInstance().CUPPCEBE_TOKEN_KEY, userResponse.TokenKey).commit();
            getPreferences().edit().putBoolean(CuppCebeApplication.getInstance().IS_LOGIN, true).commit();
            getPreferences().edit().putInt(CuppCebeApplication.getInstance().USER_ID, userResponse.user.UserId).commit();
            getPreferences().edit().putString(CuppCebeApplication.getInstance().USER_OBJECT, new Gson().toJson(userResponse, UserResponse.class)).commit();
            hideCuppCebeProgress();
            ((MainActivity) getActivity()).toShopListFragment();

            // save user, successful login
        }

    }

    @Click(R.id.loginText)
    protected void onClick(View v) {
//        if(loginUserNameEditText.getText().length()>3&&loginPasswordEdittext.getText().length()>3)
//        {
//            login(loginUserNameEditText.getText().toString(),loginPasswordEdittext.getText().toString());
//        }
        login("ersin@nomadcommerce.com", "takumasato");

    }
}
