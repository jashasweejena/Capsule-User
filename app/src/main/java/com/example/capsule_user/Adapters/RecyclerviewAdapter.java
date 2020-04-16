package com.example.capsule_user.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capsule_user.R;

import java.util.List;

public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.Viewholder> {

    private List<String> shopsList;
    private Context mContext;

    public RecyclerviewAdapter(List<String> shopsList, Context mContext) {
        this.shopsList = shopsList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recycler, parent, false);
        RecyclerviewAdapter.Viewholder vh = new Viewholder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        TextView name = holder.name;
        name.setText(shopsList.get(position));
    }

    @Override
    public int getItemCount() {
        return shopsList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        private TextView name;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.shop_name);
        }
    }
}
