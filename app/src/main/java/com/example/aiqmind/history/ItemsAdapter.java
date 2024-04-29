package com.example.aiqmind.history;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aiqmind.R;

import java.util.List;
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {
    private List<Item> items;

    public ItemsAdapter(List<Item> items) {
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recylceview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Item item = items.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView;
        private TextView descriptionTextView;
        private TextView idTextView;
        private TextView scoreTextView;
        private TextView dateTextView; // Supposons que "date" représente le temps

        public ViewHolder(View itemView) {
            super(itemView);
            idTextView = itemView.findViewById(R.id.idTextView);
            scoreTextView = itemView.findViewById(R.id.scoreTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
        }

        public void bind(Item item) {
            idTextView.setText(item.getId());
            scoreTextView.setText(item.getScore());
            // Supposons que "date" est un objet Date
            dateTextView.setText(item.getDate().toString()); // Vous pouvez formater la date selon vos besoins
        }
    }
}
