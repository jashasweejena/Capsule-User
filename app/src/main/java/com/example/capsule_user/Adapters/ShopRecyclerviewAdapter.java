package com.example.capsule_user.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capsule_user.Database.Medicine;
import com.example.capsule_user.R;

import org.w3c.dom.Text;

import java.util.List;

public class ShopRecyclerviewAdapter extends RecyclerView.Adapter<ShopRecyclerviewAdapter.Viewholder> {

    private static final String TAG = "ShopRecyclerviewAdapter";
    private List<Medicine> medsList;
    private Context mContext;

    public ShopRecyclerviewAdapter(List<Medicine> shopsList, Context mContext) {
        this.medsList = shopsList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recycler_medicine, parent, false);
        ShopRecyclerviewAdapter.Viewholder vh = new Viewholder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        TextView descTextView = holder.desc;
        TextView priceTextView = holder.price;
        TextView qtyTextView = holder.qty;

        String desc = medsList.get(position).getDesc();
        String price = "" + medsList.get(position).getPrice();
        String qty = "" + medsList.get(position).getQty();

//        desc.setText(medsList.get(position).getDesc());
//        price.setText(medsList.get(position).getPrice());
//        qty.setText(medsList.get(position).getQty());

        descTextView.setText(desc);
        priceTextView.setText(price);
        qtyTextView.setText(qty);

        Log.d(TAG, "onBindViewHolder: " + medsList.get(position).getDesc());
    }

    @Override
    public int getItemCount() {
        return medsList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        private TextView desc;
        private TextView price;
        private TextView qty;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            desc = itemView.findViewById(R.id.meds_desc);
            price = itemView.findViewById(R.id.meds_price);
            qty = itemView.findViewById(R.id.meds_qty);

        }
    }

}
