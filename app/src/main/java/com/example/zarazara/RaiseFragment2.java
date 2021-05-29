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

public class RaiseFragment2 extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_raise2,container,false);

        ImageButton raiseExerciseBtn = (ImageButton)v.findViewById(R.id.raise_exerciseBtn);
        raiseExerciseBtn.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.raise_exerciseBtn:
                // 버튼 누름 작동 여부 판단 위한 예시
                Toast.makeText(getActivity(), "운동하자", LENGTH_SHORT).show();
                break;
        }
    }
}
