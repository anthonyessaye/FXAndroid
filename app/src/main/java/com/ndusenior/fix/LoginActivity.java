package com.ndusenior.fix;


import android.Manifest;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.ndusenior.fix.SQLite.CredentialDbAdapter;
import com.ndusenior.fix.XMLRelated.XMLparsing;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Array;
import java.sql.Time;

import static android.widget.Toast.LENGTH_LONG;

public class LoginActivity extends AppCompatActivity {


    final private int REQUEST_CODE_ASK_PERMISSIONS = 1;
    private static final String TAG = "";
    private EditText EmailText;
    private EditText PasswordText;
    private Button LoginButton;


    public String SettingsXML;
    public String OutputXML;
    public String profileImage;

    private MyFTPClientFunctions ftpclient = null;

    private File rootFolder;
    public String UserData;
    private CheckBox shouldIRemember;

    private CredentialDbAdapter theDB;
    private boolean didILogin = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        CheckForPermissions();

        EmailText = (EditText) findViewById(R.id.EmailText);
        PasswordText = (EditText) findViewById(R.id.PasswordText);
        LoginButton = (Button) findViewById(R.id.LoginBtn);

        rootFolder = new File(Environment.getExternalStorageDirectory(), "FIX");
        UserData = rootFolder.getAbsolutePath() + "/UserData.xml";
        shouldIRemember = (CheckBox) findViewById(R.id.RememberCheckBox);


        theDB = new CredentialDbAdapter(this);

        ViewData(EmailText, PasswordText, shouldIRemember);
        ftpclient = new MyFTPClientFunctions();


        createBasicFiles();
        LoginClickListener();
    }


    public void goforIt() {

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
                    didILogin = true;


                    if (shouldIRemember.isChecked())
                        RememberUser(EmailText.getText().toString(), PasswordText.getText().toString());
                    if (shouldIRemember.isChecked() == false)
                        RememberUser("", "");




                    Intent LoggedIn = new Intent(getApplication(), UserActivity.class);



                    LoggedIn.putExtra("Email", EmailText.getText().toString())
                            .putExtra("Password", PasswordText.getText().toString());

                    GetLatestFiles();
                    startActivity(LoggedIn);


                }

                else {
                     com.ndusenior.fix.Message.showToast("Login Failed. Check Username and Password", LoginActivity.this);
                    didILogin = true;

                }

            }
        }).start();

    }


    public void GetLatestFiles() {

        new Thread(new Runnable() {
            public void run() {

                boolean successStatus = false;

                File rootFolder = new File(Environment.getExternalStorageDirectory(), "FIX");


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

    public void LoginClickListener() {
        LoginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                LoginButton.setEnabled(false);
                EmailText.setEnabled(false);
                PasswordText.setEnabled(false);
                shouldIRemember.setEnabled(false);
                goforIt();


                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            // Keep checking for statuses every one second
                            if(didILogin){
                                LoginButton.setEnabled(true);
                                EmailText.setEnabled(true);
                                PasswordText.setEnabled(true);
                                shouldIRemember.setEnabled(true);
                            }

                        }
                    }, 1000);




            }

        });
    }


    public void createBasicFiles() {

        File rootFolder = new File(Environment.getExternalStorageDirectory(), "FIX");
        if (!rootFolder.exists()) {
            rootFolder.mkdirs();

            Toast.makeText(this, "Main Folder Created", LENGTH_LONG).show();
        }

    }

    private void RememberUser(String theEmail, String thePassword) {
        theDB.insertData(theEmail, thePassword);

    }

    private void UpdateData(String theEmail, String thePassword) {
        theDB.updateName(theEmail, thePassword);

    }

    public void ViewData(EditText theEmail, EditText thePassword, CheckBox rememberState) {
        String[] data = theDB.getData();


        if (data[0] != null) {
            rememberState.setChecked(true);
            theEmail.setText(data[0]);
            thePassword.setText(data[1]);

        } else
            rememberState.setChecked(false);


    }



    private void CheckForPermissions() {
        int hasStoragePermissions = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);

        if (hasStoragePermissions != PackageManager.PERMISSION_GRANTED) {
            if (!shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                showMessageOKCancel("Some Permissions Are Needed",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE,
                                                Manifest.permission.ACCESS_COARSE_LOCATION},
                                        REQUEST_CODE_ASK_PERMISSIONS);


                            }
                        });
                return;
            }

        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(LoginActivity.this)
                .setMessage(message)
                .setPositiveButton("Ok", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(LoginActivity.this, "Permission Granted", Toast.LENGTH_SHORT)
                            .show();
                    // Permission Granted
                } else {
                    // Permission Denied
                    Toast.makeText(LoginActivity.this, "Permission Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


@Override
    public void onResume(){
    super.onResume();

    didILogin = false;
    LoginButton.setEnabled(true);
    EmailText.setEnabled(true);
    PasswordText.setEnabled(true);
    shouldIRemember.setEnabled(true);
}


}






