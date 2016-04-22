package com.nomad.cuppcebe.fragments;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.ActionBar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.nomad.cuppcebe.CuppCebeApplication;
import com.nomad.cuppcebe.MainActivity;
import com.nomad.cuppcebe.R;
import com.nomad.cuppcebe.interfaces.OnMainFragmentListener;
import com.nomad.cuppcebe.model.Point;
import com.nomad.cuppcebe.restful.RestClient;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;

import java.util.ArrayList;

/**
 * Created by kaplanfatt on 27/11/15.
 */
@EFragment(R.layout.wallet_fragment)
public class WalletFragment extends BaseFragment {

    OnMainFragmentListener mainFragmentListener;

    @ViewById(R.id.walletFragmentPointListView)
    ListView walletFragmentPointListView;

    @RestService
    protected RestClient restClient;

    ArrayList<Point> pointArrayList;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mainFragmentListener = (OnMainFragmentListener) activity;
        ((MainActivity) getActivity()).showOrHideBottomContainer(1);
    }


    @AfterViews
    protected void afterViews() {
        showCuppCebeProgress();
        initActionBar();
        getPoints();
    }

    @Background
    protected void getPoints() {
        pointArrayList = restClient.getPointHistory(getPreferences().getString(CuppCebeApplication.getInstance().CUPPCEBE_TOKEN_KEY, ""),
                Integer.toString(getPreferences().getInt(CuppCebeApplication.getInstance().USER_ID, 0)));
        hideCuppCebeProgress();
        if (pointArrayList.size() > 0) {
            initUI(pointArrayList);
        }
    }

    @UiThread
    protected void initUI(ArrayList<Point> points) {
        PointListAdapter adapter = new PointListAdapter(getActivity(), R.layout.point_list_row, points);
        walletFragmentPointListView.setAdapter(adapter);
    }


    @UiThread
    public void initActionBar() {

        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT);
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_HORIZONTAL;
        View customActionBarView = getActivity().getLayoutInflater().inflate(R.layout.custom_action_bar_wallet, null);
        ActionBar actionbar = ((MainActivity) getActivity()).getSupportActionBar();


        actionbar.setDefaultDisplayHomeAsUpEnabled(true);
        actionbar.setCustomView(customActionBarView, layoutParams);
        actionbar.setDisplayShowCustomEnabled(true);
    }

    class PointListAdapter extends ArrayAdapter<Point> {

        private ArrayList<Point> items;
        private Context context;
        private MainActivity activity;

//        @RestService
//        protected RestClient restClient;

        public PointListAdapter(Context context, int textViewResourceId, ArrayList<Point> items) {
            super(context, textViewResourceId, items);
            this.items = items;
            this.context = context;
            this.activity = (MainActivity) context;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            final ViewHolder holder;

            if (v == null) {
                LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.point_list_row, null);

                holder = new ViewHolder();
                holder.shopName = (TextView) v.findViewById(R.id.pointItemName);
                holder.date = (TextView) v.findViewById(R.id.pointItemDate);
                holder.point = (TextView) v.findViewById(R.id.pointItemAmount);

                v.setTag(holder);
            } else {
                holder = (ViewHolder) v.getTag();
            }

            final Point point = items.get(position);
            if (point != null) {

                holder.shopName.setText(point.ShopName);
                holder.date.setText(point.PointGainDate + " Tarihinde yaptığınız " + point.Description + " harcamasından");
                holder.point.setText(point.Point + "  puan kazandınız");


            }
            return v;
        }


        class ViewHolder {
            TextView shopName;
            TextView date;
            TextView point;

        }
    }
}
