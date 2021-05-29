package com.example.zarazara;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;

import java.util.Map;
import java.util.Set;
import java.util.zip.Inflater;

import static android.content.Context.MODE_PRIVATE;
import static android.widget.Toast.LENGTH_SHORT;

public class RaiseFragment1 extends Fragment implements View.OnClickListener {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    View toastLayout;
    TextView toastText;

    // 코인 변동 관련 변수
    TextView coinText;
    int coin;
    int price_meal = 50;
    int price_snack = 70;

    // 모찌 상태 수치 변동 관련 변수
    // 밥을 먹으면 포만감 +30
    // 간식을 먹으면 포만감 +20 / 행복 +10
    // 포만감이 이미 full -> 먹을 수 없음
    int gaugeFull;
    int gaugeHappy;
    int full_meal = 30;
    int full_snack = 20;
    int happy_snack = 10;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        sharedPreferences = this.getActivity().getSharedPreferences("shared", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        View v = inflater.inflate(R.layout.fragment_raise1,container,false);

        toastLayout = inflater.inflate(R.layout.custom_toast,(ViewGroup)v.findViewById(R.id.customToastLayout));
        toastText = toastLayout.findViewById(R.id.customToastText);

        coinText = (TextView) getActivity().findViewById(R.id.userCoin);
        TextView mealPrice = (TextView)v.findViewById(R.id.price_meal);
        TextView snackPrice = (TextView)v.findViewById(R.id.price_snack);
        ImageButton raiseMealBtn = (ImageButton)v.findViewById(R.id.raise_mealBtn);
        ImageButton raiseSnackBtn = (ImageButton)v.findViewById(R.id.raise_snackBtn);

        mealPrice.setText(Integer.toString(price_meal)+"C");
        snackPrice.setText(Integer.toString(price_snack)+"C");
        raiseMealBtn.setOnClickListener(this);
        raiseSnackBtn.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        Toast toast = new Toast(this.getActivity());
        toast.setDuration(LENGTH_SHORT);
        coin = sharedPreferences.getInt("userCoin", 0);
        gaugeFull = sharedPreferences.getInt("gaugeFull", 0);
        gaugeHappy = sharedPreferences.getInt("gaugeHappy", 0);

        switch(v.getId()) {

            case R.id.raise_mealBtn:
            //    coin = sharedPreferences.getInt("userCoin", 0);
            //    gaugeFull = sharedPreferences.getInt("gaugeFull", 0);

                if (coin<price_meal){
                    //Toast.makeText(getActivity(), "코인이 부족합니다", LENGTH_SHORT).show();
                    toastText.setText("코인이 부족합니다");
                }
                else if (gaugeFull>=100) {
                    toastText.setText("이미 배불러요 !");
                }
                else {  // if(coin>=price_meal && gaugeFull<100)
                    // 코인
                    coin -= price_meal;
                    editor.putInt("userCoin", coin);
                    coinText.setText(Integer.toString(coin));
                    //Toast.makeText(getActivity(), "밥을 먹어요! " + Integer.toString(price_meal) + " 코인이 차감됩니다", LENGTH_SHORT).show();
                    toastText.setText("밥을 먹어요!\n" + Integer.toString(price_meal) + " 코인이 차감됩니다");
                    // 포만감
                    gaugeFull += full_meal;
                    if(gaugeFull>100) gaugeFull=100;    //MAX 100
                    editor.putInt("gaugeFull", gaugeFull);

                    editor.apply();
                }
                break;

            case R.id.raise_snackBtn:
            //    coin = sharedPreferences.getInt("userCoin", 0);
            //    gaugeFull = sharedPreferences.getInt("gaugeFull", 0);
            //    gaugeHappy = sharedPreferences.getInt("gaugeHappy", 0);

                if(coin<price_snack) {
                    //Toast.makeText(getActivity(), "코인이 부족합니다", LENGTH_SHORT).show();
                    toastText.setText("코인이 부족합니다");
                }
                else if (gaugeFull>=100) {
                    toastText.setText("이미 배불러요 !");
                }
                else {
                    // 코인
                    coin -= price_snack;
                    editor.putInt("userCoin", coin);
                    coinText.setText(Integer.toString(coin));
                    //Toast.makeText(getActivity(), "간식을 먹어요! " + Integer.toString(price_snack) + " 코인이 차감됩니다", LENGTH_SHORT).show();
                    toastText.setText("간식을 먹어요!\n" + Integer.toString(price_snack) + " 코인이 차감됩니다");
                    // 수치 변화
                    gaugeFull += full_snack;
                    gaugeHappy += happy_snack;
                    if(gaugeFull>100) gaugeFull=100;
                    if(gaugeHappy>100) gaugeHappy=100;
                    editor.putInt("gaugeFull", gaugeFull);
                    editor.putInt("gaugeHappy", gaugeHappy);

                    editor.apply();
                }
                break;
        }
        toast.setView(toastLayout);
        toast.show();
    }
}

