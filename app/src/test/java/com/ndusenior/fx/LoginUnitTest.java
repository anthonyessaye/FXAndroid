package com.ndusenior.fx;

import android.os.Environment;
import android.test.mock.MockContext;
import android.widget.Toast;

import com.ndusenior.fix.MyFTPClientFunctions;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.File;
import java.util.Set;
import java.util.regex.Pattern;

import static android.widget.Toast.LENGTH_LONG;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertThat;

/**
 * Created by antho on 5/3/2017.
 */

public class LoginUnitTest {
    MyFTPClientFunctions ftp = new MyFTPClientFunctions();

    @Test
    public void emailValidator_CorrectEmailandPassword_ReturnsTrue() {
    assertThat(ftp.ftpConnect("ftp.bodirectors.com", "test@bodirectors.com",
            "theTest.", 21), is(true));
 }

    @Test
    public void areFilesDownloadable_ReturnsTrue() {



                ftp.ftpConnect("ftp.bodirectors.com", "test@bodirectors.com",
                        "theTest.", 21);

                File rootFolder = new File(Environment.getExternalStorageDirectory(), "FIX");


                String  SettingsXML = rootFolder.getAbsolutePath() + "/SettingsData.xml";
                String OutputXML = rootFolder.getAbsolutePath() + "/OutputNames.xml";
                String profileImage = rootFolder.getAbsolutePath() + "/profile.jpg";
        assertThat(ftp.ftpDownload("SettingsData.xml", SettingsXML), is(true));
            }





    }






