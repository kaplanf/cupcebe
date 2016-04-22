package com.nomad.cuppcebe.fragments;


import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBar;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.CircularImageView;
import com.google.gson.Gson;
import com.nomad.cuppcebe.CuppCebeApplication;
import com.nomad.cuppcebe.MainActivity;
import com.nomad.cuppcebe.R;
import com.nomad.cuppcebe.interfaces.OnMainFragmentListener;
import com.nomad.cuppcebe.model.UserResponse;
import com.nomad.cuppcebe.restful.RestClient;
import com.squareup.picasso.Picasso;

import net.glxn.qrgen.android.QRCode;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;

@EFragment(R.layout.profile_fragment)
public class ProfileFragment extends BaseFragment {

    private OnMainFragmentListener mainFragmentListener;

    @RestService
    protected RestClient restClient;

    @ViewById
    CircularImageView profileCircularImage;

    @ViewById
    TextView profileNameText;

    @ViewById
    TextView profilePointText;

    @ViewById
    ImageView profileQrImage;

    @ViewById(R.id.profileWalletIcon)
    ImageView profileWalletIcon;

    private UserResponse userResponse;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mainFragmentListener = (OnMainFragmentListener) activity;
        ((MainActivity) getActivity()).showOrHideBottomContainer(1);
        initActionBar();
        Gson gson = new Gson();
        String userObjectString = getPreferences().getString(CuppCebeApplication.getInstance().USER_OBJECT, "");
        if (userObjectString != null && !userObjectString.equals("")) {
            userResponse = gson.fromJson(userObjectString, UserResponse.class);
        }
    }

    @org.androidannotations.annotations.UiThread
    public void initActionBar() {

        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT);
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_HORIZONTAL;
        View customActionBarView = getActivity().getLayoutInflater().inflate(R.layout.custom_action_bar_profile, null);
        ActionBar actionbar = ((MainActivity) getActivity()).getSupportActionBar();


        actionbar.setDefaultDisplayHomeAsUpEnabled(true);
        actionbar.setCustomView(customActionBarView, layoutParams);
        actionbar.setDisplayShowCustomEnabled(true);

    }

    @AfterViews
    protected void afterViews() {

        if (userResponse.user.UserImagePath != null && !userResponse.user.UserImagePath.equals("")) {
            Picasso.with(getActivity()).load(userResponse.user.UserImagePath).into(profileCircularImage);
        }
        profileNameText.setText(userResponse.user.Name + " " + userResponse.user.Surname);
        Bitmap myBitmap = QRCode.from(userResponse.user.UserId + userResponse.user.Name).bitmap();
        profileQrImage.setImageBitmap(myBitmap);
    }

    @Click(R.id.profileWalletIcon)
    protected void walletClick() {
        ((MainActivity) getActivity()).toWalletFragment();
    }
}
