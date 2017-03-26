package com.ndusenior.fix;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class MainActivity extends AppCompatActivity {

    private String Email;
    private String Password;
    public String SettingsXML;
    public String OutputXML;
    private MyFTPClientFunctions ftpclient;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Getting extras from sign in
        Email = getIntent().getExtras().getString("Email");
        Password = getIntent().getExtras().getString("Password");

        ftpclient = new MyFTPClientFunctions();


        GetLatestFiles();
    }

    public void GetLatestFiles()
    {


        new Thread(new Runnable() {
            public void run() {

                boolean successStatus =false;
                successStatus = ftpclient.ftpConnect("ftp.bodirectors.com", Email + "@bodirectors.com", Password, 21);

                if (successStatus == true) {
                   // Toast.makeText(getApplicationContext(), "Logged In Successfully", Toast.LENGTH_LONG).show();

                    File rootFolder = new File(Environment.getExternalStorageDirectory(),"FIX");
                    SettingsXML = rootFolder.getAbsolutePath() + "/SettingsData.xml";
                    OutputXML = rootFolder.getAbsolutePath() + "/OutputNames.xml";


                   successStatus = ftpclient.ftpDownload("SettingsData.xml", SettingsXML);
                    successStatus = ftpclient.ftpDownload("OutputNames.xml", OutputXML);
                   // Toast.makeText(getApplicationContext(), "Latest Data Retrieved", Toast.LENGTH_LONG).show();

                    ftpclient.ftpDisconnect();

                } else
                    Toast.makeText(getApplicationContext(), "Logged In Failed. Data Might not be the latest", Toast.LENGTH_LONG).show();
            }
        }).start();

    }
}
