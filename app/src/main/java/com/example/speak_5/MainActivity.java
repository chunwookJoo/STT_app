package com.example.speak_5;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.speak_5.Infterface.OnDialogListener;
import com.example.speak_5.Infterface.OnPersonItemClickListener;
import com.example.speak_5.Save_memo.JsonManager;
import com.example.speak_5.Save_memo.PreferenceManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import static com.example.speak_5.R.id.activity_chooser_view_content;
import static com.example.speak_5.R.id.content;
import static com.example.speak_5.R.id.dialog_button;
import static com.example.speak_5.R.id.info;
import static com.example.speak_5.R.id.item_maintext;
import static com.example.speak_5.R.id.listMode;
import static com.example.speak_5.R.id.list_item;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    FloatingActionButton make_Note_Buttom;
    List<Memo> memoList;
    OnPersonItemClickListener listener;
    Context context;

    static List<Memo> seved_memoList;
    JsonManager jsonManager;
    static boolean onceChk = true;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        memoList = new ArrayList<>();
        seved_memoList = new ArrayList<>();
        recyclerAdapter = new RecyclerAdapter(memoList, context);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recyclerAdapter);

        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);

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

    //키다운 확인
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
            case KeyEvent.KEYCODE_APP_SWITCH:
                moveTaskToBack(true);
                ActivityCompat.finishAffinity(this);
                android.os.Process.killProcess(android.os.Process.myPid());
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0 && data != null) {
            String strMain = data.getStringExtra("main");
            String strSub = data.getStringExtra("sub");

            Memo memo = new Memo(strMain, strSub, 0);
            updata_save_Memo(memo);
            recyclerAdapter.addItem(memo);
            recyclerAdapter.notifyDataSetChanged();
            Log.d("error", "twiecMemo");
        }
    }


    public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder>
            implements OnPersonItemClickListener, OnDialogListener {
        List<Memo> memoList;
        Context context;

        public RecyclerAdapter(List<Memo> memoList, Context context) {
            this.memoList = memoList;
            this.context = context;
        }

        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
            return new ItemViewHolder(view,this);
        }

        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {
            Memo memo = memoList.get(i);

            itemViewHolder.maintext.setText(memo.getMaintext());
            itemViewHolder.subtext.setText(memo.getSubtext());
        }
        //
        @Override
        public int getItemCount() {
            if(memoList != null) {
                return memoList.size();
            }
            return 0;
        }
        public boolean moveItem(int fromPosition, int toPosition) {
            memoList.remove(fromPosition);
            notifyItemMoved(fromPosition, toPosition);
            return true;
        }
        // 추가
        void addItem(Memo memo) {
            memoList.add(memo);
        }
        // 삭제
        void removeItem(int position) {
            memoList.remove(position);
            notifyItemRemoved(position);
            save_Memo(memoList);
        }

        public class ItemViewHolder extends RecyclerView.ViewHolder {

            private TextView subtext;
            private TextView maintext;
            private CheckBox checkBox;

                public ItemViewHolder(@NonNull View itemView, final OnPersonItemClickListener listener) {
                    super(itemView);
                    maintext = itemView.findViewById(item_maintext);
                    subtext = itemView.findViewById(R.id.item_subtext);
                    checkBox = itemView.findViewById(R.id.item_check);

                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int position = getAdapterPosition();
                            if (listener != null) {
                                listener.onItemClick(ItemViewHolder.this, v, position);
                                Log.d("text","position : " + Integer.toString(position));
                            }
                        }
                    });
                }

            public void bind(String text){
                maintext.setText(text);
                subtext.setText(text);
                checkBox.setText(text);
            }
        }
        @Override
        public void onFinish(int position, Memo memo_data) {
            this.memoList.set(position, memo_data);
            notifyItemChanged(position);
        }
        @Override
        public void onItemClick(ItemViewHolder holder, View view, int position) {

            Log.d("Tag", "onIemClick");
            Log.d("Tag", memoList.get(position).getMaintext());
            setContentView(R.layout.customdialog);

            if(listener != null){
                listener.onItemClick(holder,view,position);

                new AlertDialog.Builder(MainActivity.this);
                CustomDialog dialog = new CustomDialog(this.context, position, memoList.get(position));


                DisplayMetrics dm = this.context.getApplicationContext().getResources().getDisplayMetrics();
                int width = dm.widthPixels; int height = dm.heightPixels;

                //다이얼로그 사이즈 세팅
                WindowManager.LayoutParams wm = dialog.getWindow().getAttributes();
                wm.copyFrom(dialog.getWindow().getAttributes());
                wm.width =  width;
                wm.height = height;

                //다이얼로그 Listener 세팅
                dialog.setDialogListener(this);

                //다이얼로그 띄우기
                dialog.show();
            }
        }
    }
    // 데이터 저장
    void updata_save_Memo(Memo memo) {
        seved_memoList.add(memo);
        PreferenceManager.Clear(MainActivity.this);
        jsonManager = new JsonManager(seved_memoList);
        PreferenceManager.setString(MainActivity.this, "MemoJson", jsonManager.trListJson());
    }
    // 스와이프 삭제
    void save_Memo(List<Memo> memos){
        PreferenceManager.Clear(MainActivity.this);
        jsonManager = new JsonManager(memos);
        PreferenceManager.setString(MainActivity.this, "MemoJson", jsonManager.trListJson());
    }
    // 데이터 불러오기
    void Recall_a_note() {
        String saved_Memo = PreferenceManager.getString(MainActivity.this, "MemoJson");
        jsonManager = new JsonManager();
        List<Memo> saved_List_Memo = jsonManager.jsonParsing(saved_Memo);
        if (saved_List_Memo != null) {
            seved_memoList = saved_List_Memo;
            append_List_Memo(saved_List_Memo);
        }
    }

    void append_List_Memo(List<Memo> listdata) {
        for (int i = 0; i < listdata.size(); i++) {
            recyclerAdapter.addItem(listdata.get(i));
            recyclerAdapter.notifyDataSetChanged();
        }
    }

    // 스와이프 구현
    ItemTouchHelper.Callback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return recyclerAdapter.moveItem(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            seved_memoList.remove(viewHolder.getAdapterPosition());
            recyclerAdapter.removeItem(viewHolder.getAdapterPosition());
        }
    };
}