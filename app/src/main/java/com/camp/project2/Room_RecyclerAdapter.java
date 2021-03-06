package com.camp.project2;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Room_RecyclerAdapter extends RecyclerView.Adapter<Room_RecyclerAdapter.ViewHolder> {
    private final ArrayList<Room_PlayerInfo> mData;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView color;
        TextView name;

        ViewHolder(View view) {
            super(view);
            color = view.findViewById(R.id.room_playerColor);
            name = view.findViewById(R.id.room_playerName);
        }
    }

    public Room_RecyclerAdapter(ArrayList<Room_PlayerInfo> list) {
        mData = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.fragment_room_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Room_PlayerInfo item = mData.get(position);
        holder.name.setText(item.getUserName());
        switch (item.getIconColor()) {
            case "white" :
                holder.color.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
                break;
            case "red" :
                holder.color.setBackgroundColor(Color.parseColor("#c8636b"));
                break;
            case "yellow" :
                holder.color.setBackgroundColor(Color.parseColor("#dcc770"));
                break;
            case "green" :
                holder.color.setBackgroundColor(Color.parseColor("#7eb369"));
                break;
            case "blue" :
                holder.color.setBackgroundColor(Color.parseColor("#628cb9"));
                break;
            case "purple" :
                holder.color.setBackgroundColor(Color.parseColor("#81bdca"));
                break;
            case "black" :
                holder.color.setBackgroundColor(Color.parseColor("#A881CA"));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

}
