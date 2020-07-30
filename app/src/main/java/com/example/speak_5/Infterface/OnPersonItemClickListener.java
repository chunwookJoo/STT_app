package com.example.speak_5.Infterface;

import android.view.View;

import com.example.speak_5.MainActivity;

public interface OnPersonItemClickListener {
    public void onItemClick(MainActivity.RecyclerAdapter.ItemViewHolder holder, View view, int position);
}
