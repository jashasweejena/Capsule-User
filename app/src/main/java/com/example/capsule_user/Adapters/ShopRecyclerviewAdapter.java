package com.example.capsule_user.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capsule_user.Database.Medicine;
import com.example.capsule_user.Interfaces.OnItemCheckListener;
import com.example.capsule_user.R;

import org.w3c.dom.Text;

import java.util.List;

public class ShopRecyclerviewAdapter extends RecyclerView.Adapter<ShopRecyclerviewAdapter.Viewholder> {

    private static final String TAG = "ShopRecyclerviewAdapter";
    private List<Medicine> medsList;
    private Context mContext;
    private OnItemCheckListener callback;

    public ShopRecyclerviewAdapter(List<Medicine> shopsList, Context mContext, OnItemCheckListener callback) {
        this.medsList = shopsList;
        this.mContext = mContext;
        this.callback = callback;
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
        final Medicine currentItem = medsList.get(position);

        final TextView descTextView = holder.desc;
        TextView priceTextView = holder.price;
        TextView qtyTextView = holder.qty;
        final CheckBox checkBox = holder.checkBox;

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isChecked = checkBox.isChecked();
                Log.d(TAG, "onClick: " + descTextView + " " + isChecked);
                Toast.makeText(mContext, descTextView.getText() + " " + isChecked, Toast.LENGTH_SHORT).show();
                if(isChecked){
                    callback.onItemCheck(currentItem);
                }
                else{
                    callback.onItemUncheck(currentItem);
                }
            }
        });

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
        private CheckBox checkBox;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            desc = itemView.findViewById(R.id.meds_desc);
            price = itemView.findViewById(R.id.meds_price);
            qty = itemView.findViewById(R.id.meds_qty);
            checkBox = itemView.findViewById(R.id.meds_checkbox);
        }


    }

}
