package com.example.zarazara;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import static android.widget.Toast.LENGTH_SHORT;

public class MissionFragment1 extends Fragment {

    View v;
    DBHelper helper;
    SQLiteDatabase db;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    // Handler handler;
    // ArrayList<Toast> toasts;
    // Toast toast;
    View toastLayout;
    TextView toastText;
    TextView coinText;

    ImageView checkBoxX11;
    ImageView checkBoxX12;
    ImageView checkBoxX13;
    ImageView checkBoxX14;
    ImageView checkBoxX15;

    ImageView checkBoxO11;
    ImageView checkBoxO12;
    ImageView checkBoxO13;
    ImageView checkBoxO14;
    ImageView checkBoxO15;


    int userCoin;   //메인에서 불러온 코인값 저장
    Boolean dailyMission1, dailyMission2, dailyMission3, dailyMission4, dailyMission5;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_mission1,container,false);

        toastLayout = inflater.inflate(R.layout.custom_toast,(ViewGroup)v.findViewById(R.id.customToastLayout));
        toastText = toastLayout.findViewById(R.id.customToastText);

        sharedPreferences = this.getActivity().getSharedPreferences("shared", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        helper = new DBHelper(getActivity(), "stepStore.db", null, 1);
        db = helper.getWritableDatabase();
        helper.onCreate(db);

        coinText = (TextView) getActivity().findViewById(R.id.userCoin);

        checkBoxX11 = v.findViewById(R.id.checkbox_x11);
        checkBoxX12 = v.findViewById(R.id.checkbox_x12);
        checkBoxX13 = v.findViewById(R.id.checkbox_x13);
        checkBoxX14 = v.findViewById(R.id.checkbox_x14);
        checkBoxX15 = v.findViewById(R.id.checkbox_x15);

        checkBoxO11 = v.findViewById(R.id.checkbox_o11);
        checkBoxO12 = v.findViewById(R.id.checkbox_o12);
        checkBoxO13 = v.findViewById(R.id.checkbox_o13);
        checkBoxO14 = v.findViewById(R.id.checkbox_o14);
        checkBoxO15 = v.findViewById(R.id.checkbox_o15);

        setWalkActivity();

        return v;
    }

    //WalkActivity walkActivity = new WalkActivity(); //walkActivity에서 걸음수 활용
    //MainActivity mainActivity = new MainActivity(); //mainActivity에서 시간과 날짜 활용

    public void setWalkActivity(){

        String nowDate = sharedPreferences.getString("currentDate", "Default");
        int walkResult = helper.getStep(nowDate);
        Log.d("CheckWalk", "날짜 "+nowDate+"걸음수 "+ Integer.toString(walkResult));
        ArrayList<String> msg = new ArrayList<>();
        // String text = null;

        dailyMission5 = sharedPreferences.getBoolean("dailyMission5", false);
        if(walkResult >= 10000 &&  dailyMission5 == false){
        //    Toast.makeText(getActivity(), "오늘 할 일은 끝났어요!", LENGTH_SHORT).show();
        //    makeToast("오늘 할 일은 끝났어요!");
            msg.add("오늘 할 일은 끝났어요!");

            userCoin = sharedPreferences.getInt("userCoin", 0) + 120;

        //    Toast.makeText(getActivity(), "120코인이 적립되었어요!", LENGTH_SHORT).show();
        //    makeToast("120코인이 적립되었어요!");
            msg.add("120코인이 적립되었어요!");

            editor.putInt("userCoin", userCoin);
            editor.apply();
            dailyMission5 = true;

        }
        dailyMission4 = sharedPreferences.getBoolean("dailyMission4", false);
        if(walkResult >= 5000 && dailyMission4 == false){

            userCoin = sharedPreferences.getInt("userCoin", 0) + 60;

            msg.add("60코인이 적립되었어요!");

            editor.putInt("userCoin", userCoin);
            editor.apply();
            dailyMission4 = true;

        }
        dailyMission3 = sharedPreferences.getBoolean("dailyMission3", false);
        if(walkResult >= 1000 && dailyMission3 == false){

            userCoin = sharedPreferences.getInt("userCoin", 0) + 20;

            msg.add("20코인이 적립되었어요!");

            editor.putInt("userCoin", userCoin);
            editor.apply();
            dailyMission3 = true;

        }
        dailyMission2 = sharedPreferences.getBoolean("dailyMission2", false);
        if(walkResult >= 500 && dailyMission2 == false){

            userCoin = sharedPreferences.getInt("userCoin", 0) + 5;

            msg.add("5코인이 적립되었어요!");

            editor.putInt("userCoin", userCoin);
            editor.apply();
            dailyMission2 = true;

        }
        dailyMission1 = sharedPreferences.getBoolean("dailyMission1", false);
        if(walkResult >= 100 && dailyMission1 == false){

            userCoin = sharedPreferences.getInt("userCoin", 0) + 1;

            msg.add("1코인이 적립되었어요!");

            editor.putInt("userCoin", userCoin);
            editor.apply();
            dailyMission1 = true;
        }

        if(!msg.isEmpty()) {
            String text = msg.get(0);
            for (int i = 1; i < msg.size(); i++) {
                text += '\n' + msg.get(i);
            }
            makeToast(text);
        }
        coinText.setText(Integer.toString(sharedPreferences.getInt("userCoin", 0))+"C");


        if (dailyMission1 == true){
            checkBoxX11.setVisibility(View.INVISIBLE);
            checkBoxO11.setVisibility(View.VISIBLE);
        }
        if (dailyMission2 == true){
            checkBoxX12.setVisibility(View.INVISIBLE);
            checkBoxO12.setVisibility(View.VISIBLE);
        }
        if(dailyMission3 == true){
            checkBoxX13.setVisibility(View.INVISIBLE);
            checkBoxO13.setVisibility(View.VISIBLE);
        }
        if (dailyMission4 == true){
            checkBoxX14.setVisibility(View.INVISIBLE);
            checkBoxO14.setVisibility(View.VISIBLE);
        }
        if (dailyMission5 == true){
            checkBoxX15.setVisibility(View.INVISIBLE);
            checkBoxO15.setVisibility(View.VISIBLE);
        }

        editor.putBoolean("dailyMission1", dailyMission1);
        editor.putBoolean("dailyMission2", dailyMission2);
        editor.putBoolean("dailyMission3", dailyMission3);
        editor.putBoolean("dailyMission4", dailyMission4);
        editor.putBoolean("dailyMission5", dailyMission5);

        editor.apply();
    }

    void makeToast(String text) {
        Toast toast = new Toast(this.getActivity());
        toastText.setText(text);
        toast.setDuration(LENGTH_SHORT);
        toast.setView(toastLayout);
        toast.show();
    }
}