package yj.p.macaron;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import yj.p.macaron.R;
import yj.p.macaron.Work_date;

public class Work_date_adpater extends RecyclerView.Adapter<Work_date_adpater.ViewHolder>{
    ArrayList<Work_date> items = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.date_item, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Work_date item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(Work_date item) {
        items.add(item);
    }

    public void setItems(ArrayList<Work_date> items) {
        this.items = items;
    }

    public Work_date getItem(int position) {
        return items.get(position);
    }
    public void setItem(int position, Work_date item) {
        items.set(position, item);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        TextView textView2;
        TextView textView3;

        public ViewHolder(View item) {
            super(item);
            textView = item.findViewById(R.id.year_data);
            textView2 = item.findViewById(R.id.month_data);
            textView3 = item.findViewById(R.id.day_data);
        }

        public void setItem(Work_date item) {
            textView.setText(String.valueOf(item.getYear()));
            textView2.setText(String.valueOf(item.getMonth()));
            textView3.setText(String.valueOf(item.getDate()));
        }
    }
}
