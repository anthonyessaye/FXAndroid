package com.ndusenior.fix;


import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ndusenior.fix.XMLRelated.XMLparsing;

import org.json.JSONException;

import java.io.File;
import java.util.Set;

public class UserActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public String Email;
    public String Password;
    public String SettingsXML;
    public String OutputXML;
    public String profileImage;

    public static final String EMAIL_KEY = "email";
    public static final String PASSWORD_KEY = "password";


    private MyFTPClientFunctions ftpclient;

    private TextView UserText;
    private ImageView ProfilePictureDownloaded;
    private Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        toolbar =(Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("FIX");
        setSupportActionBar(toolbar);



        //Getting extras from sign in
        Email = getIntent().getExtras().getString("Email");
        Password = getIntent().getExtras().getString("Password");
        UserText = (TextView) findViewById(R.id.user);
        ProfilePictureDownloaded = (ImageView) findViewById(R.id.imageProfile);



        ftpclient = new MyFTPClientFunctions();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


       UserText.setText(Email);

        SetTheImage();

        // Code below this is just to load the first fragment. aka: fragment for home with logo
        Fragment firstBoot = new FragmentHome();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_user, firstBoot);
        ft.commit();


    }



    public void SetTheImage()
    {
        File rootFolder = new File(Environment.getExternalStorageDirectory(),"FIX");
        profileImage = rootFolder.getAbsolutePath() + "/profile.jpg";
        SettingsXML = rootFolder.getAbsolutePath() + "/SettingsData.xml";
        OutputXML = rootFolder.getAbsolutePath() + "/OutputNames.xml";
        setPicture(ProfilePictureDownloaded,profileImage);

    }

   public void setPicture(ImageView image, String pathToImage){
       image.setImageBitmap(BitmapFactory.decodeFile(pathToImage));

   }






    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



        return super.onOptionsItemSelected(item);
    }

    private void displayScreenSelected(int id) {
        Fragment newFragment = null;

        switch (id) {
            case R.id.nav_maindevice:

                newFragment = new ControlsMenu();
                Bundle theInfo = new Bundle();
                theInfo.putString(EMAIL_KEY,Email);
                theInfo.putString(PASSWORD_KEY,Password);
                newFragment.setArguments(theInfo);
                toolbar.setTitle("Main Device");

                break;
            case R.id.nav_home:
                newFragment = new FragmentHome();
                toolbar.setTitle("FIX");
                break;
            case R.id.nav_assistant:
                newFragment = new FragmentBot();
                toolbar.setTitle("Assistant");
                break;

            case R.id.nav_settings:
                newFragment = new FragmentSettings();
                toolbar.setTitle("Settings");
                break;
            case R.id.nav_component:
                newFragment = new FragmentComponent();
                toolbar.setTitle("Component");
                break;
            case R.id.nav_logout:
                finish();
                break;
        }

        if(newFragment != null){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_user, newFragment);
            ft.commit();
        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        displayScreenSelected(id);


        return true;
    }




}
