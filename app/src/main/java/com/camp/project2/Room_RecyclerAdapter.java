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
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

}
