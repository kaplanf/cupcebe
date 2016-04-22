package com.nomad.cuppcebe.fragments;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;

import java.util.ArrayList;


@EFragment(R.layout.shoplistfragment)
public class ShopListFragment extends BaseFragment {

    OnMainFragmentListener mainFragmentListener;

    @ViewById(R.id.shopList)
    ListView shopList;

    @RestService
    protected RestClient restClient;

    private ArrayList<Shop> shopArrayList;

    private ArrayList<Shop> favoriteShopList;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) getActivity()).getSupportActionBar().hide();
        mainFragmentListener = (OnMainFragmentListener) activity;
        ((MainActivity) getActivity()).showOrHideBottomContainer(1);
    }

    @AfterViews
    protected void afterViews() {
        getFavoriteList();
//        getShopList();
    }


    @Background
    public void getShopList() {
        showCuppCebeProgress();
        shopArrayList = restClient.getShopList(getPreferences().getString(CuppCebeApplication.getInstance().CUPPCEBE_TOKEN_KEY, ""),
                getPreferences().getInt(CuppCebeApplication.getInstance().USER_ID, 0), "", 41.088466, 29.005534, 5, 1);
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

    @Background
    public void getFavoriteList() {
        favoriteShopList = restClient.getFavorites(getPreferences().getString(CuppCebeApplication.getInstance().CUPPCEBE_TOKEN_KEY, ""),
                Integer.toString(getPreferences().getInt(CuppCebeApplication.getInstance().USER_ID, 0)));
        getShopList();
    }

    @UiThread
    protected void initShopList(ArrayList<Shop> shops) {

        if (favoriteShopList.size() > 0) {
            int favoriteSize = favoriteShopList.size();
            int shopSize = shops.size();

            for (int a = 0; a < shopSize; a++) {
                for (int b = 0; b < favoriteSize; b++) {
                    if (shops.get(a).ShopId == favoriteShopList.get(b).ShopId) {
                        shops.get(a).isFavorite = true;
                    }
                }
            }
        }

        ShopListAdapter adapter = new ShopListAdapter(getActivity(), R.layout.shop_list_row, shops);
        shopList.setAdapter(adapter);
    }

    @UiThread
    protected void initFavoriteButtons(ArrayList<Shop> favoriteList) {

    }

    @ItemClick
    void shopList(int position) {
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
