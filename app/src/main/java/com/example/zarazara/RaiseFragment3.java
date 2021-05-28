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

public class RaiseFragment3 extends Fragment implements View.OnClickListener {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    View toastLayout;
    TextView toastText;

    TextView coinText;
    int coin;
    int price_play = 150;
    int price_sing = 135;
    int price_read = 170;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        sharedPreferences = this.getActivity().getSharedPreferences("shared", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        View v = inflater.inflate(R.layout.fragment_raise3,container,false);

        toastLayout = inflater.inflate(R.layout.custom_toast,(ViewGroup)v.findViewById(R.id.customToastLayout));
        toastText = toastLayout.findViewById(R.id.customToastText);

        coinText = (TextView) getActivity().findViewById(R.id.userCoin);
        TextView playPrice = (TextView)v.findViewById(R.id.price_play);
        TextView singPrice = (TextView)v.findViewById(R.id.price_sing);
        TextView readPrice = (TextView)v.findViewById(R.id.price_read);
        ImageButton raiseHobby1Btn = (ImageButton)v.findViewById(R.id.raise_hobbyBtn1);
        ImageButton raiseHobby2Btn = (ImageButton)v.findViewById(R.id.raise_hobbyBtn2);
        ImageButton raiseHobby3Btn = (ImageButton)v.findViewById(R.id.raise_hobbyBtn3);

        playPrice.setText(Integer.toString(price_play));
        singPrice.setText(Integer.toString(price_sing));
        readPrice.setText(Integer.toString(price_read));
        raiseHobby1Btn.setOnClickListener(this);
        raiseHobby2Btn.setOnClickListener(this);
        raiseHobby3Btn.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        Toast toast = new Toast(this.getActivity());
        toast.setDuration(LENGTH_SHORT);

        switch(v.getId()) {
            case R.id.raise_hobbyBtn1:
                // 버튼 누름 작동 여부 판단 위한 예시
                //Toast.makeText(getActivity(), "놀자1", LENGTH_SHORT).show();
                coin = sharedPreferences.getInt("userCoin", 0) - price_play;

                if(coin>=0) {
                    editor.putInt("userCoin", coin);
                    editor.apply();
                    coinText.setText(Integer.toString(coin));
                    //Toast.makeText(getActivity(), "장난감을 가지고 놀아요! " + Integer.toString(price_play) + " 코인이 차감됩니다", LENGTH_SHORT).show();
                    toastText.setText("장난감을 가지고 놀아요!\n" + Integer.toString(price_play) + " 코인이 차감됩니다");
                }
                else {
                    //Toast.makeText(getActivity(), "코인이 부족합니다", LENGTH_SHORT).show();
                    toastText.setText("코인이 부족합니다");
                }
                break;
            case R.id.raise_hobbyBtn2:
                //Toast.makeText(getActivity(), "놀자2", LENGTH_SHORT).show();
                coin = sharedPreferences.getInt("userCoin", 0) - price_sing;

                if(coin>=0) {
                    editor.putInt("userCoin", coin);
                    editor.apply();
                    coinText.setText(Integer.toString(coin));
                    //Toast.makeText(getActivity(), "노래를 불러요! " + Integer.toString(price_sing) + " 코인이 차감됩니다", LENGTH_SHORT).show();
                    toastText.setText("노래를 불러요!\n" + Integer.toString(price_sing) + " 코인이 차감됩니다");
                }
                else {
                    //Toast.makeText(getActivity(), "코인이 부족합니다", LENGTH_SHORT).show();
                    toastText.setText("코인이 부족합니다");
                }
                break;
            case R.id.raise_hobbyBtn3:
                //Toast.makeText(getActivity(), "놀자3", LENGTH_SHORT).show();
                coin = sharedPreferences.getInt("userCoin", 0) - price_read;

                if(coin>=0) {
                    editor.putInt("userCoin", coin);
                    editor.apply();
                    coinText.setText(Integer.toString(coin));
                    //Toast.makeText(getActivity(), "책을 읽어요! " + Integer.toString(price_read) + " 코인이 차감됩니다", LENGTH_SHORT).show();
                    toastText.setText("책을 읽어요!\n" + Integer.toString(price_read) + " 코인이 차감됩니다");
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
