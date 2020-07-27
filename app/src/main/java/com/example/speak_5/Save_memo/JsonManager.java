package com.example.speak_5.Save_memo;

import android.util.Log;

import com.example.speak_5.Memo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonManager {
    private List<Memo> ListMemo = new ArrayList<Memo>();
    public JsonManager(){

    };
    public JsonManager(List<Memo> Memo){
        this.ListMemo = Memo;
    }

    public String trListJson(){
        JSONObject obj = new JSONObject();
        try{
            JSONArray jArray = new JSONArray();
            for(int i = 0; i< ListMemo.size();i++){
                JSONObject sObject= new JSONObject();
                sObject.put("main_text", ListMemo.get(i).getMaintext());
                sObject.put("sub_text", ListMemo.get(i).getSubtext());
                sObject.put("number", ListMemo.get(i).getIsdone());
                jArray.put(sObject);
            }
            obj.put("Memo_data", jArray);
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return obj.toString();
    }

    //Json을 Lnote_data로 변경
    public List<Memo> jsonParsing(String json){
        try{
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jArray = jsonObject.getJSONArray("Memo_data");
            Log.d("Error",jArray.toString());
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject movieObject = jArray.getJSONObject(i);
                Log.d("Error",movieObject.toString());
                Memo memo_data = new Memo(movieObject.getString("main_text"),
                        movieObject.getString("sub_text"),
                        Integer.parseInt(movieObject.getString("number")));
                ListMemo.add(memo_data);
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return ListMemo;
    }

}
