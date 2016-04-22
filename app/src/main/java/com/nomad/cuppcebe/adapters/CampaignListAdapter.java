package com.nomad.cuppcebe.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nomad.cuppcebe.R;
import com.nomad.cuppcebe.components.SquareImageView;
import com.nomad.cuppcebe.model.Campaign;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CampaignListAdapter extends ArrayAdapter<Campaign> {

    private ArrayList<Campaign> items;
    private Context context;

    public CampaignListAdapter(Context context, int textViewResourceId, ArrayList<Campaign> items) {
        super(context, textViewResourceId, items);
        this.items = items;
        this.context = context;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder holder;

        if (v == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.campaign_list_row, null);

            holder = new ViewHolder();
            holder.productName = (TextView) v.findViewById(R.id.campaignProductName);
            holder.shopName = (TextView) v.findViewById(R.id.campaignShopName);
            holder.image = (SquareImageView) v.findViewById(R.id.campaignListImage);

            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        Campaign campaign = items.get(position);
        if (campaign != null) {
            holder.productName.setText(campaign.ProductName);
            holder.shopName.setText(campaign.ShopName);
            Picasso.with(context).load(campaign.ImagePath).into(holder.image);
        }
        return v;
    }


    static class ViewHolder {
        TextView productName;
        TextView shopName;

        SquareImageView image;
    }
}
