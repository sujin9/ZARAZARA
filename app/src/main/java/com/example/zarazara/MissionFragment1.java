package com.example.zarazara;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ComponentActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import com.amitshekhar.DebugDB;

import java.util.Date;

import static android.widget.Toast.LENGTH_SHORT;

//변수를 사용해서 오류가 난다, 메인액티비티에서 바로 usercoin을 얻어오는 방법은???
public class MissionFragment1 extends Fragment {

    View v;
    DBHelper helper;
    SQLiteDatabase db;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

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

    int count = 0; //체크박스 개수 세기, 1개라도 체크일시 00시에 0으로 초기화하고 빈박스로 변경하는 작업 진행
    int userCoin;   //메인에서 불러온 코인값 저장
    Boolean dailyMission1, dailyMission2, dailyMission3, dailyMission4, dailyMission5;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_mission1,container,false);

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
        Toast toast = new Toast(this.getActivity());
        toast.setDuration(LENGTH_SHORT);
        toast.setView(toastLayout);

        String nowDate = sharedPreferences.getString("currentDate", "Default");
        int walkResult = helper.getStep(nowDate);
        Log.d("CheckWalk", "날짜 "+nowDate+"걸음수 "+ Integer.toString(walkResult));

        dailyMission5 = sharedPreferences.getBoolean("dailyMission5", false);
        if(walkResult >= 10000 &&  dailyMission5 == false){
            Toast.makeText(getActivity(), "오늘 할 일은 끝났어요!", LENGTH_SHORT).show();
            //toast.setText("오늘 할 일은 끝났어요!");
            //toast.show();

            userCoin = sharedPreferences.getInt("userCoin", 0) + 120;

            Toast.makeText(getActivity(), "120코인이 적립되었어요!", LENGTH_SHORT).show();
            //toast.setText("120코인이 적립되었어요!");
            //toast.show();

            editor.putInt("userCoin", userCoin);
            editor.apply();
            coinText.setText(Integer.toString(userCoin)+"C");
            dailyMission5 = true;

            count++;
        }
        dailyMission4 = sharedPreferences.getBoolean("dailyMission4", false);
        if(walkResult >= 5000 && dailyMission4 == false){

            userCoin = sharedPreferences.getInt("userCoin", 0) + 60;

            Toast.makeText(getActivity(), "60코인이 적립되었어요!", LENGTH_SHORT).show();

            editor.putInt("userCoin", userCoin);
            dailyMission4 = true;
            editor.apply();
            coinText.setText(Integer.toString(userCoin)+"C");


            count++;
        }
        dailyMission3 = sharedPreferences.getBoolean("dailyMission3", false);
        if(walkResult >= 1000 && dailyMission3 == false){

            userCoin = sharedPreferences.getInt("userCoin", 0) + 20;

            Toast.makeText(getActivity(), "20코인이 적립되었어요!", LENGTH_SHORT).show();

            editor.putInt("userCoin", userCoin);
            dailyMission3 = true;
            editor.apply();
            coinText.setText(Integer.toString(userCoin)+"C");

            count++;
        }
        dailyMission2 = sharedPreferences.getBoolean("dailyMission2", false);
        if(walkResult >= 500 && dailyMission2 == false){

            userCoin = sharedPreferences.getInt("userCoin", 0) + 5;

            Toast.makeText(getActivity(), "5코인이 적립되었어요!", LENGTH_SHORT).show();

            dailyMission2 = true;
            editor.putInt("userCoin", userCoin);
            editor.apply();
            coinText.setText(Integer.toString(userCoin)+"C");

            count++;
        }
        dailyMission1 = sharedPreferences.getBoolean("dailyMission1", false);
        if(walkResult >= 100 && dailyMission1 == false){

            userCoin = sharedPreferences.getInt("userCoin", 0) + 1;

            Toast.makeText(getActivity(), "1코인이 적립되었어요!", LENGTH_SHORT).show();

            editor.putInt("userCoin", userCoin);
            dailyMission1 = true;
            editor.apply();
            coinText.setText(Integer.toString(userCoin)+"C");

            count++;
        }


        Boolean date_result = sharedPreferences.getBoolean("CheckDateChanged", false);
        if(date_result == true && count >= 1) { //00시 되면 체크박스 초기화
            //check표시된 박스
            checkBoxO11.setVisibility(View.INVISIBLE);
            checkBoxO12.setVisibility(View.INVISIBLE);
            checkBoxO13.setVisibility(View.INVISIBLE);
            checkBoxO14.setVisibility(View.INVISIBLE);
            checkBoxO15.setVisibility(View.INVISIBLE);

            //빈박스로 초기화
            checkBoxX11.setVisibility(View.VISIBLE);
            checkBoxX12.setVisibility(View.VISIBLE);
            checkBoxX13.setVisibility(View.VISIBLE);
            checkBoxO14.setVisibility(View.VISIBLE);
            checkBoxO15.setVisibility(View.VISIBLE);

            //초기화
            count = 0;

            editor.putBoolean("CheckDateChanged", false);
            editor.apply();
        }

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
}