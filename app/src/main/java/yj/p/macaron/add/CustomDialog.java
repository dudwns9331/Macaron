package yj.p.macaron.add;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import java.util.Objects;

import yj.p.macaron.R;
import yj.p.macaron.add.Work_date;

public class CustomDialog extends Dialog {

    private OnDialogListener listener;
    private Context context;
    private Button mod_button;
    private EditText mod_name, mod_time;
    private String name;
    private int time;

    public CustomDialog(Context context, final int position, Work_date date) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        (getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.customdialog);

        mod_name = findViewById(R.id.mod_name);
        mod_name.setText(name);

        mod_button = findViewById(R.id.mod_button);
        mod_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null) {
                    String name = mod_name.getText().toString();
                    Work_date work_date = new Work_date(name);

                    listener.onFinish(position, work_date);
                    dismiss();
                }
            }
        });
    }

    public void setDialogListener(OnDialogListener listener) {
        this.listener = listener;
    }
}
