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

import java.util.Timer;

import static android.widget.Toast.LENGTH_SHORT;

public class MissionFragment2 extends Fragment {
    View v;
    DBHelper helper;
    SQLiteDatabase db;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    TextView walkText;
    String walkText1 = "";

    ImageView checkBoxX2;
    ImageView checkBoxO2;

    int userCoin;   //메인에서 불러온 코인값 저장
    int randomCoin = (int) (Math.random()*60) + 1, TotalStep; //randomCoin은 0 ~ 50 정수, 5000보부터 시작
    int randomNum = 70;    //변화하는 수
    int accMission;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstaveState) {
        View v = inflater.inflate(R.layout.fragment_mission2, container, false);

        sharedPreferences = this.getActivity().getSharedPreferences("shared", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        helper = new DBHelper(getActivity(), "stepStore.db", null, 1);
        db = helper.getWritableDatabase();
        helper.onCreate(db);

        checkBoxX2 = v.findViewById(R.id.checkbox_x2);
        checkBoxO2 = v.findViewById(R.id.checkbox_o2);

        walkText = v.findViewById(R.id.accumulatemission);

        setWalkActivity();

        return v;
    }

    public void setWalkActivity() {
        int walkTotal = helper.getTotalStep();
        TotalStep = sharedPreferences.getInt("totalStep", 5000);
        accMission = sharedPreferences.getInt("accMission", 0);
        walkText1 = sharedPreferences.getString("walkText", "5000보 걷기");

        if(walkTotal == TotalStep){
            accMission = 0;
            editor.putInt("accMission", accMission);
            editor.apply();
        }

        if (walkTotal >= TotalStep) {
            checkBoxX2.setVisibility(View.INVISIBLE);
            checkBoxO2.setVisibility(View.VISIBLE);

            //코인 값 추가 및 말풍선 개수 추가
            if(accMission == 0) {
                userCoin = sharedPreferences.getInt("userCoin", 0) + randomCoin;

                Toast.makeText(getActivity(), "랜덤박스에서 " + String.valueOf(randomCoin) + "코인을 획득했어요!", Toast.LENGTH_LONG).show();

                editor.putInt("userCoin", userCoin);

                accMission = 1;
                editor.putInt("accMission", 1);

                editor.apply();
            }

            TotalStep += 5000;
            editor.putInt("totalStep", TotalStep);
            editor.apply();

            walkText1 = (String.valueOf(TotalStep) + "보 걷기");
            Log.d("walkText1", walkText1);
            editor.putString("walkText", walkText1);
            editor.apply();

            walkText.setText(walkText1);

            checkBoxO2.setVisibility(View.INVISIBLE);
            checkBoxX2.setVisibility(View.VISIBLE);

            randomNum += 50;
            randomCoin += (int) (Math.random() * randomNum) + 1;

            Toast.makeText(getActivity(), "다음 미션을 향해서!", LENGTH_SHORT).show();
        }
    }
}
