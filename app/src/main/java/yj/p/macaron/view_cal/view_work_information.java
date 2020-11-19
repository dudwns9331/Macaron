package yj.p.macaron.view_cal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executors;

import yj.p.macaron.MainActivity;
import yj.p.macaron.R;
import yj.p.macaron.decorators.EventDecorator;
import yj.p.macaron.decorators.OneDayDecorator;
import yj.p.macaron.decorators.SaturdayDecorator;
import yj.p.macaron.decorators.SundayDecorator;

public class view_work_information extends AppCompatActivity {

    private final OneDayDecorator oneDayDecorator = new OneDayDecorator();

    MaterialCalendarView materialCalendarView;
    ArrayList<String> selected_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_work_information);

        materialCalendarView = findViewById(R.id.view_work_date);

        Intent intent = getIntent();
        selected_list = (ArrayList<String>) intent.getSerializableExtra("work_data"); // 배열안에 날짜 들어가 있음.


        materialCalendarView.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from(2020,0,1))
                .setMaximumDate(CalendarDay.from(2030,11,31))
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();

        materialCalendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_SINGLE);
        materialCalendarView.setShowOtherDates(MaterialCalendarView.SHOW_OUT_OF_RANGE);

        materialCalendarView.addDecorators(
                new SaturdayDecorator(),
                new SundayDecorator(),
                oneDayDecorator);


        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                // 날짜 클릭시 해당 근무자 시간표 나오기.
                materialCalendarView.clearSelection();
                Toast.makeText(view_work_information.this, "test", Toast.LENGTH_SHORT).show();
            }
        });


        new ApiSimulator(selected_list).executeOnExecutor(Executors.newSingleThreadExecutor());

    }
    private class ApiSimulator extends AsyncTask<Void, Void, List<CalendarDay>> {

        ArrayList<String> Time_Result;

        ApiSimulator(ArrayList<String> Time_Result) {
            this.Time_Result = Time_Result;
        }

        @Override
        protected List<CalendarDay> doInBackground(Void... voids) {

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Calendar calendar = Calendar.getInstance();
            ArrayList<CalendarDay> dates = new ArrayList<>();
            calendar.add(Calendar.MONTH, 0);

            /**
             * 특정날짜 달력에 점 표시 해주는 곳
             * 월을 0이 1월 년, 일은 그대로 한다.
             * String 문자열인 Time_Result 를 받아와서 ,를 기준으로 자르고 String을 int로 변환
             * 아직 안씀.
             */

            for(int i = 0; i < Time_Result.size(); i++) {
                String[] time = Time_Result.get(i).split(",");
                int year = Integer.parseInt(time[0]);
                int month = Integer.parseInt(time[1]);
                int dayy = Integer.parseInt(time[2]);
                CalendarDay day = CalendarDay.from(year, month-1, dayy);
                dates.add(day);
                calendar.set(year,month - 1, dayy);
            }
            return dates;
        }

        /**
         * 이것도 건들지 마세요!
         */
        @Override
        protected void onPostExecute(List<CalendarDay> calendarDays) {
            super.onPostExecute(calendarDays);

            if(isFinishing()) {
                return;
            }
            materialCalendarView.addDecorator(new EventDecorator(Color.RED, calendarDays, view_work_information.this));
        }
    }
}