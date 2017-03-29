package com.ndusenior.fix;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * Created by antho on 3/28/2017.
 */

public class ControlsMenu extends Fragment {

    EditText[] editTextArray;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Main Device");

        editTextArray = new EditText[] {
                (EditText) getView().findViewById(R.id.OutputOneName),
                (EditText) getView().findViewById(R.id.OutputTwoName),
                (EditText) getView().findViewById(R.id.OutputThreeName),
                (EditText) getView().findViewById(R.id.OutputFourName)};

        editTextArray[1].setText("Test");



    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.menu_controlsfragment,container,false);
    }
}
