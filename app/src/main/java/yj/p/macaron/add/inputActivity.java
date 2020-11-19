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

import java.util.ArrayList;

import yj.p.macaron.R;
import yj.p.macaron.view_cal.view_work_information;

public class inputActivity extends AppCompatActivity {

    ItemTouchHelper itemTouchHelper;
    RecyclerView recyclerView;

    Button select_all_button;
    Button delete_button;
    Button save_button;

    public static ArrayList<String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        // 선택 버튼
        select_all_button = findViewById(R.id.select_all);
        // 지우기 버튼
        delete_button = findViewById(R.id.delete);

        // 저장 버튼
        save_button = findViewById(R.id.save);


        // 리싸이클러뷰 -> 달력에서 넣은 날짜 배열 리스트로 표혀현해서 보여줌
        recyclerView = findViewById(R.id.recyclerView);
        // 레이아웃 지정
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        // 어댑터 추가
        final Work_date_adapter date_adpater = new Work_date_adapter(this);
        // 어댑터 보여주기
        recyclerView.setAdapter(date_adpater);

        // 스와이프, 터치 관련 인터페이스, 클래스 활용 이거는 건드시면 안됩니다.
        itemTouchHelper = new ItemTouchHelper(new ItemTouchHelperCallback(date_adpater));
        itemTouchHelper.attachToRecyclerView(recyclerView);

        // 메인 엑티비티(달력) 에서 intent로 값 불러옴
        Intent intent = getIntent();
        data = (ArrayList<String>) intent.getSerializableExtra("work_data"); // 배열안에 날짜 들어가 있음.

        assert data != null;
        for(int i = 0; i < data.size(); i++) {
            // , 으로 구분된 날짜 하나씩 찢기
            String result[] = data.get(i).split(",");
            int year = Integer.parseInt(result[0]);
            int month = Integer.parseInt(result[1]);
            int dayy = Integer.parseInt(result[2]);
            String worker = dayy + "일 근무자";
            Work_date work_date = new Work_date(year, month, dayy, worker);
            work_date.setWork_time("근무 시간");
            date_adpater.addItem(work_date);
        }

        // 삭제 버튼 눌렸을때, 삭제
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

        // 전체 삭제 눌렸을때
        select_all_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i = 0; i < date_adpater.getItemCount(); i++)
                date_adpater.toggleItemSelected(i);
            }
        });


        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), view_work_information.class);
                intent.putExtra("work_data", data);
                startActivity(intent);
            }
        });
        
        setUpRecyclerView();
    }

    //리싸이클려 뷰 다시 그려줌
    private void setUpRecyclerView() {
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.onDraw(c, parent, state);
            }
        });
    }
}