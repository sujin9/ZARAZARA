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

import org.w3c.dom.Text;

import static android.content.Context.MODE_PRIVATE;
import static android.widget.Toast.LENGTH_SHORT;

public class RaiseFragment3 extends Fragment implements View.OnClickListener {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    View toastLayout;
    TextView toastText;
    ProgressBar progressBar;

    // 코인 변동 관련
    TextView coinText;
    int coin;
    int price_play = 150;
    int price_sing = 135;
    int price_read = 170;

    // 캐릭터 수치 변동 관련
    // 장난감  행복+20
    // 노래    행복+15
    // 책      행복+30
    int gaugeHappy;
    int happy_play = 20;
    int happy_sing = 15;
    int happy_read = 30;
    // 취미 경험치 +10
    int totalExp;
    int hobbyExp;
    int getExp = 10;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        sharedPreferences = this.getActivity().getSharedPreferences("shared", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        View v = inflater.inflate(R.layout.fragment_raise3,container,false);

        toastLayout = inflater.inflate(R.layout.custom_toast,(ViewGroup)v.findViewById(R.id.customToastLayout));
        toastText = toastLayout.findViewById(R.id.customToastText);

        coinText = (TextView) getActivity().findViewById(R.id.userCoin);
        progressBar = getActivity().findViewById(R.id.expProgressBar);
        TextView playPrice = (TextView)v.findViewById(R.id.price_play);
        TextView singPrice = (TextView)v.findViewById(R.id.price_sing);
        TextView readPrice = (TextView)v.findViewById(R.id.price_read);
        TextView playExp = (TextView)v.findViewById(R.id.explain_play);
        TextView singExp = (TextView)v.findViewById(R.id.explain_sing);
        TextView readExp = (TextView)v.findViewById(R.id.explain_read);
        ImageButton raiseHobby1Btn = (ImageButton)v.findViewById(R.id.raise_hobbyBtn1);
        ImageButton raiseHobby2Btn = (ImageButton)v.findViewById(R.id.raise_hobbyBtn2);
        ImageButton raiseHobby3Btn = (ImageButton)v.findViewById(R.id.raise_hobbyBtn3);

        playPrice.setText(Integer.toString(price_play)+"C");
        singPrice.setText(Integer.toString(price_sing)+"C");
        readPrice.setText(Integer.toString(price_read)+"C");
        playExp.setText("행복감 +"+happy_play);
        singExp.setText("행복감 +"+happy_sing);
        readExp.setText("행복감 +"+happy_read);
        raiseHobby1Btn.setOnClickListener(this);
        raiseHobby2Btn.setOnClickListener(this);
        raiseHobby3Btn.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        Toast toast = new Toast(this.getActivity());
        toast.setDuration(LENGTH_SHORT);
        coin = sharedPreferences.getInt("userCoin", 0);
        gaugeHappy = sharedPreferences.getInt("gaugeHappy", 0);

        hobbyExp = sharedPreferences.getInt("hobbyExp", 0);
        totalExp = sharedPreferences.getInt("totalExp",0);

        switch(v.getId()) {
            case R.id.raise_hobbyBtn1:  // play

                if(coin<price_play) {
                    //Toast.makeText(getActivity(), "코인이 부족합니다", LENGTH_SHORT).show();
                    toastText.setText("코인이 부족해요");
                }
                else if(gaugeHappy>=100) {
                    toastText.setText("너무 행복해요 !\n즐겁게 쉬고 있어요");
                }
                else {
                    // 코인
                    coin -= price_play;
                    editor.putInt("userCoin", coin);
                    coinText.setText(Integer.toString(coin)+"C");
                    //Toast.makeText(getActivity(), "장난감을 가지고 놀아요! " + Integer.toString(price_play) + " 코인이 차감됩니다", LENGTH_SHORT).show();
                    toastText.setText("장난감을 가지고 놀아요!\n" + Integer.toString(price_play) + " 코인이 차감됐어요");
                    // 수치 변화
                    gaugeHappy += happy_play;
                    if (gaugeHappy>100) gaugeHappy=100;
                    editor.putInt("gaugeHappy", gaugeHappy);
                    editor.apply();
                    // 경험치
                    setExp();
                }
                break;

            case R.id.raise_hobbyBtn2:  // sing
                //Toast.makeText(getActivity(), "놀자2", LENGTH_SHORT).show();
                //coin = sharedPreferences.getInt("userCoin", 0) - price_sing;

                if(coin<price_sing) {
                    //Toast.makeText(getActivity(), "코인이 부족합니다", LENGTH_SHORT).show();
                    toastText.setText("코인이 부족해요");
                }
                else if(gaugeHappy>=100) {
                    toastText.setText("너무 행복해요 !\n즐겁게 쉬고 있어요");
                }
                else {
                    // 코인
                    coin -= price_sing;
                    editor.putInt("userCoin", coin);
                    coinText.setText(Integer.toString(coin)+"C");
                    //Toast.makeText(getActivity(), "노래를 불러요! " + Integer.toString(price_sing) + " 코인이 차감됩니다", LENGTH_SHORT).show();
                    toastText.setText("노래를 불러요!\n" + Integer.toString(price_sing) + " 코인이 차감됐어요");
                    // 수치 변화
                    gaugeHappy += happy_sing;
                    if (gaugeHappy>100) gaugeHappy=100;
                    editor.putInt("gaugeHappy", happy_sing);
                    editor.apply();
                    // 경험치
                    setExp();
                }
                break;

            case R.id.raise_hobbyBtn3:  // read
                //Toast.makeText(getActivity(), "놀자3", LENGTH_SHORT).show();
                //coin = sharedPreferences.getInt("userCoin", 0) - price_read;

                if(coin<price_read) {
                    //Toast.makeText(getActivity(), "코인이 부족합니다", LENGTH_SHORT).show();
                    toastText.setText("코인이 부족해요");
                }
                else if(gaugeHappy>=100) {
                    toastText.setText("너무 행복해요 !\n즐겁게 쉬고 있어요");
                }
                else {
                    // 코인 변화
                    coin -= price_read;
                    editor.putInt("userCoin", coin);
                    coinText.setText(Integer.toString(coin)+"C");
                    //Toast.makeText(getActivity(), "책을 읽어요! " + Integer.toString(price_read) + " 코인이 차감됩니다", LENGTH_SHORT).show();
                    toastText.setText("책을 읽어요!\n" + Integer.toString(price_read) + " 코인이 차감됐어요");
                    // 수치 관련
                    gaugeHappy += happy_read;
                    if (gaugeHappy>100) gaugeHappy=100;
                    editor.putInt("gaugeHappy", gaugeHappy);
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
        hobbyExp += getExp;
        if(hobbyExp>100) hobbyExp=100;
        editor.putInt("hobbyExp", hobbyExp);
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
