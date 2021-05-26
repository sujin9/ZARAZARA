package com.example.zarazara;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class TutorialFragment extends Fragment {

    private Button buttonTutorialEnd;

    private TutorialFragment(){
    }

    public static TutorialFragment newInstance(int page){
        TutorialFragment tutorialFragment = new TutorialFragment();
        Bundle args = new Bundle();
        args.putInt("tutorial_page", page);
        tutorialFragment.setArguments(args);
        return tutorialFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        int page = this.getArguments().getInt("tutorial_page");
        View view = inflater.inflate(page, container, false);

        if(page == R.layout.tutorial4){
            buttonTutorialEnd = view.findViewById(R.id.tutorial_end_button);
            buttonTutorialEnd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view){
                    getActivity().startActivity(new Intent(getActivity(), MainActivity.class));
                    getActivity().finish();
                }
            });
        }

        return view;
    }

}
