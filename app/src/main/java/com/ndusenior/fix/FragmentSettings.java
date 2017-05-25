package com.ndusenior.fix;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.ndusenior.fix.XMLRelated.SettingsQueries;

import org.w3c.dom.Text;

import java.io.File;

/**
 * Created by antho on 3/29/2017.
 */

public class FragmentSettings extends Fragment {

    private EditText[] editTextArray;

    private TextView theTempText;

    private Switch theUnit;
    private SettingsQueries theSettings;

    public static final String EMAIL_KEY = "email";
    public static final String PASSWORD_KEY = "password";

    public String Email;
    public String Password;


    private Button theSaveBtn;

    File rootFolder = new File(Environment.getExternalStorageDirectory(),"FIX");
    String SettingsXML= rootFolder.getAbsolutePath() + "/SettingsData.xml";

    private MyFTPClientFunctions theFTP = null;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        getActivity().setTitle("Settings");

        editTextArray = new EditText[] {
                (EditText) getView().findViewById(R.id.AccountNameText),
                (EditText) getView().findViewById(R.id.DisplayNameText),
                (EditText) getView().findViewById(R.id.CountryNameText),
                (EditText) getView().findViewById(R.id.AssistantNameText)};

        editTextArray[0].setEnabled(false);

        theFTP = new MyFTPClientFunctions();
        Bundle bundle = getArguments();

        Email = bundle.getString(EMAIL_KEY);
        Password = bundle.getString(PASSWORD_KEY);


        theUnit = (Switch) getView().findViewById(R.id.UnitSwitch);
        theTempText = (TextView) getView().findViewById(R.id.TempUnitText);
        theSaveBtn = (Button)  getView().findViewById(R.id.saveBtn);

        theSettings = new SettingsQueries(Email + "@bodirectors.com",Password);
        theSettings.LoadOutputXml(editTextArray , theUnit, theTempText);




        theUnit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (theUnit.isChecked())
                    theTempText.setText(theUnit.getTextOn());
                else
                    theTempText.setText(theUnit.getTextOff());

            }

        });

        theSaveBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
              Message.showToast("Settings Updated",getActivity());

                theSettings.SaveSettings(editTextArray, theUnit);
                uploadIt();

            }

        });



    }


    public void uploadIt(){

        // MyFTPClientFunctions is an open source class created by Tejas Jasani
        // available for public use and sale/resale of any product developed using such work
        // with licensing available for free with credits

        new Thread(new Runnable() {
            public void run() {
                boolean uploadStatus = false;

                uploadStatus = theFTP.ftpConnect("ftp.bodirectors.com", Email+ "@bodirectors.com",
                        Password, 21);

                if (uploadStatus == true) {
                    theFTP.ftpUpload(SettingsXML, "SettingsData.xml", "/", getContext());
                    theFTP.ftpDisconnect();
                    // Toast.makeText(getContext(),"Files Updated", Toast.LENGTH_LONG).show();
                }

            }
        }).start();

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_settings,container,false);
    }
}
