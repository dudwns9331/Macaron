package yj.p.macaron;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import yj.p.macaron.inputActivity;
import yj.p.macaron.decorators.EventDecorator;
import yj.p.macaron.decorators.OneDayDecorator;
import yj.p.macaron.decorators.SaturdayDecorator;
import yj.p.macaron.decorators.SundayDecorator;

public class MainActivity extends AppCompatActivity {

    String time, kcal, menu;
    int Year, Month, Day;
    private final OneDayDecorator oneDayDecorator = new OneDayDecorator();
    Cursor cursor;
    MaterialCalendarView materialCalendarView;

    ArrayList<String> selected_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        materialCalendarView = findViewById(R.id.calendarView);
        Button add_button = findViewById(R.id.add_button);
        Button clear_button = findViewById(R.id.clear_button);

        materialCalendarView.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from(2020,0,1))
                .setMaximumDate(CalendarDay.from(2030,11,31))
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();

        materialCalendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_MULTIPLE);
        materialCalendarView.setShowOtherDates(MaterialCalendarView.SHOW_OUT_OF_RANGE);
        materialCalendarView.addDecorators(
                new SaturdayDecorator(),
                new SundayDecorator(),
                oneDayDecorator);

      selected_list = new ArrayList<>();

//        new ApiSimulator(result).executeOnExecutor(Executors.newSingleThreadExecutor());

        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                 Year = date.getYear();
                 Month = date.getMonth() + 1;
                 Day = date.getDay();

                Log.i("Year Test", Year + "");
                Log.i("Month Test", Month + "");
                Log.i("Day Test", Day + "");

                String shot_Day = Year + "," + Month + "," + Day;

                if(selected)  selected_list.add(shot_Day);
                else  selected_list.remove(shot_Day);

                Log.i("shot_Day test", shot_Day + "");

                Toast.makeText(MainActivity.this, selected_list.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), inputActivity.class);
                intent.putExtra("work_data", selected_list);
                startActivity(intent);
            }
        });

        clear_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private class ApiSimulator extends AsyncTask<Void, Void, List<CalendarDay>> {

        String[] Time_Result;

        ApiSimulator(String[] Time_Result) {
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


            /**
             * 특정날짜 달력에 점 표시 해주는 곳
             * 월을 0이 1월 년, 일은 그대로 한다.
             * String 문자열인 Time_Result 를 받아와서 ,를 기준으로 자르고 String을 int로 변환
             */

            for(int i = 0; i < Time_Result.length; i++) {
                CalendarDay day = CalendarDay.from(calendar);
                String[] time = Time_Result[i].split(",");
                int year = Integer.parseInt(time[0]);
                int month = Integer.parseInt(time[1]);
                int dayy = Integer.parseInt(time[2]);

                dates.add(day);
                calendar.set(year,month -1, dayy);
            }
            return dates;
        }

        @Override
        protected void onPostExecute(List<CalendarDay> calendarDays) {
            super.onPostExecute(calendarDays);

            if(isFinishing()) {
                return;
            }
            materialCalendarView.addDecorator(new EventDecorator(Color.GREEN, calendarDays, MainActivity.this));
        }
    }
}