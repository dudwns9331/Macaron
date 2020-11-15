package yj.p.macaron.add;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

import yj.p.macaron.R;
import yj.p.macaron.add.Work_date;
import yj.p.macaron.add.Work_date_adpater;

public class inputActivity extends AppCompatActivity {

    ItemTouchHelper itemTouchHelper;
    RecyclerView recyclerView;

    Button select_all_button;
    Button delete_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        select_all_button = findViewById(R.id.select_all);
        delete_button = findViewById(R.id.delete);

        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        final Work_date_adpater date_adpater = new Work_date_adpater(this);
        recyclerView.setAdapter(date_adpater);

        itemTouchHelper = new ItemTouchHelper(new ItemTouchHelperCallback(date_adpater));
        itemTouchHelper.attachToRecyclerView(recyclerView);

        Intent intent = getIntent();
        ArrayList<String> data = (ArrayList<String>) intent.getSerializableExtra("work_data");

        assert data != null;
        for(int i = 0; i < data.size(); i++) {
            String result[] = data.get(i).split(",");
            int year = Integer.parseInt(result[0]);
            int month = Integer.parseInt(result[1]);
            int dayy = Integer.parseInt(result[2]);
            String worker = dayy + "일 근무자";
            Work_date work_date = new Work_date(year, month, dayy, worker);
            date_adpater.addItem(work_date);
        }
        
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i = date_adpater.getItemCount()-1; i >=0; i--) {
                    if(date_adpater.isItemSelected(i)) {
                        date_adpater.deleteItem(date_adpater.getItem(i));
                        date_adpater.notifyItemRemoved(i);
                        date_adpater.notifyItemRangeChanged(i, date_adpater.getItemCount());
                    }
                }
                date_adpater.clearSelectedItem();
            }
        });

        select_all_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i = 0; i < date_adpater.getItemCount(); i++)
                date_adpater.toggleItemSelected(i);
            }
        });
        
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.onDraw(c, parent, state);
            }
        });
    }
}