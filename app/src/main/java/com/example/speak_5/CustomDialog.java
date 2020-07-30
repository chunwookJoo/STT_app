package com.example.speak_5;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.speak_5.Infterface.OnDialogListener;

public class CustomDialog extends Dialog {
    private OnDialogListener listener;
    private Button mod_bt;
    private EditText modify_content;
    private String contents;
    private Context context;

    public CustomDialog(final Context context, final int position, Memo note_data) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.customdialog);
        contents = note_data.getMaintext();
        this.context = context;

        // EditText에 값 채우기
        modify_content = findViewById(R.id.modify_title);
        modify_content.setText(contents);

        mod_bt = findViewById(R.id.mod_bt);
        mod_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) { //EditText의 수정된 값 가져오기
                    String contents = modify_content.getText().toString();

                    Memo note_ = new Memo();

                    note_.setMaintext(contents);

                    //Listener를 통해서 person객체 전달
                    listener.onFinish(position, note_);

                    //다이얼로그 종료
                    dismiss();
                }
            }
        });

    }

    public void setDialogListener(OnDialogListener listener){
        this.listener = listener; }
}