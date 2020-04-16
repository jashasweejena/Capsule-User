package com.example.capsule_user.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capsule_user.Database.ShopDetails;
import com.example.capsule_user.R;

import java.util.List;

public class DatabaseRecyclerviewAdapter extends RecyclerView.Adapter<DatabaseRecyclerviewAdapter.Viewholder> {

    private List<String> shopsList;
    private Context mContext;
    private static final String TAG = "DatabaseRecyclerview";

    public DatabaseRecyclerviewAdapter(List<String> shopsList, Context mContext) {
        this.shopsList = shopsList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recycler, parent, false);
        DatabaseRecyclerviewAdapter.Viewholder vh = new Viewholder(view);
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

    public class Viewholder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView name;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.shop_name);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Context mContext = view.getContext();
            String shopName = shopsList.get(getAdapterPosition());
            Log.d(TAG, "onClick: " + shopsList.get(getAdapterPosition()));
            Toast.makeText(view.getContext(), shopsList.get(getAdapterPosition()), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(mContext, ShopDetails.class);
            intent.putExtra(mContext.getString(R.string.shop_name), shopName);
            mContext.startActivity(intent);
        }
    }

}
