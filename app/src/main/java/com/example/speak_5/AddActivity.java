package com.example.speak_5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddActivity extends AppCompatActivity {

    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        editText = findViewById(R.id.edtMemo);

        findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String str = editText.getText().toString();

                if(str.length() > 0){
                    Date date = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                    String substr = sdf.format(date);
                    Toast.makeText(AddActivity.this,str + ", "+ substr, Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent();
                    intent.putExtra("main",str);
                    intent.putExtra("sub",substr);
                    setResult(0,intent);
                    finish();
                }
            }
        });
        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
