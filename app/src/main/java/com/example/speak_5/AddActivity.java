package com.example.speak_5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddActivity extends AppCompatActivity {

    EditText editText;
    Button btnback;
    Button btnsave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_add);

            Setting();
            AddListener();
    }
    void Setting(){
        editText = findViewById(R.id.edtMemo);
        btnback = findViewById(R.id.btnBack);
        btnsave = findViewById(R.id.btnSave);
    }

    void AddListener(){
        btnback.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("main", editText.getText());
                startActivity(intent);
            }
        });
        btnsave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String str = editText.getText().toString();

                if(str.length() > 0){
                    Date date = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                    String substr = sdf.format(date);
                    Toast.makeText(AddActivity.this, "저장 되었습니다.", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent();
                    intent.putExtra("main",str);
                    intent.putExtra("sub",substr);
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }
        });
    }
}
