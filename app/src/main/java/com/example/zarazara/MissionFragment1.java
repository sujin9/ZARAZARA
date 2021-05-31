package com.example.zarazara;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import java.util.Date;

import static android.widget.Toast.LENGTH_SHORT;

public class MissionFragment1 extends Fragment {
    View v;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_mission1,container,false);
        return v;
    }

    int count = 0; //체크박스 개수 세기, 1개라도 체크일시 00시에 0으로 초기화하고 빈박스로 변경하는 작업 진행
    int userCoin;   //메인에서 불러온 코인값 저장

    WalkActivity walkActivity = new WalkActivity(); //walkActivity에서 걸음수 활용
    MainActivity mainActivity = new MainActivity(); //mainActivity에서 시간과 날짜 활용

    public void setWalkActivity(){
        //열어주기 및 editor 사용할 수 있게
        SharedPreferences sharedPreferences = mainActivity.getSharedPreferences("shared", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if(walkActivity.mCurrentSteps >= 100 && walkActivity.mCurrentSteps < 500){
            Toast.makeText(getActivity(), "500보 걷자", LENGTH_SHORT).show();

            //말풍선 change
            v.findViewById(R.id.checkbox_x11).setVisibility(View.INVISIBLE);
            v.findViewById(R.id.checkbox_o11).setVisibility(View.VISIBLE);

            //코인 값 추가 및 말풍선 개수 추가
            userCoin = sharedPreferences.getInt("userCoin", 0) + 1;

            Toast.makeText(getActivity(), "1코인이 적립되었어요!", LENGTH_SHORT).show();

            editor.putInt("userCoin", userCoin);
            editor.apply();

            count++;
        }
        else if(walkActivity.mCurrentSteps >= 500 && walkActivity.mCurrentSteps < 1000){
            Toast.makeText(getActivity(), "1000보 걷자", LENGTH_SHORT).show();

            v.findViewById(R.id.checkbox_x12).setVisibility(View.INVISIBLE);
            v.findViewById(R.id.checkbox_o12).setVisibility(View.VISIBLE);

            userCoin = sharedPreferences.getInt("userCoin", 0) + 5;

            Toast.makeText(getActivity(), "5코인이 적립되었어요!", LENGTH_SHORT).show();

            editor.putInt("userCoin", userCoin);
            editor.apply();
            count++;
        }
        else if(walkActivity.mCurrentSteps >= 1000 && walkActivity.mCurrentSteps < 5000){
            Toast.makeText(getActivity(), "5000보 걷자", LENGTH_SHORT).show();

            v.findViewById(R.id.checkbox_x13).setVisibility(View.INVISIBLE);
            v.findViewById(R.id.checkbox_o13).setVisibility(View.VISIBLE);

            userCoin = sharedPreferences.getInt("userCoin", 0) + 20;

            Toast.makeText(getActivity(), "20코인이 적립되었어요!", LENGTH_SHORT).show();

            editor.putInt("userCoin", userCoin);
            editor.apply();

            count++;
        }
        else if(walkActivity.mCurrentSteps >= 5000 && walkActivity.mCurrentSteps < 10000){
            Toast.makeText(getActivity(), "10000보 걷자", LENGTH_SHORT).show();

            v.findViewById(R.id.checkbox_x14).setVisibility(View.INVISIBLE);
            v.findViewById(R.id.checkbox_o14).setVisibility(View.VISIBLE);

            userCoin = sharedPreferences.getInt("userCoin", 0) + 60;

            Toast.makeText(getActivity(), "60코인이 적립되었어요!", LENGTH_SHORT).show();

            editor.putInt("userCoin", userCoin);
            editor.apply();

            count++;
        }
        else{
            Toast.makeText(getActivity(), "오늘 할일 끝!", LENGTH_SHORT).show();

            v.findViewById(R.id.checkbox_x15).setVisibility(View.INVISIBLE);
            v.findViewById(R.id.checkbox_o15).setVisibility(View.VISIBLE);

            userCoin = sharedPreferences.getInt("userCoin", 0) + 120;

            Toast.makeText(getActivity(), "120코인이 적립되었어요!", LENGTH_SHORT).show();

            editor.putInt("userCoin", userCoin);
            editor.apply();

            count++;
        }

        //날짜 불러오기
        String date1 = sharedPreferences.getString("currentDate", "Deafault");
        String date2 = mainActivity.getNowDate();

        Boolean date_result = mainActivity.checkDateChanged(date1, date2);
        if(date_result == true && count >= 1) { //00시 되면 체크박스 초기화
            //check표시된 박스
            v.findViewById(R.id.checkbox_o11).setVisibility(View.INVISIBLE);
            v.findViewById(R.id.checkbox_o12).setVisibility(View.INVISIBLE);
            v.findViewById(R.id.checkbox_o13).setVisibility(View.INVISIBLE);
            v.findViewById(R.id.checkbox_o14).setVisibility(View.INVISIBLE);
            v.findViewById(R.id.checkbox_o15).setVisibility(View.INVISIBLE);

            //빈박스로 초기화
            v.findViewById(R.id.checkbox_x11).setVisibility(View.VISIBLE);
            v.findViewById(R.id.checkbox_x12).setVisibility(View.VISIBLE);
            v.findViewById(R.id.checkbox_x13).setVisibility(View.VISIBLE);
            v.findViewById(R.id.checkbox_x14).setVisibility(View.VISIBLE);
            v.findViewById(R.id.checkbox_x15).setVisibility(View.VISIBLE);

            //초기화
            count = 0;
        }
    }
}
