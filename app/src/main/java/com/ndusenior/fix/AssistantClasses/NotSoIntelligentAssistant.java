package com.ndusenior.fix.AssistantClasses;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.TimedText;
import android.os.Environment;
import android.text.format.Time;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ndusenior.fix.LoginActivity;
import com.ndusenior.fix.MyFTPClientFunctions;
import com.ndusenior.fix.R;
import com.ndusenior.fix.XMLRelated.OutputQueries;

import org.simpleframework.xml.convert.Convert;
import org.w3c.dom.Text;

import java.io.File;
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

    public String theAnswer = "I'm not that smart";

    // Basic Strings for the XML file
    private String KEY_OUTPUT= "Output"; // parent node
    private String KEY_NAME = "name";
    private String KEY_ID= "id";
    private String KEY_STATUS = "status";

    private RelativeLayout theLayout;
    private String Email;
    private String Password;
    private MyFTPClientFunctions theFTP;
    private OutputQueries iCanControlOutputs;

    public int Response;

    private File rootFolder = new File(Environment.getExternalStorageDirectory(),"FIX");
    private String OutputXML= rootFolder.getAbsolutePath() + "/OutputNames.xml";

    private Context theMainContext;
    private UserConversation theUser;

    private ScrollView theMessageScroll;

    private int LayoutNumber = 80000000;
    private int LayoutNumberNext;

    public NotSoIntelligentAssistant(RelativeLayout LayoutToExtend, Context context, String theEmail,
                                     String thePassword, ScrollView theScroll){
        theLayout = LayoutToExtend;
        theMainContext = context;
        Email = theEmail;
        Password = thePassword;

        theMessageScroll = theScroll;

        theFTP = new MyFTPClientFunctions();
        iCanControlOutputs = new OutputQueries(theEmail,thePassword);

        theUser = new UserConversation(theMainContext,theLayout);
        CreateMyEnteringSpeech(HelloText());
    }


    private String HelloText(){

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
    private String IAmStuuuupid(){

        Calendar theCalender = Calendar.getInstance();
        Random aRandomNumber = new Random(theCalender.getTimeInMillis());

        if((aRandomNumber.nextInt()) % NumberOfSentencesIKnow == 0)
            WelcomingTexts = "Nope I don't get that\nTry typing help";
        if(aRandomNumber.nextInt() % NumberOfSentencesIKnow == 1)
            WelcomingTexts = "My knowledge is limited.\nSay help and I will";
        if(aRandomNumber.nextInt() % NumberOfSentencesIKnow == 2)
            WelcomingTexts = "It is help you need.\nAsk me to help you.";
        if(aRandomNumber.nextInt() % NumberOfSentencesIKnow == 3)
            WelcomingTexts = "You think I'm that smart?\nNope, try help";
        if(aRandomNumber.nextInt() % NumberOfSentencesIKnow == 4)
            WelcomingTexts = "Help. Help. Help. Maybe I will";
        if(aRandomNumber.nextInt() % NumberOfSentencesIKnow == 5)
            WelcomingTexts = "Try saying that in binary.";


        return WelcomingTexts;
    }
    private String WhatIsThat(){

        String WhatThe = "Focus on the name. Genius.";
        Calendar theCalender = Calendar.getInstance();
        Random aRandomNumber = new Random(theCalender.getTimeInMillis());

        if((aRandomNumber.nextInt()) % NumberOfSentencesIKnow == 0)
            WhatThe = "What the hell is that?";
        if(aRandomNumber.nextInt() % NumberOfSentencesIKnow == 1)
            WhatThe = "I know you don't have\nsuch a device";
        if(aRandomNumber.nextInt() % NumberOfSentencesIKnow == 2)
            WhatThe = "I may not be smart, but\nI know that nothing is called that";
        if(aRandomNumber.nextInt() % NumberOfSentencesIKnow == 3)
            WhatThe = "Auto correct?";
        if(aRandomNumber.nextInt() % NumberOfSentencesIKnow == 4)
            WhatThe = "I bet you that name is wrong";
        if(aRandomNumber.nextInt() % NumberOfSentencesIKnow == 5)
            WhatThe = "Oh well, If only i knew what that is!";


        return WhatThe;
    }
    private String TurnOnOffText(String OnOff){
        String OnOffText = "Okay. " + OnOff;
        Calendar theCalender = Calendar.getInstance();
        Random aRandomNumber = new Random(theCalender.getTimeInMillis());

        if((aRandomNumber.nextInt()) % NumberOfSentencesIKnow == 0)
            OnOffText = "Yes sir I will turn that " + OnOff;
        if(aRandomNumber.nextInt() % NumberOfSentencesIKnow == 1)
            OnOffText = "I bet it's " + OnOff + " now!";
        if(aRandomNumber.nextInt() % NumberOfSentencesIKnow == 2)
            OnOffText = "Count on me to turn that " + OnOff;
        if(aRandomNumber.nextInt() % NumberOfSentencesIKnow == 3)
            OnOffText = "You have to trust me that\n this is " + OnOff;
        if(aRandomNumber.nextInt() % NumberOfSentencesIKnow == 4)
            OnOffText = "Some people get paid for that\nTurning this " + OnOff + " for FREE!";
        if(aRandomNumber.nextInt() % NumberOfSentencesIKnow == 5)
            OnOffText = OnOff.toUpperCase() + " it is";


        return OnOffText;
    }
    private String AlreadyOnOff(String AlreadyOnOff){
        String OnOffText = "This is already on";
        Calendar theCalender = Calendar.getInstance();
        Random aRandomNumber = new Random(theCalender.getTimeInMillis());

        if((aRandomNumber.nextInt()) % NumberOfSentencesIKnow == 0)
            OnOffText = "But,but.. This is already " + AlreadyOnOff;
        if(aRandomNumber.nextInt() % NumberOfSentencesIKnow == 1)
            OnOffText = "I think it's " + AlreadyOnOff ;
        if(aRandomNumber.nextInt() % NumberOfSentencesIKnow == 2)
            OnOffText = "You don't need my help. It was " + AlreadyOnOff;
        if(aRandomNumber.nextInt() % NumberOfSentencesIKnow == 3)
            OnOffText = "Guess you didn't realise\n It is already " + AlreadyOnOff;
        if(aRandomNumber.nextInt() % NumberOfSentencesIKnow == 4)
            OnOffText = "No need for that. Already " + AlreadyOnOff ;
        if(aRandomNumber.nextInt() % NumberOfSentencesIKnow == 5)
            OnOffText = AlreadyOnOff.toUpperCase() + " " + AlreadyOnOff.toUpperCase() +" "+ AlreadyOnOff.toUpperCase()
                        + ". That's how it is";


        return OnOffText;
    }


    //Function Read is responsible to create the user class and create the user speech
    // then it passes the text to function TryToUnderstand()
    public void Read(String theText){

        LayoutNumber = theUser.CreateUserSpeech(LayoutNumber,theText);
        TryToUnderStand(theText);
        theMessageScroll.fullScroll(ScrollView.FOCUS_DOWN);


    }


    //Function TryToUnderstand is responsible for splitting the given sentence
    // and try to study different elements of the sentence and make use of predefined variables
    // then it will pass the string to function CreateMySpeech and other functions depending on what is understood
    private void TryToUnderStand(String splitText){


            boolean theTest = false;

            LayoutNumberNext = LayoutNumber + 1;
            if (splitText.contains("Hey") || splitText.contains("Hello") ||
                    splitText.contains("hello") || splitText.contains("hey") ||
                        splitText.contains("Hi") || splitText.contains("hi"))
            {
                CreateMySpeech(LayoutNumberNext,HelloText());
                Response = 0;
                theTest = true;
            }

            else if (splitText.contains("turn") || splitText.contains("switch")
                        || splitText.contains("Turn") || splitText.contains("Switch")
                         || splitText.contains("Power") || splitText.contains("power"))
            {
                CreateMySpeech(LayoutNumberNext,iShouldTurnSomething(splitText));
                theTest = true;
            }

            else if(splitText.contains("Help") || splitText.contains("help"))
            {
                CreateMySpeech(LayoutNumberNext,"So far i can only welcome you\n and turn things on and off");
                theTest=true;
            }

            if (theTest == false)
                CreateMySpeech(LayoutNumberNext,IAmStuuuupid());



    }

    //iShouldTurnSomething() is a function i created to receive a text that contains
    // some predefined words such as "turn" "power" etc... it then goes on to check
    // what status it should move on.

    //iCanControlOutputs is of type OutputQueries(), a class that can read XML values and manipulate them
    private String iShouldTurnSomething(String somethingIShouldDo){

        iCanControlOutputs.resetBooleanValues();

        if(somethingIShouldDo.contains("On") || somethingIShouldDo.contains("on")) {

            iCanControlOutputs.LoadOutputXmlWithNoGui(somethingIShouldDo,"On");

            if(iCanControlOutputs.isOutputNameAvailable == true && iCanControlOutputs.OutputStatus == true)
                return AlreadyOnOff("on");
            else if(iCanControlOutputs.isOutputNameAvailable == true && iCanControlOutputs.OutputStatus == false) {
                iCanControlOutputs.SetOutputs(OutputXML,KEY_STATUS,  KEY_OUTPUT,iCanControlOutputs.itsPosition,"On");
                uploadIt();
                return TurnOnOffText("on");

            }
            else  if(somethingIShouldDo.contains("all") || somethingIShouldDo.contains("All") ||
                    somethingIShouldDo.contains("everything") || somethingIShouldDo.contains("Everything")){
                for (int i = 0; i<4;i++)
                {
                    iCanControlOutputs.SetOutputs(OutputXML,KEY_STATUS,  KEY_OUTPUT,i,"On");
                }
                uploadIt();
                return "Everthing is on now";
            }
            else if (iCanControlOutputs.isOutputNameAvailable == false)
                return WhatIsThat();
        }

        if(somethingIShouldDo.contains("Off") || somethingIShouldDo.contains("off")) {

            iCanControlOutputs.LoadOutputXmlWithNoGui(somethingIShouldDo,"Off");

            if(iCanControlOutputs.isOutputNameAvailable == true && iCanControlOutputs.OutputStatus == true)
                return AlreadyOnOff("off");
            else if(iCanControlOutputs.isOutputNameAvailable == true && iCanControlOutputs.OutputStatus == false) {
                iCanControlOutputs.SetOutputs(OutputXML,KEY_STATUS,  KEY_OUTPUT,iCanControlOutputs.itsPosition,"Off");
                uploadIt();
                return TurnOnOffText("off");
            }
            else  if(somethingIShouldDo.contains("all") || somethingIShouldDo.contains("All") ||
                    somethingIShouldDo.contains("everything") || somethingIShouldDo.contains("Everything")){
                for (int i = 0; i<4;i++)
                {
                    iCanControlOutputs.SetOutputs(OutputXML,KEY_STATUS,  KEY_OUTPUT,i,"Off");
                }
                uploadIt();
                return "Everthing is off now";
            }

            else if (iCanControlOutputs.isOutputNameAvailable == false)
                return WhatIsThat();
        }




        return "I dont get what i should change";

    }


// The next 5 functions are related to manipulating the UI dynamically. For the bot
    // for user manipulation check UserConversation.class

    private void CreateMyEnteringSpeech(String theText){

        LinearLayout newLayout = new LinearLayout(theMainContext);
        newLayout.setOrientation(LinearLayout.HORIZONTAL);




        //First I need to create the profile image
        ImageView botImage = CreateBotDP(theMainContext);
        newLayout.addView(botImage);

        // ---------- Then I will create the triangle ------------//

        ImageView theTriangle = CreateBotArrow(theMainContext);
        newLayout.addView(theTriangle);


        // Create a TextView programmatically.

        TextView mySpeech = CreateBotTextView(theMainContext, theText);
        newLayout.addView(mySpeech);

        // Add newly created TextView to parent view group (RelativeLayout)

        newLayout.setId(LayoutNumber);
        theLayout.addView(newLayout);




    }
    public void CreateMySpeech(int LayoutNumberYouAreOn, String theText){


        LinearLayout newLayout = new LinearLayout(theMainContext);
        newLayout.setOrientation(LinearLayout.HORIZONTAL);


        RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);


        p.addRule(RelativeLayout.BELOW,LayoutNumber);



        //lastly I need to create the profile image
        ImageView userImage = CreateBotDP(theMainContext);
        newLayout.addView(userImage);

        // ---------- Then I will create the triangle ------------//

        ImageView theTriangle = CreateBotArrow(theMainContext);
        newLayout.addView(theTriangle);

        // Create a TextView programmatically.

        TextView userSpeech = CreateBotTextView(theMainContext,theText);
        userSpeech.setText(theText);
        newLayout.addView(userSpeech);







        LayoutNumber = LayoutNumber+1;
        // Add newly created TextView to parent view group (RelativeLayout)

        newLayout.setId(LayoutNumber);
        newLayout.setLayoutParams(p);
        newLayout.setPadding(0,30,0,0);

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
    private TextView CreateBotTextView(Context context, String theText){

        TextView mySpeech = new TextView(context);
        LinearLayout.LayoutParams Parameters = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, // Width of TextView
                LinearLayout.LayoutParams.WRAP_CONTENT);

        Parameters.weight = 6;
        Parameters.setMargins(0,10,0,0);

        // Apply the layout parameters to TextView widget
        mySpeech.setLayoutParams(Parameters);
        // Set text to display in TextView
        mySpeech.setText(theText);
        // Set a text color for TextView text
        mySpeech.setTextColor(Color.parseColor("#000000"));
        mySpeech.setBackgroundResource(R.drawable.rounded_corner_alternative);
        mySpeech.setPadding(40,40,40,40);

        return mySpeech;
    }


    // uploadIt() is a basic function that logins to the FTP server and uploads file :)
    public void uploadIt(){

        // MyFTPClientFunctions is an open source class created by Tejas Jasani
        // available for public use and sale/resale of any product developed using such work
        // with licensing available for free with credits

        new Thread(new Runnable() {
            public void run() {
                boolean status = false;

                boolean uploadStatus;

                uploadStatus = theFTP.ftpConnect("ftp.bodirectors.com", Email + "@bodirectors.com",
                        Password, 21);

                if (uploadStatus == true) {
                    theFTP.ftpUpload(OutputXML, "OutputNames.xml", "/", theMainContext);
                    theFTP.ftpDisconnect();
                    // Toast.makeText(getContext(),"Files Updated", Toast.LENGTH_LONG).show();
                }

            }
        }).start();

    }


}
