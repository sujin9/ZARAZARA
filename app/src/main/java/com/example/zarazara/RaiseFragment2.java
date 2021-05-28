package com.example.zarazara;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;

import static android.content.Context.MODE_PRIVATE;
import static android.widget.Toast.LENGTH_SHORT;

public class RaiseFragment2 extends Fragment implements View.OnClickListener {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    View toastLayout;
    TextView toastText;

    TextView coinText;
    int coin;
    int price_exercise = 100;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        sharedPreferences = this.getActivity().getSharedPreferences("shared", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        View v = inflater.inflate(R.layout.fragment_raise2,container,false);

        toastLayout = inflater.inflate(R.layout.custom_toast,(ViewGroup)v.findViewById(R.id.customToastLayout));
        toastText = toastLayout.findViewById(R.id.customToastText);

        coinText = (TextView) getActivity().findViewById(R.id.userCoin);
        TextView exercisePrice = (TextView)v.findViewById(R.id.price_exercise);
        ImageButton raiseExerciseBtn = (ImageButton)v.findViewById(R.id.raise_exerciseBtn);

        exercisePrice.setText(Integer.toString(price_exercise));
        raiseExerciseBtn.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        Toast toast = new Toast(this.getActivity());
        toast.setDuration(LENGTH_SHORT);

        switch(v.getId()) {
            case R.id.raise_exerciseBtn:
                // 버튼 누름 작동 여부 판단 위한 예시
                coin = sharedPreferences.getInt("userCoin", 0) - price_exercise;

                if(coin>=0) {
                    editor.putInt("userCoin", coin);
                    editor.apply();
                    coinText.setText(Integer.toString(coin));
                    //Toast.makeText(getActivity(), "운동을 해요! " + Integer.toString(price_exercise) + " 코인이 차감됩니다", LENGTH_SHORT).show();
                    toastText.setText("운동을 해요!\n" + Integer.toString(price_exercise) + " 코인이 차감됩니다");
                }
                else {
                    //Toast.makeText(getActivity(), "코인이 부족합니다", LENGTH_SHORT).show();
                    toastText.setText("코인이 부족합니다");
                }
                break;
        }
        toast.setView(toastLayout);
        toast.show();
    }
}
