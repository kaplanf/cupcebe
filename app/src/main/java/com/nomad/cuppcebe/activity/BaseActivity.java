package com.nomad.cuppcebe.activity;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.facebook.appevents.AppEventsLogger;
import com.nomad.cuppcebe.R;
import com.nomad.cuppcebe.util.DialogManager;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;

/**
 * Created by kaplanfatt on 19/09/15.
 */
@EActivity
public abstract class BaseActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private ProgressDialog progressDialog;

    protected void addFragment(int containerViewId, Fragment fragment, boolean isBackStack) {
        String tag = fragment.getClass().getSimpleName();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(containerViewId, fragment, tag);
        if (isBackStack) fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.commit();
    }

    protected void replaceFragment(int containerViewId, Fragment fragment, boolean isBackStack) {
        String tag = fragment.getClass().getSimpleName();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(containerViewId, fragment, tag);
        if (isBackStack) fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.commit();
    }

    protected void addFragmentAndClearBackStack(int containerViewId, Fragment fragment, boolean isBackStack) {
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        String tag = fragment.getClass().getSimpleName();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(containerViewId, fragment, tag);
        if (fragmentManager.getBackStackEntryCount() > 1) {
            int fragmentId = fragmentManager.getBackStackEntryAt(1).getId();
            fragmentManager.popBackStack(fragmentId, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        if (isBackStack) fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.commit();
    }

    protected void replaceFragmentAndClearBackStack(int containerViewId, Fragment fragment, boolean isBackStack) {
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        String tag = fragment.getClass().getSimpleName();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(containerViewId, fragment, tag);
        if (fragmentManager.getBackStackEntryCount() > 1) {
            int fragmentId = fragmentManager.getBackStackEntryAt(1).getId();
            fragmentManager.popBackStack(fragmentId, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        if (isBackStack) fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
//            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    protected SharedPreferences getPreferences() {
        if (preferences == null) preferences = getPreferences(MODE_PRIVATE);
        return preferences;
    }

    protected boolean isVisibleFragment(String fragmentTag) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(fragmentTag);
        if (fragment != null && fragment.isResumed()) return true;
        else return false;
    }

    @UiThread
    protected void showProgressDialog() {
        if (progressDialog == null) progressDialog = DialogManager.getInstance().getProgressDialog(this, R.string.loading);
        progressDialog.show();
    }

    @UiThread
    protected void hideProgressDialog() {
        progressDialog.dismiss();
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppEventsLogger.activateApp(this);

    }

    @Override
    protected void onPause() {
        super.onPause();
        AppEventsLogger.deactivateApp(this);
    }
}
