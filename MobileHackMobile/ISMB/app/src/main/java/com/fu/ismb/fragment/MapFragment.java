package com.fu.ismb.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fu.ismb.R;

import de.greenrobot.event.EventBus;

/**
 * Created by PhucNT on 10/1/2016.
 */

public class MapFragment extends Fragment {
    private TextView textView;
    private TextView textViewX;
    private TextView textViewY;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);
        textView = (TextView) v.findViewById(R.id.currentArea);
        textViewX = (TextView) v.findViewById(R.id.currentX);
        textViewY = (TextView) v.findViewById(R.id.currentY);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().registerSticky(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    public void onEventMainThread(AreaName event) {
        textView.setText(event.getName());
        textViewX.setText(String.valueOf(event.getX()));
        textViewY.setText(String.valueOf(event.getY()));
    }
}



