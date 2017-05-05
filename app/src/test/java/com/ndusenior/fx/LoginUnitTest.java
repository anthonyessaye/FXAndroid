package com.ndusenior.fx;

import android.os.Environment;

import com.ndusenior.fix.MyFTPClientFunctions;

import org.junit.Test;

import java.io.File;
import java.util.Set;
import java.util.regex.Pattern;

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



}
