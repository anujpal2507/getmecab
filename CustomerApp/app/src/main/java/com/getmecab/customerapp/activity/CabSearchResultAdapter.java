package com.getmecab.customerapp.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.getmecab.customerapp.R;
import com.getmecab.customerapp.database.Pricing;

import java.util.ArrayList;

/**
 * Created by anuj "email : anujpal2507@gmail.com" on 10/10/16.
 */
public class CabSearchResultAdapter extends BaseAdapter {

    Context context;
    ArrayList<Pricing> pricingArrayList;

    public CabSearchResultAdapter(Context context, ArrayList<Pricing> pricingArrayList) {
        this.context = context;
        this.pricingArrayList = pricingArrayList;
    }

    @Override
    public int getCount() {
        return pricingArrayList.size();
    }

    @Override
    public Pricing getItem(int position) {
        return pricingArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.cab_details_row, null);
            holder = new ViewHolder();
            holder.cabModelImageView = (ImageView) convertView.findViewById(R.id.cab_model_image);
            holder.cabCostInfoImageView = (ImageView) convertView.findViewById(R.id.cab_cost_info_button);
            holder.cabBrandTextView = (TextView) convertView.findViewById(R.id.cab_model_brand);
            holder.cabModeTextView = (TextView) convertView.findViewById(R.id.cab_model);
            holder.cabSeatsTextView = (TextView) convertView.findViewById(R.id.cab_seats);
            holder.cabRateTextView = (TextView) convertView.findViewById(R.id.cab_rate);
            holder.cabCostTextView = (TextView) convertView.findViewById(R.id.cab_cost);
//            holder.cabTaxMsgTextView = (TextView) convertView.findViewById(R.id.cab_tax_msg);
            holder.cabBookButton = (Button) convertView.findViewById(R.id.book_now_button);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Pricing pricing = getItem(position);
        setViewHolder(holder, pricing);
        return convertView;
    }

    private void setViewHolder(ViewHolder holder, Pricing pricing) {
        String modelInfo[] = pricing.getModel_name().split(" ");
        holder.cabBrandTextView.setText(modelInfo[0]);
        holder.cabModeTextView.setText(pricing.getModel_name().substring(modelInfo[0].length()+1));
        holder.cabSeatsTextView.setText(pricing.getSeats()+" + 1");
        holder.cabRateTextView.setText("\u20B9" + " " + pricing.getExclusive_price().toString() + "/km");
        holder.cabCostTextView.setText("\u20B9" + " " + pricing.getGMC_Price().toString());
    }

    private static class ViewHolder {
        ImageView cabModelImageView;
        ImageView cabCostInfoImageView;
        TextView cabBrandTextView;
        TextView cabModeTextView;
        TextView cabSeatsTextView;
        TextView cabRateTextView;
        TextView cabCostTextView;
        TextView cabTaxMsgTextView;
        Button cabBookButton;
    }
}
