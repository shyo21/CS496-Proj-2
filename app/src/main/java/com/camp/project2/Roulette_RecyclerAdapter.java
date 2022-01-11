package com.camp.project2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Roulette_RecyclerAdapter extends RecyclerView.Adapter<Roulette_RecyclerAdapter.ViewHolder> {
    private final ArrayList<String> mData;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView text;

        ViewHolder(View view) {
            super(view);
            text = view.findViewById(R.id.roulette_text);
        }
    }

    public Roulette_RecyclerAdapter(ArrayList<String> list) { mData = list; }

    @NonNull
    @Override
    public Roulette_RecyclerAdapter.ViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.fragment_roulette_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String item = mData.get(position);
        holder.text.setText(item);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

}
