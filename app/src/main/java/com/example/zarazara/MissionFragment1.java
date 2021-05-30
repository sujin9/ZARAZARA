package com.example.zarazara;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ComponentActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

public class MissionFragment1 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mission1, container, false);
    }

    int count = 0; //체크박스 개수 세기, 1개라도 체크일시 00시에 0으로 초기화하고 빈박스로 변경하는 작업 진행
    WalkActivity walkActivity = new WalkActivity(); //walkActivity에서 시간과 날짜 활용하기 위함

    public void setWalkActivity(){
        if(walkActivity.mCurrentSteps >= 100 && walkActivity.mCurrentSteps < 500){
            R.id.checkbox_x11.setVisibility(View.INVISIBLE);
            R.id.checkbox_o11.setVisibility(View.VISIBLE);
            count++;
        }
        else if(walkActivity.mCurrentSteps >= 500 && walkActivity.mCurrentSteps < 1000){
            R.id.checkbox_x12.setVisibility(View.INVISIBLE);
            R.id.checkbox_o12.setVisibility(View.VISIBLE);
            count++;
        }
        else if(walkActivity.mCurrentSteps >= 1000 && walkActivity.mCurrentSteps < 5000){
            R.id.checkbox_x13.setVisibility(View.INVISIBLE);
            R.id.checkbox_o13.setVisibility(View.VISIBLE);
            count++;
        }
        else if(walkActivity.mCurrentSteps >= 5000 && walkActivity.mCurrentSteps < 10000){
            R.id.checkbox_x14.setVisibility(View.INVISIBLE);
            R.id.checkbox_o14.setVisibility(View.VISIBLE);
            count++;
        }
        else{
            R.id.checkbox_x15.setVisibility(View.INVISIBLE);
            R.id.checkbox_o15.setVisibility(View.VISIBLE);
            count++;
        }

        /*
        if(walkActivity. && count >= 1){ //00시 되면 체크박스 초기화, 시간변수 어떻게 사용?
            R.id.checkbox_o11.setVisibility(View.INVISIBLE);
            R.id.checkbox_o12.setVisibility(View.INVISIBLE);
            R.id.checkbox_o13.setVisibility(View.INVISIBLE);
            R.id.checkbox_o14.setVisibility(View.INVISIBLE);
            R.id.checkbox_o15.setVisibility(View.INVISIBLE);

            R.id.checkbox_x11.setVisibility(View.VISIBLE);
            R.id.checkbox_x12.setVisibility(View.VISIBLE);
            R.id.checkbox_x13.setVisibility(View.VISIBLE);
            R.id.checkbox_x14.setVisibility(View.VISIBLE);
            R.id.checkbox_x15.setVisibility(View.VISIBLE);
            //초기화
            count = 0;
        }
         */
    }
}
