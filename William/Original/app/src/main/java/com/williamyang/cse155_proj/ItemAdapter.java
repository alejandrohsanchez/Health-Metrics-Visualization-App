package com.williamyang.cse155_proj;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.itemViewHolder> {

    private List<String> mList;

    public ItemAdapter(List<String> list){
        mList = list;
    }

    @NonNull
    @Override
    public itemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_layout, viewGroup, false);
        return new itemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull itemViewHolder itemViewHolder, int position) {
        if (position == 0){
            itemViewHolder.container.setBackgroundResource(R.drawable.round_corner_top);
        }

        if (position == mList.size()-1){
            itemViewHolder.container.setBackgroundResource(R.drawable.round_corner_bottom);
            itemViewHolder.divider.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class itemViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout container;
        View divider;

        public itemViewHolder(View itemView){
            super(itemView);
            container = itemView.findViewById(R.id.container);
            //divider = itemView.findViewById((R.id.divider));
        }
    }
}
