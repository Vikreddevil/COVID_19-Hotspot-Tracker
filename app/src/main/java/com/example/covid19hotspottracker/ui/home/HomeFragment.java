package com.example.covid19hotspottracker.ui.home;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.covid19hotspottracker.R;
import com.example.covid19hotspottracker.TrackingPageActivity;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private Activity mActivity;
    private RelativeLayout start_tracking_relative_layout;
    private RelativeLayout affected_places_in_the_city_relative_layout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        start_tracking_relative_layout=root.findViewById(R.id.start_tracking_relative_layout);
        affected_places_in_the_city_relative_layout=root.findViewById(R.id.affected_places_in_the_city_relative_layout);

        start_tracking_relative_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mActivity.finish();
                Intent intent=new Intent(mActivity, TrackingPageActivity.class);
                startActivity(intent);

            }
        });

        affected_places_in_the_city_relative_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
//        final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }
    @TargetApi(23)
    @Override public void onAttach(Context context) {
        //This method avoid to call super.onAttach(context) if I'm not using api 23 or more
        //if (Build.VERSION.SDK_INT >= 23) {
        super.onAttach(context);
        setRetainInstance(true);
        onAttachToContext(context);
        //}
    }

    /*
     * Deprecated on API 23
     * Use onAttachToContext instead
     */
    @SuppressWarnings("deprecation")
    @Override public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (Build.VERSION.SDK_INT < 23) {
            onAttachToContext(activity);
        }
    }
    private void onAttachToContext(Context context){

        mActivity=(Activity)context;
    }
}
