package com.camp.project2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.ViewHolder> {

    private final ArrayList<playerListItem> mData;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView color;
        TextView name;

        ViewHolder(View view) {
            super(view);
            color = view.findViewById(R.id.playerColor);
            name = view.findViewById(R.id.playerName);
        }
    }

    public recyclerAdapter(ArrayList<playerListItem> list) {
        mData = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.playerlist,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        playerListItem item = mData.get(position);
        holder.name.setText(item.getUserName());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

}
