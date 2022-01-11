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

public class Result_RecyclerAdapter extends RecyclerView.Adapter<Result_RecyclerAdapter.ViewHolder> {

    private final ArrayList<Result_PlayerInfo> mData;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView color;
        TextView name;
        TextView score;

        ViewHolder(View view) {
            super(view);
            color = view.findViewById(R.id.result_playerColor);
            name = view.findViewById(R.id.result_playerName);
            score = view.findViewById(R.id.result_score);
        }
    }

    public Result_RecyclerAdapter(ArrayList<Result_PlayerInfo> list) { mData = list; }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.fragment_result_list, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Result_PlayerInfo item = mData.get(position);
        holder.name.setText(item.getUserName());
        holder.score.setText(String.valueOf(item.getUserScore()));

        String dataColor = item.getIconColor();
        switch (dataColor) {
            case "white" : holder.color.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
            case "red" : holder.color.setBackgroundColor(Color.parseColor("#c8636b"));
            case "yellow" : holder.color.setBackgroundColor(Color.parseColor("#dcc770"));
            case "green" : holder.color.setBackgroundColor(Color.parseColor("#7eb369"));
            case "blue" : holder.color.setBackgroundColor(Color.parseColor("#628cb9"));
            case "purple" : holder.color.setBackgroundColor(Color.parseColor("#81bdca"));
            case "black" : holder.color.setBackgroundColor(Color.parseColor("#A881CA"));
        }
    }

    @Override
    public int getItemCount() { return mData.size(); }
}
