package com.ndusenior.fix;


import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.ndusenior.fix.XMLRelated.OutputQueries;
import com.ndusenior.fix.XMLRelated.XMLparsing;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Delayed;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

/**
 * Created by antho on 3/28/2017.
 */

public class ControlsMenu extends Fragment  {

    EditText[] editTextArray;
    ToggleButton[] toggleButtons;


    File rootFolder = new File(Environment.getExternalStorageDirectory(),"FIX");
    String OutputXML= rootFolder.getAbsolutePath() + "/OutputNames.xml";


    private Button saveBtn;
    private MyFTPClientFunctions theFTP = null;

    public static final String EMAIL_KEY = "email";
    public static final String PASSWORD_KEY = "password";

    // XML node keys
    private String KEY_OUTPUT= "Output"; // parent node
    private String KEY_NAME = "name";
    private String KEY_ID= "id";
    private String KEY_STATUS = "status";

    private String Email;
    private String Password;

    private TextView statusText;

    private OutputQueries theOutputs;



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Main Device");

        editTextArray = new EditText[] {
                (EditText) getView().findViewById(R.id.OutputOneName),
                (EditText) getView().findViewById(R.id.OutputTwoName),
                (EditText) getView().findViewById(R.id.OutputThreeName),
                (EditText) getView().findViewById(R.id.OutputFourName)};
        toggleButtons = new ToggleButton[] {
                (ToggleButton) getView().findViewById(R.id.OutputOneToggle),
                (ToggleButton) getView().findViewById(R.id.OutputTwoToggle),
                (ToggleButton) getView().findViewById(R.id.OutputThreeToggle),
                (ToggleButton) getView().findViewById(R.id.OutputFourToggle)};


        statusText = (TextView) getView().findViewById(R.id.statusText);
        statusText.setText("Getting Latest Files");

        theOutputs = new OutputQueries(Email,Password);
        theOutputs.LoadOutputXml(toggleButtons,editTextArray,statusText);


        onButtonToggles();
        onTextChanges();



    }







 public void SaveButtonListener(){
     saveBtn.setOnClickListener(new View.OnClickListener() {

         @Override
         public void onClick(View v) {


             for(int i = 0; i< editTextArray.length; i++) {
                 theOutputs.SetOutputs(OutputXML, KEY_NAME, KEY_OUTPUT, i, editTextArray[i].getText().toString());
                 theOutputs.SetOutputs(OutputXML, KEY_STATUS, KEY_OUTPUT, i, toggleButtons[i].getText().toString());
             }
             uploadIt();

             Message.showToast("Files Uploaded",getActivity());




         }

     });
}
public void onTextChanges(){
    editTextArray[0].setOnFocusChangeListener(new View.OnFocusChangeListener() {

        @Override
        public void onFocusChange(View v, boolean hasFocus) {

            if(!hasFocus){
                //this if condition is true when edittext lost focus...
                //check here for number is larger than 10 or not

                theOutputs.SetOutputs(OutputXML, KEY_NAME, KEY_OUTPUT, 0, editTextArray[0].getText().toString());
                uploadIt();
                Message.showToast("Name Updated",getActivity());
            }
        }
    });

    editTextArray[1].setOnFocusChangeListener(new View.OnFocusChangeListener() {

        @Override
        public void onFocusChange(View v, boolean hasFocus) {

            if(!hasFocus){
                //this if condition is true when edittext lost focus...
                //check here for number is larger than 10 or not

                theOutputs.SetOutputs(OutputXML, KEY_NAME, KEY_OUTPUT, 1, editTextArray[1].getText().toString());
                uploadIt();
                Message.showToast("Name Updated",getActivity());
            }
        }
    });

    editTextArray[2].setOnFocusChangeListener(new View.OnFocusChangeListener() {

        @Override
        public void onFocusChange(View v, boolean hasFocus) {

            if(!hasFocus){
                //this if condition is true when edittext lost focus...
                //check here for number is larger than 10 or not

                theOutputs.SetOutputs(OutputXML, KEY_NAME, KEY_OUTPUT, 2, editTextArray[2].getText().toString());
                uploadIt();
                Message.showToast("Name Updated",getActivity());
            }
        }
    });

    editTextArray[3].setOnFocusChangeListener(new View.OnFocusChangeListener() {

        @Override
        public void onFocusChange(View v, boolean hasFocus) {

            if(!hasFocus){
                //this if condition is true when edittext lost focus...
                //check here for number is larger than 10 or not

                theOutputs.SetOutputs(OutputXML, KEY_NAME, KEY_OUTPUT, 3, editTextArray[3].getText().toString());
                uploadIt();
                Message.showToast("Name Updated",getActivity());
            }
        }
    });

}
public void onButtonToggles(){

    toggleButtons[0].setOnClickListener(new View.OnClickListener(){

        @Override
        public void onClick(View v){

            theOutputs.SetOutputs(OutputXML, KEY_STATUS, KEY_OUTPUT, 0, toggleButtons[0].getText().toString());

            uploadIt();

            Message.showToast("Status Updated",getActivity());
        }
    });

    toggleButtons[1].setOnClickListener(new View.OnClickListener(){

        @Override
        public void onClick(View v){

            theOutputs.SetOutputs(OutputXML, KEY_NAME, KEY_OUTPUT, 1, editTextArray[1].getText().toString());
            theOutputs.SetOutputs(OutputXML, KEY_STATUS, KEY_OUTPUT, 1, toggleButtons[1].getText().toString());

            uploadIt();

            Message.showToast("Status Updated",getActivity());
        }
    });

    toggleButtons[2].setOnClickListener(new View.OnClickListener(){

        @Override
        public void onClick(View v){

            theOutputs.SetOutputs(OutputXML, KEY_NAME, KEY_OUTPUT, 2, editTextArray[2].getText().toString());
            theOutputs.SetOutputs(OutputXML, KEY_STATUS, KEY_OUTPUT, 2, toggleButtons[2].getText().toString());

            uploadIt();

            Message.showToast("Status Updated",getActivity());
        }
    });

    toggleButtons[3].setOnClickListener(new View.OnClickListener(){

        @Override
        public void onClick(View v){

            theOutputs.SetOutputs(OutputXML, KEY_NAME, KEY_OUTPUT, 3, editTextArray[3].getText().toString());
            theOutputs.SetOutputs(OutputXML, KEY_STATUS, KEY_OUTPUT, 3, toggleButtons[3].getText().toString());

            uploadIt();

            Message.showToast("Status Updated",getActivity());
        }
    });

}

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
                    theFTP.ftpUpload(OutputXML, "OutputNames.xml", "/", getContext());
                    theFTP.ftpDisconnect();
                   // Toast.makeText(getContext(),"Files Updated", Toast.LENGTH_LONG).show();
                }

            }
        }).start();

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View theView = inflater.inflate(R.layout.menu_controlsfragment,container,false);
        theFTP = new MyFTPClientFunctions();
        Bundle bundle = getArguments();

        Email = bundle.getString(EMAIL_KEY);
        Password = bundle.getString(PASSWORD_KEY);


        return theView;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {

        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        saveBtn = (Button) getView().findViewById(R.id.SaveButton);

        SaveButtonListener();
    }



}
