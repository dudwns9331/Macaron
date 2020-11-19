package yj.p.macaron.add;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import java.util.Objects;

import yj.p.macaron.R;

/**
 * inputActivity 에서 스와이프 하고 수정 버튼을 누르면 수행되는 클래스 (다이얼로그)
 */
public class CustomDialog extends Dialog {

    private OnDialogListener listener;
    private Context context;
    private EditText mod_name;
    private TextView start_time;
    private TextView end_time;

    public String work_time;

    private String name;
    private int s_Hour, s_Minute;
    private int e_Hour, e_Minute;
    String s_time;
    String e_time;

    @SuppressLint("SetTextI18n")
    public CustomDialog(final Context context, final int position, final Work_date date) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        (Objects.requireNonNull(getWindow())).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.customdialog);


        mod_name = findViewById(R.id.mod_name);
        mod_name.setText("근무자");

        // 근무자 추가 버튼
        Button mod_button = findViewById(R.id.mod_button);
        mod_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null) {
                    String name = mod_name.getText().toString();
                    date.setWorker(name);
                    work_time = s_time + " ~ " + e_time;
                    date.setWork_time(work_time);
                    listener.onFinish(position, date);
                    dismiss();
                }
            }
        });

        // 시작 시간 추가 버튼
        start_time = findViewById(R.id.start_time);

        start_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        s_Hour = hourOfDay;
                        s_Minute = minute;
                        s_time = s_Hour + "시 " + s_Minute + " 분 ";
                        start_time.setText(s_time);
                    }

                }, s_Hour, s_Minute, false);
                timePickerDialog.show();
            }
        });

        // 종료 시간 버튼
        end_time = findViewById(R.id.end_time);
        end_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        e_Hour = hourOfDay;
                        e_Minute = minute;
                        e_time = e_Hour + "시 " + e_Minute + " 분 ";
                        end_time.setText(e_time);
                    }

                }, e_Hour, e_Minute, false);
                timePickerDialog.show();
            }
        });
    }

    public void setDialogListener(OnDialogListener listener) {
        this.listener = listener;
    }
}
