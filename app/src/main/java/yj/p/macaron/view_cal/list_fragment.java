package yj.p.macaron.view_cal;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
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
    RecyclerView recyclerView;
    static Work_date_adapter date_adapter;
    static boolean first;

    static Work_date recent_item;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_list_fragment, container, false);

        first = true;
//        Bundle bundle = getArguments();

//        if(bundle != null) {
//            work_list = bundle.getStringArrayList("work_data");
//        }

        recyclerView = rootView.findViewById(R.id.recyclerView);
        // 레이아웃 지정
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        // 어댑터 추가
        date_adapter = inputActivity.date_adapter;
        recyclerView.setAdapter(date_adapter);

        return rootView;
    }

//    public void
}