package com.ndusenior.fix;


import android.app.Application;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Time;

public class LoginActivity extends AppCompatActivity {


    private static final String TAG = "" ;
    private EditText EmailText;
    private EditText PasswordText;
    private Button LoginButton;

    public String SettingsXML;
    public String OutputXML;
    public String profileImage;

    private MyFTPClientFunctions ftpclient = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        EmailText = (EditText) findViewById(R.id.EmailText);
        PasswordText = (EditText) findViewById(R.id.PasswordText);
        LoginButton = (Button) findViewById(R.id.LoginBtn);

        ftpclient = new MyFTPClientFunctions();



        createBasicFiles();
        LoginClickListener();
    }


    public void goforIt(){

        // MyFTPClientFunctions is an open source class created by Tejas Jasani
        // available for public use and sale/resale of any product developed using such work
        // with licensing available for free with credits

        new Thread(new Runnable() {
            public void run() {
                boolean status = false;


                    status = ftpclient.ftpConnect("ftp.bodirectors.com", EmailText.getText().toString() + "@bodirectors.com",
                            PasswordText.getText().toString(), 21);

                    if (status == true) {
                        Log.d(TAG, "Connection Success");


                        Intent LoggedIn = new Intent(getApplication(), UserActivity.class);
                        LoggedIn.putExtra("Email", EmailText.getText().toString())
                                .putExtra("Password", PasswordText.getText().toString());

                        GetLatestFiles();
                        startActivity(LoggedIn);


                    }
                else {
                        showToast("Login Failed. Check Username and Password");
                    }

            }
        }).start();

    }

    public void GetLatestFiles()
    {


        new Thread(new Runnable() {
            public void run() {

                boolean successStatus =false;

                    File rootFolder = new File(Environment.getExternalStorageDirectory(),"FIX");
                    SettingsXML = rootFolder.getAbsolutePath() + "/SettingsData.xml";
                    OutputXML = rootFolder.getAbsolutePath() + "/OutputNames.xml";
                    profileImage = rootFolder.getAbsolutePath() + "/profile.jpg";

                    successStatus = ftpclient.ftpDownload("SettingsData.xml", SettingsXML);
                    successStatus = ftpclient.ftpDownload("OutputNames.xml", OutputXML);
                    successStatus = ftpclient.ftpDownload("profile.jpg", profileImage);

                    ftpclient.ftpDisconnect();


            }
        }).start();

    }

   public void LoginClickListener()
   {
       LoginButton.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View arg0) {

               goforIt();

           }

       });
   }


    public void createBasicFiles() {

        try {
            File rootFolder = new File(Environment.getExternalStorageDirectory(),
                    "FIX");
            if (!rootFolder.exists()) {
                rootFolder.mkdirs();
            }
            File settingsfile = new File(rootFolder, "SettingsData.xml");
            FileWriter writer = new FileWriter(settingsfile);
            writer.append("File Creation");
            writer.flush();
            writer.close();

            File outputsfile = new File(rootFolder, "OutputNames.xml");
            FileWriter outputs = new FileWriter(outputsfile);
            outputs.append("File Creation");
            outputs.flush();
            outputs.close();

            Toast.makeText(this, "Saved : " + rootFolder.getAbsolutePath(),
                    Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showToast(final String toast)
    {
        runOnUiThread(new Runnable() {
            public void run()
            {
                Toast.makeText(LoginActivity.this, toast, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
