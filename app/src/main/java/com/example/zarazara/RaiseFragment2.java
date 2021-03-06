package com.example.zarazara;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import static android.content.Context.MODE_PRIVATE;
import static android.widget.Toast.LENGTH_SHORT;

public class RaiseFragment2 extends Fragment implements View.OnClickListener {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    View toastLayout;
    TextView toastText;
    ProgressBar progressBar;

    // 코인 변돋 관련
    TextView coinText;
    int coin;
    int price_exercise = 100;

    // 모찌 수치 변동 관련
    // 운동하면 건강 +20 / 포만감 -10
    int gaugeHealth;
    int gaugeFull;
    int exercise_health = 20;
    int exercise_full = -10;
    // 운동 경험치 +10
    int totalExp;
    int exerciseExp;
    int getExp = 10;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        sharedPreferences = this.getActivity().getSharedPreferences("shared", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        View v = inflater.inflate(R.layout.fragment_raise2,container,false);

        toastLayout = inflater.inflate(R.layout.custom_toast,(ViewGroup)v.findViewById(R.id.customToastLayout));
        toastText = toastLayout.findViewById(R.id.customToastText);

        coinText = (TextView) getActivity().findViewById(R.id.userCoin);
        progressBar = getActivity().findViewById(R.id.expProgressBar);
        TextView exercisePrice = (TextView)v.findViewById(R.id.price_exercise);
        TextView exerciseExp = (TextView)v.findViewById(R.id.explain_exercise);
        ImageButton raiseExerciseBtn = (ImageButton)v.findViewById(R.id.raise_exerciseBtn);

        exercisePrice.setText(Integer.toString(price_exercise)+"C");
        exerciseExp.setText("운동량 +"+exercise_health+"\n포만감 "+exercise_full);
        raiseExerciseBtn.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        Toast toast = new Toast(this.getActivity());
        toast.setDuration(LENGTH_SHORT);
        coin = sharedPreferences.getInt("userCoin", 0);
        gaugeHealth = sharedPreferences.getInt("gaugeHealth", 0);
        gaugeFull = sharedPreferences.getInt("gaugeFull", 0);

        exerciseExp = sharedPreferences.getInt("exerciseExp", 0);
        totalExp = sharedPreferences.getInt("totalExp",0);

        switch(v.getId()) {
            case R.id.raise_exerciseBtn:
                if(coin<price_exercise) {
                    //Toast.makeText(getActivity(), "코인이 부족합니다", LENGTH_SHORT).show();
                    toastText.setText("코인이 부족해요");
                }
                else if(gaugeFull<=10) {
                    toastText.setText("배고파서 운동을 할 수 없어요");
                }
                else if(gaugeHealth>=100) {
                    toastText.setText("엄청 건강해요 !\n잠시 휴식을 취하고 있어요");
                }
                else {
                    // 코인
                    coin -= price_exercise;
                    editor.putInt("userCoin", coin);
                    coinText.setText(Integer.toString(coin)+"C");
                    //Toast.makeText(getActivity(), "운동을 해요! " + Integer.toString(price_exercise) + " 코인이 차감됩니다", LENGTH_SHORT).show();
                    toastText.setText("운동을 해요!\n" + Integer.toString(price_exercise) + " 코인이 차감됐어요");
                    // 수치 변화
                    gaugeHealth += exercise_health;
                    gaugeFull += exercise_full;
                    if (gaugeHealth>100) gaugeHealth=100;
                    if (gaugeFull>100) gaugeFull=100;
                    editor.putInt("gaugeHealth", gaugeHealth);
                    editor.putInt("gaugeFull", gaugeFull);
                    editor.apply();
                    // 경험치
                    setExp();
                }
                break;
        }
        setTotalExp();
        toast.setView(toastLayout);
        toast.show();
    }

    void setExp() {
        exerciseExp += getExp;
        if(exerciseExp>100) exerciseExp=100;
        editor.putInt("exerciseExp", exerciseExp);
        editor.apply();
    }

    void setTotalExp() {
        totalExp = sharedPreferences.getInt("mealExp",0)
                +sharedPreferences.getInt("exerciseExp",0)
                +sharedPreferences.getInt("hobbyExp",0);
        editor.putInt("totalExp", totalExp);
        editor.apply();
        progressBar.setProgress(totalExp);
    }
}
