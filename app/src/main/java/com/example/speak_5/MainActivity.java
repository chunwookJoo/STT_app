package com.example.speak_5;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    Button btnAdd;
    List<Memo> memoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        memoList = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerAdapter = new RecyclerAdapter(memoList);
        recyclerView.setAdapter(recyclerAdapter);

        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AddActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 0){
            String strMain = data.getStringExtra("main");
            String strSub = data.getStringExtra("sub");

            Memo memo = new Memo(strMain,strSub,0);
            recyclerAdapter.addItem(memo);
            recyclerAdapter.notifyDataSetChanged();
        }
    }

    class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder>{
        private List<Memo> listdata;

        public RecyclerAdapter(List<Memo> listdata) {
            this.listdata = listdata;
        }

        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup,int i){
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item,viewGroup,false);
            return new ItemViewHolder(view);
        }

        @Override
        public int getItemCount(){
            return listdata.size();
        }

        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i){
            Memo memo = listdata.get(i);

            itemViewHolder.maintext.setText(memo.getMaintext());
            itemViewHolder.subtext.setText(memo.getMaintext());
        }

        void addItem(Memo memo){
            listdata.add(memo);
        }

        void removeItem(int position){
            listdata.remove(position);
        }

        class ItemViewHolder extends RecyclerView.ViewHolder{

            private TextView maintext;
            private TextView subtext;

            private CheckBox checkBox;
            public ItemViewHolder(@NonNull View itemView){
                super(itemView);

                maintext = itemView.findViewById(R.id.item_maintext);
                subtext = itemView.findViewById(R.id.item_subtext);
                checkBox = itemView.findViewById(R.id.item_check);
            }

        }
    }
}
