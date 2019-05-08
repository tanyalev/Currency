package ru.sberbank.converter.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.sberbank.converter.R;
import ru.sberbank.converter.entity.Currency;

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.ViewHolder> {
    private List<Currency> currencyList = new ArrayList<>();
    private OnItemClickListener listener;

    public CurrencyAdapter(List<Currency> currencyList, OnItemClickListener listener) {
        this.currencyList = currencyList;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater
                .from(parent.getContext()).inflate(R.layout.item_currency, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvCurrencyName.setText(currencyList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return currencyList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCurrencyName;

        ViewHolder(View itemView) {
            super(itemView);
            this.tvCurrencyName = itemView.findViewById(R.id.tvCurrencyName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClicked(currencyList.get(getAdapterPosition()).getId());
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClicked(String currencyId);
    }
}