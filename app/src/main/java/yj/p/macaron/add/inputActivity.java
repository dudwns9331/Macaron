package yj.p.macaron;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import java.io.Serializable;
import java.util.ArrayList;

import yj.p.macaron.R;

public class inputActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        Work_date_adpater date_adpater = new Work_date_adpater();

        Intent intent = getIntent();
        ArrayList<String> data = (ArrayList<String>) intent.getSerializableExtra("work_data");

        assert data != null;
        for(int i = 0; i < data.size(); i++) {
            String result[] = data.get(i).split(",");
            int year = Integer.parseInt(result[0]);
            int month = Integer.parseInt(result[1]);
            int dayy = Integer.parseInt(result[2]);
            Work_date work_date = new Work_date(year, month, dayy);
            date_adpater.addItem(work_date);
        }

        recyclerView.setAdapter(date_adpater);
    }
}