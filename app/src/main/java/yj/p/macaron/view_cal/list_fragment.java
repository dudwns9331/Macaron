package yj.p.macaron.view_cal;

import android.graphics.Canvas;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import yj.p.macaron.R;
import yj.p.macaron.add.Work_date;
import yj.p.macaron.add.Work_date_adapter;
import yj.p.macaron.add.inputActivity;


public class list_fragment extends Fragment {

    ArrayList<String> work_list;
    public RecyclerView recyclerView;
    public static Work_date_adapter date_adapter;
    static boolean first;
    ItemTouchHelper itemTouchHelper;

    static Work_date recent_item;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_list, container, true);

        first = true;
//        Bundle bundle = getArguments();

//        if(bundle != null) {
//            work_list = bundle.getStringArrayList("work_data");
//        }

        recyclerView = rootView.findViewById(R.id.recyclerView_fragment);
        // 레이아웃 지정
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        // 스와이프, 터치 관련 인터페이스, 클래스 활용
//        itemTouchHelper = new ItemTouchHelper(new ItemTouchHelperCallback(date_adapter));
//        itemTouchHelper.attachToRecyclerView(recyclerView);

        // 어댑터 추가

        date_adapter = inputActivity.date_adapter;

        recyclerView.setAdapter(date_adapter);
        setUpRecyclerView();
        return rootView;

    }

    private void setUpRecyclerView() {
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.onDraw(c, parent, state);
            }
        });
    }
//    public void
}