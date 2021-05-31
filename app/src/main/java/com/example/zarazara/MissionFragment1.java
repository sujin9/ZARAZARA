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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_mission1,container,false);

        sharedPreferences = this.getActivity().getSharedPreferences("shared", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        helper = new DBHelper(getActivity(), "stepStore.db", null, 1);
        db = helper.getWritableDatabase();
        helper.onCreate(db);

        checkBoxX11 = v.findViewById(R.id.checkbox_x11);
        checkBoxX12 = v.findViewById(R.id.checkbox_x12);
        checkBoxX13 = v.findViewById(R.id.checkbox_x13);
        checkBoxX14 = v.findViewById(R.id.checkbox_x14);
        checkBoxX15 = v.findViewById(R.id.checkbox_x15);

        checkBoxO11 = v.findViewById(R.id.checkbox_o11);
        checkBoxO12 = v.findViewById(R.id.checkbox_o12);
        checkBoxO13 = v.findViewById(R.id.checkbox_o13);
        checkBoxO14 = v.findViewById(R.id.checkbox_o15);
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

        if(walkResult >= 10000){
            Toast.makeText(getActivity(), "오늘 할 일은 끝났어요!", LENGTH_SHORT).show();

            checkBoxX15.setVisibility(View.INVISIBLE);
            checkBoxO15.setVisibility(View.VISIBLE);

            userCoin = sharedPreferences.getInt("userCoin", 0) + 120;

            Toast.makeText(getActivity(), "120코인이 적립되었어요!", LENGTH_SHORT).show();

            editor.putInt("userCoin", userCoin);
            editor.apply();

            count++;
        }
        else if(walkResult >= 5000){
            Toast.makeText(getActivity(), "10000보 걷자", LENGTH_SHORT).show();

            checkBoxX14.setVisibility(View.INVISIBLE);
            checkBoxO14.setVisibility(View.VISIBLE);

            userCoin = sharedPreferences.getInt("userCoin", 0) + 60;

            Toast.makeText(getActivity(), "60코인이 적립되었어요!", LENGTH_SHORT).show();

            editor.putInt("userCoin", userCoin);
            editor.apply();

            count++;
        }
        else if(walkResult >= 1000){
            Toast.makeText(getActivity(), "5000보 걷자", LENGTH_SHORT).show();

            checkBoxX13.setVisibility(View.INVISIBLE);
            checkBoxO13.setVisibility(View.VISIBLE);

            userCoin = sharedPreferences.getInt("userCoin", 0) + 20;

            Toast.makeText(getActivity(), "20코인이 적립되었어요!", LENGTH_SHORT).show();

            editor.putInt("userCoin", userCoin);
            editor.apply();

            count++;
        }
        else if(walkResult >= 500){
            Toast.makeText(getActivity(), "1000보 걷자", LENGTH_SHORT).show();

            checkBoxX12.setVisibility(View.INVISIBLE);
            checkBoxO12.setVisibility(View.VISIBLE);

            userCoin = sharedPreferences.getInt("userCoin", 0) + 5;

            Toast.makeText(getActivity(), "5코인이 적립되었어요!", LENGTH_SHORT).show();

            editor.putInt("userCoin", userCoin);
            editor.apply();

            count++;
        }
        else if(walkResult >= 100){
            Toast.makeText(getActivity(), "500보 걷자", LENGTH_SHORT).show();

            checkBoxX11.setVisibility(View.INVISIBLE);
            checkBoxO11.setVisibility(View.VISIBLE);

            userCoin = sharedPreferences.getInt("userCoin", 0) + 1;

            Toast.makeText(getActivity(), "1코인이 적립되었어요!", LENGTH_SHORT).show();

            editor.putInt("userCoin", userCoin);
            editor.apply();

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
        }
    }
}