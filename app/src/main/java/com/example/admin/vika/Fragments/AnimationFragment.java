package com.example.admin.vika.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.admin.vika.R;

public class AnimationFragment extends Fragment {
    private ImageView image1;
    private ImageView image2;
    private ImageView image3;
    private ImageView image4;
    private Animation animation1;
    private Animation animation2;
    private Animation animation3;
    private Animation animation4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_animation_fragment, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        image1 = (ImageView) getActivity().findViewById(R.id.image);
        image1.setImageResource(R.drawable.rectangle_1);
        animation1 = AnimationUtils.loadAnimation(getActivity(), R.anim.blink);
        image1.startAnimation(animation1);


        image2 = (ImageView) getActivity().findViewById(R.id.image2);
        image2.setImageResource(R.drawable.oval_1);
        animation2 = AnimationUtils.loadAnimation(getActivity(), R.anim.bounce);
        image2.startAnimation(animation2);


        image3 = (ImageView) getActivity().findViewById(R.id.image3);
        image3.setImageResource(R.drawable.rectangle_2);
        animation3 = AnimationUtils.loadAnimation(getActivity(), R.anim.move);
        image3.startAnimation(animation3);


        image4 = (ImageView) getActivity().findViewById(R.id.image4);
        image4.setImageResource(R.drawable.oval_2);
        animation4 = AnimationUtils.loadAnimation(getActivity(), R.anim.zoom_in);
        image4.startAnimation(animation4);

    }
}