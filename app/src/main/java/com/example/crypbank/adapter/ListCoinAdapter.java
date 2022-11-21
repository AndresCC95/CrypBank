package com.example.crypbank.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.crypbank.R;
import com.example.crypbank.coingecko.models.Coin;

import java.util.List;

public class ListCoinAdapter extends RecyclerView.Adapter<ListCoinAdapter.ViewHolder> {
    private List<Coin> mData;
    private LayoutInflater mInflater;
    private Context context;
    final ListCoinAdapter.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Coin item);
    }

    public ListCoinAdapter(List<Coin> itemList, Context context, ListCoinAdapter.OnItemClickListener listener) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
        this.listener = listener;
    }

    @Override
    public int getItemCount(){
        return mData.size();
    }

    @Override
    public ListCoinAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_element, null);
        return new ListCoinAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListCoinAdapter.ViewHolder holder, final int position) {
        holder.bindData(mData.get(position));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iconImage;
        TextView nameView, priceView;

        ViewHolder(View itemView) {
            super(itemView);
            iconImage = itemView.findViewById(R.id.iconImageView);
            nameView = itemView.findViewById(R.id.nameTextView);
            priceView = itemView.findViewById(R.id.priceTextView);
        }

        void bindData(final Coin item) {
            nameView.setText(item.getNombre());
            priceView.setText(String.valueOf(item.getPrecio()));

            if (item.getNombre().equals("bitcoin")) {
                iconImage.setImageResource(R.drawable.bitcoin);
            } else if (item.getNombre().equals("ethereum")) {
                iconImage.setImageResource(R.drawable.ethereum);
            } else if (item.getNombre().equals("dogecoin")) {
                iconImage.setImageResource(R.drawable.dogecoin);
            } else if (item.getNombre().equals("cardano")) {
                iconImage.setImageResource(R.drawable.cardano);
            } else if (item.getNombre().equals("solana")) {
                iconImage.setImageResource(R.drawable.solana);
            }
            itemView.setOnClickListener(view -> listener.onItemClick(item));
        }
    }

}
