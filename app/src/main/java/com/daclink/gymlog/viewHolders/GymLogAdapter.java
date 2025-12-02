package com.daclink.gymlog.viewHolders;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.daclink.gymlog.database.entities.GymLog;

public class GymLogAdapter extends ListAdapter<GymLog, GymLogViewHolder> {
    @Override
    public void onBindViewHolder(@NonNull GymLogViewHolder holder, int position) {
        GymLog current = getItem(position);
        holder.bind(current.toString());
    }

    public GymLogAdapter(@NonNull DiffUtil.ItemCallback<GymLog> diffCallback){
        super(diffCallback);
    }

    @NonNull
    @Override
    public GymLogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return GymLogViewHolder.create(parent);
    }

    public static class GymLogDiff extends DiffUtil.ItemCallback<GymLog>{
        @Override
        public boolean areItemsTheSame(@NonNull GymLog oldItem, @NonNull GymLog newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull GymLog oldItem, @NonNull GymLog newItem) {
            return oldItem.equals(newItem);
        }
    }
}
