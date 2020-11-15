package yj.p.macaron.add;

import android.content.Context;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import yj.p.macaron.R;
import yj.p.macaron.add.Work_date;

public class Work_date_adpater extends RecyclerView.Adapter<Work_date_adpater.ViewHolder>
        implements ItemTouchHelperListener, OnDialogListener, OnWorkDateClickListener{

    private SparseBooleanArray mSelectedItems = new SparseBooleanArray(0);
    ArrayList<Work_date> items = new ArrayList<>();
    Context context;
    OnWorkDateClickListener listener;

    public Work_date_adpater(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.date_item, viewGroup, false);

        return new ViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Work_date item = items.get(position);
        viewHolder.setItem(item);
        if (isItemSelected(position)) {
            viewHolder.itemView.setBackgroundColor(Color.CYAN);
        } else {
            viewHolder.itemView.setBackgroundColor(Color.WHITE);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(Work_date item) {
        items.add(item);
    }
    public void deleteItem(Work_date item) { items.remove(item); }

    public void setItems(ArrayList<Work_date> items) {
        this.items = items;
    }

    public Work_date getItem(int position) {
        return items.get(position);
    }
    public void setItem(int position, Work_date item) {
        items.set(position, item);
    }

    public void toggleItemSelected(int position) {
        if (mSelectedItems.get(position, false)) {
            mSelectedItems.delete(position);
        } else {
            mSelectedItems.put(position, true);
        }
        notifyItemChanged(position);
    }

    public boolean isItemSelected(int position) {
        return mSelectedItems.get(position, false);
    }

    public void clearSelectedItem() {
        int position;
        for (int i = 0; i < mSelectedItems.size(); i++) {
            position = mSelectedItems.keyAt(i);
            mSelectedItems.put(position, false);
            notifyItemChanged(position);
        }
        mSelectedItems.clear();
    }


    @Override
    public boolean onItemMove(int from_position, int to_position) {
        Work_date work_date = items.get(from_position);
        items.remove(from_position);
        items.add(to_position, work_date);
        notifyItemMoved(from_position, to_position);
        return true;
    }

    @Override
    public void onItemSwipe(int position) {
        items.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onLeftClick(int position, RecyclerView.ViewHolder viewHolder) {
        CustomDialog dialog = new CustomDialog(context,position, items.get(position));
        DisplayMetrics dm = context.getApplicationContext().getResources().getDisplayMetrics();

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        WindowManager.LayoutParams wm = dialog.getWindow().getAttributes();
        wm.copyFrom(dialog.getWindow().getAttributes());
        wm.width = (int)(width * 0.7);
        wm.height = height/3;

        dialog.setDialogListener(this);
        dialog.show();
    }

    @Override
    public void onRightClick(int position, RecyclerView.ViewHolder viewHolder) {
        items.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onFinish(int position, Work_date work_date) {
        items.set(position, work_date);
        notifyItemChanged(position);
    }

    @Override
    public void onItemClick(ViewHolder holder, View view, int position) {
        if(listener != null) {
            listener.onItemClick(holder, view, position);
        }
    }

    public void setOnItemClickListener(OnWorkDateClickListener listener) {
        this.listener = listener;
    }

     public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        TextView textView2;
        TextView textView3;
        TextView textView4;

        public ViewHolder(View item, final OnWorkDateClickListener listener) {
            super(item);
            textView = item.findViewById(R.id.year_data);
            textView2 = item.findViewById(R.id.month_data);
            textView3 = item.findViewById(R.id.day_data);
            textView4 = item.findViewById(R.id.name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    toggleItemSelected(position);
                    Log.d("test", "position = " + position);
                    Toast.makeText(context, mSelectedItems.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        public void setItem(Work_date item) {
            textView.setText(String.valueOf(item.getYear()));
            textView2.setText(String.valueOf(item.getMonth()));
            textView3.setText(String.valueOf(item.getDate()));
            textView4.setText(String.valueOf(item.getWorker()));
        }
    }
}
