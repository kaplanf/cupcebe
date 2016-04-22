package com.nomad.cuppcebe;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.nomad.cuppcebe.activity.BaseActivity;
import com.nomad.cuppcebe.components.RightDrawerLayout;
import com.nomad.cuppcebe.fragments.CampaignListFragment;
import com.nomad.cuppcebe.fragments.CampaignListFragment_;
import com.nomad.cuppcebe.fragments.FavoritesListFragment;
import com.nomad.cuppcebe.fragments.FavoritesListFragment_;
import com.nomad.cuppcebe.fragments.LoginFragment;
import com.nomad.cuppcebe.fragments.LoginFragment_;
import com.nomad.cuppcebe.fragments.ProfileFragment;
import com.nomad.cuppcebe.fragments.ProfileFragment_;
import com.nomad.cuppcebe.fragments.SearchShopFragment;
import com.nomad.cuppcebe.fragments.SearchShopFragment_;
import com.nomad.cuppcebe.fragments.ShopListFragment;
import com.nomad.cuppcebe.fragments.ShopListFragment_;
import com.nomad.cuppcebe.fragments.WalletFragment;
import com.nomad.cuppcebe.fragments.WalletFragment_;
import com.nomad.cuppcebe.interfaces.OnMainFragmentListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity implements OnMainFragmentListener {

    @ViewById(R.id.bottomContainer)
    RelativeLayout bottomContainer;

    @ViewById(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @ViewById(R.id.rightDrawerLayout)
    RightDrawerLayout rightDrawerLayout;

    @ViewById(R.id.bottomMenuHome)
    ImageView bottomMenuHome;

    @ViewById(R.id.bottomMenuCharacter)
    ImageView bottomMenuCharacter;

    @ViewById(R.id.bottomMenuSearch)
    ImageView bottomMenuSearch;

    @ViewById(R.id.bottomMenuTag)
    ImageView bottomMenuTag;

    @ViewById(R.id.bottomMenuWish)
    ImageView bottomMenuWish;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!checkIfLogin()) {
            toLoginFragment();
        } else {
            toProfileFragment();
        }
    }

    @AfterViews
    protected void afterViews() {
        setDrawerListeners();
    }

    @Click(R.id.bottomMenuHome)
    protected void onHomeClicked(View v) {
        setSelectTabItem(0);
        toShopListFragment();
    }

    @Click(R.id.bottomMenuCharacter)
    protected void onProfileClicked(View v) {
        toProfileFragment();
    }

    @Click(R.id.bottomMenuTag)
    protected void onTagClicked(View v) {
        setSelectTabItem(2);
    }

    @Click(R.id.bottomMenuSearch)
    protected void onSearchClicked(View v) {
        setSelectTabItem(1);
        toSearchFragment();
    }

    @Click(R.id.bottomMenuWish)
    protected void onWishClicked(View v) {
        setSelectTabItem(3);
    }

    public void toLoginFragment() {
        getSupportActionBar().hide();
        LoginFragment loginFragment = new LoginFragment_();
        replaceFragmentAndClearBackStack(R.id.main_frame, loginFragment, true);

    }

    public void toShopListFragment() {
        getSupportActionBar().hide();
        ShopListFragment shopListFragment = new ShopListFragment_();
        replaceFragment(R.id.main_frame, shopListFragment, true);
    }

    public void toCampaignListFragment(int shopIdToSent, String shopTitleToSent) {
        getSupportActionBar().show();
        CampaignListFragment campaignListFragment = CampaignListFragment_.builder().shopId(shopIdToSent).shopTitle(shopTitleToSent).build();
        replaceFragment(R.id.main_frame, campaignListFragment, true);
    }

    public void toProfileFragment() {
        getSupportActionBar().show();
        ProfileFragment profileFragment = new ProfileFragment_();
        replaceFragment(R.id.main_frame, profileFragment, true);
    }

    public void toSearchFragment() {
        getSupportActionBar().show();
        SearchShopFragment searchShopFragment = new SearchShopFragment_();
        replaceFragment(R.id.main_frame, searchShopFragment, true);
    }

    public void toFavoritesListFragment() {
        getSupportActionBar().show();
        FavoritesListFragment favoritesListFragment = new FavoritesListFragment_();
        replaceFragment(R.id.main_frame, favoritesListFragment, true);
    }

    public void toWalletFragment() {
        getSupportActionBar().show();
        WalletFragment walletFragment = new WalletFragment_();
        replaceFragment(R.id.main_frame, walletFragment, true);
    }


    private boolean checkIfLogin() {
        return getPreferences().getBoolean(CuppCebeApplication.getInstance().IS_LOGIN, false);
    }

    @Override
    public void onCloseFragment(String tag) {

    }

    @Override
    public void onStartFragment(String tag) {

    }

    public void showOrHideBottomContainer(int key) {
        if (key == 0) {
            bottomContainer.setVisibility(View.GONE);

        } else {
            bottomContainer.setVisibility(View.VISIBLE);
        }
    }

    public void setDrawerListeners() {
        rightDrawerLayout.rightDrawerProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.closeDrawers();
                toProfileFragment();

            }
        });

        rightDrawerLayout.rightDrawerLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.closeDrawers();
                getPreferences().edit().clear().commit();
                toLoginFragment();
            }
        });
        rightDrawerLayout.slidingMenuFavLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.closeDrawers();
                toFavoritesListFragment();
            }
        });

    }

    public void setSelectTabItem(int tabIndex) {
        ImageView[] imageViews = {bottomMenuHome, bottomMenuSearch, bottomMenuTag, bottomMenuWish};
        for (int a = 0; a < imageViews.length; a++) {
            if (a == tabIndex) {
                imageViews[a].setSelected(true);
            } else {
                imageViews[a].setSelected(false);
            }
        }
    }
}
