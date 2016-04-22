package com.nomad.cuppcebe.fragments;


import android.app.Activity;
import android.support.v7.app.ActionBar;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nomad.cuppcebe.CuppCebeApplication;
import com.nomad.cuppcebe.MainActivity;
import com.nomad.cuppcebe.R;
import com.nomad.cuppcebe.adapters.CampaignListAdapter;
import com.nomad.cuppcebe.interfaces.OnMainFragmentListener;
import com.nomad.cuppcebe.model.Campaign;
import com.nomad.cuppcebe.restful.RestClient;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;

import java.util.ArrayList;

@EFragment(R.layout.campaign_list_fragment)
public class CampaignListFragment extends BaseFragment {

    @ViewById(R.id.campaignListView)
    ListView campaignListView;

    OnMainFragmentListener mainFragmentListener;

    private ArrayList<Campaign> campaigns;

    @RestService
    protected RestClient restClient;

    @FragmentArg
    int shopId;

    @FragmentArg
    String shopTitle;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mainFragmentListener = (OnMainFragmentListener) activity;
        ((MainActivity) getActivity()).showOrHideBottomContainer(1);
    }

    @AfterViews
    protected void afterViews() {
        initActionBar();
        getCampaignList();
    }

    @Background
    public void getCampaignList() {
        showCuppCebeProgress();
        campaigns = restClient.getCampaignList(getPreferences().getString(CuppCebeApplication.getInstance().CUPPCEBE_TOKEN_KEY, ""),
                getPreferences().getInt(CuppCebeApplication.getInstance().USER_ID, 0), shopId, 1, 10);
        hideCuppCebeProgress();
        if (campaigns != null && campaigns.size() > 0) {
            initUI(campaigns);
        } else {
//            Toast.makeText(getActivity(), "No Campaigns Found", Toast.LENGTH_LONG);
        }
    }

    @UiThread
    public void initUI(ArrayList<Campaign> campaignArrayList) {

        CampaignListAdapter adapter = new CampaignListAdapter(getActivity(), R.layout.campaign_list_row, campaignArrayList);
        campaignListView.setAdapter(adapter);
    }

    @UiThread
    public void initActionBar() {

        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT);
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_HORIZONTAL;
        View customActionBarView = getActivity().getLayoutInflater().inflate(R.layout.custom_action_bar_campaign_list, null);
        ActionBar actionbar = ((MainActivity) getActivity()).getSupportActionBar();

        TextView title = (TextView) customActionBarView.findViewById(R.id.customActionBarCampaignListTitle);
        if (shopTitle != null && !shopTitle.equals("")) {
            title.setText(shopTitle);
        }
        ImageView back = (ImageView) customActionBarView.findViewById(R.id.customActionBarCampaignListBackButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });


        actionbar.setDisplayHomeAsUpEnabled(false);
        actionbar.setDisplayShowCustomEnabled(true);
        actionbar.setDisplayShowTitleEnabled(false);
        actionbar.setCustomView(customActionBarView, layoutParams);
    }
}
