package com.ndusenior.fix.AssistantClasses;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.TimedText;
import android.text.format.Time;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ndusenior.fix.R;

import org.w3c.dom.Text;

import java.security.SecureRandom;
import java.security.Timestamp;
import java.util.Calendar;
import java.util.Random;


/**
 * Created by antho on 4/16/2017.
 */

public class NotSoIntelligentAssistant {

    private int Randomizer;
    private int NumberOfSentencesIKnow = 6;
    private String WelcomingTexts = "Hello,\nYour wish is my command";

    private String theAnswer = "I'm not that smart";
    private RelativeLayout theLayout;

    private Context theMainContext;

    public NotSoIntelligentAssistant(RelativeLayout LayoutToExtend, Context context){
        theLayout = LayoutToExtend;
        theMainContext = context;
        CreateMyEnteringSpeech();
    }


    public String HelloText(){

        Calendar theCalender = Calendar.getInstance();
        Random aRandomNumber = new Random(theCalender.getTimeInMillis());

        if((aRandomNumber.nextInt()) % NumberOfSentencesIKnow == 0)
            WelcomingTexts = "Hello, How can i help you?";
        if(aRandomNumber.nextInt() % NumberOfSentencesIKnow == 1)
            WelcomingTexts = "Hope your day is fine.\nWhat can i do for you?";
        if(aRandomNumber.nextInt() % NumberOfSentencesIKnow == 2)
            WelcomingTexts = "Yes, I'm awake, What is it you need?";
        if(aRandomNumber.nextInt() % NumberOfSentencesIKnow == 3)
            WelcomingTexts = "JARVIS? No, it's just me. \nWhat can i help you with?";
        if(aRandomNumber.nextInt() % NumberOfSentencesIKnow == 4)
            WelcomingTexts = "Tell me my son, you can trust me";
        if(aRandomNumber.nextInt() % NumberOfSentencesIKnow == 5)
            WelcomingTexts = "No shame commanding your home\nCome on do it.";


        return WelcomingTexts;
    }


    public void Read(String theText){


    }


    private void CreateMyEnteringSpeech(){

        LinearLayout newLayout = new LinearLayout(theMainContext);
        newLayout.setOrientation(LinearLayout.HORIZONTAL);


        //First I need to create the profile image
        ImageView botImage = CreateBotDP(theMainContext);
        newLayout.addView(botImage);

        // ---------- Then I will create the triangle ------------//

        ImageView theTriangle = CreateBotArrow(theMainContext);
        newLayout.addView(theTriangle);


        // Create a TextView programmatically.

        TextView mySpeech = CreateBotTextView(theMainContext);
        newLayout.addView(mySpeech);

        // Add newly created TextView to parent view group (RelativeLayout)

        newLayout.setId(R.id.layout1);
        theLayout.addView(newLayout);

    }

    private ImageView CreateBotDP(Context context){


        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, context.getResources().getDisplayMetrics());
        int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, context.getResources().getDisplayMetrics());


        ImageView botImage = new ImageView(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, height);
        layoutParams.setMargins(0,0,0,0);
        layoutParams.weight = 3;

        botImage.setLayoutParams(layoutParams);

        botImage.setImageResource(R.mipmap.logopng);
        return botImage;
    }
    private ImageView CreateBotArrow(Context context){

        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, context.getResources().getDisplayMetrics());
        int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, context.getResources().getDisplayMetrics());
        int marginLeft = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, context.getResources().getDisplayMetrics());


        LinearLayout.LayoutParams ArrowLayout = new LinearLayout.LayoutParams(width, height);
        ArrowLayout.setMargins(marginLeft, 10,-15,10);
        ArrowLayout.weight = 1;

        ImageView theTriangle = new ImageView(context);
        theTriangle.setLayoutParams(ArrowLayout);

        theTriangle.setImageResource(R.drawable.arrow_bg2);

        return theTriangle;
    }
    private TextView CreateBotTextView(Context context){

        TextView mySpeech = new TextView(context);
        LinearLayout.LayoutParams Parameters = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, // Width of TextView
                LinearLayout.LayoutParams.WRAP_CONTENT);

        Parameters.weight = 6;
        Parameters.setMargins(0,10,0,0);

        // Apply the layout parameters to TextView widget
        mySpeech.setLayoutParams(Parameters);
        // Set text to display in TextView
        mySpeech.setText(HelloText());
        // Set a text color for TextView text
        mySpeech.setTextColor(Color.parseColor("#000000"));
        mySpeech.setBackgroundResource(R.drawable.rounded_corner_alternative);
        mySpeech.setPadding(40,40,40,40);

        return mySpeech;
    }

}
