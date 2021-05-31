package com.example.zarazara;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static android.widget.Toast.LENGTH_SHORT;

public class MissionFragment2 extends Fragment {
    View v;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstaveState) {
        View v = inflater.inflate(R.layout.fragment_mission2, container, false);
        return v;
    }

    TextView walkText;

    int count = 0; //체크박스 개수 세기, 1개라도 체크일시 00시에 0으로 초기화하고 빈박스로 변경하는 작업 진행
    int userCoin;   //메인에서 불러온 코인값 저장
    int randomCoin = (int) (Math.random()) * 50, TotalStep = 5000; //초기값 설정
    int randomNum = 100;

    WalkActivity walkActivity = new WalkActivity(); //walkActivity에서 걸음수 활용
    MainActivity mainActivity = new MainActivity(); //mainActivity에서 시간과 날짜 활용

    public void setWalkActivity() {
        SharedPreferences sharedPreferences = mainActivity.getSharedPreferences("shared", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (walkActivity.mTotalSteps >= TotalStep && walkActivity.mTotalSteps < TotalStep + 10) {
            Toast.makeText(getActivity(), "다음 미션을 향해서!", LENGTH_SHORT).show();

            v.findViewById(R.id.checkbox_x2).setVisibility(View.INVISIBLE);
            v.findViewById(R.id.checkbox_o2).setVisibility(View.VISIBLE);

            //코인 값 추가 및 말풍선 개수 추가
            userCoin = sharedPreferences.getInt("userCoin", 0) + randomCoin;

            Toast.makeText(getActivity(), "랜덤박스에서" + String.valueOf(randomCoin) + "코인을\n획득했어요!", Toast.LENGTH_LONG).show();

            editor.putInt("userCoin", userCoin);
            editor.apply();

            walkText = v.findViewById(R.id.accumulatemission);  //주소값 받기

            TotalStep += 1000;
            walkText.setText(String.valueOf(TotalStep) + "보 걷기");  //새롭게 설정

            v.findViewById(R.id.checkbox_x2).setVisibility(View.VISIBLE);
            v.findViewById(R.id.checkbox_o2).setVisibility(View.INVISIBLE);

            randomNum += 20;
            randomCoin += (int) (Math.random()) * randomNum;
        }
    }
}
