package com.nomad.cuppcebe.fragments;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.ActionBar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nomad.cuppcebe.CuppCebeApplication;
import com.nomad.cuppcebe.MainActivity;
import com.nomad.cuppcebe.R;
import com.nomad.cuppcebe.components.SquareImageView;
import com.nomad.cuppcebe.interfaces.OnMainFragmentListener;
import com.nomad.cuppcebe.model.FavoriteResult;
import com.nomad.cuppcebe.model.Shop;
import com.nomad.cuppcebe.restful.RestClient;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;

import java.util.ArrayList;

/**
 * Created by kaplanfatt on 25/11/15.
 */
@EFragment(R.layout.search_shop_fragment)
public class SearchShopFragment extends BaseFragment {


    OnMainFragmentListener mainFragmentListener;

    @ViewById(R.id.searchShopFragmentListview)
    ListView searchShopFragmentListview;

    @ViewById(R.id.searchShopFragmentSearchButton)
    ImageView searchShopFragmentSearchButton;

    @ViewById(R.id.searchShopFragmentEditText)
    EditText searchShopFragmentEditText;

    @RestService
    protected RestClient restClient;

    private ArrayList<Shop> shopArrayList;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mainFragmentListener = (OnMainFragmentListener) activity;
        ((MainActivity) getActivity()).showOrHideBottomContainer(1);
    }

    @AfterViews
    protected void afterViews() {
        initActionBar();
    }

    @Background
    public void getShopList(String shopKeyword) {
        showCuppCebeProgress();
        shopArrayList = restClient.getShopList(getPreferences().getString(CuppCebeApplication.getInstance().CUPPCEBE_TOKEN_KEY, ""),
                getPreferences().getInt(CuppCebeApplication.getInstance().USER_ID, 0), shopKeyword, 41.088466, 29.005534, 5, 1);
        if (shopArrayList != null && shopArrayList.size() > 0) {
            hideCuppCebeProgress();
            initShopList(shopArrayList);
        }

    }

    @Background
    public void addFavorite(int shopId) {
        FavoriteResult result = restClient.addFavorite(getPreferences().getString(CuppCebeApplication.getInstance().CUPPCEBE_TOKEN_KEY, ""),
                Integer.toString(getPreferences().getInt(CuppCebeApplication.getInstance().USER_ID, 0)),
                Integer.toString(shopId), Integer.toString(shopId), "1");
    }


    @UiThread
    protected void initShopList(ArrayList<Shop> shops) {
        ShopListAdapter adapter = new ShopListAdapter(getActivity(), R.layout.shop_list_row, shops);
        searchShopFragmentListview.setAdapter(adapter);
    }

    @Click(R.id.searchShopFragmentSearchButton)
    protected void searchShop(View view) {
        if (searchShopFragmentEditText.getText().toString().length() > 3) {
            getShopList(searchShopFragmentEditText.getText().toString());
        }
    }


    @UiThread
    public void initActionBar() {

        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT);
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_HORIZONTAL;
        View customActionBarView = getActivity().getLayoutInflater().inflate(R.layout.custom_action_bar_search_shop, null);
        ActionBar actionbar = ((MainActivity) getActivity()).getSupportActionBar();


//        actionbar.setDisplayHomeAsUpEnabled(false);
//        actionbar.setDisplayShowHomeEnabled(false);
        actionbar.setDefaultDisplayHomeAsUpEnabled(true);
        actionbar.setCustomView(customActionBarView, layoutParams);
        actionbar.setDisplayShowCustomEnabled(true);

    }

    @ItemClick
    void searchShopFragmentListview(int position) {
        int shopId = shopArrayList.get(position).ShopId;
        String shopTitle = shopArrayList.get(position).Name;
        if (shopId != 0) {
            ((MainActivity) getActivity()).toCampaignListFragment(shopId, shopTitle);

        }
    }

    class ShopListAdapter extends ArrayAdapter<Shop> {

        private ArrayList<Shop> items;
        private Context context;
        private MainActivity activity;

//        @RestService
//        protected RestClient restClient;

        public ShopListAdapter(Context context, int textViewResourceId, ArrayList<Shop> items) {
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
                v = vi.inflate(R.layout.shop_list_row, null);

                holder = new ViewHolder();
                holder.text = (TextView) v.findViewById(R.id.shopListText);
                holder.image = (SquareImageView) v.findViewById(R.id.shopListImage);
                holder.desc = (TextView) v.findViewById(R.id.shopListItemDesc);
                holder.favoriteButton = (ImageView) v.findViewById(R.id.shopListItemFavoriteButton);

                v.setTag(holder);
            } else {
                holder = (ViewHolder) v.getTag();
            }

            final Shop shop = items.get(position);
            if (shop != null) {
                holder.text.setText(shop.Name);
                holder.desc.setText(shop.Slogan);

                if (shop.Logo.endsWith(".png")) {
                    Picasso.with(context).load(shop.Logo).into(holder.image);

                }

                if (shop.isFavorite) {
                    holder.favoriteButton.setSelected(true);
                } else {
                    holder.favoriteButton.setSelected(false);
                }

                holder.favoriteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (holder.favoriteButton.isSelected()) {
                            addFavorite(shop.ShopId);
                            shop.isFavorite = false;
                            notifyDataSetChanged();

                        } else {
                            addFavorite(shop.ShopId);
                            shop.isFavorite = true;
                            notifyDataSetChanged();
                        }
                    }
                });

            }
            return v;
        }


        class ViewHolder {
            TextView text;
            TextView desc;
            SquareImageView image;
            ImageView favoriteButton;
        }
    }
}
