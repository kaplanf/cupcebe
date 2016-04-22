package com.nomad.cuppcebe.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.nomad.cuppcebe.R;
import com.nomad.cuppcebe.components.CuppCebeProgressDialog;
import com.nomad.cuppcebe.interfaces.OnMainFragmentListener;
import com.nomad.cuppcebe.util.DialogManager;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;

/**
 * Created by kaplanfatt on 19/09/15.
 */
@EFragment
public abstract class BaseFragment extends Fragment{

    private ProgressDialog progressDialog;
    private SharedPreferences preferences;
    private OnMainFragmentListener listener;
    private CuppCebeProgressDialog cuppCebeProgressDialog;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (OnMainFragmentListener) activity;
        } catch (ClassCastException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (listener != null) listener.onCloseFragment(this.getClass().getSimpleName());
    }

    @Override
    public void onResume() {
        super.onResume();
        if (listener != null) listener.onStartFragment(this.getClass().getSimpleName());
    }

    @UiThread
    protected void fragmentTransaction(int containerViewId, Fragment fragment) {
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(containerViewId, fragment, fragment.getClass().getSimpleName());
        fragmentTransaction.commit();
    }

    @UiThread
    protected void showProgressDialog() {
        if (getActivity() != null) {
            if (progressDialog == null)
                progressDialog = DialogManager.getInstance().getProgressDialog(getActivity(), R.string.loading);
            progressDialog.show();
        }
    }

    @UiThread
    protected void hideProgressDialog() {
        if (progressDialog != null) progressDialog.dismiss();
    }


    protected boolean isFMVisibleFragment(String fragmentTag) {
        Fragment fragment = getFragmentManager().findFragmentByTag(fragmentTag);
        if (fragment != null && fragment.isVisible()) return true;
        else return false;
    }

    protected boolean isChildFMVisibleFragment(String fragmentTag) {
        Fragment fragment = getChildFragmentManager().findFragmentByTag(fragmentTag);
        if (fragment != null && fragment.isVisible()) return true;
        else return false;
    }

    protected SharedPreferences getPreferences() {
        if (preferences == null)
            preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        return preferences;
    }

    public String getFramentTag() {
        String tag = this.getClass().getSimpleName();
        return tag;
    }

    @UiThread
    protected void showCuppCebeProgress() {
        if (getActivity() != null) {
            if (cuppCebeProgressDialog == null) {
                cuppCebeProgressDialog = CuppCebeProgressDialog.getInstance(getContext()).getCuppCebeProgressDialog(getContext(), null, null, true, false, null);
            }
            cuppCebeProgressDialog.show();
        }
    }

    @UiThread
    protected void hideCuppCebeProgress() {
        if (cuppCebeProgressDialog != null)
            cuppCebeProgressDialog.dismiss();

    }
}
