package com.ndusenior.fix;

import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ndusenior.fix.AssistantClasses.NotSoIntelligentAssistant;

import java.io.File;

/**
 * Created by antho on 3/29/2017.
 */

public class FragmentBot extends Fragment {

    private NotSoIntelligentAssistant theBot;
    private TextView FirstText;

    private ImageView UserDp;
    private ImageView sendButton;
    private EditText UserMessage;

    private RelativeLayout mainLayout;


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // ------- Code Below this is just initializing stuff -------- //

        mainLayout = (RelativeLayout) getView().findViewById(R.id.mainBotLayout);

        theBot = new NotSoIntelligentAssistant(mainLayout,getContext());
        sendButton = (ImageView) getView().findViewById(R.id.SendBtn);
        UserMessage = (EditText) getView().findViewById(R.id.MessageText);





        // ------------------ Bezyede Intializing ---------------------//




        sendButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                    theBot.Read(UserMessage.getText().toString());
                    UserMessage.setText("");


            }
        });

        getActivity().setTitle("Assistant");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View theView = inflater.inflate(R.layout.fragment_bot,container,false);

        return theView;
    }
}
