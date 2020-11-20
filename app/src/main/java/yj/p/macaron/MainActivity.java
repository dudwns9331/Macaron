package yj.p.macaron;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
// import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
// import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnRangeSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import yj.p.macaron.add.Work_date_adapter;
import yj.p.macaron.add.inputActivity;
import yj.p.macaron.decorators.OneDayDecorator;
import yj.p.macaron.decorators.SaturdayDecorator;
import yj.p.macaron.decorators.SundayDecorator;
import yj.p.macaron.view_cal.list_fragment;

public class MainActivity extends AppCompatActivity {

    int Year, Month, Day;       // 선택된 날짜 연도, 달, 일 기록하는 변수
    private final OneDayDecorator oneDayDecorator = new OneDayDecorator(); // 현재 날짜를 커스텀하기 위한 데코레이터
    MaterialCalendarView materialCalendarView;
    ArrayList<String> selected_list;            // 선택된 날짜 String 값으로 저장하는 리스트
    ArrayList<String> selected_list2;            // 선택된 날짜 String 값으로 저장하는 리스트


    public boolean mode;            // 날짜를 선택하는 모드가 무엇인지 -> 다중 선택모드(여러개 따로 선택), 범위선택(처음과 끝까지)

    private int count = 0;      // 날짜 범위 선택시 첫번째 선택인지, 두번째 선택인지 지정.

    list_fragment list_fragment;

    @Override
    protected void onResume() {
        super.onResume();
        try {
            selected_list2 =  (ArrayList<String>) getIntent().getSerializableExtra("work_data_new");
            Toast.makeText(this, selected_list2.toString(), Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        materialCalendarView = findViewById(R.id.calendarView);     // 캘린더 아이디 지정
        Button add_button = findViewById(R.id.add_button);          // 추가 버튼
        Button clear_button = findViewById(R.id.clear_button);      // 선택 해제 버튼
        final Button select_all_range = findViewById(R.id.select_range); // 범위선택 버튼

        list_fragment = new list_fragment();

        mode = true;

        final Intent intent = getIntent();
        selected_list = (ArrayList<String>) intent.getSerializableExtra("work_data");


        // 달력 초기 설정
        materialCalendarView.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from(2020,0,1))     // 달력 표시 최소범위
                .setMaximumDate(CalendarDay.from(2030,11,31))   // 달려 표시 최대 범위
                .setCalendarDisplayMode(CalendarMode.MONTHS)                    // 달로 보여주기 WEEK 도 가능
                .commit();

        //달력 선택 모드, 표현 범위
        materialCalendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_MULTIPLE);

//        materialCalendarView.setShowOtherDates(MaterialCalendarView.SHOW_OUT_OF_RANGE);

       //달력에 토요일, 일요일, 현재 날짜 표시하기 위해 참조
        materialCalendarView.addDecorators(
                new SaturdayDecorator(), //토요일 표시기
                new SundayDecorator(),  // 일요일 표시기
                oneDayDecorator);       // 하루 표시기

      selected_list = new ArrayList<>();        // 선택된 날짜 리스트 -> 클릭했을 때 색으로 변하는 날짜들은 여기에 들어감

        // 만약 선택되었다면
        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                // 날짜가 선택되었다면,
                if(mode) { // mode = true 시 다중선택모드
                    Year = date.getYear();
                    Month = date.getMonth() + 1;        // 달의 값은 하나 적어서 +1
                    Day = date.getDay();

//                    Log.i("Year Test", Year + "");
//                    Log.i("Month Test", Month + "");
//                    Log.i("Day Test", Day + "");

                    String shot_Day = Year + "," + Month + "," + Day; // 선택한 날짜 2020,00,00 형식으로 들어감.

                    if (selected) selected_list.add(shot_Day); // 만약에 선택되었다면 -> 리스트에 추가 "2020,00,00"
                    else selected_list.remove(shot_Day);       // 선택 해제 시 리스트에서 제거

//                    Toast.makeText(MainActivity.this, selected_list.toString(), Toast.LENGTH_SHORT).show();
//                    Log.i("shot_Day test", shot_Day + "");
                }
            }
        });

        // 범위선택 시
        materialCalendarView.setOnRangeSelectedListener(new OnRangeSelectedListener() {
            @Override
            public void onRangeSelected(@NonNull MaterialCalendarView widget, @NonNull List<CalendarDay> dates) {

                if(count == 1) { // 두번째 선택될 때
                    selected_list.clear();  // 이전에 선택됐던 값 제거
                    count = 0;  // 처음 선택으로 되돌림
                }

                if (!mode) {    // 범위 선택일때,
                    for (int i = 0; i < dates.size(); i++) {
                        // 선택된 날짜들 리스트 dates 불러와서 연도, 달, 일 저장
                        Year = dates.get(i).getYear();
                        Month = dates.get(i).getMonth() + 1;
                        Day = dates.get(i).getDay();

                        String shot_Day = Year + "," + Month + "," + Day; // 선택한 날짜 2020,00,00 형식으로 들어감.
                        selected_list.add(shot_Day);
//                        Toast.makeText(MainActivity.this, selected_list.toString(), Toast.LENGTH_SHORT).show();
                    }
                    count++; // 범위로 선택 한번 하면 카운트 증가
                }
            }
        });


        // 추가 버튼 누르면 수행 -> 선택된 날짜 리스트 "2020,00,00" intent 로 보내고 inputActivity 시작함.
        // inputActivity 는 선택한 날짜를 recyclerView 로 목록을 만들어 보여주는 엑티비티입니다.
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent input_activity = new Intent(getApplicationContext(), inputActivity.class);   // 인텐트 생성
                order_date(selected_list);      // 선택된 날짜 이른 날짜부터 정렬
                input_activity.putExtra("work_data", selected_list);        // "work_data" 로 선택된 리스트 넘김
                input_activity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(input_activity);
            }
        });

        // 삭제 버튼일때,
        clear_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selected_list.clear();      // 리스트 초기화
                materialCalendarView.clearSelection();  // 선택으로 칠해진 날짜들 초기화
//                Toast.makeText(MainActivity.this, selected_list.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        // 범위 선택 버튼이 눌렸을때,
        select_all_range.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mode) {
                    materialCalendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_RANGE); // 달력 선택 모드 바꿈 -> 범위
                    select_all_range.setText("다중 선택");
                    mode = false;
                    count = 0;
                }
                else {
                    materialCalendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_MULTIPLE); // 달력 선택 모드 바꿈 -> 다중
                    select_all_range.setText("범위 선택");
                    mode = true;
                }
            }
        });
    }

    /**
     * 날짜순으로 정렬한다.
     * @param list 선택된 날짜 리스트
     */
    public void order_date(ArrayList<String> list) {
        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String t1, String t2) {
                String[] result1 = t1.split(",");
                String[] result2 = t2.split(",");

                StringBuilder time1 = new StringBuilder();
                StringBuilder time2 = new StringBuilder();

                for(int i = 0; i < 3; i++) {
                    time1.append(result1[i]);
                    time2.append(result2[i]);
                }
                return Integer.compare(Integer.parseInt(time1.toString()), Integer.parseInt(time2.toString()));
            }
        });
    }
}