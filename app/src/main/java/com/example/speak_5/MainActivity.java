package com.example.speak_5;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.speak_5.Save_memo.JsonManager;
import com.example.speak_5.Save_memo.PreferenceManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    FloatingActionButton make_Note_Buttom;
    List<Memo> memoList;
    static List<Memo> seved_memoList;
    JsonManager jsonManager;
    static boolean onceChk = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        memoList = new ArrayList<>();
        seved_memoList = new ArrayList<>();
        recyclerAdapter = new RecyclerAdapter(memoList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recyclerAdapter);

        make_Note_Buttom = findViewById(R.id.make_note_buttom);
        make_Note_Buttom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(getApplicationContext(), AddActivity.class);
                    startActivityForResult(intent, 0);
                } catch (Exception e) {
                    Log.d("error", "intentError");
                }
            }
        });
        if (onceChk) {
            Log.d("error", "onceChk");
            Recall_a_note();
            onceChk = false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 0 && data != null) {
            String strMain = data.getStringExtra("main");
            String strSub = data.getStringExtra("sub");

            Memo memo = new Memo(strMain,strSub,0);
            updata_save_Memo(memo);
            recyclerAdapter.addItem(memo);
            recyclerAdapter.notifyDataSetChanged();
            Log.d("error", "twiecMeom");
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
            itemViewHolder.subtext.setText(memo.getSubtext());
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

    void updata_save_Memo(Memo memo){
        seved_memoList.add(memo);
        PreferenceManager.Clear(MainActivity.this);
        jsonManager = new JsonManager(seved_memoList);
        PreferenceManager.setString(MainActivity.this,"MemoJson",jsonManager.trListJson());
    }

    void Recall_a_note(){
        String saved_Memo = PreferenceManager.getString(MainActivity.this,"MemoJson");
        jsonManager = new JsonManager();
        List<Memo> saved_List_Memo = jsonManager.jsonParsing(saved_Memo);
        if(saved_List_Memo != null) {
            seved_memoList = saved_List_Memo;
            append_List_Memo(saved_List_Memo);
        }
    }

    void append_List_Memo(List<Memo> listdata){
        for(int i = 0;i<listdata.size();i++){
            recyclerAdapter.addItem(listdata.get(i));
            recyclerAdapter.notifyDataSetChanged();
        }
    }
}