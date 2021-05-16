package com.example.zarazara;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class RaiseFragment1 extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_raise1,container,false);

        ImageButton raiseMealBtn = (ImageButton)v.findViewById(R.id.raise_mealBtn);
        ImageButton raiseSnackBtn = (ImageButton)v.findViewById(R.id.raise_snackBtn);
        raiseMealBtn.setOnClickListener(this);
        raiseSnackBtn.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.raise_mealBtn:
                break;
            case R.id.raise_snackBtn:
                break;
        }
    }
}
