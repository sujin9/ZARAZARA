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

    TextView coinText;
    int coin;
    int price_meal = 50;
    int price_snack = 70;

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

        mealPrice.setText(Integer.toString(price_meal));    // 글씨 뒤에 C 표시 어떻게?
        snackPrice.setText(Integer.toString(price_snack));
        raiseMealBtn.setOnClickListener(this);
        raiseSnackBtn.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        Toast toast = new Toast(this.getActivity());
        toast.setDuration(LENGTH_SHORT);

        switch(v.getId()) {
            case R.id.raise_mealBtn:
                coin = sharedPreferences.getInt("userCoin", 0) - price_meal;

                if(coin>=0) {
                    editor.putInt("userCoin", coin);
                    editor.apply();
                    coinText.setText(Integer.toString(coin));
                    //Toast.makeText(getActivity(), "밥을 먹어요! " + Integer.toString(price_meal) + " 코인이 차감됩니다", LENGTH_SHORT).show();
                    toastText.setText("밥을 먹어요!\n" + Integer.toString(price_meal) + " 코인이 차감됩니다");
                }
                else {
                    //Toast.makeText(getActivity(), "코인이 부족합니다", LENGTH_SHORT).show();
                    toastText.setText("코인이 부족합니다");
                }
                break;

            case R.id.raise_snackBtn:
                coin = sharedPreferences.getInt("userCoin", 0) - price_snack;

                if(coin>=0) {
                    editor.putInt("userCoin", coin);
                    editor.apply();
                    coinText.setText(Integer.toString(coin));
                    //Toast.makeText(getActivity(), "간식을 먹어요! " + Integer.toString(price_snack) + " 코인이 차감됩니다", LENGTH_SHORT).show();
                    toastText.setText("간식을 먹어요!\n" + Integer.toString(price_snack) + " 코인이 차감됩니다");
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

