package com.example.zarazara;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static android.widget.Toast.LENGTH_SHORT;

public class RaiseFragment3 extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_raise3,container,false);

        ImageButton raiseHobby1Btn = (ImageButton)v.findViewById(R.id.raise_hobbyBtn1);
        ImageButton raiseHobby2Btn = (ImageButton)v.findViewById(R.id.raise_hobbyBtn2);
        ImageButton raiseHobby3Btn = (ImageButton)v.findViewById(R.id.raise_hobbyBtn3);
        raiseHobby1Btn.setOnClickListener(this);
        raiseHobby2Btn.setOnClickListener(this);
        raiseHobby3Btn.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.raise_hobbyBtn1:
                // 버튼 누름 작동 여부 판단 위한 예시
                Toast.makeText(getActivity(), "놀자1", LENGTH_SHORT).show();
                break;
            case R.id.raise_hobbyBtn2:
                Toast.makeText(getActivity(), "놀자2", LENGTH_SHORT).show();
                break;
            case R.id.raise_hobbyBtn3:
                Toast.makeText(getActivity(), "놀자3", LENGTH_SHORT).show();
                break;
        }
    }
}
