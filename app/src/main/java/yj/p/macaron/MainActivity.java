package yj.p.macaron;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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

import yj.p.macaron.add.inputActivity;
import yj.p.macaron.decorators.OneDayDecorator;
import yj.p.macaron.decorators.SaturdayDecorator;
import yj.p.macaron.decorators.SundayDecorator;

public class MainActivity extends AppCompatActivity {

    int Year, Month, Day;
    private final OneDayDecorator oneDayDecorator = new OneDayDecorator();
    MaterialCalendarView materialCalendarView;
    ArrayList<String> selected_list;

    public boolean mode;

    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        materialCalendarView = findViewById(R.id.calendarView);
        Button add_button = findViewById(R.id.add_button);
        Button clear_button = findViewById(R.id.clear_button);
        final Button select_all_range = findViewById(R.id.select_range);

        mode = true;

        // 달력 초기 설정
        materialCalendarView.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from(2020,0,1))
                .setMaximumDate(CalendarDay.from(2030,11,31))
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();

        //달력 선택 모드, 표현 범위
        materialCalendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_MULTIPLE);
        materialCalendarView.setShowOtherDates(MaterialCalendarView.SHOW_OUT_OF_RANGE);

       //달력에 토요일, 일요일, 현재 날짜 표시하기 위해 참조
        materialCalendarView.addDecorators(
                new SaturdayDecorator(),
                new SundayDecorator(),
                oneDayDecorator);

      selected_list = new ArrayList<>();

        // 만약 선택되었다면
        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

                if(mode) {
                    Year = date.getYear();
                    Month = date.getMonth() + 1;
                    Day = date.getDay();

                    Log.i("Year Test", Year + "");
                    Log.i("Month Test", Month + "");
                    Log.i("Day Test", Day + "");

                    String shot_Day = Year + "," + Month + "," + Day;

                    if (selected) selected_list.add(shot_Day);
                    else selected_list.remove(shot_Day);

                    Toast.makeText(MainActivity.this, selected_list.toString(), Toast.LENGTH_SHORT).show();

                    Log.i("shot_Day test", shot_Day + "");
                }
            }
        });

        // 범위선택 시
        materialCalendarView.setOnRangeSelectedListener(new OnRangeSelectedListener() {
            @Override
            public void onRangeSelected(@NonNull MaterialCalendarView widget, @NonNull List<CalendarDay> dates) {

                if(count == 1) {
                    selected_list.clear();
                    count = 0;
                }

                if (!mode) {
                    for (int i = 0; i < dates.size(); i++) {
                        Year = dates.get(i).getYear();
                        Month = dates.get(i).getMonth() + 1;
                        Day = dates.get(i).getDay();

                        String shot_Day = Year + "," + Month + "," + Day;
                        selected_list.add(shot_Day);
                        Toast.makeText(MainActivity.this, selected_list.toString(), Toast.LENGTH_SHORT).show();
                    }
                    count++;
                }
            }
        });


        // 추가 버튼 누르면 수행 -> 선택된 날짜 리스트 intent로 보내고 inputActivity 시작함.
        // 엑티비티 수준이면 이부분 손봐야 할거 같습니다.
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), inputActivity.class);
                order_date(selected_list);
                intent.putExtra("work_data", selected_list);
                Toast.makeText(MainActivity.this, selected_list.toString(), Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

        // 삭제 버튼일때,
        clear_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selected_list.clear();
                materialCalendarView.clearSelection();
                Toast.makeText(MainActivity.this, selected_list.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        select_all_range.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mode) {
                    materialCalendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_RANGE);
                    select_all_range.setText("다중 선택");
                    mode = false;
                    count = 0;
                }
                else {
                    materialCalendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_MULTIPLE);
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